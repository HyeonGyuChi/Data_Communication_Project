/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dc_professor;
import org.json.*;
import ClientServer.ServerConnection;
import java.awt.Dialog;
import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;
import javax.swing.JTable;
import dc_login.Login;

/**
 *
 * @author HyeonGyu
 */
public class LectureManage extends javax.swing.JFrame {

    
     // ���� �α����� ��������
    String userId;
    String userPasswd;
    String userName;
    String userDept;
    int userGrade;
    int userPoint; 
    int userSecurity;
    
    
    /** Creates new form LectureManage */
    public LectureManage() {
        initComponents();
        getCurrentUser(); // ���۽� �α����� �������� ��������
        
    }
    
        public void getCurrentUser(){ // ���� �α����� �������� ��������
        JSONObject data = new JSONObject();
        data.put("command", "showCurrentUser");
        
        JSONObject result = new ServerConnection().request(data);
        boolean rs = result.getBoolean("result");
        String fromMessage = result.getString("message");
        
        if(rs){ // �α����� ������ �����ʱ�ȭ
            userId = result.getString("id");
            userPasswd = result.getString("passwd");
            userName = result.getString("name");
            userDept = result.getString("dpt");
            userGrade = result.getInt("grade");
            userPoint = result.getInt("credit");
            userSecurity = result.getInt("security");
            
            // ���� label�� �������� ������Ʈ
            id.setText(userId);
            name.setText(userName);
            dpt.setText(userDept);
        }
        
        else{ // �α����� ���� ������
            showDialog("Error", fromMessage);
        }   
    }
    public void cleanTable(JTable jtable){ // ���̺����� ����
        jtable.setModel(new DefaultTableModel(null, new String[]{"�����ڵ�","���Ǹ�","����","�г�", "�а�", "�����ο�", "�ִ��ο�", "����", "����", "����"}));
        // MyCourseTable.setModel(new DefaultTableModel(null, new String[]{"�����ڵ�","���Ǹ�","����","�г�", "�а�", "�����ο�", "�ִ��ο�", "����", "����", "����"}));
    }
    
