package com.emiliorodo.ad;

import com.emiliorodo.ad.connector.AppdirectConnectorImpl;

public class AppdirectConnectorBuilder {
	
	public AppdirectConnector build() {
		return new AppdirectConnectorImpl();
	};
}
