package com.curba.zonedesigner.client;

import java.util.Date;

import org.vaadin.gwtgraphics.client.Image;


public class CropGraphic extends Image /*Rectangle*/ {
	
	//private CropEntity m_crop;
	private PlantEntity m_plant;
	private GardenGraphic m_garden;
	GardenGraphic getGarden()
	{
		return m_garden;
	}
	void setGarden(GardenGraphic value)
	{
		m_garden = value;
	}
	private ZoneGraphic m_zone;
	ZoneGraphic getZone()
	{
		return m_zone;
	}
	void setZone(ZoneGraphic value)
	{
		m_zone = value;
	}
	
	private int m_id;
	private int m_zoneId;
	private int m_plantId;
	private Date m_initialRealDate;
	private Date m_initialPlannedDate;
	private Date m_finalRealDate;
	private Date m_finalPlannedDate;
	private int m_pointX;
	public int getPointX()
	{
		return m_pointX;
	}
	public void setPointX(int value)
	{
		m_pointX = value;
	}
	private int m_pointY;
	public int getPointY()
	{
		return m_pointY;
	}
	public void setPointY(int value)
	{
		m_pointY = value;
	}
	private int m_numPlants;

	/*
	public CropGraphic(int x, int y, int width, int height, ZoneGraphic zone, GardenGraphic garden) {
		super(x, y, width, height, "http://www.ikea.com/gb/en/images/products/ficus-microcarpa-ginseng-potted-plant__67525_PE181357_S4.jpg");
		// TODO Auto-generated constructor stub
		
		setZone(zone);
		setGarden(garden);
		//this.setFillColor("green");
		//this.setFillOpacity(0.5);
		
		//this.setRotation(20);
	}
	*/
	
	/*
	//Constructor que calcula l'area de plantaci� en funci� del volum necessitat per la planta
	//i de la profuncitat de la taula de plantaci�. P.e. una taula poc fonda generar� un marc de
	//plantaci� m�s gran i una taula molt profunda generar� un marc de plantaci� est�ndar.
	public CropGraphic(CropEntity crop, PlantEntity plant, ZoneGraphic zone, GardenGraphic garden) {
		super(crop.getPointX(), crop.getPointY(), plant.getWidthSpacing(), plant.getHeightSpacing(), "http://www.ikea.com/gb/en/images/products/ficus-microcarpa-ginseng-potted-plant__67525_PE181357_S4.jpg");
		
		// TODO Auto-generated constructor stub
		setCropEntity(crop);
		setPlantEntity(plant);
		setZone(zone);
		setGarden(garden);
		
		//this.setFillColor("green");
		//this.setFillOpacity(0.5);
		
		//this.setRotation(20);
		
		m_id = -1;
		m_zoneId = zone.getZoneEntity().getId();
		m_plantId = -1;
		//m_initialRealDate;
		//m_initialPlannedDate;
		//m_finalRealDate;
		//m_finalPlannedDate;
		m_pointX = x;
		m_pointY = y;
		m_numPlants = 1;
	}
	*/
	
	//Constructor que calcula l'area de plantaci� en funci� del volum necessitat per la planta
	//i de la profuncitat de la taula de plantaci�. P.e. una taula poc fonda generar� un marc de
	//plantaci� m�s gran i una taula molt profunda generar� un marc de plantaci� est�ndar.
	public CropGraphic(int id, int numPlants, int x, int y, PlantEntity plant, ZoneGraphic zone, GardenGraphic garden) {
		super(x, y, plant.getWidthSpacing(), plant.getHeightSpacing(), "http://www.ikea.com/gb/en/images/products/ficus-microcarpa-ginseng-potted-plant__67525_PE181357_S4.jpg");
		
		// TODO Auto-generated constructor stub
		//setCropEntity(crop);
		setPlantEntity(plant);
		setZone(zone);
		setGarden(garden);
		
		//this.setFillColor("green");
		//this.setFillOpacity(0.5);
		
		//this.setRotation(20);
		
		m_id = id;
		m_zoneId = zone.getZoneEntity().getId();
		m_plantId = plant.getId();
		if (id == -1) m_initialRealDate = new Date();
		//m_initialPlannedDate;
		//m_finalRealDate;
		//m_finalPlannedDate;
		m_pointX = x;
		m_pointY = y;
		m_numPlants = numPlants;
	}
	
	public void SetRealFinalDate(Date date)
	{
		//getCropEntity().setFinalRealDate(date);
		m_finalRealDate = date;
	}

	/*
	public void setCropEntity(CropEntity crop) {
		this.m_crop = crop;
	}

	public CropEntity getCropEntity() {
		return m_crop;
	}
	*/

	public void setPlantEntity(PlantEntity plant) {
		this.m_plant = plant;
	}

	public PlantEntity getPlantEntity() {
		return m_plant;
	}
	
	public void Zoom(int zoom)
	{
		if (zoom == 0) zoom = 1;
		
		if (zoom >= 0)
		{
			super.setX(m_pointX /*getCropEntity().getPointX()*/ * zoom);
			super.setY(m_pointY /*getCropEntity().getPointY()*/ * zoom);
			super.setWidth(getPlantEntity().getWidthSpacing() * zoom);
			super.setHeight(getPlantEntity().getHeightSpacing() * zoom);
		}
		else
		{
			super.setX(m_pointX /*getCropEntity().getPointX()*/ / Math.abs(zoom));
			super.setY(m_pointY /*getCropEntity().getPointY()*/ / Math.abs(zoom));
			super.setWidth(getPlantEntity().getWidthSpacing() / Math.abs(zoom));
			super.setHeight(getPlantEntity().getHeightSpacing() / Math.abs(zoom));
		}
	}
}