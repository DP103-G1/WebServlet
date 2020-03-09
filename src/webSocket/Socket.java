package webSocket;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

@ServerEndpoint("/Socket/{user}")
public class Socket {
	private static final String TAG = "TAG_Socket";
	private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();
	Gson gson = new Gson();
	
	@OnOpen
	public void onOpen(@PathParam("user") String user, Session userSession) throws IOException {
		sessionMap.put(user, userSession);
		System.out.println(TAG + "Socket open connection: " + user);
	}
	
	@OnMessage
	public void onMessage(Session userSession, String messageJson) {
		SocketMessage socketMessage = gson.fromJson(messageJson, SocketMessage.class);
		String receiver = socketMessage.getReceiver();
		Session receiverSession = sessionMap.get(receiver);
		if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(messageJson);
		} else {
			sessionMap.remove(receiver);
		}
		System.out.println(TAG + "receiver: " + receiver);
		System.out.println(TAG + "Message received: " + messageJson);
	}
	
	@OnError
	public void onError(Session userSession, Throwable e) {
		Set<String> userNames = sessionMap.keySet();
		for (String userName : userNames) {
			if (sessionMap.get(userName).equals(userSession)) {
				System.out.println(TAG + "Error: " + userName + " " + e.toString());
			}
		}
	}
	
	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		Set<String> userNames = sessionMap.keySet();
		for (String userName : userNames) {
			if (sessionMap.get(userName).equals(userSession)) {
				sessionMap.remove(userName);
				break;
			}
		}
	}
}
