package com.lesen.rpc.common.edecode;

public interface ParamEncodeDecode {

	byte[] encode(Object obj);

	Object decode(byte[] bytes);

	String getTypeName();
}
