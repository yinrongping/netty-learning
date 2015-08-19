package com.lesen.rpc.common.export;

import com.lesen.rpc.example.RPCObject.Person;

public class TestService implements Service {


	public int addx(int a, int b) {
		return a + b;
	}

	public String test(String name) {
		StringBuilder sb = new StringBuilder("hello:");
		sb.append(name);
		return sb.toString();
	}


	public Person query(String name) {
		Person.Builder builder = Person.newBuilder();
		builder.setId(0);
		builder.setName(name);
		Person person = builder.build();
		return person;
	}


	public String throwError() {
		throw new RuntimeException("err");
	}

}