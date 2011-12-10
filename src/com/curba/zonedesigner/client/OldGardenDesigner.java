package com.curba.zonedesigner.client;

//Vaadin examples
//http://code.google.com/p/gwt-graphics/#Demos_&_examples

//import com.curba.zonedesigner.shared.ServerPlant;
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
public class OldGardenDesigner implements EntryPoint {
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
	private final GardeningServiceAsync greetingService = GWT
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
		
		//Creates and loads the garden
		final Label gardenTitleLabel = new Label();	//Label to get the zoom factor
		gardenTitleLabel.setStylePrimaryName("zona_label");
		RootPanel.get().add(gardenTitleLabel);
		//final GardenGraphic garden = new GardenGraphic(400, 400, null);
		final GardenGraphic garden = new GardenGraphic(null);
		garden.setStylePrimaryName("garden");
		RootPanel.get().add(garden);
		//Loads garden from server
		greetingService.getGarden(1, m_Culture,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						dialogBox.setText("Load RPC - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("Error loading garden: " + caught.toString());
						dialogBox.center();
						loadButton.setEnabled(true);
					}

					public void onSuccess(String result) {
						//Process the json string: result
						JsArray<GardenEntity> gardens = asArrayOfGardenEntity(result);
						
						for (int i = 0; i < gardens.length(); i++) {
							garden.setGardenEntity(gardens.get(i));
							//Resize the Zone
							garden.ZoomToOriginal();
							
							//Sets the name of the zone
							gardenTitleLabel.setWidth(garden.getWidth() + "px");
							gardenTitleLabel.setText(
									garden.getGardenEntity().getName() + ": " + 
									garden.getGardenEntity().getDescription() + ". Total Area = " +
									NumberFormat.getFormat("0.00").format(garden.getGardenEntity().getArea()) + "m2.");
					    }
												
						loadButton.setEnabled(true);
					}
				});
		//Loads plants from server
		greetingService.getPlants(m_Culture,
				new AsyncCallback<String>() {
					public void onFailure(Throwable caught) {
						dialogBox.setText("Load RPC - Failure");
						serverResponseLabel.addStyleName("serverResponseLabelError");
						serverResponseLabel.setHTML("Error loading plants: " + caught.toString());
						dialogBox.center();
						loadButton.setEnabled(true);
					}

					public void onSuccess(String result) {
						//Process the json string: result
						m_plants = asArrayOfPlantEntity(result);
					}
				});
