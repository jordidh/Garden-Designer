package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;

public class ToolbarClickHandler implements ClickHandler {
	GardenGraphic m_Garden = null;

	public ToolbarClickHandler(GardenGraphic garden) {
		// TODO Auto-generated constructor stub
		m_Garden = garden;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
		if (event.getSource().getClass().getName() == Button.class.getName())
		{
			Button btn = (Button)event.getSource();
			
			switch(btn.getTabIndex())
			{
				case 0:	//Crop new
					CropCreationDialog diagCrop = new CropCreationDialog();
					diagCrop.ShowDialog(null);
					break;
				case 1:	//Crop or zone delete
					GardenDesigner.m_SelectedAction = GardenAction.DELETE_CROP;
					break;
				case 2:	//crop or zone properties
					GardenDesigner.m_SelectedAction = GardenAction.PROPS_CROP;
					break;
				case 3:	//Crop prune
					GardenDesigner.m_SelectedAction = GardenAction.PRUNE_CROP;
					break;
				case 4:	//Crop harvest
					GardenDesigner.m_SelectedAction = GardenAction.HARVEST_CROP;
					break;
				case 5:	//Zone new
					ZoneCreationDialog diagZone = new ZoneCreationDialog();
					diagZone.ShowDialog(null);
					break;
				case 6:	//Zone watering
					GardenDesigner.m_SelectedAction = GardenAction.WATERING_ZONE;
					break;
				case 7:	//Save
					GardenDesigner.m_SelectedAction = GardenAction.SAVE;
					break;
				case 8:	//Zoom in
					GardenDesigner.m_SelectedAction = GardenAction.ZOOM_IN;
					m_Garden.ReZoom(1);
					GardenDesigner.m_SelectedAction = GardenAction.NONE;
					break;
				case 9:	//Zoom out
					GardenDesigner.m_SelectedAction = GardenAction.ZOOM_OUT;
					m_Garden.ReZoom(-1);
					GardenDesigner.m_SelectedAction = GardenAction.NONE;
					break;
				default:	//Unknown
					GardenDesigner.m_SelectedAction = GardenAction.NONE;
					Window.alert(btn.getText() + ", " + btn.getTitle());
					break;
			}
		}
		else if (event.getSource().getClass().getName() == Image.class.getName())
		{
			Image img = (Image)event.getSource();

			if (img.getAltText().equals("CropNew"))
			{
				CropCreationDialog diag = new CropCreationDialog();
				diag.ShowDialog(null);
			}
			else if (img.getAltText().equals("CropOrZoneDel"))
			{
				GardenDesigner.m_SelectedAction = GardenAction.DELETE_CROP;
			}
			else if (img.getAltText().equals("CropOrZoneProp"))
			{
				GardenDesigner.m_SelectedAction = GardenAction.PROPS_CROP;
			}
			else if (img.getAltText().equals("CropPrune"))
			{
				GardenDesigner.m_SelectedAction = GardenAction.PRUNE_CROP;
			}
			else if (img.getAltText().equals("CropHarvest"))
			{
				GardenDesigner.m_SelectedAction = GardenAction.HARVEST_CROP;
			}
			else if (img.getAltText().equals("ZoneNew"))
			{
				ZoneCreationDialog diag = new ZoneCreationDialog();
				diag.ShowDialog(null);
			}
			else if (img.getAltText().equals("ZoneWatering"))
			{
				GardenDesigner.m_SelectedAction = GardenAction.WATERING_ZONE;
			}
			else if (img.getAltText().equals("Save"))
			{
				GardenDesigner.m_SelectedAction = GardenAction.SAVE;
			}
			else if (img.getAltText().equals("ZoomIn"))
			{
				GardenDesigner.m_SelectedAction = GardenAction.ZOOM_IN;
				m_Garden.ReZoom(1);
				GardenDesigner.m_SelectedAction = GardenAction.NONE;
			}
			else if (img.getAltText().equals("ZoomOut"))
			{
				GardenDesigner.m_SelectedAction = GardenAction.ZOOM_OUT;
				m_Garden.ReZoom(-1);
				GardenDesigner.m_SelectedAction = GardenAction.NONE;
			}
			else
			{
				GardenDesigner.m_SelectedAction = GardenAction.NONE;
				Window.alert("Unknown image clicked: " + img.getAltText());
			}			
		}
		else
		{
			Window.alert("Click handler from Object class unknown: " + event.getSource().getClass().getName());
		}
		
		/*
		if (btn.getTitle().equals("CropNew"))
		{
			//GardenDesigner.m_SelectedAction = GardenAction.NEW_CROP;
			CropCreationDialog diag = new CropCreationDialog();
			diag.ShowDialog(null);
		}
		else if (btn.getTitle().equals("CropOrZoneDelete"))
		{
			GardenDesigner.m_SelectedAction = GardenAction.DELETE_CROP;
		}
		else if (btn.getTitle().equals("CropOrZoneProps"))
		{
			GardenDesigner.m_SelectedAction = GardenAction.PROPS_CROP;
		}
//		else if (btn.getTitle().equals("CropDelete"))
//		{
//			GardenDesigner.m_SelectedAction = GardenAction.DELETE_CROP;
//		}
//		else if (btn.getTitle().equals("CropProps"))
//		{
//			GardenDesigner.m_SelectedAction = GardenAction.PROPS_CROP;
//		}
		else if (btn.getTitle().equals("CropPrune"))
		{
			GardenDesigner.m_SelectedAction = GardenAction.PRUNE_CROP;
		}
		else if (btn.getTitle().equals("CropHarvest"))
		{
			GardenDesigner.m_SelectedAction = GardenAction.HARVEST_CROP;
		}
		else if (btn.getTitle().equals("ZoneNew"))
		{
			//GardenDesigner.m_SelectedAction = GardenAction.NEW_ZONE;
			ZoneCreationDialog diag = new ZoneCreationDialog();
			diag.ShowDialog(null);
		}
//		else if (btn.getTitle().equals("ZoneDelete"))
//		{
//			GardenDesigner.m_SelectedAction = GardenAction.DELETE_ZONE;
//		}
//		else if (btn.getTitle().equals("ZoneProps"))
//		{
//			GardenDesigner.m_SelectedAction = GardenAction.PROPS_ZONE;
//		}
		else if (btn.getTitle().equals("ZoneWatering"))
		{
			GardenDesigner.m_SelectedAction = GardenAction.WATERING_ZONE;
		}
//		else if (btn.getTitle().equals("GardenProps"))
//		{
//			GardenDesigner.m_SelectedAction = GardenAction.PROPS_GARDEN;
//		}
		else if (btn.getTitle().equals("Save"))
		{
			GardenDesigner.m_SelectedAction = GardenAction.SAVE;
		}
		else if (btn.getTitle().equals("ZoomIn"))
		{
			GardenDesigner.m_SelectedAction = GardenAction.ZOOM_IN;
			m_Garden.ReZoom(1);
			GardenDesigner.m_SelectedAction = GardenAction.NONE;
		}
		else if (btn.getTitle().equals("ZoomOut"))
		{
			GardenDesigner.m_SelectedAction = GardenAction.ZOOM_OUT;
			m_Garden.ReZoom(-1);
			GardenDesigner.m_SelectedAction = GardenAction.NONE;
		}
		else
		{
			GardenDesigner.m_SelectedAction = GardenAction.NONE;
			Window.alert(btn.getText() + ", " + btn.getTitle());
		}
		*/
	}
}