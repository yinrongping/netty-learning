package com.lesen.rpc.service;

import com.lesen.rpc.common.edecode.DecodeEnodeFactory;
import com.lesen.rpc.common.edecode.ParamEncodeDecode;
import com.lesen.rpc.common.export.Service;
import com.lesen.rpc.interceptor.DefaultInterceptor;
import com.lesen.rpc.interceptor.Interceptor;
import com.lesen.rpc.interceptor.InterceptorFactory;

public class RPCService {

	private ServiceTransport service;
	private ServiceDispatcher dispatcher;
	private DecodeEnodeFactory deFactofy;
	private InterceptorFactory itpFactory;
	private ServiceExportManager exportManager;

	public RPCService(int port) {
		exportManager = new ServiceExportManager();
		deFactofy = DecodeEnodeFactory.getInstance();
		itpFactory = InterceptorFactory.getInstance();
		dispatcher = new ServiceDispatcher(exportManager, itpFactory, deFactofy);
		service = new ServiceTransport(port, dispatcher, itpFactory);
	}

	public void exportService(String exportName, Object service) {
		exportManager.exportService(exportName, service);
	}

	public void registDecodeEncode(ParamEncodeDecode encodeDecode) {
		deFactofy.registDecodeEncode(encodeDecode);
	}

	public void addInteceptor(Interceptor interceptor) {
		itpFactory.addInterceptor(interceptor);
	}

	public void run() throws Exception {
		addDefaultInterceptor();
		service.run();
	}

	public void stop() {
		itpFactory.clear();
		deFactofy.clear();
		exportManager.clear();
		service.stop();
	}

	private void addDefaultInterceptor() {
		Interceptor interceptor = new DefaultInterceptor();
		addInteceptor(interceptor);
	}
}
