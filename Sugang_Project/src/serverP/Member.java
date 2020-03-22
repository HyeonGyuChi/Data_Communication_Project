package serverP;

import java.util.ArrayList;

public class Member { // 회원 정보 저장
	private String id; // 회원 아이디
	private String passwd; // 회원 비밀번호
	private String name; // 회원 이름
	private String missNum; // 회원 인증번호
	private String dpt; // 회원 학과
	private int grade; // 회원 학년
	private int credit; // 신청학점
	private int security; // 회원 구분(학생,관리자)
	private ArrayList<Course> applyCourse; // 신청과목
	private String[][] timetable;
	
	public static int STUDENT = 0;
	public static int MANAGER = 1;
	
	public Member(String id, String passwd, String name, String missNum, String dpt, int grade, int security) {
		this.id = id;
		this.passwd = passwd;
		this.name = name;
		this.missNum = missNum;
		this.dpt = dpt;
		this.grade = grade;
		this.security = security;
		credit = 0;
		applyCourse = new ArrayList<Course>(); // 빈 arraylist 생성
		timetable = null;
	}

	public Member(String id, String passwd) {
		this.id = id;
		this.passwd = passwd;
	}

	public Member(String id, String name, String missNum) {
		this.id = id;
		this.name = name;
		this.missNum = missNum;
	}
	
	public Member(String id, String passwd, String name, String missNum,int grade) {
		this.id = id;
		this.passwd = passwd;
		this.name = name;
		this.missNum = missNum;
		this.grade = grade;
	}
	
	public Member(String name) {
		this.name = name;
	}
	
	public int getCourse(int i) {
		return applyCourse.get(i).getCourseId();
	}
	
	public int getApplyCourseSize() {
		return applyCourse.size();
	}
	
	public Course getApplyCourse(int i) {
		return applyCourse.get(i);
	}
	
	public ArrayList<Course> getApplyCourse(){
		return applyCourse;
	}
	
	public void setCourse(Course c) {
		applyCourse.add(c);
	}
	
	public void removeCourse(Course c) {
		applyCourse.remove(c);
	}

	public void setSecurity(int security) {
		this.security = security;
	}

	public int getSecurity() {
		return security;
	}

	public void setSe(int security) {
		this.security = security;
	}

	public String getDpt() {
		return dpt;
	}

	public void setDpt(String dpt) {
		this.dpt = dpt;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	
	public int getGrade() {
		return grade;
	}
	
	public void setGrade(int grade) {
		this.grade = grade;
	}
	
	public String getId() {
		return id;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMissNum() {
		return missNum;
	}

	public void setMissNum(String missNum) {
		this.missNum = missNum;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void saveTimetable(String[][] timetable) {
		this.timetable = timetable;
	}
	
	public String getTimetable(int i,int j) {
		return timetable[i][j];
	}
	
}
