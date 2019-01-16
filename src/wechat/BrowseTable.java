package wechat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBConnection;
import com.alibaba.fastjson.JSONObject;

import wechat_class.AttendList;
import wechat_class.AttendMessage;
import wechat_class.Static;
import wechat_class.StudentAttend;

/**
 * Servlet implementation class S_E_Sign
 */
@WebServlet("/BrowseTable")
public class BrowseTable extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BrowseTable() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String time1 = request.getParameter("startTime") + " 00:00";
		String time2 = request.getParameter("endTime") + " 23:59";
		String flag = request.getParameter("flag");
		//String flag = "true";
		int timeFlag = Integer.parseInt(request.getParameter("timeFlag"));
		//int timeFlag = 1; 
		String classid = request.getParameter("classid");
		//String classid = "2";
		String name = null;
		String flag1 = null; // Ç©µ½×´Ì¬
		String flag2 = null; // Ç©ÍË×´Ì¬
		String sql = null;
		ArrayList<StudentAttend> list = new ArrayList<StudentAttend>();
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (flag.equals("true")) {
				sql = "SELECT a.SName,a.flag flag1,b.flag flag2,a.timeFlag timeFlag\r\n" + 
						"FROM \r\n" + 
						"     (SELECT student.`SID` id,SName,flag,timeFlag\r\n" + 
						"      FROM student LEFT OUTER JOIN tableone ON tableone.`SID` LIKE student.`SID` AND timeFlag = ? AND checktime BETWEEN ? AND ?\r\n" + 
						"      WHERE student.CID LIKE ?) a LEFT OUTER JOIN\r\n" + 
						"      (SELECT student.`SID` id,SName,flag,timeFlag\r\n" + 
						"       FROM student LEFT OUTER JOIN tabletwo ON tabletwo.`SID` LIKE student.`SID` AND timeFlag = ? AND checktime BETWEEN ? AND ?\r\n" + 
						"       WHERE student.CID LIKE ?) b ON a.timeFlag = b.timeFlag AND a.id = b.id";
				ps = con.prepareStatement(sql);
				ps.setInt(1, timeFlag);
				ps.setString(2 , time1);
				ps.setString(3 , time2);
				ps.setString(4 , classid);
				ps.setInt(5 , timeFlag);
				ps.setString(6 , time1);
				ps.setString(7 , time2);
				ps.setString(8 , classid);
				rs = ps.executeQuery();
				while (rs.next()) {
					name = rs.getString("SName");
					flag1 = rs.getString("flag1");
					flag2 = rs.getString("flag2");
					if (flag1 == null) {
						flag1 = "È±¿¨";
					}
					if (flag2 == null) {
						flag2 = "È±¿¨";
					}
					list.add(new StudentAttend(name, flag1, flag2, timeFlag));
				}
			} else {

				sql = "       SELECT SName,flag1,flag2,timeflag \r\n" + 
						"       FROM tablethree a,student c \r\n" + 
						"       WHERE a.SID LIKE c.SID AND a.checktime BETWEEN ? AND ? AND c.CID LIKE ? and timeFlag = ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, time1);
				ps.setString(2, time2);
				ps.setString(3, classid);
				ps.setInt(4 , timeFlag);
				rs = ps.executeQuery();
				while (rs.next()) {
					name = rs.getString("SName");
					flag1 = rs.getString("flag1");
					flag2 = rs.getString("flag2");
					list.add(new StudentAttend(name, flag1, flag2, timeFlag));
				}

			}
			String mess = JSONObject.toJSONString(list);
			response.getWriter().println(mess);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}
	}

}
