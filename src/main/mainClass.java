package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import thread.EchoThread;

public class mainClass {
	public static void main(String[] args) {
		
		Socket clientSocket = null;
		ServerSocket serverSocket = null;
		Vector<Socket> vec = new Vector<Socket>();
		// 다른클라이언트에게 보내기 위해서 따로 소켓의 정보를 Vector의 저장.
		try {
			serverSocket = new ServerSocket(9000);
			// 서버 소켓을 생성, 포트는 9000번			
			while(true) {
				
				System.out.println("클라이언트를 기다리는 중...");
				clientSocket = serverSocket.accept();
				// 클라이언트 소켓이 connect()해오면 소켓에 저장.
				vec.add(clientSocket);
				// 저장한 소켓을 다시 vector에 저장.
				new EchoThread(clientSocket,vec).start();	
				// 클라이언트가 소켓을 전송하면 다른 소켓에게 전송시키기 위해서 스레드를 실행.
			}					
		} catch (IOException e) {		
			e.printStackTrace();
		}	
	}
}