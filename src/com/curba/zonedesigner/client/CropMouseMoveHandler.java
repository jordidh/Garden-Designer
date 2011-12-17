package com.curba.zonedesigner.client;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;

public class CropMouseMoveHandler implements MouseMoveHandler {

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		
		// TODO Auto-generated method stub
		CropGraphic crop = (CropGraphic)event.getSource();
		
		//For a circle
	    //crop.setX(event.getX());
	    //crop.setY(event.getY());
	    //crop.getCropEntity().setPointX((int)(event.getX() * 1.0 / m_ZoomFactor));
	    //crop.getCropEntity().setPointY((int)(event.getY() * 1.0 / m_ZoomFactor));
	    
		//For a rectangle or a image
	    crop.setX(event.getX() - (crop.getWidth() / 2));
	    crop.setY(event.getY() - (crop.getHeight() / 2));
	    //crop.getCropEntity().setPointX((int)(crop.getX() * 1.0 / 1.0));
	    //crop.getCropEntity().setPointY((int)(crop.getY() * 1.0 / 1.0));
	    crop.setPointX((int)(crop.getX() * 1.0 / 1.0));
	    crop.setPointY((int)(crop.getY() * 1.0 / 1.0));
	}
}