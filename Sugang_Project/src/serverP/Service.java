package serverP;

import java.util.ArrayList; // ArrayList
import java.util.Arrays;

import org.json.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
public class Service extends Thread{ // 수강신청 메뉴 기능 구현 클래스
	
	private ArrayList<Member> members = new ArrayList<Member>(); // 유저 정보 저장하는 곳
	public ArrayList<Course> courses = new ArrayList<Course>(Arrays.asList(new Course(0,"C 프로그래밍","신미영",2,"컴퓨터공학과",10,"MON",0,3)
			,new Course(1,"C 프로그래밍","김은주",2,"빅데이터전공",1,"MON",0,3)
			,new Course(2,"데이터통신","박찬영",3,"빅데이터전공",3,"TUE",5,2)
			,new Course(3,"데이터베이스","허종욱",2,"컴퓨터공학과",5,"TUE",1,3)
			,new Course(4,"마케팅이론","김진영",1,"광고홍보학과",5,"WED",2,3)
			,new Course(5,"수영","박태환",1,"체육학과",2,"THR",5,1)
			,new Course(6,"자료구조","주한규",2,"빅데이터전공",5,"MON",0,3)
			,new Course(7,"컴퓨터 그래픽스","송창근",3,"콘텐츠IT전공",10,"TUE",1,3)
			,new Course(8,"자바 프로그래밍","김동회",1,"스마트IOT전공",4,"WED",2,3)
			,new Course(9,"논리회로설계","이정근",2,"스마트IOT전공",5,"THR",5,3)
			,new Course(10,"알고리즘","송창근",4,"빅데이터전공",10,"FRI",4,3)
			,new Course(11,"VR/AR","이정",4,"콘텐츠IT전공",6,"FRI",4,20))); // 강의 리스트 선언과 동시에 초기화함
	private Member currentMember; // 현재 로그인한 멤버 정보를 갖는다
	public static int RL_SUC = 0; // 기능 수행 성공시
	public static int RL_ALREADY = 1; // 중복된 정보를 갖고 있을 때
	public static int RL_IDWRONG = 2; // 입력한 아이디와 존재하지 않을 때
	public static int RL_PWDWRONG = 3; // 입력한 아이디는 존재하나, 비밀번호가 일치하지 않을 때
	
	private SaveTimetable saveTimetableFun = null; // 시간표 저장 클래스
	
	public Service() { // 현재 프로그램에서 갖고있는 기본 과목 정보들을 지정해준다.
	}
	
	public String register(Member member) { // 유저가 회원가입을 할 때 실행된다
		
		JSONObject result = new JSONObject();
		result.put("command", "register");
		result.put("result", false);
		
		if(checkRegister(member) == RL_ALREADY) { // 입력한 정보의 아이디가 이미 존재하면 회원가입 실패
			result.put("message","[INFO] 중복된 아이디입니다.");
		}
		else if(checkRegister(member) == RL_SUC) {// checkRegister결과가 RL_SUC 회원가입 성공_멤버 정보를 갖는 members에 현재 회원가입 정보 저장
			members.add(member);
			result.put("result", true);
			result.put("message","[INFO] 회원가입에 성공했습니다.");
		}
		else {
			result.put("message","[ERROR] 알 수 없는 오류가 발생했습니다.");
		}
		return result.toString();
	}
	
	public int checkRegister(Member member) { // 유저들간의 아이디 중복을 막는 메서드
		/* 유저가 설정한 아이디가 이미 존재할 때, RL_ALREADY 리턴함
		 * members에 저장된 모든 유저의 아이디를 비교하고 for-each문을 나오면 중복된 아이디는 없기에 RL_SUC리턴한다 */
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
		
		if(checkLogin(member) == RL_SUC) { // 입력한 아이디, 비밀번호가 일치할 때
			for(Member m : members) {
				if(m.getId().equals(member.getId())) {
					currentMember = m; // 현재 로그인한 member
				}
			}			
			result.put("result",true);
			result.put("message", "[INFO] 로그인에 성공했습니다");
		}
		else if(checkLogin(member) == RL_IDWRONG){ // 입력한 아이디가 members에 존재하지 않을 때
			result.put("message", "[ERROR] 입력한 아이디는 존재하지 않습니다.");
		}
		else if(checkLogin(member) == RL_PWDWRONG){ // 입력한 비밀번호가, 입력한 아이디와 맞지 않을 때
			result.put("message", "[ERROR] 올바르지 않는 비밀번호입니다.");
		}
		else {
			result.put("message", "[ERROR] 로그인 과정 에러");
		}
		return result.toString();
	}
	
