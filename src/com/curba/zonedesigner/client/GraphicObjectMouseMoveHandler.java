package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;

//Class that moves a element checking not go out his container
public class GraphicObjectMouseMoveHandler implements MouseMoveHandler {

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		if (GardenDesigner.m_SelectedAction != GardenAction.NONE)
		{
			return;
		}
		
		// TODO Auto-generated method stub
		GardenGraphic garden = null;
		
		//Get the garden object
		if (event.getSource().getClass().getName() == ZoneGraphic.class.getName())
		{
			ZoneGraphic z = (ZoneGraphic)event.getSource();
			garden = z.getGarden();
		}
		else if (event.getSource().getClass().getName() == CropGraphic.class.getName())
		{
			CropGraphic c = (CropGraphic)event.getSource();
			garden = c.getGarden();
		}
		else if (event.getSource().getClass().getName() == GardenGraphic.class.getName())
		{
			garden = (GardenGraphic)event.getSource();
		}
		
		//Get the movable object and move it
		if (garden != null)
		{
			if (garden.getSelectedGraphicObject() != null)
			{
				if (garden.getSelectedGraphicObject().getClass().getName() == ZoneGraphic.class.getName())
				{
					ZoneGraphic z = (ZoneGraphic)garden.getSelectedGraphicObject();
					garden = z.getGarden();
					
					garden.MoveZone(z, event.getRelativeX(garden.getElement()), event.getRelativeY(garden.getElement()));
					
					/*
					//Move the zone and his crops
					int oldX = z.getX();
					int oldY = z.getY();
					int newX = event.getRelativeX(garden.getElement()) - (z.getWidth() / 2);
					int newY = event.getRelativeY(garden.getElement()) - (z.getHeight() / 2);
					
					if (newX < 0) newX = 0;
					if (newX + z.getWidth() > garden.getWidth()) newX = garden.getWidth() - z.getWidth();  
					if (newX >= 0 && (newX + z.getWidth() <= garden.getWidth()))
					{
						z.setX(newX);
					    //Move each zone crop
					    for (int i=0; i<z.getCrops().size(); i++)
					    {
					    	CropGraphic c = z.getCrops().get(i);
					    	c.setX(c.getX() + newX - oldX);
					    }
					}
					
					if (newY < 0) newY = 0;
					if (newY + z.getHeight() > garden.getHeight()) newY = garden.getHeight() - z.getHeight();  
					if (newY >= 0 && (newY + z.getHeight() <= garden.getHeight()))
					{
					    z.setY(newY);
					    //Move each zone crop
					    for (int i=0; i<z.getCrops().size(); i++)
					    {
					    	CropGraphic c = z.getCrops().get(i);
					    	c.setY(c.getY() + newY - oldY);
					    }
					}
					*/
				}
				else if (garden.getSelectedGraphicObject().getClass().getName() == CropGraphic.class.getName())
				{
					CropGraphic c = (CropGraphic)garden.getSelectedGraphicObject();
					garden = c.getGarden();
					
					garden.MoveCrop(c, event.getRelativeX(garden.getElement()), event.getRelativeY(garden.getElement()));
										
					/*
					//Move the crop
					ZoneGraphic zone = c.getZone();
					int newX = event.getRelativeX(garden.getElement()) - (c.getWidth() / 2);
					int newY = event.getRelativeY(garden.getElement()) - (c.getHeight() / 2);
					
					if (newX < zone.getX()) newX = zone.getX();
					if (newX + c.getWidth() > zone.getX() + zone.getWidth()) newX = zone.getX() + zone.getWidth() - c.getWidth();
					if (newX >= zone.getX() && (newX + c.getWidth() <= zone.getX() + zone.getWidth()))
					{
						c.setX(newX);
					}
					
					if (newY < zone.getY()) newY = zone.getY();
					if (newY + c.getHeight() > zone.getY() + zone.getHeight()) newY = zone.getY() + zone.getHeight() - c.getHeight();				
					if (newY >= zone.getY() && (newY + c.getHeight() <= zone.getY() + zone.getHeight()))
					{
					    c.setY(newY);
					}
					*/
				}
			}
		}


		/*
		if (event.getSource().getClass().getName() == ZoneGraphic.class.getName())
		{
			ZoneGraphic z = (ZoneGraphic)event.getSource();
			if (z.getGarden().getSelectedGraphicObject() != null)
			{
				garden = z.getGarden();
				int oldX = z.getX();
				int oldY = z.getY();
				int newX = event.getRelativeX(garden.getElement()) - (z.getWidth() / 2);
				int newY = event.getRelativeY(garden.getElement()) - (z.getHeight() / 2);
				
				if (newX < 0) newX = 0;
				if (newX + z.getWidth() > garden.getWidth()) newX = garden.getWidth() - z.getWidth();  
				if (newX >= 0 && (newX + z.getWidth() <= garden.getWidth()))
				{
					z.setX(newX);
				    //Move each zone crop
				    for (int i=0; i<z.getCrops().size(); i++)
				    {
				    	CropGraphic c = z.getCrops().get(i);
				    	c.setX(c.getX() + newX - oldX);
				    }
				}
				
				if (newY < 0) newY = 0;
				if (newY + z.getHeight() > garden.getHeight()) newY = garden.getHeight() - z.getHeight();  
				if (newY >= 0 && (newY + z.getHeight() <= garden.getHeight()))
				{
				    z.setY(newY);
				    //Move each zone crop
				    for (int i=0; i<z.getCrops().size(); i++)
				    {
				    	CropGraphic c = z.getCrops().get(i);
				    	c.setY(c.getY() + newY - oldY);
				    }
				}
			}			
		}
		else if (event.getSource().getClass().getName() == CropGraphic.class.getName())
		{
			CropGraphic c = (CropGraphic)event.getSource();
			if (c.getGarden().getSelectedGraphicObject() != null)
			{
				garden = c.getGarden();
				ZoneGraphic zone = c.getZone();
				int newX = event.getRelativeX(garden.getElement()) - (c.getWidth() / 2);
				int newY = event.getRelativeY(garden.getElement()) - (c.getHeight() / 2);
				
				if (newX < zone.getX()) newX = zone.getX();
				if (newX + c.getWidth() > zone.getX() + zone.getWidth()) newX = zone.getX() + zone.getWidth() - c.getWidth();
				if (newX >= zone.getX() && (newX + c.getWidth() <= zone.getX() + zone.getWidth()))
				{
					c.setX(newX);
				}
				
				if (newY < zone.getY()) newY = zone.getY();
				if (newY + c.getHeight() > zone.getY() + zone.getHeight()) newY = zone.getY() + zone.getHeight() - c.getHeight();				
				if (newY >= zone.getY() && (newY + c.getHeight() <= zone.getY() + zone.getHeight()))
				{
				    c.setY(newY);
				}
			}			
		}
		*/
	}
}