/*		
		//Event to move zones in the garden
		garden.addMouseMoveHandler(new MouseMoveHandler() 
		{
			public void onMouseMove(MouseMoveEvent event) 
			{
				if (m_SelectedZoneIndex >= 0)
				{
					ZoneGraphic zone = (ZoneGraphic)garden.getVectorObject(m_SelectedZoneIndex);
				    zone.setX(event.getX());
				    zone.setY(event.getY());
				    zone.getZoneEntity().setInitialPointX((int)(event.getX() * 1.0 / m_ZoomFactor));
				    zone.getZoneEntity().setInitialPointY((int)(event.getY() * 1.0 / m_ZoomFactor));
				}
			}
		});
		
		//Event to create a new zone with all his events ()
		garden.addDoubleClickHandler(new DoubleClickHandler()
		{
			public void onDoubleClick(DoubleClickEvent event)
			{
				if (event.isShiftKeyDown())
				{
					// Zone creation dialog box
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
				
				for(int i=0; i<garden.getVectorObjectCount(); i++)
				{
					ZoneGraphic zone = (ZoneGraphic)garden.getVectorObject(i);
					for(int i=0; i<zone.getVectorObjectCount(); i++)
					{
						GraphicPlantation plant = (GraphicPlantation) zone.getVectorObject(i);
						plant.setX((int)(plant.getPlantationData().getPuntX() * m_ZoomFactor));
						plant.setY((int)(plant.getPlantationData().getPuntY() * m_ZoomFactor));
						plant.setHeight((int)(plant.getVegetalData().getPlantationHeight() * m_ZoomFactor));
						plant.setWidth((int)(plant.getVegetalData().getPlantationWidth() * m_ZoomFactor));
					}
					zone.setHeight((int)(zone.getZoneData().getHeigh() * m_ZoomFactor));
					zone.setWidth((int)(zone.getZoneData().getWidth() * m_ZoomFactor));
					
					zoneTitleLabel.setWidth((int)(zone.getZoneData().getWidth() * m_ZoomFactor) + "px");
				}
				garden.setHeight((int)(garden.getGardenEntity().getHeight() * m_ZoomFactor));
				garden.setWidth((int)(garden.getGardenEntity().getWidth() * m_ZoomFactor));
				
				gardenTitleLabel.setWidth((int)(garden.getGardenEntity().getWidth() * m_ZoomFactor) + "px");
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
			//
			// Fired when the user clicks on the sendButton.
			//
			public void onClick(ClickEvent event) {
				getZoneAndPlantationsFromServer();
			}

			//
			// Gets a Zone with all active plantations
			//
			private void getZoneAndPlantationsFromServer() {
				// First, we validate the input.

				//Reload the zone
				zona.ZoomToOriginal();
				
				// Then, we send the input to the server.
				loadButton.setEnabled(false);
				greetingService.getPlantationsServer(1, m_Culture,
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
								JsArray<PlantationData> plantations = asArrayOfPlantationData(result);
								//Window.alert("Plantations: " + result);
								
								//Draw the plantations
								for (int i = 0; i < plantations.length(); i++) {
									GraphicPlantation plant = new GraphicPlantation(
											plantations.get(i).getPuntX(), 
											plantations.get(i).getPuntY(), 
											20, 
											20);
									plant.setPlantationData(plantations.get(i));
									
									//Search the vegetal
									for (int j=0; j < m_vegetals.length(); j++)
									{
										if (plant.getPlantationData().getVegetalId() == m_vegetals.get(j).getId())
										{
											plant.setVegetalData(m_vegetals.get(j));
										}
									}
									
									plant.setHeight((int)(plant.getVegetalData().getPlantationHeight() * m_ZoomFactor));
									plant.setWidth((int)(plant.getVegetalData().getPlantationWidth() * m_ZoomFactor));
									plant.setFillColor("green");
									plant.setFillOpacity(0.5);
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
													m_SelectedPlantationIndex = i;
												}
											}
										}
									});
									plant.addMouseUpHandler(new MouseUpHandler()
									{
										public void onMouseUp(MouseUpEvent event)
										{
											//Circle plant = (Circle) event.getSource();
											m_SelectedPlantationIndex = -1;
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
			//
			// Fired when the user clicks on the sendButton.
			//
			public void onClick(ClickEvent event) {
				setZoneToServer();
				setPlantationsToServer();
			}

			//
			// Sets a Zone
			//
			private void setZoneToServer() {
				// First, we validate the input.

				// Then, we send the input to the server.
				saveButton.setEnabled(false);
				greetingService.setZoneServer("",
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
			//
			// Sets the plantations
			//
			private void setPlantationsToServer() {
				String jsonPlantationVector = "[ ";
				ServerPlant sPlant = null;
				ServerPlant sPlants[] = new ServerPlant[zona.getVectorObjectCount()];
				
				// Then, we send the input to the server.
				for(int i=0; i<zona.getVectorObjectCount(); i++)
				{
					GraphicPlantation plant = (GraphicPlantation) zona.getVectorObject(i);
					jsonPlantationVector += plant.getPlantationData().toJsonString() + ", ";
					
					sPlant = plant.getPlantationData().toServerPlant();
					sPlants[i] = sPlant;
				}
				jsonPlantationVector += " ]";
				
				Window.alert(jsonPlantationVector);
				
				saveButton.setEnabled(false);
				greetingService.setPlantationsServer(sPlants,
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