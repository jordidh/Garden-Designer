package com.curba.zonedesigner.client;

import com.curba.zonedesigner.shared.ServerPlant;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	//void greetServer(String input, AsyncCallback<String> callback)
	//		throws IllegalArgumentException;
	void getZoneServer(int zoneId, String culture, AsyncCallback<String> callback)
		throws IllegalArgumentException;
	void getPlantationsServer(int zoneId, String culture, AsyncCallback<String> callback)
		throws IllegalArgumentException;
	void getVegetalsServer(String culture, AsyncCallback<String> callback)
		throws IllegalArgumentException;
	void setZoneServer(String input, AsyncCallback<String> callback)
		throws IllegalArgumentException;
	void setPlantationsServer(ServerPlant[] plant/*String input*/, AsyncCallback<String> callback)
		throws IllegalArgumentException;
}
