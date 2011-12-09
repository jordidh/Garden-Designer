package com.curba.zonedesigner.client;

import com.google.gwt.core.client.JavaScriptObject;

public class GardenTypeEntity extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected GardenTypeEntity() {}
	  
	  // JSNI methods to get stock data.
	  public final native int getId() /*-{ return this.id; }-*/;
	  public final native String getName() /*-{ return this.name; }-*/;
	  public final native String getDescription() /*-{ return this.description; }-*/;
	  
	  public final String toJsonString()
	  {
		  return "{ id: " + getId() + 
		  			", name: \"" + getName() +
		  			"\", description: \"" + getDescription() + 
		  			"\"}";
	  }
}