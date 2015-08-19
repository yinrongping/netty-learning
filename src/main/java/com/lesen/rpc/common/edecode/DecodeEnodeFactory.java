package com.lesen.rpc.common.edecode;

import java.util.HashMap;
import java.util.Map;

import com.lesen.rpc.protobuf.LesenRPCProto.LesenRPCResult;

public class DecodeEnodeFactory {

	private Map<String, ParamEncodeDecode> decodeEncodes;

	private static final DecodeEnodeFactory INSTANCE = new DecodeEnodeFactory();

	public static DecodeEnodeFactory getInstance() {
		return INSTANCE;
	}

	private DecodeEnodeFactory() {
		decodeEncodes = new HashMap<String, ParamEncodeDecode>();
		registDefaultHandler();
	}

	public void registDecodeEncode(ParamEncodeDecode encodeDecode) {
		decodeEncodes.put(encodeDecode.getTypeName(), encodeDecode);
	}

	public ParamEncodeDecode getEncodeDecode(Object obj) {
		return getEncodeDecode(obj.getClass().getName());
	}

	public ParamEncodeDecode getEncodeDecode(String clsName) {
		String objClsName = convertPrimitTypeToObjType(clsName);
		ParamEncodeDecode objectEDer = decodeEncodes.get(objClsName);
		return objectEDer;
	}

	private String convertPrimitTypeToObjType(String clsName) {
		if (clsName.equals("int"))
			return Integer.class.getName();
		if (clsName.equals("long"))
			return Long.class.getName();
		if (clsName.equals("char"))
			return Character.class.getName();
		if (clsName.equals("double"))
			return Double.class.getName();
		if (clsName.equals("float"))
			return Float.class.getName();
		if (clsName.equals("byte"))
			return Byte.class.getName();
		if (clsName.equals("short"))
			return Short.class.getName();
		return clsName;
	}

	public Object parser(LesenRPCResult result) {
		String type = result.getType();
		ParamEncodeDecode objectEncodeDecode = decodeEncodes.get(type);
		byte[] byteArray = result.getValue().toByteArray();
		return objectEncodeDecode.decode(byteArray);
	}

	private void registDefaultHandler() {

		ByteEncodeDeocde byteED = new ByteEncodeDeocde();
		CharEncodeDeocde charED = new CharEncodeDeocde();
		ShortEncodeDeocde shortED = new ShortEncodeDeocde();
		LongEncodeDeocde longED = new LongEncodeDeocde();
		IntegerEncodeDeocde integerED = new IntegerEncodeDeocde();
		DoubleEncodeDeocde doubleED = new DoubleEncodeDeocde();
		FloatEncodeDeocde floatEd = new FloatEncodeDeocde();
		StringEncodeDeocde stringED = new StringEncodeDeocde();

		registDecodeEncode(byteED);
		registDecodeEncode(charED);
		registDecodeEncode(shortED);
		registDecodeEncode(integerED);
		registDecodeEncode(doubleED);
		registDecodeEncode(longED);
		registDecodeEncode(floatEd);
		registDecodeEncode(stringED);
	}

	public void clear() {
		decodeEncodes.clear();
	}
}
