package com.curba.zonedesigner.client;

import com.curba.zonedesigner.shared.ServerPlant;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	//String greetServer(String name) throws IllegalArgumentException;
	String getZoneServer(int zoneId, String culture) throws IllegalArgumentException;
	String getPlantationsServer(int zoneId, String culture) throws IllegalArgumentException;
	String getVegetalsServer(String culture) throws IllegalArgumentException;
	String setZoneServer(String name) throws IllegalArgumentException;
	String setPlantationsServer(ServerPlant[] plant/*String name*/) throws IllegalArgumentException;
}