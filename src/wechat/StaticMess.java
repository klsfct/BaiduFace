package wechat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBConnection;
import com.alibaba.fastjson.JSONObject;

import wechat_class.Static;
import wechat_class.StudentAttend;

/**
 * Servlet implementation class StaticMess
 */
@WebServlet("/StaticMess")
public class StaticMess extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StaticMess() {
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
		String name = null;
		String flag1 = null; // 签到状态
		String flag2 = null; // 签退状态
		int timeFlag = 0;
		String classid = request.getParameter("classid");
		String sql = null;
		ArrayList<Static> list = new ArrayList<Static>();
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			if (flag.equals("true")) {
				// 没有签到的统一列为缺勤
				for (int i = 1; i <= 21; i++) {
					sql = "SELECT a.SName,a.flag flag1,b.flag flag2,a.timeFlag timeFlag\r\n" + 
							"FROM \r\n" + 
							"     (SELECT student.`SID` id,SName,flag,timeFlag\r\n" + 
							"      FROM student LEFT OUTER JOIN tableone ON tableone.`SID` LIKE student.`SID` AND timeFlag = ? AND checktime BETWEEN ? AND ?\r\n" + 
							"      WHERE student.CID LIKE ?) a LEFT OUTER JOIN\r\n" + 
							"      (SELECT student.`SID` id,SName,flag,timeFlag\r\n" + 
							"       FROM student LEFT OUTER JOIN tabletwo ON tabletwo.`SID` LIKE student.`SID` AND timeFlag = ? AND checktime BETWEEN ? AND ?\r\n" + 
							"       WHERE student.CID LIKE ?) b ON a.timeFlag = b.timeFlag AND a.id = b.id";
					ps = con.prepareStatement(sql);
					ps.setInt(1, i);
					ps.setString(2 , time1);
					ps.setString(3 , time2);
					ps.setString(4 , classid);
					ps.setInt(5 , i);
					ps.setString(6 , time1);
					ps.setString(7 , time2);
					ps.setString(8 , classid);
					rs = ps.executeQuery();
					if (rs.next()) {
						if(rs.getString("timeFlag") == null) {
							continue;
						}
						rs.previous();   //回滚到第一条记录
						Static st = new Static(i);
						st.setFlag(true);
						while (rs.next()) {
							flag1 = rs.getString("flag1");
							flag2 = rs.getString("flag2");
							if (flag1 == null | flag2 == null) {
								st.setNum4();
							} else if (flag1.equals("正常") && flag2.equals("正常")) {
								st.setNum1();
							} else if (flag1.equals("迟到") && flag2.equals("正常")) {
								st.setNum2();
							} else if (flag1.equals("正常") && flag2.equals("早退")) {
								st.setNum3();
							} else {
								st.setNum2();
								st.setNum3();
							}
						}
						list.add(st);
					}
				}
			} else {
				ArrayList<StudentAttend> list2 = new ArrayList<StudentAttend>();
				sql = "SELECT SName,flag1,flag2,timeflag \r\n" + 
						"       FROM tablethree a,student c \r\n" + 
						"       WHERE a.SID LIKE c.SID AND a.checktime BETWEEN ? AND ? AND c.CID LIKE ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, time1);
				ps.setString(2, time2);
				ps.setString(3, classid);
				rs = ps.executeQuery();
				while (rs.next()) {
					name = rs.getString("SName");
					flag1 = rs.getString("flag1");
					flag2 = rs.getString("flag2");
					timeFlag = rs.getInt("timeFlag");
					list2.add(new StudentAttend(name, flag1, flag2, timeFlag));
				}
				for (int i = 0; i < 21; i++) {
					list.add(new Static(i + 1));
				}
				for (int i = 0; i < list2.size(); i++) {
					list.get(list2.get(i).getTimeFlag() - 1).setFlag(true);;
					if (list2.get(i).getFlag1().equals("缺卡") | list2.get(i).getFlag2().equals("缺卡")) {
						list.get(list2.get(i).getTimeFlag() - 1).setNum4();
					} else if (list2.get(i).getFlag1().equals("正常") && list2.get(i).getFlag2().equals("正常")) {
						list.get(list2.get(i).getTimeFlag() - 1).setNum1();
					} else if (list2.get(i).getFlag1().equals("迟到") && list2.get(i).getFlag2().equals("正常")) {
						list.get(list2.get(i).getTimeFlag() - 1).setNum2();
					} else if (list2.get(i).getFlag1().equals("正常") && list2.get(i).getFlag2().equals("早退")) {
						list.get(list2.get(i).getTimeFlag() - 1).setNum3();
					} else {
						list.get(list2.get(i).getTimeFlag() - 1).setNum3();
						list.get(list2.get(i).getTimeFlag() - 1).setNum2();
					}
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
