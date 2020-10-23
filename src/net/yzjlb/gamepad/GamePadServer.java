package net.yzjlb.gamepad;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.handshake.Handshakedata;
import org.java_websocket.server.WebSocketServer;

public class GamePadServer {
	WebSocketServer server;
	int port;
	boolean isRun;
	MessageListener listener;
	
	
	
	public GamePadServer(int port){
		this.port = port;
		init();
	}
	
	public void setMessageListener(MessageListener listener){
		this.listener = listener;
	}
	
	
	void init(){
		 server = new WebSocketServer(new InetSocketAddress( port ), WebSocketServer.DECODERS, null ) {
			
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				System.out.println("onStart");
				if(listener!=null) listener.onMessage("onStart");
				isRun = true;
			}
			
			@Override
			public void onOpen(WebSocket conn, ClientHandshake handshake) {
				// TODO Auto-generated method stub
				System.out.print("onOpen");
				if(listener!=null) listener.onMessage("onOpen");
			}
			
			@Override
			public void onMessage(WebSocket conn, String message) {
				// TODO Auto-generated method stub
//				System.out.println("onMessage:"+message);
				if(listener!=null) listener.onMessage(""+message);
			}
			
			@Override
			public void onError(WebSocket conn, Exception ex) {
				// TODO Auto-generated method stub
				System.out.println("onError:"+ex.getMessage());
				if(listener!=null) listener.onMessage("onError:"+ex.getMessage());
			}
			
			@Override
			public void onClose(WebSocket conn, int code, String reason, boolean remote) {
				// TODO Auto-generated method stub
				System.out.println("onClose:"+conn.getResourceDescriptor()+" "+conn.getRemoteSocketAddress()+" "+conn.getLocalSocketAddress());
				if(listener!=null) listener.onMessage("onClose:"+conn.getResourceDescriptor()+" "+conn.getRemoteSocketAddress()+" "+conn.getLocalSocketAddress());
				isRun = false;
			}
		};
		
		
	}
	
	public void start(){
		server.start();
	}
	
	public boolean isRun(){
		return isRun;
	}
		
	
	
	public void stop(){
		try {
			server.stop();
		} catch (IOException e) {
			
			e.printStackTrace();
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	public interface MessageListener{
		public void onMessage(String msg);
	}

}
