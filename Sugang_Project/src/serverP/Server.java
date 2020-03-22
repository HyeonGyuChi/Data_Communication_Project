package serverP;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import org.json.JSONObject;

public class Server extends Thread {
	private Service service = new Service();
	private ServerSocket socket = null;
	private SaveTimetable saveTimetableFun = null;
	
	private int SERVER_PORT = 6006;
	
	public Server(int port) {
		SERVER_PORT = port;
		
		service.start();
	}

	public void run() {
		System.out.println("������ ����˴ϴ�.");
		
		try {
			socket = new ServerSocket(SERVER_PORT);
			
		}catch(IOException e) {
			System.err.println(SERVER_PORT + " �� ������Դϴ�.");
			System.exit(-1);
		}
		
		while(true) {
			try {
				System.out.println("[WAITING]");
				Socket client = socket.accept();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
				
				String clientIp = client.getInetAddress().getHostAddress();
				
				System.out.println(clientIp + " ���� �����Ͽ����ϴ�.");
				
				// �޽����� �׻� ��ɾ�/������ �������� ������.
				// �׷��Ƿ� ���� �޽����� '/' ���� �������� ©�� 2������ �׻� üũ�ؾ� �Ѵ�.
				String messageFromClient = in.readLine();
				
				// null �� ���� ���� ����� ������ ���� ����̴�. 
				if(messageFromClient == null) {
					System.out.println(clientIp + " ���� �α׾ƿ��Ͽ����ϴ�.");
				}
				else {
					System.out.println(clientIp + " ����ڷκ��� ���� �޽��� : " + messageFromClient);
					// ���� �޽����� / ���� �������� �ڸ���.
					
					// ������ ��� ��� JSON ����� �̿���. 
					// Ŭ���̾�Ʈ�κ��� ���� �޽����� JSON ������ String �� ���, �Ʒ��� ������� JSON ��ü�� ��ȯ�� ����.
					JSONObject messages = new JSONObject(messageFromClient);
					
					// �޽��� command �� Ȯ���Ͽ� ���񽺸� �����Ѵ�.
					String command = messages.getString("command");
					
					if(command.equals("register")) {
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");
						String name = messages.getString("name");
						String missNum = messages.getString("missNum");
						String dpt = messages.getString("dpt");
						int grade = messages.getInt("grade");
						int security = messages.getInt("security");
						
						String result = service.register(new Member(id,passwd,name,missNum,dpt,grade,security));
						
						out.println(result);
						out.flush();
					}
					else if(command.equals("login")) {
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");
						
						String result = service.login(new Member(id, passwd));
						
						out.println(result);
						out.flush();
					}
					else if(command.equals("findPasswd")) {
						// �ٸ� �����Ͱ� �����Ƿ� ����� �޾Ƽ� �������ش�.
						String id = messages.getString("id");
						String name = messages.getString("name");
						String missNum = messages.getString("missNum");
						
						String result = service.PwdResult(new Member(id, name,missNum));
						
						out.println(result);
						out.flush();
					}
					else if(command.equals("logout")) {
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");
						
						String result = service.logout(new Member(id, passwd));
						
						out.println(result);
						out.flush();
					}
					
					else if(command.equals("applyCourse")) {
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");
						
						int courseId = messages.getInt("courseId");					
						int hoped = messages.getInt("hopePeople");
						
						String result = service.applyCourse(new Member(id,passwd),new Course(courseId),hoped);
						
						out.println(result);
						out.flush();
					}
					else if(command.equals("removeCourse")) {
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");
						
						int courseId = messages.getInt("courseId");
						
						String result = service.removeCourse(new Member(id,passwd),new Course(courseId));
						
						out.println(result);
						out.flush();
					}
					else if(command.equals("lookCourse")) {
						int courseId = messages.getInt("courseId");

						String result = service.lookCourse(new Course(courseId));
						
						out.println(result);
						out.flush();
					}
					else if(command.equals("createCourse")) {
						String courseName = messages.getString("courseName");
						String professor = messages.getString("professor");
						int grade = messages.getInt("grade");
						String dpt = messages.getString("dpt");
						int maxCount = messages.getInt("maxCount");
						String courseDay = messages.getString("courseDay");
						int timeTable = messages.getInt("timeTable");
						int credit = messages.getInt("credit");
						
						String result = service.createCourse(new Course(0,courseName,professor,grade,dpt,maxCount,courseDay,timeTable,credit));
						
						out.println(result);
						out.flush();
					}
					else if(command.equals("applyCourseShow")) { // �α����� ������ ��û�� ��������
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");
						
						String result = service.applyCourseShow(new Member(id,passwd));
						
						out.print(result);
						out.flush();
					}
					else if(command.equals("showAllCourse")) { // ��� �������� ���
						String result = service.showAllCourse();
						
						out.println(result);
						out.flush();
					}
					
					else if(command.equals("showCurrentUser")) { // ���� �α��� ��� ���� Ȯ�� ���
						
						String result = service.showCurrentUser();
						
						out.println(result);
						out.flush();
					}
					
					
					else if(command.equals("increaseStudent")) {
						int courseId = messages.getInt("courseId");
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");

						String result = service.increaseStudent(new Member(id,passwd),new Course(courseId));
						
						out.println(result);
						out.flush();
					}
					else if(command.equals("showTimetable")) {
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");
						
						String result = service.showTimetable(new Member(id,passwd),0);
						
						out.println(result);
						out.flush();
					}
					else if(command.equals("saveTimetable")) {
						int courseId = messages.getInt("courseId");
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");
						
						String result = service.showTimetable(new Member(id,passwd),1);

						out.flush();
					}
					else {
						JSONObject data = new JSONObject();
						data.put("command", "Error");
						data.put("result", false);
						data.put("message", "�ùٸ��� ���� ������ �޽��� �Դϴ�");
						
						out.println(data.toString());
						out.flush();
					}
				}				
				client.close();
				
				System.out.println(clientIp + " : ������ �����մϴ�.");
			} catch (IOException e) { }
		}
	}
}
