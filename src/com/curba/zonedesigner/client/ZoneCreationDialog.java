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
	private ZoneGraphic m_zone = null;
		
	public static String Name;
	public static String Description;
	public static ZoneTypeEntity SelectedZoneType = null;
	public static int Width = 0;
	public static int Height = 0;
	public static int Depth = 0;
	
	public static Boolean ResultOk = false;

	final TextBox txtName = new TextBox();
	final TextArea txtDescription = new TextArea();
	final ListBox zoneType = new ListBox();
	final TextBox txtWidth = new TextBox();
	final TextBox txtHeigh = new TextBox();
	final TextBox txtDepth = new TextBox();
	
	public ZoneCreationDialog()
	{
		ResultOk = false;
		
		Name = "";
		Description = "";
		SelectedZoneType = null;
		Width = 1000;
		Height = 1000;
		Depth = 200;
		
		//Zone name
		txtName.setMaxLength(255);
		
		//Zone description
		txtDescription.setCharacterWidth(40);
		txtDescription.setVisibleLines(6);
		
		//List box
		for(int i=0; i<GardenDesigner.m_ZoneTypes.length(); i++)
		{
			zoneType.addItem(GardenDesigner.m_ZoneTypes.get(i).getName(), HasDirection.Direction.DEFAULT, Integer.toString(GardenDesigner.m_ZoneTypes.get(i).getId()));
		}
		zoneType.setVisibleItemCount(1);
		
		//Zone width
		txtWidth.setMaxLength(10);
		
		//Zone height
		txtHeigh.setMaxLength(10);
		
		//Zone depth
		txtDepth.setMaxLength(10);

		//Cancel Button
		final Button cancelButton = new Button("Cancel");
		cancelButton.getElement().setId("cancelButton");
		cancelButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				GardenDesigner.m_SelectedAction = GardenAction.NONE;
				//ResultOk = false;
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
					if (m_zone != null)
					{
						m_zone.setName(txtName.getValue());
						m_zone.setDescription(txtDescription.getValue());
						m_zone.setEntityWidth(Integer.parseInt(txtWidth.getValue()));
						m_zone.setEntityHeight(Integer.parseInt(txtHeigh.getValue()));
						m_zone.setDepth(Integer.parseInt(txtDepth.getValue()));
						m_zone.setZoneType(SelectedZoneType);
						m_zone.getGarden().ReZoom(0);
						GardenDesigner.m_SelectedAction = GardenAction.NONE;
					}
					else
					{
						Name = txtName.getValue();
						Description = txtDescription.getValue();
						Width = Integer.parseInt(txtWidth.getValue());
						Height = Integer.parseInt(txtHeigh.getValue());
						Depth = Integer.parseInt(txtDepth.getValue());
						GardenDesigner.m_SelectedAction = GardenAction.NEW_ZONE;
					}
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
	
	//
	//Parameters:
	//-z: null for a new zone, otherwise for update the zone
	public void ShowDialog(ZoneGraphic z){
		m_zone = z;
		
		if (z != null)
		{
			txtName.setValue(z.getName());
			txtDescription.setValue(z.getDescription());
			txtWidth.setValue(Integer.toString(z.getEntityWidth()));
			txtHeigh.setValue(Integer.toString(z.getEntityHeight()));
			txtDepth.setValue(Integer.toString(z.getDepth()));
			if (z.getZoneType() != null)
			{
				for(int i=0; i<zoneType.getItemCount(); i++)
				{
					if (Integer.toString(z.getZoneType().getId()).equals(zoneType.getValue(i)))
					{
						zoneType.setSelectedIndex(i);
						break;
					}
				}
			}
		}
		else
		{
			txtName.setValue("");
			txtDescription.setValue("");
			txtWidth.setValue("500");
			txtHeigh.setValue("500");
			txtDepth.setValue("200");
		}
		
		dialogBox.center();
	}
}
