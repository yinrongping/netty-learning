package com.lesen.rpc.common.edecode;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;

public class IntegerEncodeDeocde implements ParamEncodeDecode {

	@Override
	public byte[] encode(Object obj) {
		byte[] flatArray = new byte[4];
		CodedOutputStream stream = CodedOutputStream.newInstance(flatArray);
		try {
			Integer intg = Integer.valueOf(obj.toString());
			stream.writeInt32NoTag(intg.intValue());
			return flatArray;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Object decode(byte[] bytes) {
		CodedInputStream stream = CodedInputStream.newInstance(bytes);
		try {
			int readInt32 = stream.readInt32();
			return readInt32;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getTypeName() {
		return Integer.class.getName();
	}

}
