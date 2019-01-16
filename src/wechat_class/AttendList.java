package wechat_class;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.DBConnection;

public class AttendList {

	private static ArrayList<AttendMessage> list;

	public static void creatList(String classid,String time) {
		list = new ArrayList<AttendMessage>();
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String name = null;
		String id = null;
		String sql = "select * from Student where CID like " + classid;
		try {
			ps = con.prepareStatement(sql);
			rs= ps.executeQuery();
			while(rs.next()) {
				name = rs.getString("SName");
				id = rs.getString("SID");
				list.add(new AttendMessage(name , id , classid , time , "缺勤"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , rs);
		}
	}

	public static void destroy() {
		Connection con = DBConnection.getConnection();
	    PreparedStatement ps = null;
	    String name = null;
	    String time = null;
	    String classid = null;
	    String flag = null;
	    String studentid = null;
	    String sql ="insert into Checked values (?,?,?,?,?)";
	    try {
			ps = con.prepareStatement(sql);
			for(int i = 0;i < list.size();i++) {
				name = list.get(i).getName();
				studentid = list.get(i).getStudentid();
				time = list.get(i).getTime();
				classid = list.get(i).getClassid();
				flag = list.get(i).getFlag();
				if(flag.equals("出席")) {
					flag = "1";
				} else {
					flag = "0";
				}
				ps.setString(1 , name);
				ps.setString(2 , studentid);
				ps.setString(3 , classid);
				ps.setString(4 , time);
				ps.setString(5 , flag);
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , null);
			list = null;
		}
	}

	public static ArrayList<AttendMessage> getList() {
		return list;
	}
	
	public static void check(String id) {
		for(int i = 0;i < list.size();i++) {
			if(list.get(i).getStudentid().equals(id)) {
				list.get(i).setFlag("出席");
			}
		}
	}

}
