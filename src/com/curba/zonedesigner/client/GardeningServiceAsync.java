package com.curba.zonedesigner.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GardeningService</code>.
 */
public interface GardeningServiceAsync {
	void getGarden(int gardenId, String culture, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void getZone(int zoneId, String culture, AsyncCallback<String> callback)
		throws IllegalArgumentException;
	void setZone(String zoneJsonized, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void getCrop(int cropId, String culture, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void setCrop(String cropJsonized, AsyncCallback<String> callback)
			throws IllegalArgumentException;
	void getPlants(String culture, AsyncCallback<String> callback)
		throws IllegalArgumentException;
}