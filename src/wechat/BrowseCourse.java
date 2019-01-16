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

import wechat_class.Course;

/**
 * Servlet implementation class BrowseCourse
 */
@WebServlet("/BrowseCourse")
public class BrowseCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request , response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String classid = request.getParameter("classid");
		//int week = Integer.parseInt(request.getParameter("week"));
		String courseid = null;
		String coursename = null;
		String startTime = null;
		String endTime = null;
		int weekday = 0;
		String sql = "SELECT courseid,coursename,register_end time1,signback_start time2,weekday FROM timetable WHERE CID = ? ORDER BY WEEKDAY ASC,time1+0 ASC";
		ArrayList<Course> list = new ArrayList<Course>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1 , classid);
			rs = ps.executeQuery();
			while(rs.next()) {
				courseid = rs.getString("courseid");
				coursename = rs.getString("coursename");
				startTime = rs.getString("time1");
				endTime = rs.getString("time2");
				weekday = rs.getInt("weekday");
				list.add(new Course(courseid , coursename , startTime , endTime , weekday));
			}
			String mess = JSONObject.toJSONString(list);
			response.getWriter().println(mess);
			//System.out.println(mess);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , rs);
		}
	}

}
