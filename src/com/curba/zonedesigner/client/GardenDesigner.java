package com.curba.zonedesigner.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.core.client.JsArray;
//import com.google.gwt.i18n.client.NumberFormat;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GardenDesigner implements EntryPoint {
	private int m_width;
	private int m_height;
	private GardenGraphic m_Garden = null;
	public GardenGraphic getGarden()
	{
		return m_Garden;
	}
	static GardenAction m_SelectedAction = GardenAction.NONE;
	
	public static JsArray<PlantEntity> m_Plants = null;
	public static JsArray<GardenTypeEntity> m_GardenTypes = null;
	public static JsArray<RegionEntity> m_Regions = null;
	public static JsArray<ZoneTypeEntity> m_ZoneTypes = null;
	private JsArray<GardenEntity> m_Gardens = null;
	
	//Garden designer controls
	final FlowPanel m_pnlControls = new FlowPanel(); 
	//final Label m_lblZoom = new Label();
	final Button m_btnZoomOut = new Button("Zoom -");
	final Button m_btnZoomIn = new Button("Zoom +");
	
	final Button m_btnCropNew = new Button("Crop +");
	final Button m_btnCropDelete = new Button("Crop -");
	final Button m_btnCropProperties = new Button("Crop =");
	final Button m_btnCropPrune = new Button("Crop prune");
	final Button m_btnCropCollect = new Button("Crop collect");
		
	final Button m_btnZoneNew = new Button("Zone +");
	final Button m_btnZoneDelete = new Button("Zone -");
	final Button m_btnZoneProperties = new Button("Zone =");
	final Button m_btnZoneWatering = new Button("Zone watering");

	final Button m_btnGardenProperties = new Button("Garden =");
	
	final Button m_btnSave = new Button("Save");
	
	// Convert the string of JSON into JavaScript object.
	private final native JsArray<GardenEntity> asArrayOfGardenEntity(String json) /*-{
		return eval(json);
	}-*/;
	private final native JsArray<ZoneEntity> asArrayOfZoneEntity(String json) /*-{
		return eval(json);
	}-*/;
	private final native JsArray<ZoneTypeEntity> asArrayOfZoneTypeEntity(String json) /*-{
		return eval(json);
	}-*/;
	private final native JsArray<RegionEntity> asArrayOfRegionEntity(String json) /*-{
		return eval(json);
	}-*/;
	private final native JsArray<PlantEntity> asArrayOfPlantEntity(String json) /*-{
		return eval(json);
	}-*/;
	public final native static JsArray<CropEntity> asArrayOfCropEntity(String json) /*-{
		return eval(json);
	}-*/;
	private final native JsArray<GardenTypeEntity> asArrayOfGardenTypeEntity(String json) /*-{
		return eval(json);
	}-*/;

	/**
	 * Global variables
	 */
	//JsArray<PlantEntity> m_plants = null;
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
		//Load the initial values from hidden elements
		String parPlants = DOM.getElementAttribute(DOM.getElementById("parPlants"), "value");
		m_Plants = asArrayOfPlantEntity(parPlants.replace("%2C", ",").replace("&quot;", "\""));
		String parRegions = DOM.getElementAttribute(DOM.getElementById("parRegions"), "value");
		m_Regions = asArrayOfRegionEntity(parRegions.replace("%2C", ",").replace("&quot;", "\""));
		String parGardenTypes = DOM.getElementAttribute(DOM.getElementById("parGardenTypes"), "value");
		m_GardenTypes = asArrayOfGardenTypeEntity(parGardenTypes.replace("%2C", ",").replace("&quot;", "\""));
		String parGarden = DOM.getElementAttribute(DOM.getElementById("parGarden"), "value");
		m_Gardens = asArrayOfGardenEntity("[" + parGarden.replace("%2C", ",").replace("&quot;", "\"") + "]");
		String parZoneTypes = DOM.getElementAttribute(DOM.getElementById("parZoneTypes"), "value");
		m_ZoneTypes = asArrayOfZoneTypeEntity(parZoneTypes.replace("%2C", ",").replace("&quot;", "\""));		
			
		//Get the sizes of the control
		String parWidth = DOM.getElementAttribute(DOM.getElementById("ajaxGardenEditor"), "width"); 
		if (parWidth != null) m_width = Integer.parseInt(parWidth);
		else m_width = 200;
		String parHeight = DOM.getElementAttribute(DOM.getElementById("ajaxGardenEditor"), "height");
		if (parHeight != null) m_height = Integer.parseInt(parHeight);
		else m_height = 200;

		//Creates the GardenGraphic
		//m_Garden = new GardenGraphic(m_width, m_height, m_Gardens.get(0));
		m_Garden = new GardenGraphic(m_Gardens.get(0));
		m_Garden.setGardenType(m_GardenTypes, m_Gardens.get(0).getGardenTypeId());
		m_Garden.setRegion(m_Regions, m_Gardens.get(0).getRegionId());
		m_Garden.addMouseDownHandler(new GraphicObjectMouseDownHandler());
		m_Garden.addMouseUpHandler(new GraphicObjectMouseUpHandler());
		m_Garden.addMouseMoveHandler(new GraphicObjectMouseMoveHandler());
		String parZones = DOM.getElementAttribute(DOM.getElementById("parZones"), "value");
		m_Garden.setEntityZones(asArrayOfZoneEntity(parZones.replace("%2C", ",").replace("&quot;", "\"")));
		String parCrops = DOM.getElementAttribute(DOM.getElementById("parCrops"), "value");
		m_Garden.setEntityCrops(asArrayOfCropEntity(parCrops.replace("%2C", ",").replace("&quot;", "\"")));
		
		//Window.alert(parZones.replace("%2C", ",").replace("&quot;", "\""));
		
		m_Garden.CreateZonesAndCrops(m_Plants);
		
		//m_Garden.InitializeComponents();
		
		//Creates the controls of the designer
		//m_pnlControls.setSize("500", "100");
		m_btnCropNew.setTitle("CropNew");
		m_btnCropNew.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnCropDelete.setTitle("CropDelete");
		m_btnCropDelete.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnCropProperties.setTitle("CropProps");
		m_btnCropProperties.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnCropPrune.setTitle("CropPrune");
		m_btnCropPrune.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnCropCollect.setTitle("CropCollect");
		m_btnCropCollect.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnZoneNew.setTitle("ZoneNew");
		m_btnZoneNew.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnZoneDelete.setTitle("ZoneDelete");
		m_btnZoneDelete.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnZoneProperties.setTitle("ZoneProps");
		m_btnZoneProperties.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnZoneWatering.setTitle("ZoneWatering");
		m_btnZoneWatering.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnGardenProperties.setTitle("GardenProps");
		m_btnGardenProperties.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnSave.setTitle("Save");
		m_btnSave.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnZoomIn.setTitle("ZoomIn");
		m_btnZoomIn.addClickHandler(new ToolbarClickHandler(m_Garden));
		m_btnZoomOut.setTitle("ZoomOut");
		m_btnZoomOut.addClickHandler(new ToolbarClickHandler(m_Garden));
		
		//Adds the controls to the panel
		m_pnlControls.add(m_btnCropNew);
		m_pnlControls.add(m_btnCropDelete);
		m_pnlControls.add(m_btnCropProperties);
		m_pnlControls.add(m_btnCropPrune);
		m_pnlControls.add(m_btnCropCollect);
		m_pnlControls.add(m_btnZoneNew);
		m_pnlControls.add(m_btnZoneDelete);
		m_pnlControls.add(m_btnZoneProperties);
		m_pnlControls.add(m_btnZoneWatering);
		m_pnlControls.add(m_btnGardenProperties);
		m_pnlControls.add(m_btnSave);
		m_pnlControls.add(m_btnZoomIn);
		m_pnlControls.add(m_btnZoomOut);
		RootPanel.get("ajaxGardenControls").add(m_pnlControls);
		//Sets the control in the div named "ajaxGardenEditor"
		RootPanel.get("ajaxGardenEditor").add(m_Garden);
		
		
		
		//Window.alert("Ok");
		
		
		
		
		/*
		
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
		final ZoneGraphic zona = new ZoneGraphic(200, 200);
		zona.setStylePrimaryName("zona");
		RootPanel.get().add(zona);
		
		//Loads garden from server
		//gardeningService.getGarden(1, m_Culture,
		//		new AsyncCallback<String>() {
		//			public void onFailure(Throwable caught) {
		//				dialogBox.setText("Load RPC - Failure");
		//				serverResponseLabel.addStyleName("serverResponseLabelError");
		//				serverResponseLabel.setHTML("Error loading zone: " + caught.toString());
		//				dialogBox.center();
		//				//Window.alert("Error: " + caught.toString());
		//				//loadButton.setFocus(true);
		//				loadButton.setEnabled(true);
		//			}
		//
		//			public void onSuccess(String result) {
		//				Window.alert(result);
		//				
		//				//Process the json string: result
		//				JsArray<GardenEntity> gardens = asArrayOfGardenEntity(result);
		//				//Window.alert("Garden: " + String.valueOf(garden.length()));
		//				//Window.alert("Garden: " + result);
		//				if (gardens.length() != 1)
		//				{
		//					Window.alert("Error: searched for garden with id = 1 and found " + gardens.length() + " gardens");
		//				}
		//				else
		//				{
		//					for (int i = 0; i < gardens.length(); i++) {
		//						zona.setGardenEntity(gardens.get(i));
		//						//Resize the Zone
		//						zona.ZoomToOriginal();
		//						
		//						//Sets the name of the zone
		//						zoneTitleLabel.setWidth(zona.getWidth() + "px");
		//						zoneTitleLabel.setText(
		//								zona.getZoneEntity().getName() + ": " + 
		//								zona.getZoneEntity().getDescription() + ". Total volume = " +
		//								NumberFormat.getFormat("0.00").format(zona.getZoneEntity().getLiters()) + "liters. Total Area = " +
		//								NumberFormat.getFormat("0.00").format(zona.getZoneEntity().getArea()) + "m2.");
		//				    }
		//					//Window.alert("Ok: " + result);
		//					loadButton.setEnabled(true);
		//				}
		//			}
		//		});
		

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
						Window.alert(result);
						
						//Process the json string: result
						JsArray<ZoneEntity> zones = asArrayOfZoneEntity(result);
						//Window.alert("Zones: " + String.valueOf(zones.length()));
						//Window.alert("Zones: " + result);
						
						if (zones.length() != 1)
						{
							Window.alert("Error: searched for zone with id = 1 and found " + zones.length() + " zones");
						}
						else
						{
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
							//Window.alert("Ok: " + result);
							loadButton.setEnabled(true);
						}
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
						//Window.alert("Vegetals: " + result);
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
				try
				{
					if (m_SelectedCropIndex >= 0)
					{
						CropGraphic crop = (CropGraphic) zona.getVectorObject(m_SelectedCropIndex);
						
						//For a circle
					    //crop.setX(event.getX());
					    //crop.setY(event.getY());
					    //crop.getCropEntity().setPointX((int)(event.getX() * 1.0 / m_ZoomFactor));
					    //crop.getCropEntity().setPointY((int)(event.getY() * 1.0 / m_ZoomFactor));
					    
						//For a rectangle or a image
					    crop.setX(event.getX() - (crop.getWidth() / 2));
					    crop.setY(event.getY() - (crop.getHeight() / 2));
					    crop.getCropEntity().setPointX((int)(crop.getX() * 1.0 / m_ZoomFactor));
					    crop.getCropEntity().setPointY((int)(crop.getY() * 1.0 / m_ZoomFactor));
					}
				}
				catch(Exception ex)
				{
					Window.alert(ex.toString());
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
												", zone_id: " + zona.getZoneEntity().getId() +
												", crop_period_id: 2" +
												", plant_id: " + vegetal.getId() +
												", plant_name: \"" + vegetal.getName() + "\"" +
												", plant_description: \"" + vegetal.getDescription() + "\"" +
												", initial_real_date: \"2011-01-01 00:00:00.0\"" + 
												", initial_planned_date: \"2011-01-01 00:00:00.0\"" + 
												", final_real_date: \"null\"" + 
												", final_planned_date: \"null\"" + 
												", point_x: " + (int)(clickedX * 1.0 / m_ZoomFactor) + 
												", point_y: " + (int)(clickedY * 1.0 / m_ZoomFactor) + 
												", num_plants: " + 1 +
												", productivity: " + 1 +
												", width_spacing: " + 10 +
												", height_spacing: " + 10 +
												", soil_volume: " + 10 +
												", deleted: " + 0 + "}, ]";
							
							Window.alert(jsonPlant);
							JsArray<CropEntity> plantations = asArrayOfCropEntity(jsonPlant);
							plant.setCropEntity(plantations.get(0));
							plant.setPlantEntity(vegetal);
							plant.setHeight((int)(plant.getPlantEntity().getHeightSpacing() * m_ZoomFactor));
							plant.setWidth((int)(plant.getPlantEntity().getWidthSpacing() * m_ZoomFactor));
							//Plant event to select a plant
							plant.addMouseDownHandler(new MouseDownHandler()
							{
								public void onMouseDown(MouseDownEvent event)
								{
									try
									{
									zona.bringToFront((CropGraphic) event.getSource());
									CropGraphic plant = (CropGraphic) event.getSource();
									//lastCircleSelected.setText(plant.getFillColor());
									for(int i=0; i<zona.getVectorObjectCount(); i++)
									{
										if (zona.getVectorObject(i).equals(plant))
										{
											m_SelectedCropIndex = i;
										}
									}
									}
									catch(Exception ex)
									{
										Window.alert(ex.toString());
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
									try
									{
									CropGraphic plant = (CropGraphic) event.getSource();
									
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
										dialogBox.setText(plant.getPlantEntity().getName());
										//serverResponseLabel.addStyleName("serverResponseLabelError");
										//serverResponseLabel.setHTML("Plant " + plant.getPlantationData().getVegetalNom());
										dialogBox.center();
									}
									}
									catch(Exception ex)
									{
										Window.alert(ex.toString());
									}
								}
							});
							zona.add(plant);

							vegetalDialogBox.hide();
						}
					});

					
					vegetalDialogBox.setText("Crop creation");
					vegetalResponseLabel.addStyleName("serverResponseLabelOk");
					vegetalResponseLabel.setHTML("Choose a plant: ");
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
			//**
			// * Fired when the user clicks on the sendButton.
			// *
			public void onClick(ClickEvent event) {
				getZoneAndPlantationsFromServer();
			}

			//**
			// * Gets a Zone with all active plantations
			// *
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
			//**
			// * Fired when the user clicks on the sendButton.
			// *
			public void onClick(ClickEvent event) {
				setZoneToServer();
				setPlantationsToServer();
			}

			//**
			// * Sets a Zone
			// *
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
			//**
			// * Sets the plantations
			// *
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
	}
}