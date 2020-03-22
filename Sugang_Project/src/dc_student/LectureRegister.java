/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dc_student;
import dc_login.Login;
import ClientServer.ServerConnection;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.json.*;
import javax.swing.JFrame;

/**
 *
 * @author HyeonGyu
 */
public class LectureRegister extends javax.swing.JFrame {
    // 유저정보
    String userId;
    String userPasswd;
    String userName;
    String userDept;
    int userGrade;
    int userPoint; 
    int userSecurity;
    
    boolean Question_Result = false; // 질문에 대한 답
    /**
     * Creates new form LectureRegister
     */
    
    public LectureRegister() {
        initComponents();
        getCurrentUser(); // 시작시 로그인한 유저정보 가져오기
        // initCourse();// 시작시 과목데모정보 넣기
    }
    
    public void initCourse(){
        JSONObject data = new JSONObject();
        data.put("command", "initializeCourse"); // 과목데모정보 넣기
        
        JSONObject result = new ServerConnection().request(data);
    }

    public void getCurrentUser(){ // 현재 로그인한 유저정보 가져오기
        JSONObject data = new JSONObject();
        data.put("command", "showCurrentUser");
        
        JSONObject result = new ServerConnection().request(data);
        boolean rs = result.getBoolean("result");
        String fromMessage = result.getString("message");
        
        if(rs){ // 로그인한 유저로 정보초기화
            userId = result.getString("id");
            userPasswd = result.getString("name");
            userName = result.getString("name");
            userDept = result.getString("dpt");
            userGrade = result.getInt("grade");
            userPoint = result.getInt("credit");
            userSecurity = result.getInt("security");
            
            // 왼쪽 label에 유저정보 업데이트
            id.setText(userId);
            name.setText(userName);
            dpt.setText(userDept);
            grade.setText(String.valueOf(userGrade));
            point.setText(String.valueOf(userPoint));
        }
        
        else{ // 로그인한 유저 없을시
            showDialog("Error", fromMessage);
        }   
    }
    public void cleanTable(JTable jtable){ // 테이블정보 삭제
        jtable.setModel(new DefaultTableModel(null, new String[]{"강의코드","강의명","교수","학년", "학과", "현재인원", "최대인원", "요일", "교시", "학점"}));
        // MyCourseTable.setModel(new DefaultTableModel(null, new String[]{"강의코드","강의명","교수","학년", "학과", "현재인원", "최대인원", "요일", "교시", "학점"}));
    }
    
    public void UpdateAllCourse(){ // 전체 과목업데이트
        JSONObject data = new JSONObject();
        data.put("command", "showAllCourse");
        
        JSONObject result = new ServerConnection().request(data);
        boolean rs = result.getBoolean("result");
        String fromMessage = result.getString("message");
        
        if(rs){ // 강의존재
            DefaultTableModel Allmodel = (DefaultTableModel)AllCourseTable.getModel();
            AllCourseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            // 모든강의목록 TABLE 업데이트
            String colNames[] = {"강의코드","강의명","교수","학년", "학과", "현재인원", "최대인원", "요일", "교시", "학점"}; //10열    
            JSONArray courseArray = result.getJSONArray("courses");
                for(int i = 0 ; i < courseArray.length(); i++) {
			System.out.println(courseArray.get(i).toString()); // 각 course정보
                        JSONObject c = new JSONObject(courseArray.get(i).toString()); // 각 세부정보 접근
                        int courseid = c.getInt("courseId");
                        String courseName = c.getString("courseName");
                        String professor = c.getString("professor");
                        int grade = c.getInt("grade");
                        String dpt = c.getString("dpt");
                        int current_count = c.getInt("current_count");
                        int max_count = c.getInt("max_count");
                        String courseDay = c.getString("courseDay");
                        int timetable = c.getInt("timetable");
                        int credit = c.getInt("credit");
                        
                        // 행추가
                        Allmodel.addRow(new Object[]{courseid, courseName, professor, grade, dpt, current_count, max_count, courseDay, timetable, credit});
                }
        }
    }
    
