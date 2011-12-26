package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;

public class ToolbarClickHandler implements ClickHandler {
	GardenGraphic m_Garden = null;

	public ToolbarClickHandler(GardenGraphic garden) {
		// TODO Auto-generated constructor stub
		m_Garden = garden;
	}
	
	@Override
	public void onClick(ClickEvent event) {
		// TODO Auto-generated method stub
		Button btn = (Button)event.getSource();
		
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
	}
}