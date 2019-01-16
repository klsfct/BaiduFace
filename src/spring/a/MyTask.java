package spring.a;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.DBConnection;
import com.mysql.jdbc.PreparedStatement;

import wechat_class.StudentAttend;

public class MyTask {
	@SuppressWarnings("resource")
	public void run() {
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = "SELECT a.id,a.flag flag1,b.flag flag2,a.timeFlag timeFlag,a.checktime\r\n" + 
				"FROM \r\n" + 
				"     (SELECT student.`SID` id,SName,flag,timeFlag,checktime\r\n" + 
				"      FROM student LEFT OUTER JOIN tableone ON tableone.`SID` LIKE student.`SID` AND timeFlag = ?\r\n" + 
				"      WHERE student.CID LIKE ?) a LEFT OUTER JOIN\r\n" + 
				"      (SELECT student.`SID` id,SName,flag,timeFlag\r\n" + 
				"       FROM student LEFT OUTER JOIN tabletwo ON tabletwo.`SID` LIKE student.`SID` AND timeFlag = ?\r\n" + 
				"       WHERE student.CID LIKE ?) b ON a.timeFlag = b.timeFlag AND a.id = b.id";
		String sql2 = "insert tablethree values (?,?,?,?,?,?)";
		String sql3 = "SELECT CID id FROM class ORDER BY CID+0 DESC";  //获取班级数量
		String sql4 = "delete from tableone";
		String sql5 = "delete from tabletwo";
		int num = 0;
		String id = null;
		String flag1 = null;
		String flag2 = null;
		int timeFlag = 0;
		String checktime = null;
		ArrayList<StudentAttend> list = new ArrayList<StudentAttend>();
		try {
			ps = (PreparedStatement) con.prepareStatement(sql3);
			rs = ps.executeQuery();
			if(rs.next()) {
				num = Integer.parseInt(rs.getString("id"));  //获取班级数量
			} else {
				return;
			}
			ps = (PreparedStatement) con.prepareStatement(sql);
			for(int i = 1;i <= num;i++) {
				 for(int j = 1;j <= 21;j++) {
					  ps.setInt(1 , j);
					  ps.setString(2 , String.valueOf(i));
					  ps.setInt(3 , j);
					  ps.setString(4 , String.valueOf(i));
					  rs = ps.executeQuery();
					  while(rs.next()) {
						   id = rs.getString("id");
						   flag1 = rs.getString("flag1");
						   flag2 = rs.getString("flag2");
						   if(flag1 == null) {
							   flag1 = "缺卡";
						   }
						   if(flag2 == null) {
							   flag1 = "缺卡";
						   }
						   timeFlag = j;
						   checktime = rs.getString("checktime");
						   if(checktime == null) {
							   break;
						   }
						   list.add(new StudentAttend(id , flag1 , flag2 , j , checktime , String.valueOf(i)));//读取近一周的考勤信息并整合到一起
					  }
				 }
			}
			ps = (PreparedStatement) con.prepareStatement(sql2);
			for(int i = 0;i < list.size();i++) {
				ps.setString(1 , list.get(i).getName());
				ps.setString(2 , list.get(i).getCheckTime());
				ps.setInt(3 , list.get(i).getTimeFlag());
				ps.setString(4 , list.get(i).getFlag1());
				ps.setString(5 , list.get(i).getFlag2());
				ps.setString(6 , list.get(i).getClassid());
				ps.executeUpdate();
			}
			ps = (PreparedStatement) con.prepareStatement(sql4);
			ps.executeUpdate();
			ps = (PreparedStatement) con.prepareStatement(sql5);
			ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}
	}
}