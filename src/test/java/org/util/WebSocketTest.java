package org.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.ws.WSClient;

/**
 * 
 * @author cike
 *
 */
public class WebSocketTest {

	@Test
	public void testRun() throws Throwable {
		String uri = "ws://localhost:9222/devtools/page/EC859459D18BF5C3BC002D3EADC25410";
		// WSClient client = new org.util.tomcat.ClientWebSocket(uri);
		WSClient client = new org.util.jetty.ClientWebSocket(uri);
		String rs = client.send("{\"method\":\"Runtime.enable\",\"params\":{},\"id\":1}");
		// {"method":"DOM.getDocument","params":{},"id":21}
		// {"method":"DOM.resolveNode","params":{"nodeId":1},"id":22}
		// {"method":"Runtime.callFunctionOn","params":{"returnByValue":true,"silent":false,"generatePreview":false,"awaitPromise":false,"userGesture":false,"arguments":[{"value":"documentElement.outerHTML"}],"functionDeclaration":"function(property)
		// { return property.split('.').reduce((o, i) => o[i], this);
		// }","objectId":"{\"injectedScriptId\":3,\"id\":1}"},"id":23}
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String line = r.readLine();
			if ("quit".equals(line)) {
				break;
			}
			client.send(line);
		}
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}
