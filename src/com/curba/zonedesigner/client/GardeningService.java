package com.curba.zonedesigner.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("gardening")
public interface GardeningService extends RemoteService {
	String getGarden(int gardenId, String culture) throws IllegalArgumentException;
	String getZone(int zoneId, String culture) throws IllegalArgumentException;
	String setZone(String zoneJsonized) throws IllegalArgumentException;
	String getCrop(int cropId, String culture) throws IllegalArgumentException;
	String setCrop(String cropJsonized) throws IllegalArgumentException;
	String getPlants(String culture) throws IllegalArgumentException;
}