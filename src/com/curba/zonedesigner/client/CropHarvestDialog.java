package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CropHarvestDialog {
	final DialogBox dialogBox;
	
	private CropGraphic m_crop = null;
	private ActionTypeEntity m_type = null;
	
	final TextArea txtDescription = new TextArea();
	final TextBox txtQuantityA = new TextBox();
	final TextBox txtQuantityB = new TextBox();
	
	public CropHarvestDialog(CropGraphic c, ActionTypeEntity type)
	{
		m_crop = c;
		m_type = type;
		
	    //Number plants text box
		txtQuantityA.setMaxLength(10);
		txtQuantityA.setValue("0");
		
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
				try
				{
					ActionEntity a = new ActionEntity(m_type, m_crop, txtDescription.getValue(), Float.parseFloat(txtQuantityA.getValue()), Float.parseFloat(txtQuantityB.getValue()));
					m_crop.getGarden().getActions().add(a);
					
					GardenDesigner.m_SelectedAction = GardenAction.NONE;
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
		dialogVPanel.add(new HTML("<b>Action description:</b>"));
		dialogVPanel.add(txtDescription);
		dialogVPanel.add(new HTML("<br><b>Quantity A:</b>"));
		dialogVPanel.add(txtQuantityA);
		dialogVPanel.add(new HTML("<br><b>Quantity B:</b>"));
		dialogVPanel.add(txtQuantityB);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(dialogHPanel);
				
		//dialogBog
		dialogBox = new DialogBox();
		dialogBox.setModal(true);
		dialogBox.setText("Action");
		dialogBox.setAnimationEnabled(true);
		dialogBox.setWidget(dialogVPanel);
	}
	
	//Shows the dialog
	//Parameters:
	public void ShowDialog(){
		txtDescription.setValue("");
		txtQuantityA.setValue("0");
		txtQuantityB.setValue("0");
		dialogBox.center();
	}
}
