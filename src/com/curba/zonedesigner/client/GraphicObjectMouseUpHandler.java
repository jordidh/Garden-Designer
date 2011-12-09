package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;

//Class that saves the location in the Entity of the element moved
public class GraphicObjectMouseUpHandler implements MouseUpHandler {

	@Override
	public void onMouseUp(MouseUpEvent event) {
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
					z.getZoneEntity().setInitialPointX(event.getRelativeX(garden.getElement()) - (z.getWidth() / 2));
					z.getZoneEntity().setInitialPointY(event.getRelativeY(garden.getElement()) - (z.getHeight() / 2));
					z.getZoneEntity().setFinalPointX(z.getZoneEntity().getInitialPointX() + z.getWidth());
					z.getZoneEntity().setFinalPointY(z.getZoneEntity().getInitialPointY() + z.getHeight());
				}
				else if (garden.getSelectedGraphicObject().getClass().getName() == CropGraphic.class.getName())
				{
					CropGraphic c = (CropGraphic)garden.getSelectedGraphicObject();
					c.getCropEntity().setPointX(event.getRelativeX(garden.getElement()) - (c.getWidth() / 2));
					c.getCropEntity().setPointY(event.getRelativeY(garden.getElement()) - (c.getHeight() / 2));
				}
			}
			
			garden.setSelectedGraphicObject(null);
		}
		
		/*
		if (event.getSource().getClass().getName() == ZoneGraphic.class.getName())
		{
			ZoneGraphic z = (ZoneGraphic)event.getSource();
			z.getGarden().setSelectedGraphicObject(null);
			z.getZoneEntity().setInitialPointX(event.getRelativeX(z.getGarden().getElement()) - (z.getWidth() / 2));
			z.getZoneEntity().setInitialPointY(event.getRelativeY(z.getGarden().getElement()) - (z.getHeight() / 2));
			z.getZoneEntity().setFinalPointX(z.getZoneEntity().getInitialPointX() + z.getWidth());
			z.getZoneEntity().setFinalPointY(z.getZoneEntity().getInitialPointY() + z.getHeight());
			//Window.alert(Integer.toString(event.getRelativeX(z.getGarden().getElement())) + "," + Integer.toString(event.getRelativeY(z.getGarden().getElement())));
			//Window.alert("Zone mouse up in " + Integer.toString(z.getZoneEntity().getInitialPointX()) + "," + Integer.toString(z.getZoneEntity().getInitialPointY()));
			//return;
		}
		else if (event.getSource().getClass().getName() == CropGraphic.class.getName())
		{
			CropGraphic c = (CropGraphic)event.getSource();
			c.getGarden().setSelectedGraphicObject(null);
			c.getCropEntity().setPointX(event.getRelativeX(c.getGarden().getElement()) - (c.getWidth() / 2));
			c.getCropEntity().setPointY(event.getRelativeY(c.getGarden().getElement()) - (c.getHeight() / 2));
			//Window.alert(Integer.toString(event.getRelativeX(c.getGarden().getElement())) + "," + Integer.toString(event.getRelativeY(c.getGarden().getElement())));
			//Window.alert("Crop mouse up in " + Integer.toString(c.getCropEntity().getPointX()) + "," + Integer.toString(c.getCropEntity().getPointY()));
			//return;
		}
		else if (event.getSource().getClass().getName() == GardenGraphic.class.getName())
		{
			//GardenGraphic g = (GardenGraphic)event.getSource();
			//g.setSelectedGraphicObject(null);
			//Window.alert("Garden mouse up: " + event.getSource().getClass().getName());
		}
		//Window.alert("??? mouse down: " + event.getSource().getClass().getName());
		 * 
		 */
	}

}
