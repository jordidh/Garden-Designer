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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CropCreationDialog {
	final DialogBox dialogBox;
	
	public static PlantEntity SelectedPlant = null;
	public static int NumPlants = 1;
	
	public CropCreationDialog()
	{
		//List box
		final ListBox cropPlant = new ListBox();
		for(int i=0; i<GardenDesigner.m_Plants.length(); i++)
		{
		    cropPlant.addItem(GardenDesigner.m_Plants.get(i).getName(), HasDirection.Direction.DEFAULT, Integer.toString(GardenDesigner.m_Plants.get(i).getId()));
		}
	    cropPlant.setVisibleItemCount(1);

	    //Number plants text box
		final TextBox txtNumPlants = new TextBox();
		txtNumPlants.setMaxLength(2);
		txtNumPlants.setValue("1");
		
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
				SelectedPlant = null;
				for(int i=0; i<GardenDesigner.m_Plants.length(); i++)
				{
					if (GardenDesigner.m_Plants.get(i).getId() == Integer.parseInt(cropPlant.getValue(cropPlant.getSelectedIndex())))
					{
						SelectedPlant = GardenDesigner.m_Plants.get(i);
						break;
					}
				}
				
				NumPlants = 1;
				try
				{
					NumPlants = Integer.parseInt(txtNumPlants.getValue());
					GardenDesigner.m_SelectedAction = GardenAction.NEW_CROP;
				}
				catch(Exception ex)
				{
					Window.alert(ex.getMessage());
					GardenDesigner.m_SelectedAction = GardenAction.NONE;
				}
				
				
				dialogBox.hide();
			}
		});

		//Horizontal panel
		final HorizontalPanel dialogHPanel = new HorizontalPanel();
		dialogHPanel.add(cancelButton);
		dialogHPanel.add(acceptButton);

		//Vertical panel
		final VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Select the plant:</b>"));
		dialogVPanel.add(cropPlant);
		dialogVPanel.add(new HTML("<br><b>Set the number of plants:</b>"));
		dialogVPanel.add(txtNumPlants);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(dialogHPanel);
				
		//dialogBog
		dialogBox = new DialogBox();
		dialogBox.setModal(true);
		dialogBox.setText("New Crop");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setWidget(dialogVPanel);
	}
	
	public void ShowDialog(){
		//dialogBox.setText("Load RPC - Failure");
		dialogBox.center();
	}
}
