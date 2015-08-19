怎么使用：

一   服务端
	   1 制定要导出的接口,并将你的接口打包提供给客户端，如:
	      interface TestServiceItf {
	         void add(int a,int b);
	        }
	   2 提供一个服务端实现

	   3 创建一个RPCService对象RPCService app = new RPCService(1082);

	   4 导出你的服务app.exportService("test", new TestService());

	   5 调用RPCService.run()

   参见:
  com.lesen.rpc.example.ServiceTest

    package com.lesen.rpc.example;
	import com.lesen.rpc.common.export.TestService;
	import com.lesen.rpc.service.RPCService;

	public class ServiceTest {

		public static void main(String[] args) throws Exception {
			RPCService app = new RPCService(1082);
			app.exportService("test", new TestService());
			app.run();
		}
	}
二 客户端
   com.lesen.rpc.example.ClientTest

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
				System.out.println(service.test("12"));
				client.close();
			}
		}
