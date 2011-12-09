package com.curba.zonedesigner.client;

import com.google.gwt.core.client.JavaScriptObject;

public class ZoneData extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected ZoneData() {}

	  // JSNI methods to get stock data.
	  public final native int getId() /*-{ return this.id; }-*/;
	  public final native String getNom() /*-{ return this.nom; }-*/;
	  public final native String getDescripcio() /*-{ return this.descripcio; }-*/;
	  public final native int getTipusZonaId() /*-{ return this.tipus_zona_id; }-*/;
	  public final native int getPuntInicialX() /*-{ return this.punt_inicial_x; }-*/;
	  public final native int getPuntInicialY() /*-{ return this.punt_inicial_y; }-*/;
	  public final native int getPuntFinalX() /*-{ return this.punt_final_x; }-*/;
	  public final native int getPuntFinalY() /*-{ return this.punt_final_y; }-*/;
	  public final native int getProfunditat() /*-{ return this.profunditat; }-*/;
	  public final native int getHortId() /*-{ return this.hort_id; }-*/;

	  // Non-JSNI method to return the Width in mm
	  public final int getWidth() {
	    return Math.abs(getPuntFinalX() - getPuntInicialX());
	  }
	  // Non-JSNI method to return the Heigh in mm
	  public final int getHeigh() {
		return Math.abs(getPuntFinalY() - getPuntInicialY());
	  }
	  // Non-JSNI method to return the Area in m2
	  public final double getArea() {
	    return getHeigh() * getWidth() / 1000000.0;
	  }
	  // Non-JSNI method to return the Volume in m3
	  public final double getVolume() {
	    return getArea() * getProfunditat() / 1000.0;
	  }
	  // Non-JSNI method to return the Volume in liters
	  public final double getLiters() {
	    return getArea() * getProfunditat();
	  }
}