	public int checkLogin(Member member) { // 로그인 정보를 확인하는 메서드
		/* 유저가 입력한 아이디와 비밀번호를 members에 저장된 멤버정보와 비교한다
		 * 유저의 아이디와 members의 아이디를 비교하고 유저의 아이디가 존재하면, 그 다음으로 비밀번호를 비교한다.
		 * 비밀번호가 일치하지 않으면, RL_PWDWRONG, 일치하면 RL_SUC를 리턴함 
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
	
	public String logout(Member member) { // 로그아웃
		// 현재 존재하는 멤버의 정보가 있으면 현재 멤버 정보를 없앤 후, 로그아웃 성공 알림을 리턴한다 
		JSONObject result = new JSONObject();
		result.put("command", "logout");
		result.put("result",false);
		
		if(currentMember == null){
			result.put("message","[ERROR] 로그인을 먼저 실행하십시오.");
			return result.toString();
		}
		
		currentMember = null;
		result.put("result",true);
		result.put("message","[INFO] 로그아웃에 성공했습니다.");
		return result.toString();
	}
	
	public String PwdResult(Member member) { // 잃어버린 비밀번호를 찾아주는 메서드
		JSONObject result = new JSONObject();
		result.put("command", "findPasswd");
		result.put("result",false);
		
		String res = findPwd(member);
		
		if(res == "아이디오류") {
			result.put("message", "[ERROR] 입력한 아이디는 존재하지 않습니다.");
		}
		if(res == "인증번호오류") {
			result.put("message", "[ERROR] 올바르지 않은 인증번호 입니다.");
		}
		else {
			result.put("result",true);
			result.put("message",res);
		}
		return result.toString();
	}

	public String findPwd(Member member) { // member의 아이디와 인증번호를 비교해 각 결과에 맞는 값을 리턴하는 메서드
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				if(m.getMissNum().equals(member.getMissNum()))
					return m.getPasswd(); // 사용자가 입력한 아이디와 인증번호가 저장되어있는 값과 일치하면 RL_SUC(인증성공)을 PwdResult메서드로 리턴한다
				else
					return "인증번호오류"; // 아이디는 일치하지만 인증번호가 틀릴 때
			}
		}
		return "아이디오류"; // 입력한 아이디가 존재하지 않을 때
	}
	
public String showAllCourse() { // 등록된 모든 강의를 열람하는 메서드
		
		JSONObject result = new JSONObject();
		result.put("command", "showAllCourse");
		result.put("result",true);
		
		if(courses.size() == 0) { // 강의가 존재하지 않을 때 에러메세지 출력
			result.put("message", "[ERROR] 수강 강의가 존재하지 않습니다.");
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
			result.put("courses", courseArray); // 강의정보
			result.put("message", "강의 출력 성공");
			result.put("result", true);
			result.put("courseSize",courses.size());
			return result.toString();
		}
	}
	
	/*
	 * 신청 혹은 등록한 강의를 열람하는 메서드
	 * 학생(security = 0)일 경우, 본인이 신청한 과목의 정보를 JSONObject에 넣어서 리턴함
	 * 관리자(security = 1)일 경우, 본인이 등록한 과목의 정보들을 저장해 리턴함
	 * 등록한 강의, 신청한 강의가 없으면 에러메세지 리턴
	 */
	public String applyCourseShow(Member member) {
		JSONObject result = new JSONObject();
		result.put("command", "applyCourseShow");
		JSONArray courseArray = new JSONArray(); // 나의 cours정보들이 담긴 Array
		
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) { // 아이디 확인
				if(m.getSecurity() == 1) { // 유저가 관리자일 때
					int n = 0; // 등록된 강의 개수를 저장한다.
					for(Course c : courses) {
						if(c.getProfessor().equals(m.getName())) { // 강의 교수가 본인이라면 courseN에 저장
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
					
					if(n == 0) { // 등록된 강의가 없을 때
						result.put("message","[ERROR] 등록된 강의가 없습니다.");
						result.put("result", false);
						return result.toString();
					}
					
					result.put("result",true);
					result.put("message", "[관리자]개설정보 조회성공");
					result.put("courses", courseArray); // 등록한 강의가 있을경우  courses에 담아 출력
					return result.toString();
				}

				else { // 유저가 학생일 때
					
					if(m.getApplyCourseSize() == 0) { // 신청강의가 없을 때
						result.put("message","[ERROR] 수강 신청한 강의가 없습니다");
						result.put("result",false);
						return result.toString();
					}
					
					for(Course c : m.getApplyCourse()) { // 해당 학생객체의 신청과목 가져오기
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
					result.put("message", "[학생]신청과목 조회성공");
					result.put("courses", courseArray); // 수강신청한 강의 있을경우 courses에 담아 출력
					return result.toString();
				}
			}
		}
		result.put("message","[ERROR] 강의 열람 실패, 회원미가입");
		result.put("result", false);
		return result.toString();
	}
	
	// "수강신청" , 멤버정보, "과목이름"
	// wanted에 따라 신청할 수 없는 강의를 희망과목에 신청하게 한다. wanted = 0 -> 희망 강의 신청안함, wanted = 1 -> 희망 강의 신청
	public String applyCourse(Member member, Course course, int wanted) {
		
		JSONObject result = new JSONObject();
		result.put("command", "applyCourse");
		result.put("result",false);
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				for(Course c : courses) {
					if(c.getCourseId() == course.getCourseId()) {
						
						// member 신청한 과목들이랑 현재 신청한 과목이랑 요일비교 -> (같으면) 시간 비교 -> 시간이 같으면 에러메세지
						for(int i = 0; i < m.getApplyCourseSize(); i++) {
							if(m.getApplyCourse(i).getCourseDay().equals(c.getCourseDay())) {
								if(m.getApplyCourse(i).getTimetable() == c.getTimetable()) {
									// 중복된 시간에 들어있는 강의를 신청할 때 에러메세지를 리턴한다
									result.put("message","[ERROR] 같은 시간에 수강하는 수업이 존재합니다.");
									return result.toString();
								}
							}
						}
						if(c.getCurrent_count() >= c.getMax_count()) { // 신청할 수 있는 수강인원이 더 이상 없을 때
							// 희망인원에 신청하고 싶을 때(wanted == 1), 해당 강의의 희망인원에 유저의 정보를 저장한다. 그렇지 않으면, 신청불가 메세지가 리턴된다.
							if(wanted == 1) {
								for(int i = 0; i < c.getHopePeople_count(); i++) { // 이미 희망과목에 신청이 됐을 때, 에러메세지 리턴
									if(c.getHopePeopleInfo(i).getId().equals(m.getId()))
										result.put("message","[ERROR] 이 강의는 더 이상 신청할 수 없습니다.");
										return result.toString();
								}
								c.setHopePeople(m);
								result.put("result",true);
								result.put("message","[INFO] 희망 강의에 추가하였습니다.");
								return result.toString();
							}else {
								result.put("message","[ERROR] 이 강의는 더 이상 신청할 수 없습니다.");
								return result.toString();
							}
						}else { // 수강 인원 자리가 있을 경우
							if(m.getCredit() < 21 && m.getCredit() + c.getCredit() < 21) {
								m.setCourse(c);
								m.setCredit(c.getCredit() + m.getCredit());
								c.setCurrent_count(c.getCurrent_count() + 1);
								result.put("result",true);
								result.put("message","[INFO] 수강신청에 성공했습니다.");
								return result.toString();
							}else {
								result.put("message","[ERROR] 최대 학점 초과로 수강신청 실패");
								return result.toString();
							}
						}
					}
				}
				result.put("message","[ERROR] 존재하지 않는 과목입니다.");
				return result.toString();
			}
		}
		result.put("message","[ERROR] 존재하지 않는 회원정보입니다.");
		return result.toString();
	}
	
