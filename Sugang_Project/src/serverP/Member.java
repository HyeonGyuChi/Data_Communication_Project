package serverP;

import java.util.ArrayList;

public class Member { // ȸ�� ���� ����
	private String id; // ȸ�� ���̵�
	private String passwd; // ȸ�� ��й�ȣ
	private String name; // ȸ�� �̸�
	private String missNum; // ȸ�� ������ȣ
	private String dpt; // ȸ�� �а�
	private int grade; // ȸ�� �г�
	private int credit; // ��û����
	private int security; // ȸ�� ����(�л�,������)
	private ArrayList<Course> applyCourse; // ��û����
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
		applyCourse = new ArrayList<Course>(); // �� arraylist ����
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
