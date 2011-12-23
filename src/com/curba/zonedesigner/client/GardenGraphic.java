package com.curba.zonedesigner.client;

import org.vaadin.gwtgraphics.client.DrawingArea;
import org.vaadin.gwtgraphics.client.Image;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;

import java.util.List;
import java.util.ArrayList;

public class GardenGraphic extends DrawingArea {
	private RegionEntity m_region;
	private GardenTypeEntity m_gardenType;
	private GardenEntity m_entity;
	ArrayList<ZoneGraphic> m_zones = new ArrayList<ZoneGraphic>();
	private int m_zoom = 0;
	
	private ArrayList<CropGraphic> m_CropsDeleted = new ArrayList<CropGraphic>();
	private ArrayList<ZoneGraphic> m_ZonesDeleted = new ArrayList<ZoneGraphic>();
	
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
	
	public void ReZoom(int zoomModifier)
	{
		if (m_zoom <= 1 && m_zoom >= -1)
		{
			if (zoomModifier < 0) m_zoom = -1 + zoomModifier;
			else m_zoom = 1 + zoomModifier;
		}
		else
		{
			m_zoom = m_zoom + zoomModifier;
		}
		
		//String msg = this.toString() + " Zoom=" + Integer.toString(m_zoom);
		for(int i=0; i<m_zones.size(); i++)
		{
			//msg += " Crops=";
			for(int j=0; j<m_zones.get(i).getCrops().size(); j++)
			{
				m_zones.get(i).getCrops().get(j).Zoom(m_zoom);
				
				//msg += m_zones.get(i).getCrops().get(j).toString() + " ";
			}
			m_zones.get(i).Zoom(m_zoom);
			
			//msg += m_zones.get(i).toString() + " ";
		}
		Zoom(m_zoom);
		
		//Window.alert(msg);
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
	
	public int Unzoom(int value)
	{
		if (m_zoom == 0) m_zoom = 1;
		
		if (m_zoom <= 0)
		{
			return value * Math.abs(m_zoom);
		}
		else
		{
			return value / Math.abs(m_zoom);
		}
	}
	
	public void MoveCrop(CropGraphic crop, int zoomedX, int zoomedY)
	{
		ZoneGraphic zone = crop.getZone();
		
		int newX = zoomedX - (crop.getWidth() / 2);
		int newY = zoomedY - (crop.getHeight() / 2);
		
		if (newX < zone.getX()) newX = zone.getX();
		if (newX + crop.getWidth() > zone.getX() + zone.getWidth()) newX = zone.getX() + zone.getWidth() - crop.getWidth();
		if (newX >= zone.getX() && (newX + crop.getWidth() <= zone.getX() + zone.getWidth()))
		{
			crop.setX(newX);
		}
		
		if (newY < zone.getY()) newY = zone.getY();
		if (newY + crop.getHeight() > zone.getY() + zone.getHeight()) newY = zone.getY() + zone.getHeight() - crop.getHeight();				
		if (newY >= zone.getY() && (newY + crop.getHeight() <= zone.getY() + zone.getHeight()))
		{
		    crop.setY(newY);
		}
		
		crop.setPointX(Unzoom(newX));
		crop.setPointY(Unzoom(newY));
		
		//Unzoom x and y
		//int x = Unzoom(zoomedX);
		//int y = Unzoom(zoomedY);
		//		
		//crop.setPointX(x - (Unzoom(crop.getWidth()) / 2));
		//crop.setPointY(y - (Unzoom(crop.getHeight()) / 2));
	}
	
	public void MoveZone(ZoneGraphic zone, int zoomedX, int zoomedY)
	{
		GardenGraphic garden = zone.getGarden();
		
		//Move the zone and his crops
		int oldX = zone.getX();
		int oldY = zone.getY();
		int newX = zoomedX - (zone.getWidth() / 2);
		int newY = zoomedY - (zone.getHeight() / 2);
		
		if (newX < 0) newX = 0;
		if (newX + zone.getWidth() > garden.getWidth()) newX = garden.getWidth() - zone.getWidth();  
		if (newX >= 0 && (newX + zone.getWidth() <= garden.getWidth()))
		{
			zone.setX(newX);
		    //Move each zone crop
		    for (int i=0; i<zone.getCrops().size(); i++)
		    {
		    	CropGraphic c = zone.getCrops().get(i);
		    	c.setX(c.getX() + newX - oldX);
		    	
		    	c.setPointX(Unzoom(c.getX() + newX - oldX));
		    }
		}
		
		if (newY < 0) newY = 0;
		if (newY + zone.getHeight() > garden.getHeight()) newY = garden.getHeight() - zone.getHeight();  
		if (newY >= 0 && (newY + zone.getHeight() <= garden.getHeight()))
		{
		    zone.setY(newY);
		    //Move each zone crop
		    for (int i=0; i<zone.getCrops().size(); i++)
		    {
		    	CropGraphic c = zone.getCrops().get(i);
		    	c.setY(c.getY() + newY - oldY);
		    	
		    	c.setPointY(Unzoom(c.getY() + newY - oldY));
		    }
		}
		
		zone.setInitialPointX(Unzoom(newX));
		zone.setInitialPointY(Unzoom(newY));
		
		//Unzoom x and y
		//int x = Unzoom(zoomedX);
		//int y = Unzoom(zoomedY);
		//		
		//zone.setInitialPointX(x - (Unzoom(zone.getWidth()) / 2));
		//zone.setInitialPointY(y - (Unzoom(zone.getHeight()) / 2));
	}
	
	//Adds a crop
	public CropGraphic AddCrop(int id, int numPlants, int zoomedX, int zoomedY, PlantEntity p, ZoneGraphic z)
	{
		//Unzoom x and y
		int x = Unzoom(zoomedX);
		int y = Unzoom(zoomedY);
		
		//Create crop
		CropGraphic c = new CropGraphic(id, numPlants, x, y, p, z, this);
		c.addMouseDownHandler(new GraphicObjectMouseDownHandler());
		c.addMouseUpHandler(new GraphicObjectMouseUpHandler());
		c.addMouseMoveHandler(new GraphicObjectMouseMoveHandler());
		z.getCrops().add(c);
		c.Zoom(m_zoom);
		
		if (!c.getIsDeleted())
		{
			this.add(c);
		}
		
		return c;
	}
	
	//Adds a crop
	public ZoneGraphic AddZone(int id, String name, String description, int zoomedX, int zoomedY, int heigh, int width, int depth, ZoneTypeEntity t, GardenGraphic g)
	{
		//Unzoom x and y
		int x = Unzoom(zoomedX);
		int y = Unzoom(zoomedY);
		
		//Create zone
		ZoneGraphic z = new ZoneGraphic(-1, name, description, x, y, heigh, width, depth, t, g);
		z.addMouseDownHandler(new GraphicObjectMouseDownHandler());
		z.addMouseUpHandler(new GraphicObjectMouseUpHandler());
		z.addMouseMoveHandler(new GraphicObjectMouseMoveHandler());
		g.getZones().add(z);
		z.Zoom(m_zoom);
		
		if (!z.getIsDeleted())
		{
			this.add(z);
		}
		
		return z;
	}
	
	//Deletes a crop
	public void DeleteCrop(CropGraphic c)
	{
		c.setDeleted(true);
		this.remove(c);
		
		//Save the crop to delete from DB
		if (!c.getIsNew())
		{
			m_CropsDeleted.add(c);
		}
	}
	
	//Deletes a zone
	public void DeleteZone(ZoneGraphic z)
	{
		//Deletes all zone crops
		for(int i=0; i<z.m_crops.size(); i++)
		{
			DeleteCrop(z.m_crops.get(i));
		}
		
		//Deletes the zone
		z.setDeleted(true);
		this.remove(z);
		
		//Save the zone to delete from DB
		if (!z.getIsNew())
		{
			m_ZonesDeleted.add(z);
		}
	}
	
	//Adds a crop
	public void CreateZonesAndCrops(JsArray<PlantEntity> plants)
	{
		//Loads zones and crops to the garden
		for(int i=0; i<m_EntityZones.length(); i++)
		{
			ZoneTypeEntity zoneTypeEntity = null;
			for(int izte=0; izte<GardenDesigner.m_ZoneTypes.length(); izte++)
			{
				if (GardenDesigner.m_ZoneTypes.get(izte).getId() == m_EntityZones.get(i).getId())
				{
					zoneTypeEntity = GardenDesigner.m_ZoneTypes.get(izte);
					break;
				}
			}
			
			if (zoneTypeEntity == null)
			{
				Window.alert("Error loading zones: zone type " + Integer.toString(m_EntityZones.get(i).getId()) + " not found");
				return;
			}
			
			ZoneGraphic z = new ZoneGraphic(m_EntityZones.get(i), zoneTypeEntity, this);
			z.addMouseDownHandler(new GraphicObjectMouseDownHandler());
			z.addMouseUpHandler(new GraphicObjectMouseUpHandler());
			z.addMouseMoveHandler(new GraphicObjectMouseMoveHandler());
			if (!z.getIsDeleted())
			{
				this.add(z);
			}
			
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
							CropGraphic c = new CropGraphic(m_EntityCrops.get(j), plants.get(k), z, this);
							//CropGraphic c = new CropGraphic(m_EntityCrops.get(j).getId(), m_EntityCrops.get(j).getNumPlants(), m_EntityCrops.get(j).getPointX(), m_EntityCrops.get(j).getPointY(), plants.get(k), z, this);
							c.addMouseDownHandler(new GraphicObjectMouseDownHandler());
							c.addMouseUpHandler(new GraphicObjectMouseUpHandler());
							c.addMouseMoveHandler(new GraphicObjectMouseMoveHandler());
							z.getCrops().add(c);
							if (!c.getIsDeleted())
							{
								this.add(c);
							}
							break;
						}
					}
				}
			}
			
			this.getZones().add(z);
		}
	}
	
	@Override 
	public String toString()
	{
		return "[Garden (" + Integer.toString(m_entity.getWidth()) + "x" + Integer.toString(m_entity.getHeight()) + ")]";
	}
}
	