    public void UpdateAllCourse(){ // ��ü ���������Ʈ
        JSONObject data = new JSONObject();
        data.put("command", "showAllCourse");
        
        JSONObject result = new ServerConnection().request(data);
        boolean rs = result.getBoolean("result");
        String fromMessage = result.getString("message");
        
        if(rs){ // ��������
            DefaultTableModel Allmodel = (DefaultTableModel)AllCourseTable.getModel();
            AllCourseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            // ��簭�Ǹ�� TABLE ������Ʈ
            String colNames[] = {"�����ڵ�","���Ǹ�","����","�г�", "�а�", "�����ο�", "�ִ��ο�", "����", "����", "����"}; //10��    
            JSONArray courseArray = result.getJSONArray("courses");
                for(int i = 0 ; i < courseArray.length(); i++) {
			System.out.println(courseArray.get(i).toString()); // �� course����
                        JSONObject c = new JSONObject(courseArray.get(i).toString()); // �� �������� ����
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
                        
                        // ���߰�
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
        
        if(rs){ // ��������
            DefaultTableModel Mymodel = (DefaultTableModel)MyCourseTable.getModel();
            MyCourseTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            
            // �α����� My���Ǹ�� TABLE ������Ʈ
            String colNames[] = {"�����ڵ�","���Ǹ�","����","�г�", "�а�", "�����ο�", "�ִ��ο�", "����", "����", "����"}; //10��    
            JSONArray courseArray = result.getJSONArray("courses");
                for(int i = 0 ; i < courseArray.length(); i++) {
			System.out.println(courseArray.get(i).toString()); // �� course����
                        JSONObject c = new JSONObject(courseArray.get(i).toString()); // �� �������� ����
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
                        
                        // ���߰�
                        Mymodel.addRow(new Object[]{courseid, courseName, professor, grade, dpt, current_count, max_count, courseDay, timetable, credit});
                }
        }
    }
    
        public void showDialog(String info, String msg){ // �������� ���� Message Modal Dialog ���
        Dialog.setTitle(info); // title����
        this.msg.setText(msg); //�޼��� ����
        Dialog.pack();
        Dialog.setLocationRelativeTo(null);
        Dialog.setVisible(true);
    } 
   

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        Dialog = new javax.swing.JDialog();
        ok = new javax.swing.JButton();
        msg = new javax.swing.JLabel();
        addCourseDialog = new javax.swing.JDialog();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btn_addCourse = new javax.swing.JButton();
        tf_courseName = new javax.swing.JTextField();
        tf_professor = new javax.swing.JTextField();
        tf_dpt = new javax.swing.JTextField();
        combo_grade = new javax.swing.JComboBox<>();
        tf_maxCount = new javax.swing.JTextField();
        combo_courseDay = new javax.swing.JComboBox<>();
        combo_timeTable = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        tf_credit = new javax.swing.JTextField();
        jSplitPane2 = new javax.swing.JSplitPane();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        AllTab1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        AllCourseTable = new javax.swing.JTable();
        MyTab = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        MyCourseTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        id = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        dpt = new javax.swing.JLabel();

        Dialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        Dialog.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Dialog.setModal(true);

        ok.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        ok.setText("Ȯ��");
        ok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okActionPerformed(evt);
            }
        });

        msg.setFont(new java.awt.Font("���� ���", 1, 14)); // NOI18N
        msg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        msg.setText("�������� �޴� �޽��� �Դϴ�.");
        msg.setMaximumSize(new java.awt.Dimension(400, 20));

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

        addCourseDialog.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addCourseDialog.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addCourseDialog.setModal(true);

        jPanel6.setBackground(new java.awt.Color(153, 153, 255));

        jLabel6.setFont(new java.awt.Font("���� ���", 1, 36)); // NOI18N
        jLabel6.setText("�����߰�");

        jLabel8.setFont(new java.awt.Font("���� ���", 1, 14)); // NOI18N
        jLabel8.setText("���ο� ���Ǹ� �߰��մϴ�");

        jPanel7.setBackground(new java.awt.Color(102, 255, 255));

        jLabel9.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        jLabel9.setText("���Ǹ�");

        jLabel10.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        jLabel10.setText("���Ǳ���");

        jLabel11.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        jLabel11.setText("�а�");

        jLabel13.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        jLabel13.setText("���Ǽ���");

        jLabel14.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        jLabel14.setText("�ִ��ο�");

        jLabel15.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        jLabel15.setText("���ǿ���");

        jLabel16.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        jLabel16.setText("����");

        btn_addCourse.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        btn_addCourse.setText("���ǻ���");
        btn_addCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addCourseActionPerformed(evt);
            }
        });

        tf_courseName.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N

        tf_professor.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        tf_professor.setEnabled(false);

        tf_dpt.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        tf_dpt.setEnabled(false);
        tf_dpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tf_dptActionPerformed(evt);
            }
        });

        combo_grade.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        combo_grade.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1�г�", "2�г�", "3�г�", "4�г�" }));

        tf_maxCount.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N

        combo_courseDay.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        combo_courseDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MON", "TUE", "WED", "THR", "FRI" }));

        combo_timeTable.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        combo_timeTable.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1����(09:00)", "2����(10:00)", "3����(11:00)", "4����(12:00)", "5����(13:00)", "6����(14:00)" }));
        combo_timeTable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_timeTableActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N
        jLabel17.setText("����");

        tf_credit.setFont(new java.awt.Font("���� ���", 0, 12)); // NOI18N

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel9)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel17))
                .addGap(65, 65, 65)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_credit, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(tf_courseName)
                        .addComponent(tf_professor, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                        .addComponent(tf_dpt, javax.swing.GroupLayout.DEFAULT_SIZE, 167, Short.MAX_VALUE)
                        .addComponent(combo_grade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tf_maxCount)
                        .addComponent(combo_courseDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combo_timeTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(68, Short.MAX_VALUE))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(btn_addCourse)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(tf_courseName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(tf_professor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(tf_dpt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(combo_grade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(tf_maxCount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(combo_courseDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(combo_timeTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(tf_credit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(btn_addCourse)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout addCourseDialogLayout = new javax.swing.GroupLayout(addCourseDialog.getContentPane());
        addCourseDialog.getContentPane().setLayout(addCourseDialogLayout);
        addCourseDialogLayout.setHorizontalGroup(
            addCourseDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        addCourseDialogLayout.setVerticalGroup(
            addCourseDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(0, 255, 255));

        jButton3.setText("��ȸ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("���ǻ���");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("���� ���", 1, 14)); // NOI18N
        jLabel19.setText("���� ������ ��ü���� �����Դϴ�.");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(47, 47, 47)
                .addComponent(jButton4)
                .addGap(45, 45, 45))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jLabel19))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        AllCourseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "���� ���̵�", "���Ǹ�", "����", "�г�", "�а�", "�����ο�", "�ִ��ο�", "����", "����", "����"
            }
        ));
        jScrollPane3.setViewportView(AllCourseTable);

        javax.swing.GroupLayout AllTab1Layout = new javax.swing.GroupLayout(AllTab1);
        AllTab1.setLayout(AllTab1Layout);
        AllTab1Layout.setHorizontalGroup(
            AllTab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(AllTab1Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 891, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        AllTab1Layout.setVerticalGroup(
            AllTab1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AllTab1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3))
        );

        jTabbedPane2.addTab("��ü����", AllTab1);

        jPanel4.setBackground(new java.awt.Color(153, 255, 255));

        jButton5.setText("��ȸ");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("�����ο� �߰�");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("���� ���", 1, 14)); // NOI18N
        jLabel7.setText("���� ������ ���Ǹ�� �Դϴ�.");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton5)
                .addGap(46, 46, 46)
                .addComponent(jButton6)
                .addGap(43, 43, 43))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jLabel7))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        MyCourseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "���� ���̵�", "���Ǹ�", "����", "�г�", "�а�", "�����ο�", "�ִ��ο�", "����", "����", "����"
            }
        ));
        jScrollPane4.setViewportView(MyCourseTable);

        javax.swing.GroupLayout MyTabLayout = new javax.swing.GroupLayout(MyTab);
        MyTab.setLayout(MyTabLayout);
        MyTabLayout.setHorizontalGroup(
            MyTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 891, Short.MAX_VALUE)
        );
        MyTabLayout.setVerticalGroup(
            MyTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MyTabLayout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("��������", MyTab);

        jSplitPane2.setRightComponent(jTabbedPane2);

        jLabel1.setText("���");

        jLabel2.setText("�̸�");

        jLabel3.setText("�а�");

        btn_logout.setText("Logout");
        btn_logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_logoutActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(153, 153, 153));

        jLabel18.setIcon(new javax.swing.ImageIcon(this.getClass().getResource("hallym_logo_small.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("���� ���", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("����");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(43, 43, 43))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        id.setText("???");

        name.setText("???");

        dpt.setText("???");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dpt, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(name, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(id, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 153, Short.MAX_VALUE)
                .addComponent(btn_logout)
                .addContainerGap())
        );

        jSplitPane2.setLeftComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2)
        );

        pack();
    }// </editor-fold>                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        cleanTable(AllCourseTable);
        UpdateAllCourse();
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        
        tf_professor.setText(userName); // �����̸� ����
        tf_dpt.setText(userDept); // �����Ҽ� ����
        
        // �ʱ�ȭ
        tf_courseName.setText("");
        tf_credit.setText("");
        tf_maxCount.setText("");
        combo_courseDay.setSelectedIndex(0);
        combo_grade.setSelectedIndex(0);
        combo_timeTable.setSelectedItem(0);
        
        addCourseDialog.pack();
        addCourseDialog.setLocationRelativeTo(null);
        addCourseDialog.setVisible(true);

    }                                        

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        cleanTable(MyCourseTable);
        UpdateMyCourse();
    }                                        

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        int rowIndex = MyCourseTable.getSelectedRow(); // ������ ��ȣ ��������
        if(rowIndex == -1){ // �ƹ��͵� ���� �ȉ��� ���
            showDialog("Error", "���������� ������ �������ּ���.");
        }
        else{ // �������� ���
            int select_course_id = (int)MyCourseTable.getValueAt(rowIndex, 0); // ���� ��, 0�� (courseid) ��������

            JSONObject data = new JSONObject();
            data.put("command", "increaseStudent");
            data.put("id", userId);
            data.put("passwd", userPasswd);
            data.put("courseId", select_course_id);

            // �����޾ƿ���
            JSONObject result = new ServerConnection().request(data);
            boolean rs = result.getBoolean("result");
            String fromMessage = result.getString("message");

            if(rs){
                showDialog("Success", fromMessage);
            }
            else{
                showDialog("Error", fromMessage);
            }

            // �����ٽ� ��������
            cleanTable(MyCourseTable);
            UpdateMyCourse();
            getCurrentUser(); // ����α��� ���� �ٽ� �������� - ��û����Ȯ��
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

        if(rs){ // �α׾ƿ�����
            showDialog("Success", fromMessage);
            this.dispose();
            Login login = new Login();
            login.setVisible(true);
            login.pack();
            login.setLocationRelativeTo(null);
        }
        else{ // ����
            showDialog("Eroor", fromMessage);
        }
    }                                          

    private void okActionPerformed(java.awt.event.ActionEvent evt) {                                   
        Dialog.dispose();
    }                                  

    private void btn_addCourseActionPerformed(java.awt.event.ActionEvent evt) {                                              
        if(checkaddCourseBlank()){ // �Է¶��� ����������
            showDialog("Error", "��ĭ�� ä���ּ���");
        } else{ // ��������������
            
            String courseName = tf_courseName.getText();
            String professor = tf_professor.getText();
            int grade = combo_grade.getSelectedIndex() + 1;
            String dpt = tf_dpt.getText();
            int maxCount = Integer.parseInt(tf_maxCount.getText());
            String courseDay = (String)combo_courseDay.getSelectedItem();
            int timeTable = combo_timeTable.getSelectedIndex() + 1;
            int credit = Integer.parseInt(tf_credit.getText());
            
            JSONObject data = new JSONObject();
            data.put("command", "createCourse");
            data.put("courseName", courseName);
            data.put("professor", professor);
            data.put("grade", grade);
            data.put("dpt", dpt);
            data.put("maxCount", maxCount);
            data.put("courseDay", courseDay);
            data.put("timeTable", timeTable);
            data.put("credit", credit);
            
            JSONObject result = new ServerConnection().request(data); // ������
            boolean rs = result.getBoolean("result");
            String message = result.getString("message");
            
            if(rs){ // ���ǻ��� ����
                showDialog("Sucsses", message);
                addCourseDialog.dispose();
                
               
            }
            else { // ���ǻ��� ����
                showDialog("Error", message);
            }

        }
       
        
    }                                             

    private void tf_dptActionPerformed(java.awt.event.ActionEvent evt) {                                       
        // TODO add your handling code here:
    }                                      

    private void combo_timeTableActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    public boolean checkaddCourseBlank(){
        if(tf_courseName.getText().equals("") || tf_professor.getText().equals("") || tf_dpt.getText().equals("") || tf_maxCount.getText().equals("") || tf_credit.equals("")){
            return true; // �ϳ��� �������
        }
        return false;
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
            java.util.logging.Logger.getLogger(LectureManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LectureManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LectureManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LectureManage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LectureManage().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify                     
    private javax.swing.JTable AllCourseTable;
    private javax.swing.JPanel AllTab1;
    private javax.swing.JDialog Dialog;
    private javax.swing.JTable MyCourseTable;
    private javax.swing.JPanel MyTab;
    private javax.swing.JDialog addCourseDialog;
    private javax.swing.JButton btn_addCourse;
    private javax.swing.JButton btn_logout;
    private javax.swing.JComboBox<String> combo_courseDay;
    private javax.swing.JComboBox<String> combo_grade;
    private javax.swing.JComboBox<String> combo_timeTable;
    private javax.swing.JLabel dpt;
    private javax.swing.JLabel id;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel msg;
    private javax.swing.JLabel name;
    private javax.swing.JButton ok;
    private javax.swing.JTextField tf_courseName;
    private javax.swing.JTextField tf_credit;
    private javax.swing.JTextField tf_dpt;
    private javax.swing.JTextField tf_maxCount;
    private javax.swing.JTextField tf_professor;
    // End of variables declaration                   

}
