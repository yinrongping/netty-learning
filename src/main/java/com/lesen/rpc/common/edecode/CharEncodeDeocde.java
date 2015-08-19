package com.lesen.rpc.common.edecode;

public class CharEncodeDeocde implements ParamEncodeDecode {

	@Override
	public byte[] encode(Object obj) {
		Character bts = (Character) obj;
		return new byte[bts.charValue()];
	}

	@Override
	public Object decode(byte[] bytes) {
		return (char) bytes[0];
	}

	@Override
	public String getTypeName() {
		return Character.class.getName();
	}

}