	public String removeCourse(Member member, Course course) { // 수강 강의 취소
		
		JSONObject result = new JSONObject();
		result.put("command", "removeCourse");
		result.put("result",false);
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				for(Course c : courses) {
					if(c.getCourseId() == course.getCourseId()) {
						for(int i = 0; i < m.getApplyCourseSize(); i++) {
							if(m.getCourse(i) == c.getCourseId()) { // 유저가 신청한 과목과 삭제할 과목의 과모코드가 일치하면 해당 과목 삭제
								m.removeCourse(c);
								m.setCredit(m.getCredit() - c.getCredit());
								c.setCurrent_count(c.getCurrent_count() - 1);
								result.put("result",true);
								result.put("message","[INFO] 해당 강의를 삭제했습니다.");
								return result.toString();
							}
						}
						result.put("message","[ERROR] 신청하지 않은 과목은 삭제할 수 없습니다.");
						return result.toString(); // for문을 다 돌고 나오면 유저의 신청 강의 중에 해당 강의가 존재하지 않기에 에러메세지 리턴
					}
				}
				result.put("message","[ERROR] 존재하지 않는 과목입니다.");
				return result.toString(); // 모든 과목코드와 해당 과목코드가 일치하지 않으면 에러메세지 리턴
			}
		}
		result.put("message","[ERROR] 존재하지 않는 사용자입니다.");
		return result.toString(); // 모든 유저 아이디와 멤버 정보에 들어있는 아이디가 일치하지 않을 때 에러메세지 리턴
	}
	
	public String createCourse(Course course) { // 강의 생성
		
		JSONObject result = new JSONObject();
		result.put("command", "createCourse");
		result.put("result",true);
		
		// 강의 입력란에 제대로된 정보를 기재하지 않으면, 강의 생성 실패
		if(course.getCourseDay() == null || course.getCourseName() == null || course.getCredit() <= 0 || course.getCredit() > 3
				|| course.getDpt() == null || course.getGrade() <= 0 || course.getGrade() > 4 || course.getMax_count() <= 0
				|| course.getProfessor() == null || course.getTimetable() <= 0 || course.getTimetable() > 4) {
			
			result.put("message","[ERROR] 강의 생성 실패");
			result.put("result",false);
			return result.toString();
		}
		else {
			// 만약 교수가 같은 요일, 같은 시간대의 수업을 2개 이상 만들려고 할 시, 에러 메세지를 넘겨준다.
			for(Course c : courses) {
				if(currentMember.getName().equals(c.getProfessor())) {
					if(c.getCourseDay().equals(course.getCourseDay())
							&& c.getTimetable() == course.getTimetable()) {
						result.put("message", "[ERROR] 동일한 시간대의 강의 생성 불가");
						return result.toString();
					}
				}
			}
			// 강의 생성의 모든 조건을 충족할 때
			course.setCourseId(courses.size()+1); // 강의 코드 재설정
			courses.add(course);
			
			result.put("message","[INFO] 새로운 강의를 추가하였습니다.");
			return result.toString();
		}
	}
	
	public String lookCourse(Course course) { // 한 과목의 정보를 열람하는 메서드
		
		JSONObject result = new JSONObject();
		result.put("command", "lookCourse");
		result.put("result",true);
		
		for(Course c : courses) {
			if(c.getCourseId() == course.getCourseId()) { // 입력한 과목이 존재하는 과목이면
				JSONObject courseInfo = new JSONObject(c.toString()); // 담을 course정보
				result.put("message", courseInfo); // message에 찾은 course정보를 출력
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
				
				result.put("message","[INFO] 해당 과목의 정보입니다.");
				return result.toString();
				*/
				
			}
		}
		result.put("message", "[ERROR] 존재하지 않는 과목입니다.");
		result.put("result",false);
		return result.toString();
	}
	
	
