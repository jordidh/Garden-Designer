package com.curba.zonedesigner.client;

import java.util.Date;

//import com.curba.zonedesigner.shared.ServerPlant;
import com.google.gwt.core.client.JavaScriptObject;

public class CropEntity extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected CropEntity() {}

	  // JSNI methods to get stock data.
	  public final native int getId() /*-{ return this.id; }-*/;
	  public final native int getZoneId() /*-{ return this.zoneId; }-*/;
	  //public final native int getCropPeriodId() /*-{ return this.crop_period_id; }-*/;
	  public final native int getPlantId() /*-{ return this.plantId; }-*/;
	  //public final native String getPlantName() /*-{ return this.plant_name; }-*/;
	  //public final native String getPlantDescription() /*-{ return this.plant_description; }-*/;
	  public final native String getInitialRealDate() /*-{ return this.initialRealDate; }-*/;
	  public final native String getInitialPlannedDate() /*-{ return this.initialPlannedDate; }-*/;
	  public final native String getFinalRealDate() /*-{ return this.finalRealDate; }-*/;
	  public final native String getFinalPlannedDate() /*-{ return this.finalPlannedDate; }-*/;
	  public final native int getPointX() /*-{ return this.pointX; }-*/;
	  public final native int getPointY() /*-{ return this.pointY; }-*/;
	  public final native int getNumPlants() /*-{ return this.numPlants; }-*/;
	  //public final native int getProductivity() /*-{ return this.productivity; }-*/;
	  //public final native int getWidthSpacing() /*-{ return this.width_spacing; }-*/;
	  //public final native int getHeightSpacing() /*-{ return this.height_spacing; }-*/;
	  //public final native int getSoilVolume() /*-{ return this.soil_volume; }-*/;
	  //public final native int getDeleted() /*-{ return this.deleted; }-*/;  
	  
	  public final native void setZoneId(int id) /*-{ this.zoneId = id; }-*/;
	  public final native void setPlantId(int id) /*-{ this.plantId = id; }-*/;
	  public final native void setInitialRealDate(Date data) /*-{ this.initialRealDate = data; }-*/;
	  public final native void setFinalRealDate(Date data) /*-{ this.finalRealDate = data; }-*/;
	  public final native void setPointX(int x) /*-{ this.pointX = x; }-*/;
	  public final native void setPointY(int y) /*-{ this.pointY = y; }-*/;
	  public final native void setNumPlants(int num) /*-{ this.numPlants = num; }-*/;
	  //public final native void setDeleted(int deleted) /*-{ this.deleted = deleted; }-*/;
	  
	  public final String toJsonString()
	  {
		  return "{ id: " + getId() + 
		  			", zone_id: " + getZoneId() + 
		  			//", crop_period_id: " + getCropPeriodId() + 
		  			", plant_id: " + getPlantId() + 
		  			//", plant_name: \"" + getPlantName() + 
		  			//"\", plant_description: \"" + getPlantDescription() +
		  			//"\" +
		  			", initial_real_date: \"" + getInitialRealDate() +
		  			"\", initial_planned_date: \"" + getInitialPlannedDate() + 
		  			"\", final_real_date: \"" + getFinalRealDate() +
		  			"\", final_planned_date: \"" + getFinalPlannedDate() + 
		  			"\", point_x: " + getPointX() + 
		  			", point_y: " + getPointY() + 
		  			", num_plants: " + getNumPlants() +
		  			//", productivity: " + getProductivity() +
		  			//", width_spacing: " + getWidthSpacing() +
		  			//", height_spacing: " + getHeightSpacing() +
		  			//", soil_volume: " + getSoilVolume() +
		  			//", deleted: " + getDeleted() +
		  			"}";
	  }
}