package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ZoneCreationDialog {
	final DialogBox dialogBox;
	
	public static String Name;
	public static String Description;
	public static ZoneTypeEntity SelectedZoneType = null;
	public static int Width = 0;
	public static int Heigh = 0;
	public static int Depth = 0;
	
	public ZoneCreationDialog()
	{
		//Zone name
		final TextBox txtName = new TextBox();
		txtName.setMaxLength(255);
		txtName.setValue("");
		
		//Zone description
		final TextArea txtDescription = new TextArea();
		txtDescription.setCharacterWidth(40);
		txtDescription.setVisibleLines(6);
		
		//List box
		final ListBox zoneType = new ListBox();
		for(int i=0; i<GardenDesigner.m_ZoneTypes.length(); i++)
		{
			zoneType.addItem(GardenDesigner.m_ZoneTypes.get(i).getName(), HasDirection.Direction.DEFAULT, Integer.toString(GardenDesigner.m_ZoneTypes.get(i).getId()));
		}
		zoneType.setVisibleItemCount(1);
		
		//Zone width
		final TextBox txtWidth = new TextBox();
		txtWidth.setMaxLength(10);
		txtWidth.setValue("1000");
		
		//Zone height
		final TextBox txtHeigh = new TextBox();
		txtHeigh.setMaxLength(10);
		txtHeigh.setValue("1000");
		
		//Zone depth
		final TextBox txtDepth = new TextBox();
		txtDepth.setMaxLength(10);
		txtDepth.setValue("200");

		//Cancel Button
		final Button cancelButton = new Button("Cancel");
		cancelButton.getElement().setId("cancelButton");
		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GardenDesigner.m_SelectedAction = GardenAction.NONE;
				dialogBox.hide();
			}
		});		
		
		//Accept Button
		final Button acceptButton = new Button("Accept");
		acceptButton.getElement().setId("acceptButton");
		acceptButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				SelectedZoneType = null;
				for(int i=0; i<GardenDesigner.m_Plants.length(); i++)
				{
					if (GardenDesigner.m_ZoneTypes.get(i).getId() == Integer.parseInt(zoneType.getValue(zoneType.getSelectedIndex())))
					{
						SelectedZoneType = GardenDesigner.m_ZoneTypes.get(i);
						break;
					}
				}
				if (SelectedZoneType == null) return;
				
				try
				{
					Name = txtName.getValue();
					Description = txtDescription.getValue();
					Width = Integer.parseInt(txtWidth.getValue());
					Heigh = Integer.parseInt(txtHeigh.getValue());
					Depth = Integer.parseInt(txtDepth.getValue());
					GardenDesigner.m_SelectedAction = GardenAction.NEW_ZONE;
					dialogBox.hide();
				}
				catch(Exception ex)
				{
					Window.alert(ex.getMessage());
				}
			}
		});

		//Horizontal panel
		final HorizontalPanel dialogHPanel = new HorizontalPanel();
		dialogHPanel.add(cancelButton);
		dialogHPanel.add(acceptButton);

		//Vertical panel
		final VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Zone name:</b>"));
		dialogVPanel.add(txtName);
		dialogVPanel.add(new HTML("<br><b>Zone description:</b>"));
		dialogVPanel.add(txtDescription);
		dialogVPanel.add(new HTML("<br><b>Select the zone type:</b>"));
		dialogVPanel.add(zoneType);
		dialogVPanel.add(new HTML("<br><b>Zone width:</b>"));
		dialogVPanel.add(txtWidth);
		dialogVPanel.add(new HTML("<br><b>Zone heigh:</b>"));
		dialogVPanel.add(txtHeigh);
		dialogVPanel.add(new HTML("<br><b>Zone depth:</b>"));
		dialogVPanel.add(txtDepth);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(dialogHPanel);
				
		//dialogBog
		dialogBox = new DialogBox();
		dialogBox.setModal(true);
		dialogBox.setText("New Zone");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setWidget(dialogVPanel);
	}
	
	public void ShowDialog(){
		//dialogBox.setText("Load RPC - Failure");
		dialogBox.center();
	}
}
