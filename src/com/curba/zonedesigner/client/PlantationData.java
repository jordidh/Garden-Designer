package com.curba.zonedesigner.client;

import java.util.Date;

import com.curba.zonedesigner.shared.ServerPlant;
import com.google.gwt.core.client.JavaScriptObject;

public class PlantationData extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected PlantationData() {}

	  // JSNI methods to get stock data.
	  public final native int getId() /*-{ return this.id; }-*/;
	  public final native int getUsuariId() /*-{ return this.usuari_id; }-*/;
	  public final native int getVegetalId() /*-{ return this.vegetal_id; }-*/;
	  public final native String getDataInicialReal() /*-{ return this.data_inicial_real; }-*/;
	  public final native String getDataInicialPrevista() /*-{ return this.data_inicial_prevista; }-*/;
	  public final native String getDataFinalReal() /*-{ return this.data_final_real; }-*/;
	  public final native String getDataFinalPrevista() /*-{ return this.data_final_prevista; }-*/;
	  public final native int getTipusPeriodeCultiuInicialId() /*-{ return this.tipus_periode_cultiu_inicial; }-*/;
	  public final native int getZonaId() /*-{ return this.zona_id; }-*/;
	  public final native int getPuntX() /*-{ return this.punt_x; }-*/;
	  public final native int getPuntY() /*-{ return this.punt_y; }-*/;
	  public final native int getNumPlantes() /*-{ return this.num_plantes; }-*/;
	  public final native int getDeleted() /*-{ return this.deleted; }-*/;  
	  
	  public final native void setDataFinalReal(Date data) /*-{ this.data_final_real = data; }-*/;
	  public final native void setPuntX(int x) /*-{ this.punt_x = x; }-*/;
	  public final native void setPuntY(int y) /*-{ this.punt_y = y; }-*/;
	  public final native void setDeleted(int deleted) /*-{ this.deleted = deleted; }-*/;
	  
	  public final String toJsonString()
	  {
		  return "{ id: " + getId() + 
		  			", usuari_id: " + getUsuariId() + 
		  			", vegetal_id: " + getVegetalId() + 
		  			", data_inicial_real: \"" + getDataInicialReal() + 
		  			"\", data_inicial_prevista: \"" + getDataInicialPrevista() + 
		  			"\", data_final_real: \"" + getDataFinalReal() +
		  			"\", data_final_prevista: \"" + getDataFinalPrevista() +
		  			"\", tipus_periode_cultiu_inicial: " + getTipusPeriodeCultiuInicialId() + 
		  			", zona_id: " + getZonaId() + 
		  			", punt_x: " + getPuntX() + 
		  			", punt_y: " + getPuntY() + 
		  			", num_plantes: " + getNumPlantes() +
		  			", deleted: " + getDeleted() +
		  			"}";
	  }
	  
	  public final ServerPlant toServerPlant()
	  {
		  ServerPlant plant = new ServerPlant();
		  plant.setId(getId());
		  plant.setUserId(getUsuariId());
		  plant.setVegetalId(getVegetalId());
		  plant.setRealInitialDate(getDataInicialReal());
		  plant.setPlannedInitialDate(getDataInicialPrevista());
		  plant.setRealFinalDate(getDataFinalReal());
		  plant.setPlannedFinalDate(getDataFinalPrevista());
		  plant.setInitialPeriodType(getTipusPeriodeCultiuInicialId());
		  plant.setZoneId(getZonaId());
		  plant.setPointX(getPuntX());
		  plant.setPointY(getPuntY());
		  plant.setPlantsNumber(getNumPlantes());
		  plant.setDeleted(getDeleted());
		  
		  return plant;
	  }
}