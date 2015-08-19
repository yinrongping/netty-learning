package com.lesen.rpc.common.edecode;

public class ShortEncodeDeocde extends IntegerEncodeDeocde {

	@Override
	public Object decode(byte[] bytes) {
		Integer decode = (Integer) super.decode(bytes);
		return decode.shortValue();
	}

	@Override
	public String getTypeName() {
		return Short.class.getName();
	}

}
