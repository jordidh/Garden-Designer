package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Label;

public class CropCreationDialog {
	final DialogBox dialogBox;
	
	public CropCreationDialog()
	{
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
				GardenDesigner.m_SelectedAction = GardenAction.NEW_CROP;
				dialogBox.hide();
			}
		});

		//List box
		ListBox cropPlant = new ListBox();
		for(int i=0; i<GardenDesigner.m_Plants.length(); i++)
		{
		    cropPlant.addItem(GardenDesigner.m_Plants.get(i).getName(), HasDirection.Direction.DEFAULT, Integer.toString(GardenDesigner.m_Plants.get(i).getId()));
		}
	    cropPlant.setVisibleItemCount(1);
	    
		final Label textToServerLabel = new Label();
		textToServerLabel.setText("Hola 1");
		final HTML serverResponseLabel = new HTML();
		serverResponseLabel.setText("Hola 2");
		
		//Horizontal panel
		final HorizontalPanel dialogHPanel = new HorizontalPanel();
		dialogHPanel.add(cancelButton);
		dialogHPanel.add(acceptButton);

		//Vertical panel
		final VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Select the plant:</b>"));
		dialogVPanel.add(cropPlant);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
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
		dialogBox.setText("Load RPC - Failure");
		dialogBox.center();
	}
}
