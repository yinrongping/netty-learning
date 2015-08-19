package com.lesen.rpc.common.edecode;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

public class FloatEncodeDeocde implements ParamEncodeDecode {

	@Override
	public byte[] encode(Object obj) {
		byte[] flatArray = new byte[4];
		CodedOutputStream stream = CodedOutputStream.newInstance(flatArray);
		try {
			stream.writeFloatNoTag((Float) obj);
			return flatArray;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object decode(byte[] bytes) {
		CodedInputStream stream = CodedInputStream.newInstance(bytes);
		try {
			return stream.readFloat();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getTypeName() {
		return Float.class.getName();
	}
}
