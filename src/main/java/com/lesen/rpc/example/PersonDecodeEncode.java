package com.lesen.rpc.example;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lesen.rpc.common.edecode.ParamEncodeDecode;
import com.lesen.rpc.example.RPCObject.Person;

/**
 * 解析person.proto定义的PB对象,该对象的Java实现在 RPCObject中
 * 
 * @author Administrator
 * 
 */
class PersonDecodeEncode implements ParamEncodeDecode {


	public byte[] encode(Object obj) {
		Person person = (Person) obj;
		return person.toByteArray();
	}


	public Object decode(byte[] bytes) {
		try {
			return Person.parseFrom(bytes);
		} catch (InvalidProtocolBufferException e) {
			throw new RuntimeException(e);
		}
	}


	public String getTypeName() {
		return Person.class.getName();
	}
}