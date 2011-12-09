package com.curba.zonedesigner.client;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Image;
import com.google.gwt.core.client.JsArray;
import java.util.List;
import java.util.ArrayList;

public class GardenGraphic extends DrawingArea {
	private RegionEntity m_region;
	private GardenTypeEntity m_gardenType;
	private GardenEntity m_entity;
	List<ZoneGraphic> m_zones = new ArrayList<ZoneGraphic>();
	
	private Image m_backGround;
	
	private Object m_SelectedGraphicObject = null;
	public Object getSelectedGraphicObject()
	{
		return m_SelectedGraphicObject;
	}
	public void setSelectedGraphicObject(Object value)
	{
		m_SelectedGraphicObject = value;
	}
	
	
	public GardenGraphic(GardenEntity entity) {
		super(entity.getWidth(), entity.getHeight());
		// TODO Auto-generated constructor stub
		
		m_entity = entity;
		
		m_backGround = new Image(0, 0, entity.getWidth(), entity.getHeight(), "http://www.mccallsodfarm.net/images/topsoil.jpg");
		this.add(m_backGround);
	}
	
	public List<ZoneGraphic> getZones()
	{
		return m_zones;
	} 
	
	public void ZoomToOriginal()
	{
		super.setWidth(getGardenEntity().getWidth());
		super.setHeight(getGardenEntity().getHeight());
		
		m_backGround.setWidth(getGardenEntity().getWidth());
		m_backGround.setHeight(getGardenEntity().getHeight());
	}

	public void setGardenEntity(GardenEntity entity) {
		this.m_entity = entity;
	}

	public GardenEntity getGardenEntity() {
		return m_entity;
	}
	
	public void setRegion(JsArray<RegionEntity> regions, int regionId)
	{
		for(int i=0; i<regions.length(); i++)
		{
			RegionEntity r = regions.get(i);
			  
			if (r.getId() == regionId)
			{
				this.m_region = r;
				break;
			}
		}
	}
	public void setGardenType(JsArray<GardenTypeEntity> gardenTypes, int gardenTypeId)
	{
		for(int i=0; i<gardenTypes.length(); i++)
		{
			GardenTypeEntity gt = gardenTypes.get(i);
			  
			if (gt.getId() == gardenTypeId)
			{
				this.m_gardenType = gt;
				break;
			}
		}
	}

	public void Draw()
	{
		for(int i=0; i<m_zones.size(); i++)
		{
			this.add(m_zones.get(i));
			for(int j=0; j<m_zones.get(i).getCrops().size(); j++)
			{
				this.add(m_zones.get(i).getCrops().get(j));
			}
		}
	}
}
	

