package wechat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBConnection;

/**
 * Servlet implementation class FaceIdentify
 */
@WebServlet("/AddCourse")
public class AddCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCourse() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request , response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String classid = request.getParameter("classid");//班级ID
		String coursename = request.getParameter("coursename");//课程名称
		String startTime = request.getParameter("startTime");//课程开始时间
		String endTime = request.getParameter("endTime");//课程结束时间
		int week = Integer.parseInt(request.getParameter("week"));  //周几
		if(week == 7) {
			week = 0;
		}
		String time1 = null;
		String time2 = null;
		SimpleDateFormat sf = new SimpleDateFormat("HH:mm");
		Calendar calendar = Calendar.getInstance();
		String sql = "INSERT timetable VALUES(?,?,?,?,?,?,?,?)";
		String sql2 = "SELECT courseid id FROM timetable ORDER BY courseid+0 DESC";
		String courseid = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
        try {
        	con = DBConnection.getConnection();
        	ps = con.prepareStatement(sql2);
        	rs = ps.executeQuery();
        	if(rs.next()) {
        		int id = Integer.parseInt(rs.getString("id")) + 1;
        		courseid = String.valueOf(id);
        	} else {
        		courseid = String.valueOf("1");
        	}
        	ps = con.prepareStatement(sql);
			Date date1 = sf.parse(startTime);
	        Date date2 = sf.parse(endTime);
	        calendar.setTime(date1);
	        calendar.add(Calendar.MINUTE , -10);
	        String hour = null;
	        String minute = null;
	        if(calendar.get(Calendar.HOUR_OF_DAY) < 10) {
	        	hour = "0" + calendar.get(Calendar.HOUR_OF_DAY);
	        } else {
	        	hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	        }
	        if(calendar.get(Calendar.MINUTE) < 10) {
	        	minute = "0" + calendar.get(Calendar.MINUTE);
	        } else {
	        	minute = String.valueOf(calendar.get(Calendar.MINUTE));
	        }
	        time1 = hour + ":" + minute;
	        calendar.setTime(date2);
	        calendar.add(Calendar.MINUTE , 10);
	        if(calendar.get(Calendar.HOUR_OF_DAY) < 10) {
	        	hour = "0" + calendar.get(Calendar.HOUR_OF_DAY);
	        } else {
	        	hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
	        }
	        if(calendar.get(Calendar.MINUTE) < 10) {
	        	minute = "0" + calendar.get(Calendar.MINUTE);
	        } else {
	        	minute = String.valueOf(calendar.get(Calendar.MINUTE));
	        }
	        time2 = hour + ":" + minute;
	        ps.setString(1 , courseid);
	        ps.setString(2 , coursename);
	        ps.setString(3 , time1);
	        ps.setString(4 , startTime);
	        ps.setString(5 , endTime);
	        ps.setString(6 , time2);
	        ps.setInt(7 , week);
	        ps.setString(8 , classid);
	        ps.executeUpdate();
	        response.getWriter().println(courseid);
	        System.out.println("Successfully!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , rs);
		}

    }

}
