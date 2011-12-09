package com.curba.zonedesigner.client;

import com.curba.zonedesigner.shared.ServerPlant;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.JsArray;
//import com.google.gwt.i18n.client.NumberFormat;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ZoneDesigner implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GardeningServiceAsync gardeningService = GWT
			.create(GardeningService.class);

	/**
	 * Convert the string of JSON into JavaScript object.
	 */
	private final native JsArray<GardenEntity> asArrayOfGardenEntity(String json) /*-{
		return eval(json);
	}-*/;
	  
	/**
	 * Convert the string of JSON into JavaScript object.
	 */
	private final native JsArray<ZoneEntity> asArrayOfZoneEntity(String json) /*-{
		return eval(json);
	}-*/;
	  
	/**
	 * Convert the string of JSON into JavaScript object.
	 */
	private final native JsArray<CropEntity> asArrayOfCropEntity(String json) /*-{
		return eval(json);
	}-*/;

	/**
	 * Convert the string of JSON into JavaScript object.
	 */
	private final native JsArray<PlantEntity> asArrayOfPlantEntity(String json) /*-{
		return eval(json);
	}-*/;

	/**
	 * Global variables
	 */
	JsArray<PlantEntity> m_plants = null;
	double m_ZoomFactor = 1.0;
	//Contains the index of the selected ZoneGraphic to allow his movement through the garden
	int m_SelectedZoneIndex = -1;
	//Contains the index of the selected CropGraphic to allow his movement through the zone
	int m_SelectedCropIndex = -1;
	String m_Culture = "ca";
	int m_UserId = 1;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//************************************************************
		//TEST MOVE CIRCLE
		//************************************************************
		final Label infoLabel1 = new Label();
		infoLabel1.setText("To create plants: Shift + double click in canvas");
		final Label infoLabel2 = new Label();
		infoLabel2.setText("To delete plants: Control + Double click in the plant");
		final Label infoLabel3 = new Label();
		infoLabel3.setText("To edit plants: Double click in the plant");
		final Label infoLabel4 = new Label();
		infoLabel4.setText("To move plants: MouseButtonDown + move + MouseButtonUp");
		RootPanel.get().add(infoLabel1);
		RootPanel.get().add(infoLabel2);
		RootPanel.get().add(infoLabel3);
		RootPanel.get().add(infoLabel4);
		
		final Button zoomOutButton = new Button("Zoom -");
		RootPanel.get().add(zoomOutButton);
		final Button zoomInButton = new Button("Zoom +");
		RootPanel.get().add(zoomInButton);
		final Button RESTfulButton = new Button("RESTful Get");
		RootPanel.get().add(RESTfulButton);
		final Button loadButton = new Button("Load");
		RootPanel.get().add(loadButton);
		final Button saveButton = new Button("Save");
		RootPanel.get().add(saveButton);

		// Popup dialog box
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label textToServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();	    
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
		dialogVPanel.add(textToServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);
		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
			}
		});
		
		
		//Creates and loads zone
		final Label zoneTitleLabel = new Label();	//Label to get the zoom factor
		zoneTitleLabel.setStylePrimaryName("zona_label");
		RootPanel.get().add(zoneTitleLabel);
		//final ZoneGraphic zona = new ZoneGraphic(200, 200);
		final ZoneGraphic zona = new ZoneGraphic(null, null);
		zona.setStylePrimaryName("zona");
		RootPanel.get().add(zona);
		
		/*
		//Loads zone from server
		gardeningService.getZone(1, m_Culture,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						dialogBox.setText("Load RPC - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("Error loading zone: " + caught.toString());
						dialogBox.center();
						//Window.alert("Error: " + caught.toString());
						//loadButton.setFocus(true);
						loadButton.setEnabled(true);
					}

					public void onSuccess(String result) {
						//Window.alert(result);
						
						//Process the json string: result
						JsArray<ZoneEntity> zones = asArrayOfZoneEntity(result);
						//Window.alert("Zones: " + String.valueOf(zones.length()));
						//Window.alert("Zones: " + result);
						
						for (int i = 0; i < zones.length(); i++) {
							zona.setZoneEntity(zones.get(i));
							//Resize the Zone
							zona.ZoomToOriginal();
							
							//Sets the name of the zone
							zoneTitleLabel.setWidth(zona.getWidth() + "px");
							zoneTitleLabel.setText(
									zona.getZoneEntity().getName() + ": " + 
									zona.getZoneEntity().getDescription() + ". Total volume = " +
									NumberFormat.getFormat("0.00").format(zona.getZoneEntity().getLiters()) + "liters. Total Area = " +
									NumberFormat.getFormat("0.00").format(zona.getZoneEntity().getArea()) + "m2.");
					    }
												
						//dialogBox.setText("Load RPC - Successful");
						//serverResponseLabel.addStyleName("serverResponseLabelError");
						//serverResponseLabel.setHTML("Zone and plantations drawed successfully - " + result);
						//dialogBox.center();
						
						//Window.alert("Ok: " + result);
						//loadButton.setFocus(true);
						loadButton.setEnabled(true);
					}
				});
			
		//Loads plants from server
		gardeningService.getPlants(m_Culture,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						dialogBox.setText("Load RPC - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("Error loading plants: " + caught.toString());
						dialogBox.center();
						//Window.alert("Error: " + caught.toString());
						//loadButton.setFocus(true);
						loadButton.setEnabled(true);
					}

					public void onSuccess(String result) {
						//Window.alert(result);
						
						//Process the json string: result
						m_plants = asArrayOfPlantEntity(result);
						//Window.alert("Vegetals: " + result);//String.valueOf(m_plants.length()));
					}
				});
				
		//Event to move plants in the zona
		zona.addMouseMoveHandler(new MouseMoveHandler() 
		{
			public void onMouseMove(MouseMoveEvent event) 
			{
				if (m_SelectedCropIndex >= 0)
				{
					GraphicPlantation plant = (GraphicPlantation) zona.getVectorObject(m_SelectedCropIndex);
				    plant.setX(event.getX());
				    plant.setY(event.getY());
				    plant.getPlantationData().setPuntX((int)(event.getX() * 1.0 / m_ZoomFactor));
				    plant.getPlantationData().setPuntY((int)(event.getY() * 1.0 / m_ZoomFactor));
				}
			}
		});
		
		
		//Event to create a new plant with all his events ()
		zona.addDoubleClickHandler(new DoubleClickHandler()
		{
			public void onDoubleClick(DoubleClickEvent event)
			{
				if (event.isShiftKeyDown())
				{
					// Vegetal creation dialog box
					final int clickedX = event.getX();
					final int clickedY = event.getY();
					final DialogBox vegetalDialogBox = new DialogBox();
					vegetalDialogBox.setText("Remote Procedure Call");
					vegetalDialogBox.setAnimationEnabled(true);
					final Button vegetalCloseButton = new Button("Close");
					// We can set the id of a widget by accessing its Element
					vegetalCloseButton.getElement().setId("closeButton");
					final Label vegetalLabel = new Label();
					final HTML vegetalResponseLabel = new HTML();	    
					final ListBox dropBox = new ListBox(false);
				    for (int i = 0; i < m_plants.length(); i++) {
				    	dropBox.addItem(m_plants.get(i).getName());
				    }
				    dropBox.ensureDebugId("cwListBox-dropBox");
				    VerticalPanel dropBoxPanel = new VerticalPanel();
				    dropBoxPanel.add(dropBox);
					VerticalPanel vegetalDialogVPanel = new VerticalPanel();
					vegetalDialogVPanel.addStyleName("dialogVPanel");
					vegetalDialogVPanel.add(new HTML("<b>Sending name to the server:</b>"));
					vegetalDialogVPanel.add(vegetalLabel);
					vegetalDialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
					vegetalDialogVPanel.add(vegetalResponseLabel);
					vegetalDialogVPanel.add(dropBoxPanel);
					vegetalDialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
					vegetalDialogVPanel.add(vegetalCloseButton);
					vegetalDialogBox.setWidget(vegetalDialogVPanel);
					// Add a handler to close the DialogBox
					vegetalCloseButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							//Get the vegetal selected
							PlantEntity vegetal = m_plants.get(dropBox.getSelectedIndex());
							
							//Window.alert(clickedX + ", " + clickedY + "," + vegetal.getPlantationHeight() + ", " + vegetal.getPlantationWidth());
							
							//Create a new plant
							CropGraphic plant = new CropGraphic(
									clickedX, 
									clickedY, 
									(int)(vegetal.getHeightSpacing() * 1.0 / m_ZoomFactor), 
									(int)(vegetal.getWidthSpacing() * 1.0 / m_ZoomFactor));
							//String jsonPlant = " [ { id: -1, usuari_id: 1, vegetal_id: " + vegetal.getId() + ", vegetal_nom: \"" + vegetal.getName() + "\", vegetal_descripcio: \"" + vegetal.getDescription() + "\", data_inicial_real: \"2011-01-01 00:00:00.0\", data_inicial_prevista: \"2011-01-01 00:00:00.0\", data_final_real: \"null\", data_final_prevista: \"null\", tipus_periode_cultiu_inicial: 2, zona_id: " + zona.getZoneData().getId() + ", productivitat: " + vegetal.getProductivity() + ", marc_de_plantacio_x: " + vegetal.getPlantationWidth() + ", marc_de_plantacio_y: " + vegetal.getPlantationHeight() + ", volum_de_terra: " + vegetal.getVolume() + ", punt_x: " + (int)(event.getX() * 1.0 / m_ZoomFactor) + ", punt_y: " + (int)(event.getY() * 1.0 / m_ZoomFactor) + ", num_plantes: 1}, ]";
							String jsonPlant = " [ { id: -1, usuari_id: " + m_UserId + 
												", vegetal_id: " + vegetal.getId() + 
												", data_inicial_real: \"2011-01-01 00:00:00.0\"" + 
												", data_inicial_prevista: \"2011-01-01 00:00:00.0\"" + 
												", data_final_real: \"null\"" + 
												", data_final_prevista: \"null\"" + 
												", tipus_periode_cultiu_inicial: 2" + 
												", zona_id: " + zona.getZoneEntity().getId() + 
												", punt_x: " + (int)(clickedX * 1.0 / m_ZoomFactor) + 
												", punt_y: " + (int)(clickedY * 1.0 / m_ZoomFactor) + 
												", num_plantes: " + 1 +
												", deleted: " + 0 + "}, ]";
							Window.alert(jsonPlant);
							JsArray<CropEntity> plantations = asArrayOfCropEntity(jsonPlant);
							plant.setCropEntity(plantations.get(0));
							plant.setPlantEntity(vegetal);
							//plant.setFillColor("green");
							//plant.setFillOpacity(0.5);
							plant.setHeight((int)(plant.getPlantEntity().getHeightSpacing() * m_ZoomFactor));
							plant.setWidth((int)(plant.getPlantEntity().getWidthSpacing() * m_ZoomFactor));
							//Plant event to select a plant
							plant.addMouseDownHandler(new MouseDownHandler()
							{
								public void onMouseDown(MouseDownEvent event)
								{
									zona.bringToFront((GraphicPlantation) event.getSource());
									GraphicPlantation plant = (GraphicPlantation) event.getSource();
									//lastCircleSelected.setText(plant.getFillColor());
									for(int i=0; i<zona.getVectorObjectCount(); i++)
									{
										if (zona.getVectorObject(i).equals(plant))
										{
											m_SelectedCropIndex = i;
										}
									}
								}
							});
							//Plant event to unselect a plant
							plant.addMouseUpHandler(new MouseUpHandler()
							{
								public void onMouseUp(MouseUpEvent event)
								{
									//Circle plant = (Circle) event.getSource();
									m_SelectedCropIndex = -1;
								}
							});
							//Plant event to modify or delete a plant
							plant.addDoubleClickHandler(new DoubleClickHandler()
							{
								public void onDoubleClick(DoubleClickEvent event)
								{
									GraphicPlantation plant = (GraphicPlantation) event.getSource();
									
									if (event.isControlKeyDown())
									{
										//Delete or undelete
										zona.remove(plant);
										
										//if (plant.getPlantationData().getDeleted() == 1)
										//{
										//	plant.setFillColor("green");
										//	plant.getPlantationData().setDeleted(0);													
										//}
										//else
										//{
										//	plant.setFillColor("red");
										//	plant.getPlantationData().setDeleted(1);
										//}
									}
									else
									{
										dialogBox.setText(plant.getVegetalData().getName());
										//serverResponseLabel.addStyleName("serverResponseLabelError");
										//serverResponseLabel.setHTML("Plant " + plant.getPlantationData().getVegetalNom());
										dialogBox.center();
									}
								}
							});
							zona.add(plant);

							vegetalDialogBox.hide();
						}
					});

					
					vegetalDialogBox.setText("Load RPC - Failure");
					vegetalResponseLabel.addStyleName("serverResponseLabelError");
					vegetalResponseLabel.setHTML("Error: ");
					vegetalDialogBox.center();
				}
			}
		});
		
		
		// Handler for the Zoom Buttons
		class ZoomHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
				Button btn = (Button)event.getSource();
				if (btn.getText().equals("Zoom -"))
				{
					m_ZoomFactor = m_ZoomFactor / 2;
				}
				else
				{
					m_ZoomFactor = m_ZoomFactor * 2;
				}
				
				for(int i=0; i<zona.getVectorObjectCount(); i++)
				{
					GraphicPlantation plant = (GraphicPlantation) zona.getVectorObject(i);
					plant.setX((int)(plant.getPlantationData().getPuntX() * m_ZoomFactor));
					plant.setY((int)(plant.getPlantationData().getPuntY() * m_ZoomFactor));
					plant.setHeight((int)(plant.getVegetalData().getPlantationHeight() * m_ZoomFactor));
					plant.setWidth((int)(plant.getVegetalData().getPlantationWidth() * m_ZoomFactor));
				}

				zona.setHeight((int)(zona.getZoneEntity().getHeigh() * m_ZoomFactor));
				zona.setWidth((int)(zona.getZoneEntity().getWidth() * m_ZoomFactor));
				
				zoneTitleLabel.setWidth((int)(zona.getZoneEntity().getWidth() * m_ZoomFactor) + "px");
			}
		}
		ZoomHandler zoomHandler = new ZoomHandler();
		zoomInButton.addClickHandler(zoomHandler);
		zoomOutButton.addClickHandler(zoomHandler);

		// Handler for the RESTful Button
		class RESTfulHandler implements ClickHandler {
			public void onClick(ClickEvent event) {
			  //String url = "http://www.google.com/calendar/feeds/developer-calendar@google.com/public/full?alt=json-in-script";
			  //String url = "http://localhost:8084/frontend_dev.php/apihort?alt=json-in-script"; //Error de timeout => potser perque demana el login?
			  //String url = "http://localhost:8084/frontend_dev.php/apihort.json"; //Error de timeout => potser perque demana el login?
			  //String url = "http://localhost:8084/frontend_dev.php/apihort.xml"; //Error de timeout => potser perque demana el login?
			  //String url = "http://localhost:8084/apihort.xml";
				String url = "http://localhost:8888/graphics/hort?q=ABC+DEF";
					
				RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
				try {
					//builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
					builder.sendRequest(null, new RequestCallback()	{
						public void onError(Request request, Throwable exception) {
							Window.alert(exception.getMessage());
						}
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								String response_ok = response.getStatusText();
								String response_text = response.getText();
								Window.alert(response_ok + ": " + response_text);
							} else {
								String response_ko = response.getStatusText();
								//String response_text = response.getText();
								Window.alert(response_ko + ": " + response.toString());
							}
						}
					});
				} 
				catch (Exception e) {
					Window.alert(e.toString());
				}
			}
		}
		RESTfulHandler restfulHandler = new RESTfulHandler();
		RESTfulButton.addClickHandler(restfulHandler);
		
		// Handler for the Load Button
		class LoadHandler implements ClickHandler {
			
			// * Fired when the user clicks on the sendButton.
			
			public void onClick(ClickEvent event) {
				getZoneAndPlantationsFromServer();
			}

			
			// * Gets a Zone with all active plantations
			
			private void getZoneAndPlantationsFromServer() {
				// First, we validate the input.

				//Reload the zone
				zona.ZoomToOriginal();
				
				// Then, we send the input to the server.
				loadButton.setEnabled(false);
				gardeningService.getCrop(1, m_Culture,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								dialogBox.setText("Load RPC - Failure");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML("Error: " + caught.toString());
								dialogBox.center();
								//Window.alert("Error: " + caught.toString());
								//loadButton.setFocus(true);
								loadButton.setEnabled(true);
							}

							public void onSuccess(String result) {
								//Clear the objects from the zona
								zona.clear();
								zona.ZoomToOriginal();
								m_ZoomFactor = 1.0;
								zoneTitleLabel.setWidth(zona.getWidth() + "px");
								
								//Process the json string: result
								JsArray<CropEntity> plantations = asArrayOfCropEntity(result);
								//Window.alert("Plantations: " + result);
								
								//Draw the plantations
								for (int i = 0; i < plantations.length(); i++) {
									CropGraphic plant = new CropGraphic(
											plantations.get(i).getPointX(), 
											plantations.get(i).getPointY(), 
											20, 
											20);
									plant.setCropEntity(plantations.get(i));
									
									//Search the vegetal
									for (int j=0; j < m_plants.length(); j++)
									{
										if (plant.getPlantEntity().getId() == m_plants.get(j).getId())
										{
											plant.setPlantEntity(m_plants.get(j));
										}
									}
									
									plant.setHeight((int)(plant.getPlantEntity().getHeightSpacing() * m_ZoomFactor));
									plant.setWidth((int)(plant.getPlantEntity().getWidthSpacing() * m_ZoomFactor));
									//plant.setFillColor("green");
									//plant.setFillOpacity(0.5);
									plant.addMouseDownHandler(new MouseDownHandler()
									{
										public void onMouseDown(MouseDownEvent event)
										{
											zona.bringToFront((GraphicPlantation) event.getSource());
											GraphicPlantation plant = (GraphicPlantation) event.getSource();
											//lastCircleSelected.setText(plant.getFillColor());
											
											for(int i=0; i<zona.getVectorObjectCount(); i++)
											{
												if (zona.getVectorObject(i).equals(plant))
												{
													m_SelectedCropIndex = i;
												}
											}
										}
									});
									plant.addMouseUpHandler(new MouseUpHandler()
									{
										public void onMouseUp(MouseUpEvent event)
										{
											//Circle plant = (Circle) event.getSource();
											m_SelectedCropIndex = -1;
										}
									});
									plant.addDoubleClickHandler(new DoubleClickHandler()
									{
										public void onDoubleClick(DoubleClickEvent event)
										{
											GraphicPlantation plant = (GraphicPlantation) event.getSource();
											
											if (event.isControlKeyDown())
											{
												//Delete or undelete
												if (plant.getPlantationData().getDeleted() == 1)
												{
													plant.setFillColor("green");
													plant.getPlantationData().setDeleted(0);													
												}
												else
												{
													plant.setFillColor("red");
													plant.getPlantationData().setDeleted(1);
													//zona.remove(plant);
												}
											}
											else
											{
												dialogBox.setText(plant.getVegetalData().getName());
												//serverResponseLabel.addStyleName("serverResponseLabelError");
												//serverResponseLabel.setHTML("Plant " + plant.getPlantationData().getVegetalNom());
												dialogBox.center();
											}
										}
									});
									zona.add(plant);
								}
								
								//dialogBox.setText("Load RPC - Successful");
								//serverResponseLabel.addStyleName("serverResponseLabelError");
								//serverResponseLabel.setHTML("Zone and plantations drawed successfully - " + result);
								//dialogBox.center();
								
								//Window.alert("Ok: " + result);
								//loadButton.setFocus(true);
								loadButton.setEnabled(true);
							}
						});
			}
		}
		LoadHandler loadHandler = new LoadHandler();
		loadButton.addClickHandler(loadHandler);
		
		// Handler for the Save Button
		class SaveHandler implements ClickHandler {
			
			// * Fired when the user clicks on the sendButton.
			
			public void onClick(ClickEvent event) {
				setZoneToServer();
				setPlantationsToServer();
			}

			
			// * Sets a Zone
			
			private void setZoneToServer() {
				// First, we validate the input.

				// Then, we send the input to the server.
				saveButton.setEnabled(false);
				gardeningService.setZone("",
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								dialogBox.setText("Save RPC - Failure");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML("Error: " + caught.toString());
								dialogBox.center();
								//Window.alert("Error: " + caught.toString());
								//saveButton.setFocus(true);
								saveButton.setEnabled(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Save RPC - Successful");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML("Ok: " + result);
								dialogBox.center();
								//Window.alert("Ok: " + result);
								//saveButton.setFocus(true);
								saveButton.setEnabled(true);
							}
						});
			}
			
			// * Sets the plantations
			
			private void setPlantationsToServer() {
				String jsonPlantationVector = "[ ";
				//ServerPlant sPlant = null;
				//ServerPlant sPlants[] = new ServerPlant[zona.getVectorObjectCount()];
				
				// Then, we send the input to the server.
				for(int i=0; i<zona.getVectorObjectCount(); i++)
				{
					CropGraphic crop = (CropGraphic) zona.getVectorObject(i);
					jsonPlantationVector += crop.getCropEntity().toJsonString() + ", ";
					
					//sPlant = plant.getPlantationData().toServerPlant();
					//sPlants[i] = sPlant;
				}
				jsonPlantationVector += " ]";
				
				Window.alert(jsonPlantationVector);
				
				saveButton.setEnabled(false);
				gardeningService.setCrop(jsonPlantationVector,
						new AsyncCallback<String>() {
							public void onFailure(Throwable caught) {
								dialogBox.setText("Save RPC - Failure");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML("Error: " + caught.toString());
								dialogBox.center();
								saveButton.setEnabled(true);
							}

							public void onSuccess(String result) {
								dialogBox.setText("Save RPC - Successful");
								serverResponseLabel.addStyleName("serverResponseLabelError");
								serverResponseLabel.setHTML("Ok: " + result);
								dialogBox.center();
								saveButton.setEnabled(true);
							}
						});
			}
		}
		SaveHandler saveHandler = new SaveHandler();
		saveButton.addClickHandler(saveHandler);
		*/
		//************************************************************
		// END TEST
		//************************************************************
	}
}
