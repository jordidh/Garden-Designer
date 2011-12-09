package com.curba.zonedesigner.client;

import org.vaadin.gwtgraphics.client.DrawingArea;


public class GraphicZone extends DrawingArea {
/*
	private int m_id;
	private String m_name;
	private String m_description;
	private int m_zoneTypeId;
	private int m_initialPointX;
	private int m_initialPointY;
	private int m_finalPointX;
	private int m_finalPointY;
	private int m_deptht;
	private int m_garden;
*/	
	private ZoneData m_zone;
	
	public GraphicZone(int width, int height) {
		super(width, height);
		// TODO Auto-generated constructor stub
	}
	
	public GraphicZone(ZoneData zone) {
		super(zone.getWidth(), zone.getHeigh()); 
		// TODO Auto-generated constructor stub
		setZoneData(zone);
	}
	
	public void ZoomToOriginal()
	{
		super.setWidth(getZoneData().getWidth());
		super.setHeight(getZoneData().getHeigh());
	}

	public void setZoneData(ZoneData m_zone) {
		this.m_zone = m_zone;
	}

	public ZoneData getZoneData() {
		return m_zone;
	}
}
