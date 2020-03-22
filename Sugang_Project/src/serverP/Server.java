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
		System.out.println("서버가 실행됩니다.");
		
		try {
			socket = new ServerSocket(SERVER_PORT);
			
		}catch(IOException e) {
			System.err.println(SERVER_PORT + " 가 사용중입니다.");
			System.exit(-1);
		}
		
		while(true) {
			try {
				System.out.println("[WAITING]");
				Socket client = socket.accept();
				
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
				
				String clientIp = client.getInetAddress().getHostAddress();
				
				System.out.println(clientIp + " 님이 접속하였습니다.");
				
				// 메시지는 항상 명령어/데이터 포맷으로 보낸다.
				// 그러므로 받은 메시지를 '/' 값을 기준으로 짤라서 2개인지 항상 체크해야 한다.
				String messageFromClient = in.readLine();
				
				// null 이 오는 경우는 상대방과 연결이 끊긴 경우이다. 
				if(messageFromClient == null) {
					System.out.println(clientIp + " 님이 로그아웃하였습니다.");
				}
				else {
					System.out.println(clientIp + " 사용자로부터 받은 메시지 : " + messageFromClient);
					// 받은 메시지를 / 값을 기준으로 자른다.
					
					// 기존의 방식 대신 JSON 방식을 이용함. 
					// 클라이언트로부터 받은 메시지가 JSON 포맷의 String 인 경우, 아래의 방법으로 JSON 객체로 변환이 가능.
					JSONObject messages = new JSONObject(messageFromClient);
					
					// 메시지 command 를 확인하여 서비스를 진행한다.
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
						// 다른 데이터가 없으므로 결과만 받아서 리턴해준다.
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
					else if(command.equals("applyCourseShow")) { // 로그인한 유저가 신청한 강의정보
						String id = messages.getString("id");
						String passwd = messages.getString("passwd");
						
						String result = service.applyCourseShow(new Member(id,passwd));
						
						out.print(result);
						out.flush();
					}
					else if(command.equals("showAllCourse")) { // 모든 과목정보 출력
						String result = service.showAllCourse();
						
						out.println(result);
						out.flush();
					}
					
					else if(command.equals("showCurrentUser")) { // 현재 로그인 멤버 정보 확인 명령
						
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
						data.put("message", "올바르지 않은 포맷의 메시지 입니다");
						
						out.println(data.toString());
						out.flush();
					}
				}				
				client.close();
				
				System.out.println(clientIp + " : 접속을 종료합니다.");
			} catch (IOException e) { }
		}
	}
}
