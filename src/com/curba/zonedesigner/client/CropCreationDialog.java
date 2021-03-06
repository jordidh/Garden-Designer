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
	
	private CropGraphic m_crop = null;
	
	public static PlantEntity SelectedPlant = null;
	public static int NumPlants = 1;
	
	public static Boolean ResultOk = false;

	final ListBox cropPlant = new ListBox();
	final TextBox txtNumPlants = new TextBox();
	
	public CropCreationDialog()
	{
		ResultOk = false;
		
		SelectedPlant = null;
		NumPlants = 1;
		
		
		//List box
		for(int i=0; i<GardenDesigner.m_Plants.length(); i++)
		{
		    cropPlant.addItem(GardenDesigner.m_Plants.get(i).getName(), HasDirection.Direction.DEFAULT, Integer.toString(GardenDesigner.m_Plants.get(i).getId()));
		}
	    cropPlant.setVisibleItemCount(1);

	    //Number plants text box
		txtNumPlants.setMaxLength(2);
		txtNumPlants.setValue("1");
		
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
				SelectedPlant = null;
				for(int i=0; i<GardenDesigner.m_Plants.length(); i++)
				{
					if (GardenDesigner.m_Plants.get(i).getId() == Integer.parseInt(cropPlant.getValue(cropPlant.getSelectedIndex())))
					{
						SelectedPlant = GardenDesigner.m_Plants.get(i);
						break;
					}
				}
				if (SelectedPlant == null) return;
				
				NumPlants = 1;
				try
				{
					NumPlants = Integer.parseInt(txtNumPlants.getValue());
					
					if (m_crop != null)
					{
						//Update the crop
						m_crop.setNumPlants(NumPlants);
						m_crop.setPlantEntity(SelectedPlant);
						GardenDesigner.m_SelectedAction = GardenAction.NONE;
					}
					else
					{
						GardenDesigner.m_SelectedAction = GardenAction.NEW_CROP;
					}
					
					//ResultOk = true;
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
	
	//Shows the dialog
	//Parameters:
	//-c: null to create a new crop, otherwise to update a crop
	public void ShowDialog(CropGraphic c){
		m_crop = c;
		
		if (c != null)
		{
			txtNumPlants.setValue(Integer.toString(c.getNumPlants()));
		}
		else
		{
			txtNumPlants.setValue("1");
		}
		
		if (SelectedPlant != null)
		{
			for(int i=0; i<cropPlant.getItemCount(); i++)
			{
				if (Integer.toString(SelectedPlant.getId()).equals(cropPlant.getValue(i)))
				{
					cropPlant.setSelectedIndex(i);
					break;
				}
			}
		}
		dialogBox.center();
	}
}