    public void UpdateMyCourse(){
        JSONObject data = new JSONObject();
        data.put("command", "applyCourseShow");
        data.put("id", userId);
        data.put("passwd", userPasswd);
        
        JSONObject result = new ServerConnection().request(data);
        boolean rs = result.getBoolean("result");
        String fromMessage = result.getString("message");
        
        if(rs){ // 강의존재
            DefaultTableModel Mymodel = (DefaultTableModel)MyCourseTable.getModel();
            MyCourseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            // 로그인한 My강의목록 TABLE 업데이트
            String colNames[] = {"강의코드","강의명","교수","학년", "학과", "현재인원", "최대인원", "요일", "교시", "학점"}; //10열    
            JSONArray courseArray = result.getJSONArray("courses");
                for(int i = 0 ; i < courseArray.length(); i++) {
			System.out.println(courseArray.get(i).toString()); // 각 course정보
                        JSONObject c = new JSONObject(courseArray.get(i).toString()); // 각 세부정보 접근
                        int courseid = c.getInt("courseId");
                        String courseName = c.getString("courseName");
                        String professor = c.getString("professor");
                        int grade = c.getInt("grade");
                        String dpt = c.getString("dpt");
                        int current_count = c.getInt("current_count");
                        int max_count = c.getInt("max_count");
                        String courseDay = c.getString("courseDay");
                        int timetable = c.getInt("timetable");
                        int credit = c.getInt("credit");
                        
                        // 행추가
                        Mymodel.addRow(new Object[]{courseid, courseName, professor, grade, dpt, current_count, max_count, courseDay, timetable, credit});
                }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        Dialog = new javax.swing.JDialog();
        ok = new javax.swing.JButton();
        msg = new javax.swing.JLabel();
        QuestionDialog = new javax.swing.JDialog();
        btn_false = new javax.swing.JButton();
        btn_true = new javax.swing.JButton();
        QestionMSG = new javax.swing.JLabel();
        lb_result = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        AllTab = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        AllCourseTable = new javax.swing.JTable();
        MyTab = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        MyCourseTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        dpt = new javax.swing.JLabel();
        grade = new javax.swing.JLabel();
        point = new javax.swing.JLabel();

        Dialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Dialog.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Dialog.setModal(true);

        ok.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        ok.setText("확인");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        msg.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        msg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        msg.setText("서버에서 받는 메시지 입니다.");

        javax.swing.GroupLayout DialogLayout = new javax.swing.GroupLayout(Dialog.getContentPane());
        Dialog.getContentPane().setLayout(DialogLayout);
        DialogLayout.setHorizontalGroup(
            DialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogLayout.createSequentialGroup()
                .addComponent(msg, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(DialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ok, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        DialogLayout.setVerticalGroup(
            DialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(msg, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(ok)
                .addGap(21, 21, 21))
        );

        QuestionDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        QuestionDialog.setModal(true);

        btn_false.setText("취소");
        btn_false.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_falseActionPerformed(evt);
            }
        });

        btn_true.setText("확인");
        btn_true.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_trueActionPerformed(evt);
            }
        });

        QestionMSG.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        QestionMSG.setText("메시지");

        javax.swing.GroupLayout QuestionDialogLayout = new javax.swing.GroupLayout(QuestionDialog.getContentPane());
        QuestionDialog.getContentPane().setLayout(QuestionDialogLayout);
        QuestionDialogLayout.setHorizontalGroup(
            QuestionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(QuestionDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(QuestionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(QestionMSG, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(QuestionDialogLayout.createSequentialGroup()
                        .addComponent(btn_true, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addComponent(btn_false, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(QuestionDialogLayout.createSequentialGroup()
                .addComponent(lb_result)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        QuestionDialogLayout.setVerticalGroup(
            QuestionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, QuestionDialogLayout.createSequentialGroup()
                .addComponent(lb_result)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(QestionMSG, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(QuestionDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_true)
                    .addComponent(btn_false))
                .addContainerGap())
        );

        jLabel7.setText("jLabel7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N

        jPanel2.setBackground(new java.awt.Color(0, 255, 255));

        jButton1.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        jButton1.setText("조회");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        jButton2.setText("수강신청");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        jLabel6.setText("학생을 위한 수강신청을 프로그램입니다");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(47, 47, 47)
                .addComponent(jButton2)
                .addGap(45, 45, 45))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jLabel6))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        AllCourseTable.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        AllCourseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "강의 아이디", "강의명", "교수", "학년", "학과", "현재인원", "최대인원", "요일", "교시", "학점"
            }
        ));
        jScrollPane3.setViewportView(AllCourseTable);

        javax.swing.GroupLayout AllTabLayout = new javax.swing.GroupLayout(AllTab);
        AllTab.setLayout(AllTabLayout);
        AllTabLayout.setHorizontalGroup(
            AllTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AllTabLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 891, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        AllTabLayout.setVerticalGroup(
            AllTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AllTabLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("수강신청", AllTab);

        jPanel3.setBackground(new java.awt.Color(153, 255, 255));

        jButton3.setText("확인");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("삭제");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("맑은 고딕", 1, 14)); // NOI18N
        jLabel9.setText("내가신청한 수강내역 입니다.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(106, 106, 106)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(46, 46, 46)
                .addComponent(jButton4)
                .addGap(43, 43, 43))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jLabel9))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        MyCourseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "강의 아이디", "강의명", "교수", "학년", "학과", "현재인원", "최대인원", "요일", "교시", "학점"
            }
        ));
        jScrollPane4.setViewportView(MyCourseTable);

        javax.swing.GroupLayout MyTabLayout = new javax.swing.GroupLayout(MyTab);
        MyTab.setLayout(MyTabLayout);
        MyTabLayout.setHorizontalGroup(
            MyTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
        );
        MyTabLayout.setVerticalGroup(
            MyTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyTabLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("수강신청 내역", MyTab);

        jSplitPane1.setRightComponent(jTabbedPane1);

        jLabel1.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        jLabel1.setText("학번");

        jLabel2.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        jLabel2.setText("이름");

        jLabel3.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        jLabel3.setText("학과");

        jLabel4.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        jLabel4.setText("학년");

        jLabel5.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        jLabel5.setText("신청학점");

        btn_logout.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        btn_logout.setText("Logout");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        jPanel4.setBackground(new java.awt.Color(102, 204, 255));

        jLabel8.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("hallym_logo_small.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("맑은 고딕", 1, 24)); // NOI18N
        jLabel10.setText("학생");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel10)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        id.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        id.setText("???");

        name.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        name.setText("???");

        dpt.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        dpt.setText("???");

        grade.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        grade.setText("???");

        point.setFont(new java.awt.Font("맑은 고딕", 0, 12)); // NOI18N
        point.setText("???");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(point, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(grade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dpt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(name, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(id, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(id))
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(name))
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(dpt))
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(grade))
                .addGap(49, 49, 49)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(point))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(btn_logout)
                .addContainerGap())
        );

        jSplitPane1.setLeftComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void okActionPerformed(java.awt.event.ActionEvent evt) {                                   
        Dialog.dispose();
    }                                  

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        cleanTable(AllCourseTable);
        UpdateAllCourse();
    }                                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        cleanTable(MyCourseTable);
        UpdateMyCourse();
    }                                        

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        int rowIndex = AllCourseTable.getSelectedRow(); // 선택줄 번호 가져오기
        if(rowIndex == -1){ // 아무것도 선택 안됬을 경우
            showDialog("Error", "수강신청할 과목을 선택해주세요.");
        }
        else{ // 선택했을 경우
            int select_course_id = (int)AllCourseTable.getValueAt(rowIndex, 0); // 선택 행, 0열 (courseid) 가져오기
            int select_current_count = (int)AllCourseTable.getValueAt(rowIndex, 5); // 선택행, current_count
            int select_max_count = (int)AllCourseTable.getValueAt(rowIndex, 6); // 선택행, max_COUNT
            
            JSONObject data = new JSONObject();
            data.put("command", "applyCourse");
            data.put("id", userId);
            data.put("passwd", userPasswd);
            data.put("courseId", select_course_id);
            data.put("hopePeople", 0); // 정원초과가 아닌 기본추가
            
            
            if(select_current_count >= select_max_count){ // 최대인원 초과시
                // 희망강좌로 추가
                data.put("hopePeople", 1);    
            } 
            
           // 정보받아오기
            JSONObject result = new ServerConnection().request(data);
            boolean rs = result.getBoolean("result");
            String fromMessage = result.getString("message");
            
            if(rs){
                showDialog("Success", fromMessage);
                

            }
            else{
                showDialog("Error", fromMessage);
            }
            
            // 정보다시 가져오기
            cleanTable(AllCourseTable);
            UpdateAllCourse();
            getCurrentUser(); // 현재로그인 정보 다시 가져오기 - 신청학점확인
        }
        
    }                                        

    private void btn_trueActionPerformed(java.awt.event.ActionEvent evt) {                                         
        Question_Result = true; // 확인시 true로 초기화
    }                                        

    private void btn_falseActionPerformed(java.awt.event.ActionEvent evt) {                                          
        Question_Result = false; // 취소시 false로 초기화
    }                                         

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        int rowIndex = MyCourseTable.getSelectedRow(); // 선택줄 번호 가져오기
        if(rowIndex == -1){ // 아무것도 선택 안됬을 경우
            showDialog("Error", "수강삭제할 과목을 선택해주세요.");
        }
        else{ // 선택했을 경우
            int select_course_id = (int)MyCourseTable.getValueAt(rowIndex, 0); // 선택 행, 0열 (courseid) 가져오기
            
            JSONObject data = new JSONObject();
            data.put("command", "removeCourse");
            data.put("id", userId);
            data.put("passwd", userPasswd);
            data.put("courseId", select_course_id);            
            
            // 정보받아오기
            JSONObject result = new ServerConnection().request(data);
            boolean rs = result.getBoolean("result");
            String fromMessage = result.getString("message");
            
            if(rs){
                showDialog("Success", fromMessage);
                // 정보다시 가져오기
            }
            else{
                showDialog("Error", fromMessage);
            }
            
            cleanTable(MyCourseTable);
            UpdateMyCourse();
            getCurrentUser(); // 현재로그인 정보 다시 가져오기 - 신청학점확인
        }
    }                                        

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
        JSONObject data = new JSONObject();
        data.put("command", "logout");
        data.put("id", userId);
        data.put("passwd", userPasswd);

        JSONObject result = new ServerConnection().request(data);
        boolean rs = result.getBoolean("result");
        String fromMessage = result.getString("message");

        if(rs){ // 로그아웃성공
            showDialog("Success", fromMessage);
            this.dispose();
            Login login = new Login();
            login.setVisible(true);
            login.pack();
            login.setLocationRelativeTo(null);
        }
        else{ // 실패
            showDialog("Eroor", fromMessage);
        }
    }                                          

    public void showDialog(String info, String msg){ // 서버에서 받은 Message Modal Dialog 출력
        Dialog.setTitle(info); // title지정
        this.msg.setText(msg); //메세지 지정
        Dialog.pack();
        Dialog.setLocationRelativeTo(null);
        Dialog.setVisible(true);
    } 
    
    public void showQuestionDialog(String info, String msg){ // 사용자에게 물어보기
        QuestionDialog.setTitle(info); // title 지정
        QestionMSG.setText(msg); // 메세지 지정\
        QuestionDialog.pack();
        QuestionDialog.setLocationRelativeTo(null);
        QuestionDialog.setVisible(true);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LectureRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LectureRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LectureRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LectureRegister.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LectureRegister().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JTable AllCourseTable;
    private javax.swing.JPanel AllTab;
    private javax.swing.JDialog Dialog;
    private javax.swing.JTable MyCourseTable;
    private javax.swing.JPanel MyTab;
    private javax.swing.JLabel QestionMSG;
    private javax.swing.JDialog QuestionDialog;
    private javax.swing.JButton btn_false;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_true;
    private javax.swing.JLabel dpt;
    private javax.swing.JLabel grade;
    private javax.swing.JLabel id;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lb_result;
    private javax.swing.JLabel msg;
    private javax.swing.JLabel name;
    private javax.swing.JButton ok;
    private javax.swing.JLabel point;
    // End of variables declaration                   
}
