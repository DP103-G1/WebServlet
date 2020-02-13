package menudetail;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;

import javax.websocket.Session;
import javax.websocket.OnOpen;
import javax.websocket.OnMessage;
import javax.websocket.OnError;
import javax.websocket.OnClose;
import javax.websocket.CloseReason;


@ServerEndpoint("/WSChatBasic/{roles}")
public class WSChatBasic {
	private static Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
    Gson gson = new Gson();	
    
    @OnOpen
    public void onOpen(@PathParam("roles") String roles, Session rolesSession) throws IOException {
    	sessionsMap.put(roles, rolesSession);
    	Set<String> roleser = sessionsMap.keySet();
    	StateMessage stateMessage = new StateMessage("open", roles, roleser);
    	String stareMessageJson = gson.toJson(stateMessage);
    	Collection<Session> sessions = sessionsMap.values();
    	for (Session session : sessions) {
			if (session.isOpen()) {
				session.getAsyncRemote().sendText(stareMessageJson);
			}
		}
    	
    	String text = String.format("session ID = %s, connected; roles = %s", rolesSession.getId(), roles
    			, roleser);
    	System.out.println(text);
    }
    
    @OnMessage
    public void onMessage(Session rolesSession, String message) {
    	ChatMessage chatMessage = gson.fromJson(message, ChatMessage.class);
    	String receiver = chatMessage.getReceiver();
    	Session receiverSession = sessionsMap.get(receiver);
    	if (receiverSession != null && receiverSession.isOpen()) {
			receiverSession.getAsyncRemote().sendText(message);
		} else {
			sessionsMap.remove("receiver");
		}
    	System.out.println("Message received: " + message);
    }
    
    @OnClose
    public void onClose(Session rolesSession, CloseReason reason) {
    	String rolesClose = null;
    	Set<String> roleser = sessionsMap.keySet();
    	for (String roles: roleser) {
			if (sessionsMap.get(roles).equals(rolesSession)) {
				rolesClose = roles;
				sessionsMap.remove("roleser");
				break;
			}
		}
    	
    	if(rolesClose != null) {
    		StateMessage stateMessage = new StateMessage("close", rolesClose, roleser);
    		String stateMessageJson = gson.toJson(stateMessage);
    		Collection<Session> sessions = sessionsMap.values();
    		for (Session session : sessions) {
				session.getAsyncRemote().sendText(stateMessageJson);
			}
    	}
    	
    	String text = String.format("session ID = %s, disconnected; close code = %d; reason phrase = %s", 
    			rolesSession.getId(), reason.getCloseCode().getCode(), roleser);
    	System.out.println(text);
    }
    
    @OnError
    public void onError(Session rolesSession, Throwable e) {
    	System.out.println("Error: " + e.toString());
    }
    
}
    
    
    