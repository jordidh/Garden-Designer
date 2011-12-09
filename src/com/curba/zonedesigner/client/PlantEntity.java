package com.curba.zonedesigner.client;

import com.google.gwt.core.client.JavaScriptObject;

public class PlantEntity extends JavaScriptObject {
	  // Overlay types always have protected, zero argument constructors.
	  protected PlantEntity() {}

      // JSNI methods to get stock data.
	  public final native int getId() /*-{ return this.id; }-*/;
	  public final native String getName() /*-{ return this.name; }-*/;
	  public final native String getDescription() /*-{ return this.description; }-*/;
	  public final native String getScientificName() /*-{ return this.scientificName; }-*/;
	  public final native int getProductivity() /*-{ return this.productivity; }-*/;
	  public final native int getWidthSpacing() /*-{ return this.widthSpacing; }-*/;
	  public final native int getHeightSpacing() /*-{ return this.heightSpacing; }-*/;
	  public final native int getSoilVolume() /*-{ return this.soilVolume; }-*/;
	  public final native int getRootDepth() /*-{ return this.rootDepth; }-*/;
	  public final native int getPlantHeight() /*-{ return this.plantHeight; }-*/;
	  public final native int getSeedDepth() /*-{ return this.seedDepth; }-*/;
	  public final native int getPhMinimum() /*-{ return this.phMinimum; }-*/;
	  public final native int getPhMaximum() /*-{ return this.phMaximum; }-*/;
	  public final native int getTemperatureMinimum() /*-{ return this.temperatureMinimum; }-*/;
	  public final native int getTemperatureMaximum() /*-{ return this.temperatureMaximum; }-*/;
	  public final native String getcreated_at() /*-{ return this.createdAt; }-*/;
	  public final native String getupdated_at() /*-{ return this.updatedAt; }-*/;
	  
	  public final String toJsonString()
	  {
		  return "{ id: " + getId() + 
		  			", name: \"" + getName() +
		  			"\", description: \"" + getDescription() + 
		  			"\", scientific_name: \"" + getScientificName() + 
		  			"\", productivity: " + getProductivity() + 
		  			", width_spacing: " + getWidthSpacing() + 
		  			", height_spacing: " + getHeightSpacing() + 
		  			", soil_volume: " + getSoilVolume() + 
		  			", root_depth: " + getRootDepth() +
		  			", plant_height: " + getPlantHeight() +
		  			", seed_depth: " + getSeedDepth() +
		  			", ph_minimum: " + getPhMinimum() +
		  			", ph_maximum: " + getPhMaximum() +
		  			", temperature_minimum: " + getTemperatureMinimum() +
		  			", temperature_maximum: " + getTemperatureMaximum() +
		  			"}";
	  }
	  
      // JSNI methods to get stock data.
	  //public final native int getId() /*-{ return this.id; }-*/;
	  //public final native String getName() /*-{ return this.name; }-*/;
	  //public final native String getDescription() /*-{ return this.description; }-*/;
	  //public final native String getScientificName() /*-{ return this.scientific_name; }-*/;
	  //public final native int getProductivity() /*-{ return this.productivity; }-*/;
	  //public final native int getWidthSpacing() /*-{ return this.width_spacing; }-*/;
	  //public final native int getHeightSpacing() /*-{ return this.height_spacing; }-*/;
	  //public final native int getSoilVolume() /*-{ return this.soil_volume; }-*/;
	  //public final native int getSoilTypeId() /*-{ return this.soil_type_id; }-*/;
	  //public final native int getClimateTypeId() /*-{ return this.climate_type_id; }-*/;
	  //public final native int getIrrigationTypeId() /*-{ return this.irrigation_type_id; }-*/;
	  //public final native int getFertilizerTypeId() /*-{ return this.fertilizer_type_id; }-*/;
	  //public final native int getLifeCicleTypeId() /*-{ return this.life_cicle_type_id; }-*/;
	  //public final native int getRootDepth() /*-{ return this.root_depth; }-*/;
	  //public final native int getPlantHeight() /*-{ return this.plant_height; }-*/;
	  //public final native int getSeedDepth() /*-{ return this.seed_depth; }-*/;
	  //public final native int getPlantCareId() /*-{ return this.plant_care_id; }-*/;
	  //public final native int getActionTypeId() /*-{ return this.action_type_id; }-*/;
	  //public final native int getPlantFamilyId() /*-{ return this.plant_family_id; }-*/;
	  //public final native int getPhMinimum() /*-{ return this.ph_minimum; }-*/;
	  //public final native int getPhMaximum() /*-{ return this.ph_maximum; }-*/;
	  //public final native int getTemperatureMinimum() /*-{ return this.temperature_minimum; }-*/;
	  //public final native int getTemperatureMaximum() /*-{ return this.temperature_maximum; }-*/;
	  //public final native int getFrostResistenceTypeId() /*-{ return this.frost_resistence_type_id; }-*/;
	  //public final native int getSalinityToleranceTypeId() /*-{ return this.salinity_tolerance_type_id; }-*/;
	  /*
	  public final String toJsonString()
	  {
		  return "{ id: " + getId() + 
		  			", name: \"" + getName() +
		  			"\", description: \"" + getDescription() + 
		  			"\", scientific_name: \"" + getScientificName() + 
		  			"\", productivity: " + getProductivity() + 
		  			", width_spacing: " + getWidthSpacing() + 
		  			", height_spacing: " + getHeightSpacing() + 
		  			", soil_volume: " + getSoilVolume() + 
		  			", soil_type_id: " + getSoilTypeId() +
		  			", climate_type_id: " + getClimateTypeId() +
		  			", irrigation_type_id: " + getIrrigationTypeId() +
		  			", fertilizer_type_id: " + getFertilizerTypeId() +
		  			", life_cicle_type_id: " + getLifeCicleTypeId() +
		  			", root_depth: " + getRootDepth() +
		  			", plant_height: " + getPlantHeight() +
		  			", seed_depth: " + getSeedDepth() +
		  			", plant_care_id: " + getPlantCareId() +
		  			", action_type_id: " + getActionTypeId() +
		  			", plant_family_id: " + getPlantFamilyId() +
		  			", ph_minimum: " + getPhMinimum() +
		  			", ph_maximum: " + getPhMaximum() +
		  			", temperature_minimum: " + getTemperatureMinimum() +
		  			", temperature_maximum: " + getTemperatureMaximum() +
		  			", frost_resistence_type_id: " + getFrostResistenceTypeId() +
		  			", salinity_tolerance_type_id: " + getSalinityToleranceTypeId() +
		  			"}";
	  }*/
}