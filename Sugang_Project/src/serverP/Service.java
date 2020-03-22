package serverP;

import java.util.ArrayList; // ArrayList
import java.util.Arrays;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
public class Service extends Thread{ // ������û �޴� ��� ���� Ŭ����
	
	private ArrayList<Member> members = new ArrayList<Member>(); // ���� ���� �����ϴ� ��
	public ArrayList<Course> courses = new ArrayList<Course>(Arrays.asList(new Course(0,"C ���α׷���","�Ź̿�",2,"��ǻ�Ͱ��а�",10,"MON",0,3)
			,new Course(1,"C ���α׷���","������",2,"����������",1,"MON",0,3)
			,new Course(2,"���������","������",3,"����������",3,"TUE",5,2)
			,new Course(3,"�����ͺ��̽�","������",2,"��ǻ�Ͱ��а�",5,"TUE",1,3)
			,new Course(4,"�������̷�","������",1,"����ȫ���а�",5,"WED",2,3)
			,new Course(5,"����","����ȯ",1,"ü���а�",2,"THR",5,1)
			,new Course(6,"�ڷᱸ��","���ѱ�",2,"����������",5,"MON",0,3)
			,new Course(7,"��ǻ�� �׷��Ƚ�","��â��",3,"������IT����",10,"TUE",1,3)
			,new Course(8,"�ڹ� ���α׷���","�赿ȸ",1,"����ƮIOT����",4,"WED",2,3)
			,new Course(9,"��ȸ�μ���","������",2,"����ƮIOT����",5,"THR",5,3)
			,new Course(10,"�˰���","��â��",4,"����������",10,"FRI",4,3)
			,new Course(11,"VR/AR","����",4,"������IT����",6,"FRI",4,20))); // ���� ����Ʈ ����� ���ÿ� �ʱ�ȭ��
	private Member currentMember; // ���� �α����� ��� ������ ���´�
	public static int RL_SUC = 0; // ��� ���� ������
	public static int RL_ALREADY = 1; // �ߺ��� ������ ���� ���� ��
	public static int RL_IDWRONG = 2; // �Է��� ���̵�� �������� ���� ��
	public static int RL_PWDWRONG = 3; // �Է��� ���̵�� �����ϳ�, ��й�ȣ�� ��ġ���� ���� ��
	
	private SaveTimetable saveTimetableFun = null; // �ð�ǥ ���� Ŭ����
	
	public Service() { // ���� ���α׷����� �����ִ� �⺻ ���� �������� �������ش�.
	}
	
	public String register(Member member) { // ������ ȸ�������� �� �� ����ȴ�
		
		JSONObject result = new JSONObject();
		result.put("command", "register");
		result.put("result", false);
		
		if(checkRegister(member) == RL_ALREADY) { // �Է��� ������ ���̵� �̹� �����ϸ� ȸ������ ����
			result.put("message","[INFO] �ߺ��� ���̵��Դϴ�.");
		}
		else if(checkRegister(member) == RL_SUC) {// checkRegister����� RL_SUC ȸ������ ����_��� ������ ���� members�� ���� ȸ������ ���� ����
			members.add(member);
			result.put("result", true);
			result.put("message","[INFO] ȸ�����Կ� �����߽��ϴ�.");
		}
		else {
			result.put("message","[ERROR] �� �� ���� ������ �߻��߽��ϴ�.");
		}
		return result.toString();
	}
	
	public int checkRegister(Member member) { // �����鰣�� ���̵� �ߺ��� ���� �޼���
		/* ������ ������ ���̵� �̹� ������ ��, RL_ALREADY ������
		 * members�� ����� ��� ������ ���̵� ���ϰ� for-each���� ������ �ߺ��� ���̵�� ���⿡ RL_SUC�����Ѵ� */
		for(Member m : members) {
			if(m.getId().equals(member.getId())) 
				return RL_ALREADY;
		}
		return RL_SUC; 
	}
	
