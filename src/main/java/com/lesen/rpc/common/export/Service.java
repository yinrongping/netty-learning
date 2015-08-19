package com.lesen.rpc.common.export;

import com.lesen.rpc.example.RPCObject;

public interface Service {

	int addx(int a,int b);
	
	String test(String name);
	
	RPCObject.Person query(String name);
	
	String throwError();
}
