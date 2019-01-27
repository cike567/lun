package org.util.jetty;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.ws.Message;
import org.util.ws.WSClient;

@WebSocket(maxTextMessageSize = 1024 * 1024)
public class ClientWebSocket extends WSClient {
	private final CountDownLatch sessionLatch = new CountDownLatch(1);

	public ClientWebSocket(String uri) throws Throwable {
		super(uri);
	}

	public ClientWebSocket(String uri, Message message) throws Throwable {
		super(uri, message);
	}

	@Override
	public void connect() throws Throwable {
		WebSocketClient client = new WebSocketClient();
		client.start();
		client.connect(this, uri, new ClientUpgradeRequest());
	}

	public String send(String text) throws IOException, InterruptedException {
		sessionLatch.await();
		session.getRemote().sendStringByFuture(text);
		return message.result();
	}

	@OnWebSocketConnect
	public void onConnect(Session session) {
		this.session = session;
		sessionLatch.countDown();
	}

	@OnWebSocketMessage
	public void onMessage(String msg) throws IOException {
		message.handle(msg);
	}

	@OnWebSocketError
	public void onError(Throwable error) throws IOException {
		log.info(error.getMessage());
	}

	@OnWebSocketClose
	public void onClose(int statusCode, String reason) throws IOException {
		log.info("Connection closed: %d - %s%n", statusCode, reason);
		this.session = null;
	}

	private Session session;

	public final Logger log = LoggerFactory.getLogger(this.getClass());

}