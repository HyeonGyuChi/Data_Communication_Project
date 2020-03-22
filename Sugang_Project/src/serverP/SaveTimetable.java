package serverP;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class SaveTimetable {
	private static final String DATABASE_FILE = "timeTable.txt";
	
	public boolean saveMemberInfo(Course[][] userTimetable) {
		PrintWriter out = null;
		
		
		try {
			out = new PrintWriter(new FileWriter(new File(DATABASE_FILE)));
		}catch(IOException e) {
			e.printStackTrace();
			System.out.println("[ERROR] ���Ͼ��� �� �� ������ �߻��߽��ϴ�.");
			return false;
		}
		
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < userTimetable[i].length; j++ ) {
				
				out.println(userTimetable[i][j].getCourseName());
			}
			out.flush();
		}
		
		out.close();
		
		return true;
	}
}
