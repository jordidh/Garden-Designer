package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.Window;

//Selects the clicked crop or zone
public class GraphicObjectMouseDownHandler implements MouseDownHandler {

	@Override
	public void onMouseDown(MouseDownEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource().getClass().getName() == ZoneGraphic.class.getName())
		{
			ZoneGraphic z = (ZoneGraphic)event.getSource();
			z.getGarden().setSelectedGraphicObject(z);
			//Window.alert("Zone mouse down: " + event.getSource().getClass().getName());
			//return;
		}
		else if (event.getSource().getClass().getName() == CropGraphic.class.getName())
		{
			CropGraphic c = (CropGraphic)event.getSource();
			c.getGarden().setSelectedGraphicObject(c);
			//Window.alert("Crop mouse down: " + event.getSource().getClass().getName());
			//return;
		}
		else if (event.getSource().getClass().getName() == GardenGraphic.class.getName())
		{
			//GardenGraphic g = (GardenGraphic)event.getSource();
			//g.setSelectedGraphicObject(null);
			//Window.alert("Garden mouse down: " + event.getSource().getClass().getName());
		}
		//Window.alert("??? mouse down: " + event.getSource().getClass().getName());
	}
}