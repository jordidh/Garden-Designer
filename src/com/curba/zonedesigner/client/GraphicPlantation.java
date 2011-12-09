package com.curba.zonedesigner.client;

import java.util.Date;

import org.vaadin.gwtgraphics.client.shape.Rectangle;


public class GraphicPlantation extends Rectangle {
	/*
	//Plantations fields
	private int m_userId;
	private int m_vegetalId;
	private Date m_dateInitialReal;
	private Date m_dateInitialPlanned;
	private Date m_dateFinalReal;
	private Date m_dateFinalPlanned;
	private int m_zoneId;
	private int m_pointX;
	private int m_pointY;
	private int m_numPlants;
	
	//Vegetal fields
	private String m_scientificName;
	private int m_familyId;
	private int m_productivity;
	private int m_plantationHeight;
	private int m_plantationWidth;
	private int m_plantationVolume;
	private int m_soilPreferenceId;
	private int m_climatePreferenceId;
	private int m_irrigationId;
	private int m_fertilizerId;
	private int m_lifecycleId;
	private int m_heigh;
	private int m_sowingDepth;
	private int m_minimumPh;
	private int m_maximumPh;
	private int m_minimumTemperature;
	private int m_maximumTemperature;
	private int m_iceResistance;
	private int m_saltResistance;
	*/
	
	private PlantationData m_plantation;
	private VegetalData m_vegetal;
	
	public GraphicPlantation(int x, int y, int width, int height) {
		super(x, y, width, height);
		// TODO Auto-generated constructor stub
	}
	
	//Constructor que calcula l'area de plantació en funció del volum necessitat per la planta
	//i de la profuncitat de la taula de plantació. P.e. una taula poc fonda generarà un marc de
	//plantació més gran i una taula molt profunda generarà un marc de plantació estàndar.
	public GraphicPlantation(PlantationData plantation, VegetalData vegetal) {
		super(plantation.getPuntX(), plantation.getPuntY(), vegetal.getPlantationWidth(), vegetal.getPlantationHeight());
		
		// TODO Auto-generated constructor stub
		setPlantationData(plantation);
		setVegetalData(vegetal);
	}
	
	public void SetRealFinalDate(Date date)
	{
		getPlantationData().setDataFinalReal(date);
	}

	public void setPlantationData(PlantationData m_plantation) {
		this.m_plantation = m_plantation;
	}

	public PlantationData getPlantationData() {
		return m_plantation;
	}

	public void setVegetalData(VegetalData m_vegetal) {
		this.m_vegetal = m_vegetal;
	}

	public VegetalData getVegetalData() {
		return m_vegetal;
	}
}
