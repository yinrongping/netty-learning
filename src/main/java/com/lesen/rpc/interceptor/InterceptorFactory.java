package com.lesen.rpc.interceptor;

import java.util.ArrayList;
import java.util.List;

public class InterceptorFactory {

	private List<Interceptor> interceptors;

	private static final InterceptorFactory INTERCEP = new InterceptorFactory();

	private InterceptorFactory() {

		interceptors = new ArrayList<Interceptor>();
	}

	public static InterceptorFactory getInstance() {
		return INTERCEP;
	}

	public void addInterceptor(Interceptor interceptor) {
		removeInterceptor(interceptor);
		interceptors.add(interceptor);
	}

	public void removeInterceptor(Interceptor interceptor) {
		interceptors.remove(interceptor);
	}

	public List<Interceptor> getInterceptors() {
		return interceptors;
	}

	public void clear() {
		interceptors.clear();
	}
}