	public String login(Member member) {
		JSONObject result = new JSONObject();
		result.put("command", "login");
		result.put("result",false);
		
		if(checkLogin(member) == RL_SUC) { // �Է��� ���̵�, ��й�ȣ�� ��ġ�� ��
			for(Member m : members) {
				if(m.getId().equals(member.getId())) {
					currentMember = m; // ���� �α����� member
				}
			}			
			result.put("result",true);
			result.put("message", "[INFO] �α��ο� �����߽��ϴ�");
		}
		else if(checkLogin(member) == RL_IDWRONG){ // �Է��� ���̵� members�� �������� ���� ��
			result.put("message", "[ERROR] �Է��� ���̵�� �������� �ʽ��ϴ�.");
		}
		else if(checkLogin(member) == RL_PWDWRONG){ // �Է��� ��й�ȣ��, �Է��� ���̵�� ���� ���� ��
			result.put("message", "[ERROR] �ùٸ��� �ʴ� ��й�ȣ�Դϴ�.");
		}
		else {
			result.put("message", "[ERROR] �α��� ���� ����");
		}
		return result.toString();
	}
	
	public int checkLogin(Member member) { // �α��� ������ Ȯ���ϴ� �޼���
		/* ������ �Է��� ���̵�� ��й�ȣ�� members�� ����� ��������� ���Ѵ�
		 * ������ ���̵�� members�� ���̵� ���ϰ� ������ ���̵� �����ϸ�, �� �������� ��й�ȣ�� ���Ѵ�.
		 * ��й�ȣ�� ��ġ���� ������, RL_PWDWRONG, ��ġ�ϸ� RL_SUC�� ������ 
		 */
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				if(m.getPasswd().equals(member.getPasswd()))
					return RL_SUC;
				else
					return RL_PWDWRONG;
			}
		}
		return RL_IDWRONG;
	}
	
	public String logout(Member member) { // �α׾ƿ�
		// ���� �����ϴ� ����� ������ ������ ���� ��� ������ ���� ��, �α׾ƿ� ���� �˸��� �����Ѵ� 
		JSONObject result = new JSONObject();
		result.put("command", "logout");
		result.put("result",false);
		
		if(currentMember == null){
			result.put("message","[ERROR] �α����� ���� �����Ͻʽÿ�.");
			return result.toString();
		}
		
		currentMember = null;
		result.put("result",true);
		result.put("message","[INFO] �α׾ƿ��� �����߽��ϴ�.");
		return result.toString();
	}
	
	public String PwdResult(Member member) { // �Ҿ���� ��й�ȣ�� ã���ִ� �޼���
		JSONObject result = new JSONObject();
		result.put("command", "findPasswd");
		result.put("result",false);
		
		String res = findPwd(member);
		
		if(res == "���̵����") {
			result.put("message", "[ERROR] �Է��� ���̵�� �������� �ʽ��ϴ�.");
		}
		if(res == "������ȣ����") {
			result.put("message", "[ERROR] �ùٸ��� ���� ������ȣ �Դϴ�.");
		}
		else {
			result.put("result",true);
			result.put("message",res);
		}
		return result.toString();
	}

	public String findPwd(Member member) { // member�� ���̵�� ������ȣ�� ���� �� ����� �´� ���� �����ϴ� �޼���
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				if(m.getMissNum().equals(member.getMissNum()))
					return m.getPasswd(); // ����ڰ� �Է��� ���̵�� ������ȣ�� ����Ǿ��ִ� ���� ��ġ�ϸ� RL_SUC(��������)�� PwdResult�޼���� �����Ѵ�
				else
					return "������ȣ����"; // ���̵�� ��ġ������ ������ȣ�� Ʋ�� ��
			}
		}
		return "���̵����"; // �Է��� ���̵� �������� ���� ��
	}
	
