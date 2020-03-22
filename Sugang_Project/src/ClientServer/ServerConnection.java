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

            // ȸ������
            /*
            data.put("command", "register");
            data.put("id", "demo1");
            data.put("passwd", "1111");
            data.put("name", "�����̸�");
            data.put("missNum", "����");
            data.put("dpt", "�����а�");
            data.put("grade", 3);
            data.put("security", 0);
            */

            // �α���
            //data.put("command", "login");
            //data.put("id", "demo1");
            //data.put("passwd", "1111");

            // ����������
            out.println(data.toString());
            out.flush();
            
            
            String message = null; // ������ ���� ���� ����
            
            // �� �� �����κ��� ������ �ޱ����� ó��
            try{
            message = in.readLine();
            }
            catch(Exception e){
                e.printStackTrace();
            }
            
            System.out.println("�����κ��� �޽����� �޾ҽ��ϴ� : " + message);
            
            JSONObject result = new JSONObject(message);
            
            close(); // Ŭ���̾�Ʈ ����
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
                            System.out.println("�α��� �޴�[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("�α��� �޴�[����] : " + fromMessage);
                    }
            }
            else if(command.equals("register")) {
                    if(res) {
                            System.out.println("ȸ������ �޴�[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("ȸ������ �޴�[����] : " + fromMessage);
                    }
            }
            else if(command.equals("findPasswd")) {
                    if(res) {
                            System.out.println("��й�ȣ ã��[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("��й�ȣ ã��[����] : " + fromMessage);
                    }
            }
            else if(command.equals("logout")) {
                    if(res) {
                            System.out.println("�α׾ƿ�[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("�α׾ƿ�[����] : " + fromMessage);
                    }
            }
            else if(command.equals("applyCourse")) {
                    if(res) {
                            System.out.println("������û[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("������û[����] : " + fromMessage);
                    }
            }
            else if(command.equals("removeCourse")) {
                    if(res) {
                            System.out.println("������ûöȸ[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("������ûöȸ[����] : " + fromMessage);
                    }
            }
            else if(command.equals("lookCourse")) {
                    if(res) {
                            System.out.println("�ش����������ȸ[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("�ش����������ȸ[����] : " + fromMessage);
                    }
            }
            else if(command.equals("CreateCourse")) {
                    if(res) {
                            System.out.println("��������[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("��������[����] : " + fromMessage);
                    }
            }
            else if(command.equals("applyCourseShow")) {
                    if(res) {
                            System.out.println("��û�Ѽ�����������[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("��û�Ѽ�����������[����] : " + fromMessage);
                    }
            }
            else if(command.equals("showAllCourse")) {
                    if(res) {
                            System.out.println("����������[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("����������[����] : " + fromMessage);
                    }
            }
            else if(command.equals("increaseStudent")) {
                    if(res) {
                            System.out.println("�����ο�����[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("�����ο�����[����] : " + fromMessage);
                    }
            }
            else if(command.equals("showTimeTable")) {
                    if(res) {
                            System.out.println("���ǽð�ǥ����[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("���ǽð�ǥ����[����] : " + fromMessage);
                    }
            }
            else if(command.equals("saveTimetable")) {
                    if(res) {
                            System.out.println("���ǽð�ǥ����[����] : " + fromMessage);
                    }
                    else {
                            System.out.println("���ǽð�ǥ����[����] : " + fromMessage);
                    }
            }

            else {
                    System.out.println("�ùٸ��� ���� ��ɾ��Դϴ�.");
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
