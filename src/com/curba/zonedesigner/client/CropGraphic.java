package com.curba.zonedesigner.client;

import java.util.Date;

import org.vaadin.gwtgraphics.client.Image;
import org.vaadin.gwtgraphics.client.shape.Rectangle;


public class CropGraphic extends Image /*Rectangle*/ {
	
	private CropEntity m_crop;
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
	
	public CropGraphic(int x, int y, int width, int height, ZoneGraphic zone, GardenGraphic garden) {
		super(x, y, width, height, "http://www.ikea.com/gb/en/images/products/ficus-microcarpa-ginseng-potted-plant__67525_PE181357_S4.jpg");
		// TODO Auto-generated constructor stub
		
		setZone(zone);
		setGarden(garden);
		//this.setFillColor("green");
		//this.setFillOpacity(0.5);
		
		//this.setRotation(20);
	}
	
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
	}
	
	public void SetRealFinalDate(Date date)
	{
		getCropEntity().setFinalRealDate(date);
	}

	public void setCropEntity(CropEntity crop) {
		this.m_crop = crop;
	}

	public CropEntity getCropEntity() {
		return m_crop;
	}

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
			super.setX(getCropEntity().getPointX() * zoom);
			super.setY(getCropEntity().getPointY() * zoom);
			super.setWidth(getPlantEntity().getWidthSpacing() * zoom);
			super.setHeight(getPlantEntity().getHeightSpacing() * zoom);
		}
		else
		{
			super.setX(getCropEntity().getPointX() / Math.abs(zoom));
			super.setY(getCropEntity().getPointY() / Math.abs(zoom));
			super.setWidth(getPlantEntity().getWidthSpacing() / Math.abs(zoom));
			super.setHeight(getPlantEntity().getHeightSpacing() / Math.abs(zoom));
		}
	}
}