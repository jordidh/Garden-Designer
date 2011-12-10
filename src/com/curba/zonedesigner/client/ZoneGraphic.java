package com.curba.zonedesigner.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.vaadin.gwtgraphics.client.shape.Rectangle;
import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Group;
import org.vaadin.gwtgraphics.client.Image;


public class ZoneGraphic extends Image /*DrawingArea*/ {
	//private Rectangle m_rectangle;
	//private Image m_backGround;
	private ZoneEntity m_entity;
	private GardenGraphic m_garden;
	GardenGraphic getGarden()
	{
		return m_garden;
	}
	void setGarden(GardenGraphic value)
	{
		m_garden = value;
	}
	
	List<CropGraphic> m_crops = new ArrayList<CropGraphic>();
	public List<CropGraphic> getCrops()
	{
		return m_crops;
	} 
		
	public ZoneGraphic(ZoneEntity entity, GardenGraphic garden) {
		super(entity.getInitialPointX(), entity.getInitialPointY(), entity.getWidth(), entity.getHeigh(), "http://upload.wikimedia.org/wikipedia/commons/a/a6/Grass_01.jpg");
		
		//m_rectangle = new Rectangle(zone.getInitialPointX(), zone.getInitialPointY(), zone.getWidth(), zone.getHeigh());
		//this.add(m_rectangle);
		
		setGarden(garden);
		
		// TODO Auto-generated constructor stub
		setZoneEntity(entity);
		
		//m_backGround = new Image(0, 0, zone.getWidth(), zone.getHeigh(), "http://www.mccallsodfarm.net/images/topsoil.jpg");
		//this.add(m_backGround);
	}
	
	public void setZoneEntity(ZoneEntity m_zone) {
		this.m_entity = m_zone;
	}

	public ZoneEntity getZoneEntity() {
		return m_entity;
	}
	
	public void ZoomToOriginal()
	{
		super.setWidth(getZoneEntity().getWidth());
		super.setHeight(getZoneEntity().getHeigh());
		
		//m_backGround.setWidth(getZoneEntity().getWidth());
		//m_backGround.setHeight(getZoneEntity().getHeigh());
	}
	
	public void Zoom(int zoom)
	{
		if (zoom == 0) zoom = 1;
		
		if (zoom >= 0)
		{
			super.setX(getZoneEntity().getInitialPointX() * zoom);
			super.setY(getZoneEntity().getInitialPointY() * zoom);
			super.setWidth(getZoneEntity().getWidth() * zoom);
			super.setHeight(getZoneEntity().getHeigh() * zoom);
		}
		else
		{
			super.setX(getZoneEntity().getInitialPointX() / Math.abs(zoom));
			super.setY(getZoneEntity().getInitialPointY() / Math.abs(zoom));
			super.setWidth(getZoneEntity().getWidth() / Math.abs(zoom));
			super.setHeight(getZoneEntity().getHeigh() / Math.abs(zoom));
		}
	}
}