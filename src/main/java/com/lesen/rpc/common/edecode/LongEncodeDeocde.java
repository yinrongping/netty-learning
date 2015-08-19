package com.lesen.rpc.common.edecode;

import java.io.IOException;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

public class LongEncodeDeocde implements ParamEncodeDecode {

	@Override
	public byte[] encode(Object obj) {
		byte[] flatArray = new byte[8];
		CodedOutputStream stream = CodedOutputStream.newInstance(flatArray);
		try {
			stream.writeInt64NoTag((Long) obj);
			return flatArray;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object decode(byte[] bytes) {
		CodedInputStream stream = CodedInputStream.newInstance(bytes);
		try {
			return stream.readInt64();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getTypeName() {
		return Long.class.getName();
	}

}
