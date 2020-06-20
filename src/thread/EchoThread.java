package thread;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

import dto.NetData;

public class EchoThread extends Thread{
	
	Socket clientSocket;
	Vector<Socket> vec;
	public EchoThread(Socket clientSocket,Vector<Socket> vec) {
		this.clientSocket = clientSocket;
		this.vec = vec;
	}
	
	@Override
	public void run() {
		NetData nd = null;
		try {
			while(true) {
				ObjectInputStream oji = new ObjectInputStream(clientSocket.getInputStream());			
				try {
					
					nd = (NetData)oji.readObject();
					System.out.println("송신한 소켓의 정보 : " + nd.toString());
					// 클라이언트가 객체를 송신하면 object객체를 통해서 불러온 뒤 정보를 서버에 출력.
				} catch (ClassNotFoundException e) {
					if(nd==null)
					 vec.remove(clientSocket);
					 break;			
					 // 클라이언트가 접속을 끊으면 vector에서 삭제.
				}
									
				if(nd.getTurn()==1) nd.setTurn(2);
				else nd.setTurn(1);
				// 처음 들어온 클라이언트들은 1,1에서 1,2로 준비.
				// 첫 번째 클라이언트는 턴이 1이면 전송권한을 획득하고 두 번째 클라이언트는 턴이 2이면 전송권한 획득.
				// 클라이언트가 전송하기 위해 들어오면 서버에 전송 후 다른 클라이언트의 전송권한(turn)으로 전환.
				sending(nd);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sending(NetData nd) {
		int num = 0;
			try {
				for(Socket  socket: vec) {
					// 들어온 순서대로 클라이언트에게 1,2,3... 을  number에 저장.
					ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
					num++;
					// 순서대로 멤버변수 number에게 수를 대입하기 위해서 증가.
					nd.setNumber(num);
					oos.writeObject(nd);
					// 전송한 객체에게 번호를 순서대로 계속해서 부여하고 
					// 다른 객체의 순서에 맞는 number가진 객체를 자신을 대입시키는 방식으로 전송권한의 조건을 맞춤.
					// (number와 turn의 수가 같은조건.)
					oos.flush();					
				}				
			} catch (IOException e) {				
				System.out.println(e.getMessage());
			}		
	}
}
