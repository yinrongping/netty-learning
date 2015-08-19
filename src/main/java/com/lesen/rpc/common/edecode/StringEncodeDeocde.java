package com.lesen.rpc.common.edecode;

import com.google.protobuf.ByteString;

public class StringEncodeDeocde implements ParamEncodeDecode {

	@Override
	public byte[] encode(Object obj) {
		String string = obj.toString();
		ByteString byteStr = ByteString.copyFromUtf8(string);
		return byteStr.toByteArray();
	}

	@Override
	public Object decode(byte[] bytes) {
		ByteString copyFrom = ByteString.copyFrom(bytes);
		return copyFrom.toStringUtf8();
	}

	@Override
	public String getTypeName() {
		return String.class.getName();
	}

}
