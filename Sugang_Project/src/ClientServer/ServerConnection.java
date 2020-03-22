/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.SynchronousQueue;

import org.json.JSONObject;

/**
 *
 * @author HyeonGyu
 */
public class ServerConnection {
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        
    public ServerConnection(){       
        try{
                        client = new Socket("localhost", 6006);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                        // client.close();
        }
         catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
    }
    
    public JSONObject request(JSONObject data){

            // 회원가입
            /*
            data.put("command", "register");
            data.put("id", "demo1");
            data.put("passwd", "1111");
            data.put("name", "데모이름");
            data.put("missNum", "가족");
            data.put("dpt", "데모학과");
            data.put("grade", 3);
            data.put("security", 0);
            */

            // 로그인
            //data.put("command", "login");
            //data.put("id", "demo1");
            //data.put("passwd", "1111");

            // 데이터전송
            out.println(data.toString());
            out.flush();
            
            
            String message = null; // 서버로 부터 받은 응답
            
            // 그 후 서버로부터 응답을 받기위한 처리
            try{
            message = in.readLine();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
            System.out.println("서버로부터 메시지를 받았습니다 : " + message);
            
            JSONObject result = new JSONObject(message);
            
            close(); // 클라이언트 종료
            return result;
            
            /*
             * register : id, passwd, name, missNum, dpt, grade(int), security(int) -> command, result, message  
             * login : id, passwd -> command, result, message  
             * findPasswd : id, name, missNum -> command, result, message(passwd)
             * logout : id, passwd -> command, result, message
             * applyCourse : id, passwd, courseId(int), hopePeople(int) -> command, result, message
             * removeCourse : id, passwd, courseId(int) -> command, result, message
             * lookCourse : courseId -> command, result,[ courseId, courseIdName, professor, grade, dpt, current_count, max_count, hopePeople_count, timetable, credit  ] , message
             * CreateCourse :  courseName, professor, grade(int), dpt, maxCount(int), courseDay, timeTable(int), credit(int) -> command, result, message
             * applyCoruseShow : id, passwd -> command, result, message[course]
             * showAllCourse : -> command, result, message[course]
             * increaseStudent : courseId(int), id, passwd -> command, result, message  
             * showTimetable : id, passwd
             * saveTimetable : courseId(int), id, passwd
             */
/*
            String command = result.getString("command");
            boolean res = result.getBoolean("result");
            String fromMessage = result.getString("message");

            if(command.equals("login")) {
                    if(res) {
                            System.out.println("로그인 메뉴[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("로그인 메뉴[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("register")) {
                    if(res) {
                            System.out.println("회원가입 메뉴[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("회원가입 메뉴[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("findPasswd")) {
                    if(res) {
                            System.out.println("비밀번호 찾기[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("비밀번호 찾기[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("logout")) {
                    if(res) {
                            System.out.println("로그아웃[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("로그아웃[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("applyCourse")) {
                    if(res) {
                            System.out.println("수강신청[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("수강신청[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("removeCourse")) {
                    if(res) {
                            System.out.println("수강신청철회[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("수강신청철회[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("lookCourse")) {
                    if(res) {
                            System.out.println("해당수강정보조회[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("해당수강정보조회[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("CreateCourse")) {
                    if(res) {
                            System.out.println("수강생성[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("수강생성[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("applyCourseShow")) {
                    if(res) {
                            System.out.println("신청한수강과목정보[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("신청한수강과목정보[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("showAllCourse")) {
                    if(res) {
                            System.out.println("모든수강과목[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("모든수강과목[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("increaseStudent")) {
                    if(res) {
                            System.out.println("수강인원증가[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("수강인원증가[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("showTimeTable")) {
                    if(res) {
                            System.out.println("나의시간표보기[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("나의시간표보기[실패] : " + fromMessage);
                    }
            }
            else if(command.equals("saveTimetable")) {
                    if(res) {
                            System.out.println("나의시간표저장[성공] : " + fromMessage);
                    }
                    else {
                            System.out.println("나의시간표저장[실패] : " + fromMessage);
                    }
            }

            else {
                    System.out.println("올바르지 않은 명령어입니다.");
            }
    }
    }catch(Exception e){
    e.printStackTrace();
*/
}

    
    public void close(){
        try{
        client.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
