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
		// Server ���� ����
				// ��Ʈ��ȣ�� ������ �� �ִ�.
				Server server = new Server(6006);
				// ���� Thread ����
				server.start();
				
				// �� �κ��� ������ �޴��� �ޱ� ���� �κ�
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				while(true) {
					try {
						// �Է� ���� �޽��� ó��
						String command = in.readLine();
						System.out.println("�Է��� Ŀ��� : " + command);
						
					}catch (Exception e) {					}
				}
	}

}
