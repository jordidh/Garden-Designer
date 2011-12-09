package com.curba.zonedesigner.client;

import com.google.gwt.core.client.JavaScriptObject;

public class VegetalData extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected VegetalData() {}

	  // JSNI methods to get stock data.
	  public final native int getId() /*-{ return this.id; }-*/;
	  public final native String getScientificName() /*-{ return this.nom_cientific; }-*/;
	  public final native String getName() /*-{ return this.nom; }-*/;
	  public final native String getDescription() /*-{ return this.descripcio; }-*/;
	  public final native int getProductivity() /*-{ return this.productivitat; }-*/;
	  public final native int getPlantationWidth() /*-{ return this.marc_de_plantacio_x; }-*/;
	  public final native int getPlantationHeight() /*-{ return this.marc_de_plantacio_y; }-*/;
	  public final native int getVolume() /*-{ return this.volum_de_terra; }-*/;
	  public final native int getSoilPreference() /*-{ return this.preferencia_de_sol_id; }-*/;
	  public final native int getClimatePreference() /*-{ return this.preferencia_climatica_id; }-*/;
	  public final native int getIrrigationId() /*-{ return this.rec_id; }-*/;
	  public final native int getFertilizerId() /*-{ return this.adob_id; }-*/;
	  public final native int getLifecycleId() /*-{ return this.cicle_de_vida_id; }-*/;
	  public final native int getRootDepht() /*-{ return this.profunditat_arrel; }-*/;
	  public final native int getMinimumPh() /*-{ return this.ph_minim; }-*/;
	  public final native int getMaximumPh() /*-{ return this.ph_maxim; }-*/;
	  public final native int getMinimumTemperature() /*-{ return this.temperatura_minima; }-*/;
	  public final native int getMaximumTemperature() /*-{ return this.temperatura_maxima; }-*/;
	  public final native int getIceResistance() /*-{ return this.resistencia_gelades_id; }-*/;
	  public final native int getSaltResistance() /*-{ return this.tolerancia_salinitat_id; }-*/;
}