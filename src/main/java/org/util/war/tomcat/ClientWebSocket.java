package org.util.war.tomcat;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.ws.LogMessage;
import org.util.ws.AbstractMessage;
import org.util.ws.AbstractClientws;

/**
 * 
 * @author cike
 *
 */
@ClientEndpoint
public class ClientWebSocket extends AbstractClientws {

	public ClientWebSocket(String uri) throws Throwable {
		this(uri, new LogMessage());
	}

	public ClientWebSocket(String uri, AbstractMessage message) throws Throwable {
		this.message = message;
		this.uri = new URI(uri);
		connect();
	}

	@Override
	public void connect() throws Throwable {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		int size = 1024 * 1024;
		container.setDefaultMaxTextMessageBufferSize(size);
		container.connectToServer(this, uri);
	}

	@Override
	public String send(String text) throws IOException, InterruptedException {
		log.info(text);
		session.getBasicRemote().sendText(text);
		return message.result();
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		log.info("Connected to endpoint: " + session.getBasicRemote());
	}

	@OnMessage
	public void onMessage(String msg) throws IOException {
		log.info(msg);
		message.handle(msg);
	}

	@OnError
	public void onError(Throwable t) {
		log.info(t.getMessage());
	}

	@OnClose
	public void close(CloseReason c) {
		log.info(c.toString());
	}

	// protected URI uri;

	// protected Message message;

	public Session session;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
}