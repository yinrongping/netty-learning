package com.lesen.rpc.example;

import com.lesen.rpc.client.RPCClient;
import com.lesen.rpc.common.export.Service;

public class ClientTest {

	public static void main(String[] args) {
		String serverName = "test";
		String rpcUri = "rpc://127.0.0.1:1082";
		RPCClient client = new RPCClient(rpcUri);
		client.registDecodeEncode(new PersonDecodeEncode());
		client.connectService();
		
		Service service = client.getRemoteService(serverName, Service.class);
        for (int i = 0; i < 10000; i++) {
            System.out.println(service.test("haha" + i));
        }
        client.close();
	}
}