public String increaseStudent(Member member, Course course) { // 학생 수 증가
		
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
				if(user.getName().equals(c.getProfessor())) { // 강의를 가르치는 교수만 설정할 수 있게 한다_ user안되면 currentMember로
					c.setMax_count(c.getMax_count() + 2); // 수강인원 증가
					
					if(c.getHopePeople_count() == 0) { // 현재 희망인원이 0이면 수강인원만 증가하고 종료
						result.put("result",false);
						result.put("message", "[INFO] 해당 과목의 수강인원을 증가하였습니다.");
						return result.toString();
					}
					for(int i = 0; i < c.getHopePeople_count(); i++) {
						if(c.getCurrent_count() == c.getMax_count()) // 수강인원이 가득 차면 희망인원 삽입 중지 
							break;
						Member temp = c.getHopePeopleInfo(i);
						c.removeHopePeople(temp); // 희망인원에서 해당 멤버 삭제
						
						for(int j = 0; j < temp.getApplyCourseSize(); j++) {
							if(temp.getApplyCourse(i).getCourseDay().equals(c.getCourseDay())) {
								if(temp.getApplyCourse(i).getTimetable() == c.getTimetable()) {
									// 중복된 시간에 들어있는 강의를 신청할 때 에러메세지를 리턴한다
									result.put("message","[ERROR] 같은 시간에 수강하는 수업이 존재합니다.");
									return result.toString();
								}
							}
						}
						if(temp.getCredit() < 22 && temp.getCredit() + c.getCredit() < 22) {
							temp.setCourse(c); // 멤버의 수강목록에 현재 강의 추가
							temp.setCredit(temp.getCredit() + c.getCredit());
							c.setCurrent_count(c.getCurrent_count()+1); // 현재 인원 수 1 증가
						}
					}
					result.put("message", "[INFO] 해당 과목의 수강인원을 증가하고, 희망인원을 해당 강의에 추가하였습니다.");
					result.put("result",true);
					return result.toString();
				}else { // 강의 교수와 이름이 일치하지 않을 때
					result.put("message", "[ERROR] 해당 과목의 교수만 설정할 수 있는 정보입니다.");
					return result.toString();
				}
			}
		}
		result.put("message", "[ERROR] 존재하지 않는 과목입니다.");
		result.put("result",false);
		return result.toString();
	}
	
	
	public String showCurrentUser() { // 현재 로그인한 유저의 정보를 열람하는 메서드
		JSONObject result = new JSONObject();
		JSONArray currentMemberInfo = new JSONArray();
		
		result.put("command", "showCurrentUser");
		result.put("result",false);
		
		if(currentMember == null) { // 현재 로그인한 유저가 없을 때 에러메시지 리턴
			result.put("message", "[ERROR] 로그인이 필요한 기능입니다.");
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
			result.put("message", "[INFO] 현재 유저 정보 열람 성공");
			
			return result.toString();
		}
	}
	
	
	
	
	public String showTimetable(Member member, int x) { // 유저(학생)의 시간표를 확인하는 메서드
		
		Course[][] timetable = new Course[5][courses.size()];
		
		JSONObject result = new JSONObject();
		result.put("command", "showTimetable");
		result.put("result",false);
		
		for(Member m : members) {
			if(m.getId().equals(member.getId())) {
				if(m.getApplyCourseSize() == 0) { // 유저가 신청한 강의가 없을 때 에러메세지 리턴
					result.put("message", "[ERROR] 신청한 강의가 없습니다.");
					return result.toString();
				}
				for(int i = 0; i < m.getApplyCourseSize(); i++) {
					String temp = m.getApplyCourse(i).getCourseDay();
					Course course = m.getApplyCourse(i);
					// 강의가 있는 요일에 따라 다른 배열에 값을 넣어줌
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
		result.put("FRI", timetable[4]); // 정렬된 시간표를 요일에 맞게 저장한다
		result.put("courseSize",courses.size());
		
		if(x == 1){ // 시간표 저장 여부 구분 ( x == 1, 시간표 저장한다)
			boolean res = saveTimetableFun.saveMemberInfo(timetable); // 시간표 저장 성공하면 res -> true
			if(res) { 
				result.put("message", "[INFO] 시간표 저장 성공");
				result.put("result",true);
				return result.toString();
			}
			else {
				result.put("message", "[INFO] 시간표 저장 실패");
				return result.toString();
			}
		}
		
		result.put("message", "[INFO] 시간표 생성 성공");
		result.put("result",true);
		return result.toString();
	}
	
	public Course[] bubbleSort(Course[] arr, Course x) { // 배열 내림차순으로 정렬한다
		
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
		return arr; // 정렬된 배열 리턴한다
	}
	
	
}
