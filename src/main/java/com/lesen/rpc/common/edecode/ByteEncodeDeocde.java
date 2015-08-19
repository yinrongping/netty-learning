package com.lesen.rpc.common.edecode;

public class ByteEncodeDeocde implements ParamEncodeDecode {

	@Override
	public byte[] encode(Object obj) {
		Byte bts = (Byte) obj;
		return new byte[bts];
	}

	@Override
	public Object decode(byte[] bytes) {
		return bytes[0];
	}

	@Override
	public String getTypeName() {
		return Byte.class.getName();
	}

}