public String showAllCourse() { // ��ϵ� ��� ���Ǹ� �����ϴ� �޼���
		
		JSONObject result = new JSONObject();
		result.put("command", "showAllCourse");
		result.put("result",true);
		
		if(courses.size() == 0) { // ���ǰ� �������� ���� �� �����޼��� ���
			result.put("message", "[ERROR] ���� ���ǰ� �������� �ʽ��ϴ�.");
			result.put("result",false);
			return result.toString();
		}
		else {
			JSONArray courseArray = new JSONArray();
			
			for(Course c : courses) {
			
				JSONObject course = new JSONObject();
				course.put("courseId", c.getCourseId());
				course.put("courseName", c.getCourseName());
				course.put("professor", c.getProfessor());
				course.put("grade", c.getGrade());
				course.put("dpt", c.getDpt());
				course.put("current_count", c.getCurrent_count());
				course.put("max_count", c.getMax_count());
				course.put("courseDay", c.getCourseDay());
				course.put("timetable", c.getTimetable());
				course.put("credit", c.getCredit());
				
				courseArray.put(course);
/*
 * 				JSONObject course = new JSONObject();
				course.put("courseId", c.getCourseId());
				course.put("courseName", c.getCourseName());
				course.put("professor", c.getProfessor());
				course.put("grade", c.getGrade());
				course.put("dpt", c.getDpt());
				course.put("current_count", c.getCurrent_count());
				course.put("max_count", c.getMax_count());
				course.put("courseDay", c.getCourseDay());
				course.put("timetable", c.getTimetable());
				course.put("credit", c.getCredit());
				
 */
			}
			result.put("courses", courseArray); // ��������
			result.put("message", "���� ��� ����");
			result.put("result", true);
			result.put("courseSize",courses.size());
			return result.toString();
		}
	}
	
	/*
	 * ��û Ȥ�� ����� ���Ǹ� �����ϴ� �޼���
	 * �л�(security = 0)�� ���, ������ ��û�� ������ ������ JSONObject�� �־ ������
	 * ������(security = 1)�� ���, ������ ����� ������ �������� ������ ������
	 * ����� ����, ��û�� ���ǰ� ������ �����޼��� ����
	 */
	public String applyCourseShow(Member member) {
		JSONObject result = new JSONObject();
		result.put("command", "applyCourseShow");
		JSONArray courseArray = new JSONArray(); // ���� cours�������� ��� Array
		
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) { // ���̵� Ȯ��
				if(m.getSecurity() == 1) { // ������ �������� ��
					int n = 0; // ��ϵ� ���� ������ �����Ѵ�.
					for(Course c : courses) {
						if(c.getProfessor().equals(m.getName())) { // ���� ������ �����̶�� courseN�� ����
							JSONObject course = new JSONObject();
							course.put("courseId", c.getCourseId());
							course.put("courseName", c.getCourseName());
							course.put("professor", c.getProfessor());
							course.put("grade", c.getGrade());
							course.put("dpt", c.getDpt());
							course.put("current_count", c.getCurrent_count());
							course.put("max_count", c.getMax_count());
							course.put("courseDay", c.getCourseDay());
							course.put("timetable", c.getTimetable());
							course.put("credit", c.getCredit());
							
							courseArray.put(course);
							n++;
							}
						}
					
					if(n == 0) { // ��ϵ� ���ǰ� ���� ��
						result.put("message","[ERROR] ��ϵ� ���ǰ� �����ϴ�.");
						result.put("result", false);
						return result.toString();
					}
					
					result.put("result",true);
					result.put("message", "[������]�������� ��ȸ����");
					result.put("courses", courseArray); // ����� ���ǰ� �������  courses�� ��� ���
					return result.toString();
				}

				else { // ������ �л��� ��
					
					if(m.getApplyCourseSize() == 0) { // ��û���ǰ� ���� ��
						result.put("message","[ERROR] ���� ��û�� ���ǰ� �����ϴ�");
						result.put("result",false);
						return result.toString();
					}
					
					for(Course c : m.getApplyCourse()) { // �ش� �л���ü�� ��û���� ��������
						JSONObject course = new JSONObject();
						course.put("courseId", c.getCourseId());
						course.put("courseName", c.getCourseName());
						course.put("professor", c.getProfessor());
						course.put("grade", c.getGrade());
						course.put("dpt", c.getDpt());
						course.put("current_count", c.getCurrent_count());
						course.put("max_count", c.getMax_count());
						course.put("courseDay", c.getCourseDay());
						course.put("timetable", c.getTimetable());
						course.put("credit", c.getCredit());
						
						courseArray.put(course);
					}
					
					result.put("result",true);
					result.put("message", "[�л�]��û���� ��ȸ����");
					result.put("courses", courseArray); // ������û�� ���� ������� courses�� ��� ���
					return result.toString();
				}
			}
		}
		result.put("message","[ERROR] ���� ���� ����, ȸ���̰���");
		result.put("result", false);
		return result.toString();
	}
	
	// "������û" , �������, "�����̸�"
	// wanted�� ���� ��û�� �� ���� ���Ǹ� ������� ��û�ϰ� �Ѵ�. wanted = 0 -> ��� ���� ��û����, wanted = 1 -> ��� ���� ��û
	public String applyCourse(Member member, Course course, int wanted) {
		
		JSONObject result = new JSONObject();
		result.put("command", "applyCourse");
		result.put("result",false);
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				for(Course c : courses) {
					if(c.getCourseId() == course.getCourseId()) {
						
						// member ��û�� ������̶� ���� ��û�� �����̶� ���Ϻ� -> (������) �ð� �� -> �ð��� ������ �����޼���
						for(int i = 0; i < m.getApplyCourseSize(); i++) {
							if(m.getApplyCourse(i).getCourseDay().equals(c.getCourseDay())) {
								if(m.getApplyCourse(i).getTimetable() == c.getTimetable()) {
									// �ߺ��� �ð��� ����ִ� ���Ǹ� ��û�� �� �����޼����� �����Ѵ�
									result.put("message","[ERROR] ���� �ð��� �����ϴ� ������ �����մϴ�.");
									return result.toString();
								}
							}
						}
						if(c.getCurrent_count() >= c.getMax_count()) { // ��û�� �� �ִ� �����ο��� �� �̻� ���� ��
							// ����ο��� ��û�ϰ� ���� ��(wanted == 1), �ش� ������ ����ο��� ������ ������ �����Ѵ�. �׷��� ������, ��û�Ұ� �޼����� ���ϵȴ�.
							if(wanted == 1) {
								for(int i = 0; i < c.getHopePeople_count(); i++) { // �̹� ������� ��û�� ���� ��, �����޼��� ����
									if(c.getHopePeopleInfo(i).getId().equals(m.getId()))
										result.put("message","[ERROR] �� ���Ǵ� �� �̻� ��û�� �� �����ϴ�.");
										return result.toString();
								}
								c.setHopePeople(m);
								result.put("result",true);
								result.put("message","[INFO] ��� ���ǿ� �߰��Ͽ����ϴ�.");
								return result.toString();
							}else {
								result.put("message","[ERROR] �� ���Ǵ� �� �̻� ��û�� �� �����ϴ�.");
								return result.toString();
							}
						}else { // ���� �ο� �ڸ��� ���� ���
							if(m.getCredit() < 21 && m.getCredit() + c.getCredit() < 21) {
								m.setCourse(c);
								m.setCredit(c.getCredit() + m.getCredit());
								c.setCurrent_count(c.getCurrent_count() + 1);
								result.put("result",true);
								result.put("message","[INFO] ������û�� �����߽��ϴ�.");
								return result.toString();
							}else {
								result.put("message","[ERROR] �ִ� ���� �ʰ��� ������û ����");
								return result.toString();
							}
						}
					}
				}
				result.put("message","[ERROR] �������� �ʴ� �����Դϴ�.");
				return result.toString();
			}
		}
		result.put("message","[ERROR] �������� �ʴ� ȸ�������Դϴ�.");
		return result.toString();
	}
	
	public String removeCourse(Member member, Course course) { // ���� ���� ���
		
		JSONObject result = new JSONObject();
		result.put("command", "removeCourse");
		result.put("result",false);
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				for(Course c : courses) {
					if(c.getCourseId() == course.getCourseId()) {
						for(int i = 0; i < m.getApplyCourseSize(); i++) {
							if(m.getCourse(i) == c.getCourseId()) { // ������ ��û�� ����� ������ ������ �����ڵ尡 ��ġ�ϸ� �ش� ���� ����
								m.removeCourse(c);
								m.setCredit(m.getCredit() - c.getCredit());
								c.setCurrent_count(c.getCurrent_count() - 1);
								result.put("result",true);
								result.put("message","[INFO] �ش� ���Ǹ� �����߽��ϴ�.");
								return result.toString();
							}
						}
						result.put("message","[ERROR] ��û���� ���� ������ ������ �� �����ϴ�.");
						return result.toString(); // for���� �� ���� ������ ������ ��û ���� �߿� �ش� ���ǰ� �������� �ʱ⿡ �����޼��� ����
					}
				}
				result.put("message","[ERROR] �������� �ʴ� �����Դϴ�.");
				return result.toString(); // ��� �����ڵ�� �ش� �����ڵ尡 ��ġ���� ������ �����޼��� ����
			}
		}
		result.put("message","[ERROR] �������� �ʴ� ������Դϴ�.");
		return result.toString(); // ��� ���� ���̵�� ��� ������ ����ִ� ���̵� ��ġ���� ���� �� �����޼��� ����
	}
	
	public String createCourse(Course course) { // ���� ����
		
		JSONObject result = new JSONObject();
		result.put("command", "createCourse");
		result.put("result",true);
		
		// ���� �Է¶��� ����ε� ������ �������� ������, ���� ���� ����
		if(course.getCourseDay() == null || course.getCourseName() == null || course.getCredit() <= 0 || course.getCredit() > 3
				|| course.getDpt() == null || course.getGrade() <= 0 || course.getGrade() > 4 || course.getMax_count() <= 0
				|| course.getProfessor() == null || course.getTimetable() <= 0 || course.getTimetable() > 4) {
			
			result.put("message","[ERROR] ���� ���� ����");
			result.put("result",false);
			return result.toString();
		}
		else {
			// ���� ������ ���� ����, ���� �ð����� ������ 2�� �̻� ������� �� ��, ���� �޼����� �Ѱ��ش�.
			for(Course c : courses) {
				if(currentMember.getName().equals(c.getProfessor())) {
					if(c.getCourseDay().equals(course.getCourseDay())
							&& c.getTimetable() == course.getTimetable()) {
						result.put("message", "[ERROR] ������ �ð����� ���� ���� �Ұ�");
						return result.toString();
					}
				}
			}
			// ���� ������ ��� ������ ������ ��
			course.setCourseId(courses.size()+1); // ���� �ڵ� �缳��
			courses.add(course);
			
			result.put("message","[INFO] ���ο� ���Ǹ� �߰��Ͽ����ϴ�.");
			return result.toString();
		}
	}
	
	public String lookCourse(Course course) { // �� ������ ������ �����ϴ� �޼���
		
		JSONObject result = new JSONObject();
		result.put("command", "lookCourse");
		result.put("result",true);
		
		for(Course c : courses) {
			if(c.getCourseId() == course.getCourseId()) { // �Է��� ������ �����ϴ� �����̸�
				JSONObject courseInfo = new JSONObject(c.toString()); // ���� course����
				result.put("message", courseInfo); // message�� ã�� course������ ���
				return result.toString();
				/*
				result.put("courseId", c.getCourseId());
				result.put("courseIdName", c.getCourseName());
				result.put("professor", c.getProfessor());
				result.put("grade", c.getGrade());
				result.put("dpt", c.getDpt());
				result.put("current_count", c.getCurrent_count());
				result.put("max_count", c.getMax_count());
				result.put("hopePeople_count", c.getHopePeople_count());
				result.put("timetable", c.getTimetable());
				result.put("credit", c.getCredit());
				
				result.put("message","[INFO] �ش� ������ �����Դϴ�.");
				return result.toString();
				*/
				
			}
		}
		result.put("message", "[ERROR] �������� �ʴ� �����Դϴ�.");
		result.put("result",false);
		return result.toString();
	}
	
	
