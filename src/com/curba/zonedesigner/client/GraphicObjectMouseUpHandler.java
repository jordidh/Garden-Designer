package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.Window;

public class GraphicObjectMouseUpHandler implements MouseUpHandler {

	@Override
	public void onMouseUp(MouseUpEvent event) {
		switch(GardenDesigner.m_SelectedAction) {
			case NONE:
				moveZoneOrCropTo(event);
				break;
			case NEW_CROP:
				addNewCrop(event);
				break;
			case DELETE_CROP:
				break;
			case PROPS_CROP:
				break;
			case PRUNE_CROP:
				break;
			case COLLECT_CROP:
				break;
			case NEW_ZONE:
				break;
			case DELETE_ZONE:
				break;
			case PROPS_ZONE:
				break;
			case WATERING_ZONE:
				break;
			case PROPS_GARDEN:
				break;
			case SAVE:
				break;
			case ZOOM_IN:
				break;
			case ZOOM_OUT:
				break;
			default:
				Window.alert("GardenAction unknown (" + GardenDesigner.m_SelectedAction.toString() +")");
				break;
		}
		
		GardenDesigner.m_SelectedAction = GardenAction.NONE;
	}

	//Function that saves the location in the Entity of the element moved
	private void moveZoneOrCropTo(MouseUpEvent event) {
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
	}
	
	//Function that adds a new crop into a zone
	private void addNewCrop(MouseUpEvent event) {
		GardenGraphic garden = null;
		
		//Get the zone object
		if (event.getSource().getClass().getName() == ZoneGraphic.class.getName())
		{
			ZoneGraphic z = (ZoneGraphic)event.getSource();
			garden = z.getGarden();
			
			
		}
	}
}
