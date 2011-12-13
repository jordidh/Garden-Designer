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
		
		//Window.alert(btn.getParent().getClass().getName());
		
		if (btn.getTitle().equals("ZoomIn"))
		{
			m_Garden.ReZoom(1);
		}
		else if (btn.getTitle().equals("ZoomOut"))
		{
			m_Garden.ReZoom(-1);
		}
		else
		{
			Window.alert(btn.getText() + ", " + btn.getTitle());
		}
	}

}
