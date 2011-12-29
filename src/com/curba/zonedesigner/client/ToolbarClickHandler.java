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
					m_Garden.Save(GardenDesigner.UriToSave);
					GardenDesigner.m_SelectedAction = GardenAction.NONE;
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
				m_Garden.Save(GardenDesigner.UriToSave);
				GardenDesigner.m_SelectedAction = GardenAction.NONE;
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
	}
}