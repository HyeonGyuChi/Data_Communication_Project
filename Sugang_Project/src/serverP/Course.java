package serverP;

import java.util.ArrayList;

public class Course { // 강의 정보
	private int courseId; // 강의 아이디
	private String courseName; // 강의 이름
	private String professor; // 강의 교수 이름
	private int grade; // 강의 학년
	private String dpt;
	private int current_count;
	private int max_count;
	private String courseDay;
	private int timetable;
	private int credit;
	private ArrayList<Member> hopePeople;
	
	public Course(int courseId,String courseName,String professor,int grade,String dpt,int max_count,
			String courseDay,int timetable,int credit) {
		this.courseId = courseId;
		this.courseName = courseName;
		this.professor = professor;
		this.grade = grade;
		this.dpt = dpt;
		this.current_count = 0;
		this.max_count = max_count;
		this.courseDay = courseDay;
		this.timetable = timetable;
		this.credit = credit;
		this.hopePeople = new ArrayList<Member>();
	}
	
	public String getCourseDay() {
		return courseDay;
	}
	
	public Course(int courseId) {
		this.courseId = courseId;
	}
	
	public void setHopePeople(Member m) {
		hopePeople.add(m);
	}
	
	public void removeHopePeople(Member m) {
		hopePeople.remove(m);
	}
	
	public int getHopePeople_count() {
		return hopePeople.size();
	}
	
	public Member getHopePeopleInfo(int n) {
		return hopePeople.get(n);
	}
	
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getProfessor() {
		return professor;
	}
	public void setProfessor(String professor) {
		this.professor = professor;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getDpt() {
		return dpt;
	}
	public void setDpt(String dpt) {
		this.dpt = dpt;
	}
	public int getCurrent_count() {
		return current_count;
	}
	public void setCurrent_count(int current_count) {
		this.current_count = current_count;
	}
	public int getMax_count() {
		return max_count;
	}
	public void setMax_count(int max_count) {
		this.max_count = max_count;
	}
	public int getTimetable() {
		return timetable;
	}
	public void setTimetable(int timetable) {
		this.timetable = timetable;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public ArrayList<Member> getHopePeople(){
		return hopePeople;
	}
	@Override
	public String toString() {
		
		String str = "[{\"courseId\":" + courseId + "\",\"courseName\":\"" + courseName + "\",\"professor\":\"" + professor
				+ "\",\"grade\":" + grade + "\",\"dpt\":\"" + dpt + "\",\"current_count\":" + current_count
				+ "\",\"max_count\":" + max_count + "\",\"courseDay\":\"" + courseDay + "\",\"timetable\":" + timetable
				+ "\",\"credit\":" + credit + "}]";
		return str;
		
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
	
	
}
