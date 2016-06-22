package com.arunkumarbgcbe.macdensdigitalradio.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectionDetector {
	ConnectivityManager conMgr;
	public ConnectionDetector(Context ctx) {
		conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	public boolean checkInternetConnection() {
		NetworkInfo i = conMgr.getActiveNetworkInfo();
		if (i == null)
		    return false;
		if (!i.isConnected())
		    return false;
		if (!i.isAvailable())
		    return false;
		return true;
	}
}
