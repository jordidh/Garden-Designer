package com.curba.zonedesigner.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

public class GardenEntity extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected GardenEntity() {}
	  
	  // JSNI methods to get stock data.
	  public final native int getId() /*-{ return this.id; }-*/;
	  public final native int getRegionId() /*-{ return this.regionId; }-*/;
	  public final native int getGardenTypeId() /*-{ return this.gardenTypeId; }-*/;
	  public final native String getName() /*-{ return this.name; }-*/;
	  public final native String getDescription() /*-{ return this.description; }-*/;
	  public final native int getLatitude() /*-{ return this.latitude; }-*/;
	  public final native int getLongitude() /*-{ return this.longitude; }-*/;
	  public final native int getWidth() /*-{ return this.width; }-*/;
	  public final native int getHeight() /*-{ return this.height; }-*/;
	  
	  public final native void setWidth(int w) /*-{ this.width = w; }-*/;
	  public final native void setHeight(int h) /*-{ this.height = h; }-*/;
	  
	  public final String toJsonString()
	  {
		  return "{ id: " + getId() + 
		  			", region_id: " + getRegionId() +
		  			", garden_type_id: " + getGardenTypeId() +
		  			", name: \"" + getName() +
		  			"\", description: \"" + getDescription() + 
		  			"\", latitude: " + getLatitude() + 
		  			", longitude: " + getLongitude() + 
		  			", width: " + getWidth() +
		  			", height: " + getHeight() +
		  			"}";
	  }

	  // Non-JSNI method to return the Area in m2
	  public final double getArea() {
	    return getHeight() * getWidth() / 1000000.0;
	  }
}