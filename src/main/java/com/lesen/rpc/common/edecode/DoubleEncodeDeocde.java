package com.lesen.rpc.common.edecode;

import java.io.IOException;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

public class DoubleEncodeDeocde implements ParamEncodeDecode {

	@Override
	public byte[] encode(Object obj) {
		byte[] flatArray = new byte[8];
		CodedOutputStream stream = CodedOutputStream.newInstance(flatArray);
		try {
			Double double1 = (Double) obj;
			stream.writeDoubleNoTag(double1.doubleValue());
			return flatArray;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object decode(byte[] bytes) {
		CodedInputStream stream = CodedInputStream.newInstance(bytes);
		try {
			return stream.readDouble();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getTypeName() {
		return Double.class.getName();
	}

}
