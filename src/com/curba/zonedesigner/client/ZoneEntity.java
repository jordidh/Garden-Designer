package com.curba.zonedesigner.client;

import com.google.gwt.core.client.JavaScriptObject;

public class ZoneEntity extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected ZoneEntity() {}

	  // JSNI methods to get stock data.
	  public final native int getId() /*-{ return this.id; }-*/;
	  public final native int getGardenId() /*-{ return this.gardenId; }-*/;
	  public final native int getZoneTypeId() /*-{ return this.zoneTypeId; }-*/;
	  //public final native String getZoneTypeName() /*-{ return this.zone_type_name; }-*/;
	  //public final native String getZoneTypeDescription() /*-{ return this.zone_type_description; }-*/;
	  public final native String getName() /*-{ return this.name; }-*/;
	  public final native String getDescription() /*-{ return this.description; }-*/;
	  public final native int getInitialPointX() /*-{ return this.initialPointX; }-*/;
	  public final native int getInitialPointY() /*-{ return this.initialPointY; }-*/;
	  public final native int getFinalPointX() /*-{ return this.finalPointX; }-*/;
	  public final native int getFinalPointY() /*-{ return this.finalPointY; }-*/;
	  public final native int getDepth() /*-{ return this.depth; }-*/;
	  //public final native boolean getDeleted() /*-{ return this.deleted; }-*/;

	  public final native void setInitialPointX(int x) /*-{ this.initialPointX = x; }-*/;
	  public final native void setInitialPointY(int y) /*-{ this.initialPointY = y; }-*/;
	  public final native void setFinalPointX(int x) /*-{ this.finalPointX = x; }-*/;
	  public final native void setFinalPointY(int y) /*-{ this.finalPointY = y; }-*/;
	  public final native void setDepth(int d) /*-{ this.depth = d; }-*/;
	  //public final native void setDeleted(int deleted) /*-{ this.deleted = deleted; }-*/;
	  
	  public final String toJsonString()
	  {
		  return "{ id: " + Integer.toString(getId()) + 
		  			", garden_id: " + Integer.toString(getGardenId()) + 
		  			", zone_type_id: " + Integer.toString(getZoneTypeId()) + 
		  			//", zone_type_name: \"" + getZoneTypeName() + 
		  			//"\", zone_type_description: \"" + getZoneTypeDescription() +
		  			//"\"
		  			", name: \"" + getName() +
		  			"\", description: \"" + getDescription() + 
		  			"\", initial_point_x: " + Integer.toString(getInitialPointX()) + 
		  			", initial_point_y: " + Integer.toString(getInitialPointY()) + 
		  			", final_point_x: " + Integer.toString(getFinalPointX()) + 
		  			", final_point_y: " + Integer.toString(getFinalPointY()) +
		  			", depth: " + Integer.toString(getDepth()) +
		  			//", deleted: " + getDeleted() +
		  			"}";
	  }


	  // Non-JSNI method to return the Width in mm
	  public final int getWidth() {
	    return Math.abs(getFinalPointX() - getInitialPointX());
	  }
	  // Non-JSNI method to return the Heigh in mm
	  public final int getHeigh() {
		return Math.abs(getFinalPointY() - getInitialPointY());
	  }
	  // Non-JSNI method to return the Area in m2
	  public final double getArea() {
	    return getHeigh() * getWidth() / 1000000.0;
	  }
	  // Non-JSNI method to return the Volume in m3
	  public final double getVolume() {
	    return getArea() * getDepth() / 1000.0;
	  }
	  // Non-JSNI method to return the Volume in liters
	  public final double getLiters() {
	    return getArea() * getDepth();
	  }
}