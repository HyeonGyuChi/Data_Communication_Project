package serverP;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket; // for Socket
import java.net.ServerSocket;

public class mainServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Server 서비스 구동
				// 포트번호를 설정할 수 있다.
				Server server = new Server(6006);
				// 서버 Thread 실행
				server.start();
				
				// 이 부분은 관리자 메뉴를 받기 위한 부분
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				while(true) {
					try {
						// 입력 받은 메시지 처리
						String command = in.readLine();
						System.out.println("입력한 커멘드 : " + command);
						
					}catch (Exception e) {					}
				}
	}

}
