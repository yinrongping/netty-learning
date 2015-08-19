package com.lesen.rpc.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceExportManager {

	private Map<String, Object> services;

	public ServiceExportManager() {
		services = new HashMap<String, Object>();
	}

	public void exportService(String exportName, Object service) {
		services.put(exportName, service);
	}

	public void removeService(String exportName) {
		services.remove(exportName);
	}

	public Object getService(String exportName) {
		return services.get(exportName);
	}

	public void clear() {
		services.clear();
	}
}
