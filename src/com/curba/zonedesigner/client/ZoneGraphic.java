package com.curba.zonedesigner.client;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.gwtgraphics.client.Image;


public class ZoneGraphic extends Image /*DrawingArea*/ {
	//private Rectangle m_rectangle;
	//private Image m_backGround;
	//private ZoneEntity m_entity;
	private GardenGraphic m_garden;
	GardenGraphic getGarden()
	{
		return m_garden;
	}
	void setGarden(GardenGraphic value)
	{
		m_garden = value;
	}
	
	private ZoneTypeEntity m_zoneType;
	ZoneTypeEntity getZoneType()
	{
		return m_zoneType;
	}
	void setZoneType(ZoneTypeEntity value)
	{
		m_zoneType = value;
	}

	List<CropGraphic> m_crops = new ArrayList<CropGraphic>();
	public List<CropGraphic> getCrops()
	{
		return m_crops;
	} 
	
	private int m_id;
	public int getId()
	{
		return m_id;
	}
	private String m_name;
	public String getName()
	{
		return m_name;
	}
	public void setName(String value)
	{
		m_name = value;
	}
	private String m_description;
	public String getDescription()
	{
		return m_description;
	}
	public void setDescription(String value)
	{
		m_description = value;
	}
	private int m_initialPointX;
	public int getInitialPointX()
	{
		return m_initialPointX;
	}
	public void setInitialPointX(int value)
	{
		m_initialPointX = value;
		//Window.alert("Zone x = " + Integer.toString(value));
	}
	private int m_initialPointY;
	public int getInitialPointY()
	{
		return m_initialPointY;
	}
	public void setInitialPointY(int value)
	{
		m_initialPointY = value;
		//Window.alert("Zone y = " + Integer.toString(value));
	}
	
	private int m_width;
	public int getEntityWidth()
	{
		return m_width;
	}
	public void setEntityWidth(int value)
	{
		m_width = value;
	}
	private int m_height;
	public int getEntityHeight()
	{
		return m_height;
	}
	public void setEntityHeight(int value)
	{
		m_height = value;
	}
	
	private int m_depth;
	public int getDepth()
	{
		return m_depth;
	}
	public void setDepth(int value)
	{
		m_depth = value;
	}
	
	
	private Boolean m_isNew;
	public Boolean getIsNew() {
		return m_isNew;
	}
	public void setIsNew(Boolean m_isNew) {
		this.m_isNew = m_isNew;
	}

	
	private Boolean m_isDeleted;
	private Boolean getIsDeleted()
	{
		return m_isDeleted;
	}
	private void setDeleted(Boolean value)
	{
		m_isDeleted = true;
	}

	//Creates a zone that already exists in the DB
	public ZoneGraphic(ZoneEntity entity, ZoneTypeEntity zoneType, GardenGraphic garden) {
		super(entity.getInitialPointX(), entity.getInitialPointY(), entity.getWidth(), entity.getHeigh(), "http://upload.wikimedia.org/wikipedia/commons/a/a6/Grass_01.jpg");
		
		m_id = entity.getId();
		setName(entity.getName());
		setDescription(entity.getDescription());
		m_initialPointX = entity.getInitialPointX();
		m_initialPointY = entity.getInitialPointY();
		setEntityWidth(Math.abs(entity.getFinalPointX() - entity.getInitialPointX()));
		setEntityHeight(Math.abs(entity.getFinalPointY() - entity.getInitialPointY()));
		m_depth = entity.getDepth();
		
		setZoneType(zoneType);
		setGarden(garden);
		
		m_isNew = false;
		m_isDeleted = false;
	}

	//Creates a zone that no exists in the DB
	public ZoneGraphic(int id, String name, String description, int x, int y, int height, int width, int depth, ZoneTypeEntity zoneType, GardenGraphic garden) {
		super(x, y, width, height, "http://upload.wikimedia.org/wikipedia/commons/a/a6/Grass_01.jpg");
		
		m_id = id;
		setName(name);
		setDescription(description);
		m_initialPointX = x;
		m_initialPointY = y;
		setEntityWidth(width);
		setEntityHeight(height);
		m_depth = depth;
		
		setZoneType(zoneType);
		setGarden(garden);
		
		m_isNew = true;
		m_isDeleted = false;
	}
	
	public void ZoomToOriginal()
	{
		Zoom(1);
	}
	
	public void Zoom(int zoom)
	{
		if (zoom == 0) zoom = 1;
		
		if (zoom >= 0)
		{
			super.setX(getInitialPointX() * zoom);
			super.setY(getInitialPointY() * zoom);
			super.setWidth(getEntityWidth() * zoom);
			super.setHeight(getEntityHeight() * zoom);
		}
		else
		{
			super.setX(getInitialPointX() / Math.abs(zoom));
			super.setY(getInitialPointY() / Math.abs(zoom));
			super.setWidth(getEntityWidth() / Math.abs(zoom));
			super.setHeight(getEntityHeight() / Math.abs(zoom));
		}
	}
	
	@Override 
	public String toString()
	{
		return "[Zone " + this.getName() + " (" + Integer.toString(getInitialPointX()) + "," + Integer.toString(getInitialPointY()) + ") (" +  
				Integer.toString(getEntityWidth()) + "x" + Integer.toString(getEntityHeight()) + ")]";
	}
}