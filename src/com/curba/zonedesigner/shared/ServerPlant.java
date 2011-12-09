package com.curba.zonedesigner.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ServerPlant implements IsSerializable {

	private int Id;
	private int UserId;
	private int VegetalId;
	private String RealInitialDate;
	private String PlannedInitialDate;
	private String RealFinalDate;
	private String PlannedFinalDate;
	private int InitialPeriodType;
	private int ZoneId;
	private int PointX;
	private int PointY;
	private int PlantsNumber;
	private int Deleted;
	public void setId(int id) {
		Id = id;
	}
	public int getId() {
		return Id;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	public int getUserId() {
		return UserId;
	}
	public void setVegetalId(int vegetalId) {
		VegetalId = vegetalId;
	}
	public int getVegetalId() {
		return VegetalId;
	}
	public void setRealInitialDate(String realInitialDate) {
		RealInitialDate = realInitialDate;
	}
	public String getRealInitialDate() {
		return RealInitialDate;
	}
	public void setPlannedInitialDate(String plannedInitialDate) {
		PlannedInitialDate = plannedInitialDate;
	}
	public String getPlannedInitialDate() {
		return PlannedInitialDate;
	}
	public void setRealFinalDate(String realFinalDate) {
		RealFinalDate = realFinalDate;
	}
	public String getRealFinalDate() {
		return RealFinalDate;
	}
	public void setPlannedFinalDate(String plannedFinalDate) {
		PlannedFinalDate = plannedFinalDate;
	}
	public String getPlannedFinalDate() {
		return PlannedFinalDate;
	}
	public void setInitialPeriodType(int initialPeriodType) {
		InitialPeriodType = initialPeriodType;
	}
	public int getInitialPeriodType() {
		return InitialPeriodType;
	}
	public void setZoneId(int zoneId) {
		ZoneId = zoneId;
	}
	public int getZoneId() {
		return ZoneId;
	}
	public void setPointX(int pointX) {
		PointX = pointX;
	}
	public int getPointX() {
		return PointX;
	}
	public void setPointY(int pointY) {
		PointY = pointY;
	}
	public int getPointY() {
		return PointY;
	}
	public void setPlantsNumber(int plantsNumber) {
		PlantsNumber = plantsNumber;
	}
	public int getPlantsNumber() {
		return PlantsNumber;
	}
	public void setDeleted(int deleted) {
		Deleted = deleted;
	}
	public int getDeleted() {
		return Deleted;
	}
}