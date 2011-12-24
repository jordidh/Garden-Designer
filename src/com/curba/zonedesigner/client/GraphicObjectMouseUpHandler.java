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
				deleteZoneOrCrop(event);
				break;
			case PROPS_CROP:
				propsZoneOrCrop(event);
				break;
			case PRUNE_CROP:
				addNewAction(event, GardenDesigner.m_SelectedAction);
				break;
			case COLLECT_CROP:
				addNewAction(event, GardenDesigner.m_SelectedAction);
				break;
			case NEW_ZONE:
				addNewZone(event);
				break;
			case DELETE_ZONE:
				deleteZoneOrCrop(event);
				break;
			case PROPS_ZONE:
				propsZoneOrCrop(event);
				break;
			case WATERING_ZONE:
				addNewAction(event, GardenDesigner.m_SelectedAction);
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
					//z.setInitialPointX(event.getRelativeX(garden.getElement()) - (z.getWidth() / 2));
					//z.setInitialPointY(event.getRelativeY(garden.getElement()) - (z.getHeight() / 2));
					garden.MoveZone(z, event.getRelativeX(garden.getElement()), event.getRelativeY(garden.getElement()));
					//z.setFinalPointX(z.getInitialPointX() + z.getWidth());
					//z.setFinalPointY(z.getInitialPointY() + z.getHeight());
				}
				else if (garden.getSelectedGraphicObject().getClass().getName() == CropGraphic.class.getName())
				{
					CropGraphic c = (CropGraphic)garden.getSelectedGraphicObject();
					//c.getCropEntity().setPointX(event.getRelativeX(garden.getElement()) - (c.getWidth() / 2));
					//c.getCropEntity().setPointY(event.getRelativeY(garden.getElement()) - (c.getHeight() / 2));
					//c.setPointX(event.getRelativeX(garden.getElement()) - (c.getWidth() / 2));
					//c.setPointY(event.getRelativeY(garden.getElement()) - (c.getHeight() / 2));
					garden.MoveCrop(c, event.getRelativeX(garden.getElement()), event.getRelativeY(garden.getElement()));
				}
			}
			
			garden.setSelectedGraphicObject(null);
		}
	}
	
	//Function that creates a new crop
	private void addNewCrop(MouseUpEvent event)
	{
		GardenGraphic garden = null;
		
		try
		{
			//Adds a crop
			if (event.getSource().getClass().getName() == ZoneGraphic.class.getName())
			{
				ZoneGraphic z = (ZoneGraphic)event.getSource();
				garden = z.getGarden();
				
				int newX = event.getRelativeX(garden.getElement());// - (c.getWidth() / 2);
				int newY = event.getRelativeY(garden.getElement());// - (c.getHeight() / 2);
				//int newX = event.getX();
				//int newY = event.getY();
				
				CropGraphic c = garden.AddCrop(-1, CropCreationDialog.NumPlants, newX, newY, CropCreationDialog.SelectedPlant, z);
				
				Window.alert("Crop created successfully: " + c.toString());
			}
		}
		catch(Exception ex)
		{
			Window.alert(ex.toString());
		}
		
	}

	//Function that creates a new zone
	private void addNewZone(MouseUpEvent event)
	{
		GardenGraphic garden = null;
		
		try
		{
			if (event.getSource().getClass().getName() == GardenGraphic.class.getName())
			{
				garden = (GardenGraphic)event.getSource();
				
				int newX = event.getRelativeX(garden.getElement());// - (c.getWidth() / 2);
				int newY = event.getRelativeY(garden.getElement());// - (c.getHeight() / 2);
				
				ZoneGraphic z = garden.AddZone(-1, ZoneCreationDialog.Name, ZoneCreationDialog.Description, newX, newY, 
						ZoneCreationDialog.Height, ZoneCreationDialog.Width, ZoneCreationDialog.Depth, ZoneCreationDialog.SelectedZoneType, garden);
				
				Window.alert("Zone created successfully: " + z.toString());
			}
		}
		catch(Exception ex)
		{
			Window.alert(ex.toString());
		}
		
	}
		
	//Function that deletes a crop
	private void deleteZoneOrCrop(MouseUpEvent event) {

		GardenGraphic garden = null;
		
		try
		{
			//Deletes a crop
			if (event.getSource().getClass().getName() == CropGraphic.class.getName())
			{
				CropGraphic c = (CropGraphic)event.getSource();
				garden = c.getGarden();

				if (Window.confirm("Are you sure you want to remove the crop \"" + c.getPlantName() + "\"?"))
				{
					garden.DeleteCrop(c);
					
					Window.alert("Crop deleted successfully: " + c.toString());
				}
			}
			//Deletes a zone
			else if (event.getSource().getClass().getName() == ZoneGraphic.class.getName())
			{
				ZoneGraphic z = (ZoneGraphic)event.getSource();
				garden = z.getGarden();

				if (Window.confirm("Are you sure you want to remove the zone \"" + z.getName() + "\"?"))
				{					
					garden.DeleteZone(z);
					
					Window.alert("Zone deleted successfully: " + z.toString());
				}
			}
		}
		catch(Exception ex)
		{
			Window.alert(ex.toString());
		}
	}

	//Shows properties to modify a zone or crop
	private void propsZoneOrCrop(MouseUpEvent event)
	{
		GardenGraphic garden = null;
		
		try
		{
			//Shows the properties of a crop
			if (event.getSource().getClass().getName() == CropGraphic.class.getName())
			{
				CropGraphic c = (CropGraphic)event.getSource();
				garden = c.getGarden();
				
				CropCreationDialog diag = new CropCreationDialog();
				//CropCreationDialog.SelectedPlant = c.getPlantEntity();
				//CropCreationDialog.NumPlants = c.getNumPlants();
				diag.ShowDialog(c);
			}
			//Shows the properties of a zone
			else if (event.getSource().getClass().getName() == ZoneGraphic.class.getName())
			{
				ZoneGraphic z = (ZoneGraphic)event.getSource();
				garden = z.getGarden();
				
				ZoneCreationDialog diag = new ZoneCreationDialog();
				//ZoneCreationDialog.Name = z.getName();
				//ZoneCreationDialog.Description = z.getDescription();
				//ZoneCreationDialog.Width = z.getWidth();
				//ZoneCreationDialog.Height = z.getHeight();
				//ZoneCreationDialog.Depth = z.getDepth();
				diag.ShowDialog(z);
			}
		}
		catch(Exception ex)
		{
			Window.alert(ex.toString());
		}
	}
	
	//Does an action
	public void addNewAction(MouseUpEvent event, GardenAction action)
	{
		try
		{
			if (event.getSource().getClass().getName() == ZoneGraphic.class.getName())
			{
				if (action == GardenAction.WATERING_ZONE)
				{
					
				}
			}
			else if (event.getSource().getClass().getName() == CropGraphic.class.getName())
			{
				CropGraphic c = (CropGraphic)event.getSource();
				
				switch(action)
				{
				case PRUNE_CROP:
					break;
				case COLLECT_CROP:
					CropCollectionDialog diag = new CropCollectionDialog(c, GardenDesigner.m_ActionTypes.get(1));
					diag.ShowDialog();
					break;
				}
			}
		}
		catch(Exception ex)
		{
			Window.alert(ex.toString());
		}
	}
}