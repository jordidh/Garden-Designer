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
	private int m_zoom = 0;
	
	private JsArray<CropEntity> m_EntityCrops = null;
	public JsArray<CropEntity> getEntityCrops()
	{
		return m_EntityCrops;
	}
	public void setEntityCrops(JsArray<CropEntity> entities)
	{
		m_EntityCrops = entities;
	}
	
	private JsArray<ZoneEntity> m_EntityZones = null;
	public JsArray<ZoneEntity> getEntityZones()
	{
		return m_EntityZones;
	}
	public void setEntityZones(JsArray<ZoneEntity> entities)
	{
		m_EntityZones = entities;
	}
	
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
	/*
	public void InitializeComponents()
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
	*/
	
	public void ReZoom(int zoomModifier)
	{
		//Window.alert(Integer.toString(zoomModifier));
		
		if (m_zoom <= 1 && m_zoom >= -1)
		{
			if (zoomModifier < 0) m_zoom = -1 + zoomModifier;
			else m_zoom = 1 + zoomModifier;
		}
		else
		{
			m_zoom = m_zoom + zoomModifier;
		}
				
		for(int i=0; i<m_zones.size(); i++)
		{
			for(int j=0; j<m_zones.get(i).getCrops().size(); j++)
			{
				m_zones.get(i).getCrops().get(j).Zoom(m_zoom);
			}
			m_zones.get(i).Zoom(m_zoom);
		}
		Zoom(m_zoom);
	}
	
	public void Zoom(int zoom)
	{
		//Window.alert(Integer.toString(zoom));
		
		if (zoom == 0) zoom = 1;
		
		if (zoom >= 0)
		{
			super.setWidth(getGardenEntity().getWidth() * zoom);
			super.setHeight(getGardenEntity().getHeight() * zoom);
		}
		else
		{
			super.setWidth(getGardenEntity().getWidth() / Math.abs(zoom));
			super.setHeight(getGardenEntity().getHeight() / Math.abs(zoom));
		}
	}
	
	//Adds a crop
	public void AddCrop(int id, int numPlants, int x, int y, PlantEntity p, ZoneGraphic z)
	{
		//m_EntityCrops.push(newEntity);
		CropGraphic c = new CropGraphic(id, numPlants, x, y, p, z, this);
		c.addMouseDownHandler(new GraphicObjectMouseDownHandler());
		c.addMouseUpHandler(new GraphicObjectMouseUpHandler());
		c.addMouseMoveHandler(new GraphicObjectMouseMoveHandler());
		z.getCrops().add(c);
		this.add(c);
	}
	
	//Adds a crop
	public void CreateZonesAndCrops(JsArray<PlantEntity> plants)
	{
		//Loads zones and crops to the garden
		for(int i=0; i<m_EntityZones.length(); i++)
		{
			ZoneGraphic z = new ZoneGraphic(m_EntityZones.get(i), this);
			z.addMouseDownHandler(new GraphicObjectMouseDownHandler());
			z.addMouseUpHandler(new GraphicObjectMouseUpHandler());
			z.addMouseMoveHandler(new GraphicObjectMouseMoveHandler());
			this.add(z);
			
			for(int j=0; j<m_EntityCrops.length(); j++)
			{
				if (m_EntityCrops.get(j).getZoneId() == m_EntityZones.get(i).getId())
				{
					//Search the plant of the crop
					for(int k=0; k<plants.length(); k++)
					{
						if (plants.get(k).getId() == m_EntityCrops.get(j).getPlantId())
						{
							//Create a CropGraphic and add to the graphic zone
							//CropGraphic c = new CropGraphic(m_EntityCrops.get(j), plants.get(k), z, this);
							CropGraphic c = new CropGraphic(m_EntityCrops.get(j).getId(), m_EntityCrops.get(j).getNumPlants(), m_EntityCrops.get(j).getPointX(), m_EntityCrops.get(j).getPointY(), plants.get(k), z, this);
							c.addMouseDownHandler(new GraphicObjectMouseDownHandler());
							c.addMouseUpHandler(new GraphicObjectMouseUpHandler());
							c.addMouseMoveHandler(new GraphicObjectMouseMoveHandler());
							z.getCrops().add(c);
							this.add(c);
							break;
						}
					}
				}
			}
			
			this.getZones().add(z);
		}
	}
}
	

