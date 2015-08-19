package com.lesen.rpc.example;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lesen.rpc.common.edecode.ParamEncodeDecode;
import com.lesen.rpc.example.RPCObject.Person;

/**
 * ����person.proto�����PB����,�ö����Javaʵ���� RPCObject��
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