public String increaseStudent(Member member, Course course) { // �л� �� ����
		
		JSONObject result = new JSONObject();
		result.put("command", "increaseStudent");
		result.put("result",false);
		
		Member user = null;
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				user = m;
			}
		}
		
		for(Course c : courses) {
			if(c.getCourseId() == course.getCourseId()) {
				if(user.getName().equals(c.getProfessor())) { // ���Ǹ� ����ġ�� ������ ������ �� �ְ� �Ѵ�_ user�ȵǸ� currentMember��
					c.setMax_count(c.getMax_count() + 2); // �����ο� ����
					
					if(c.getHopePeople_count() == 0) { // ���� ����ο��� 0�̸� �����ο��� �����ϰ� ����
						result.put("result",false);
						result.put("message", "[INFO] �ش� ������ �����ο��� �����Ͽ����ϴ�.");
						return result.toString();
					}
					for(int i = 0; i < c.getHopePeople_count(); i++) {
						if(c.getCurrent_count() == c.getMax_count()) // �����ο��� ���� ���� ����ο� ���� ���� 
							break;
						Member temp = c.getHopePeopleInfo(i);
						c.removeHopePeople(temp); // ����ο����� �ش� ��� ����
						
						for(int j = 0; j < temp.getApplyCourseSize(); j++) {
							if(temp.getApplyCourse(i).getCourseDay().equals(c.getCourseDay())) {
								if(temp.getApplyCourse(i).getTimetable() == c.getTimetable()) {
									// �ߺ��� �ð��� ����ִ� ���Ǹ� ��û�� �� �����޼����� �����Ѵ�
									result.put("message","[ERROR] ���� �ð��� �����ϴ� ������ �����մϴ�.");
									return result.toString();
								}
							}
						}
						if(temp.getCredit() < 22 && temp.getCredit() + c.getCredit() < 22) {
							temp.setCourse(c); // ����� ������Ͽ� ���� ���� �߰�
							temp.setCredit(temp.getCredit() + c.getCredit());
							c.setCurrent_count(c.getCurrent_count()+1); // ���� �ο� �� 1 ����
						}
					}
					result.put("message", "[INFO] �ش� ������ �����ο��� �����ϰ�, ����ο��� �ش� ���ǿ� �߰��Ͽ����ϴ�.");
					result.put("result",true);
					return result.toString();
				}else { // ���� ������ �̸��� ��ġ���� ���� ��
					result.put("message", "[ERROR] �ش� ������ ������ ������ �� �ִ� �����Դϴ�.");
					return result.toString();
				}
			}
		}
		result.put("message", "[ERROR] �������� �ʴ� �����Դϴ�.");
		result.put("result",false);
		return result.toString();
	}
	
	
	public String showCurrentUser() { // ���� �α����� ������ ������ �����ϴ� �޼���
		JSONObject result = new JSONObject();
		JSONArray currentMemberInfo = new JSONArray();
		
		result.put("command", "showCurrentUser");
		result.put("result",false);
		
		if(currentMember == null) { // ���� �α����� ������ ���� �� �����޽��� ����
			result.put("message", "[ERROR] �α����� �ʿ��� ����Դϴ�.");
			return result.toString();
		}
		else {			
			result.put("id", currentMember.getId());
			result.put("passwd", currentMember.getPasswd());
			result.put("name", currentMember.getName());
			result.put("grade", currentMember.getGrade());
			result.put("dpt", currentMember.getDpt());
			result.put("credit", currentMember.getCredit());
			result.put("security", currentMember.getSecurity());	
			
			// currentMemberInfo.put("member",result);
			
			result.put("result", true);
			result.put("message", "[INFO] ���� ���� ���� ���� ����");
			
			return result.toString();
		}
	}
	
	
	
	
	public String showTimetable(Member member, int x) { // ����(�л�)�� �ð�ǥ�� Ȯ���ϴ� �޼���
		
		Course[][] timetable = new Course[5][courses.size()];
		
		JSONObject result = new JSONObject();
		result.put("command", "showTimetable");
		result.put("result",false);
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				if(m.getApplyCourseSize() == 0) { // ������ ��û�� ���ǰ� ���� �� �����޼��� ����
					result.put("message", "[ERROR] ��û�� ���ǰ� �����ϴ�.");
					return result.toString();
				}
				for(int i = 0; i < m.getApplyCourseSize(); i++) {
					String temp = m.getApplyCourse(i).getCourseDay();
					Course course = m.getApplyCourse(i);
					// ���ǰ� �ִ� ���Ͽ� ���� �ٸ� �迭�� ���� �־���
					if(temp.equals("MON")) {
						timetable[0] = bubbleSort(timetable[0],course);
					}
					else if(temp.equals("TUE")) {
						timetable[1] = bubbleSort(timetable[1],course);
					}
					else if(temp.equals("WED")) {
						timetable[2] = bubbleSort(timetable[2],course);
					}
					else if(temp.equals("THR")) {
						timetable[3] = bubbleSort(timetable[3],course);
					}
					else if(temp.equals("FRI")) {
						timetable[4] = bubbleSort(timetable[4],course);
					}
				}
			}
		}	
		result.put("MON", timetable[0]);
		result.put("TUE", timetable[1]);
		result.put("WED", timetable[2]);
		result.put("THR", timetable[3]);
		result.put("FRI", timetable[4]); // ���ĵ� �ð�ǥ�� ���Ͽ� �°� �����Ѵ�
		result.put("courseSize",courses.size());
		
		if(x == 1){ // �ð�ǥ ���� ���� ���� ( x == 1, �ð�ǥ �����Ѵ�)
			boolean res = saveTimetableFun.saveMemberInfo(timetable); // �ð�ǥ ���� �����ϸ� res -> true
			if(res) { 
				result.put("message", "[INFO] �ð�ǥ ���� ����");
				result.put("result",true);
				return result.toString();
			}
			else {
				result.put("message", "[INFO] �ð�ǥ ���� ����");
				return result.toString();
			}
		}
		
		result.put("message", "[INFO] �ð�ǥ ���� ����");
		result.put("result",true);
		return result.toString();
	}
	
	public Course[] bubbleSort(Course[] arr, Course x) { // �迭 ������������ �����Ѵ�
		
		arr[arr.length] = x;
		for(int i = 0; i < arr.length; i++) {
			for(int j = i; j < arr.length; j++) {
				if(arr[i].getTimetable() > arr[j].getTimetable()) {
					Course temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr; // ���ĵ� �迭 �����Ѵ�
	}
	
	
}
