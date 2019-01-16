package wechat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DBConnection;
import com.alibaba.fastjson.JSONObject;

import wechat_class.Course;
import wechat_class.Time_Mess;

/**
 * Servlet implementation class GetNextTime
 */
@WebServlet("/GetNextTime")
public class GetNextTime extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetNextTime() {
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
		String timeMess = request.getParameter("TimeMess"); //获取时间信息
		//System.out.println(timeMess);
		String[] nowTime = timeMess.split(" ");
		SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String sql = "SELECT courseid,coursename,register_start time1,signback_start time2 FROM timetable WHERE WEEKDAY = ? AND CID = ? ORDER BY time1+0 ASC";
		ArrayList<Course> list = new ArrayList<Course>();
		String courseid = null;
		String coursename = null;
		String startTime1 = null;
		String startTime2 = null;
		String mess = null;
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		//HttpSession session = request.getSession();
		//session.setMaxInactiveInterval(86400);
		//System.out.println(nowTime[0] + " " + nowTime[1].substring(0 , 5));
		try {
			Date date = sf.parse(nowTime[0] + " " + nowTime[1].substring(0 , 5));
			Date date2;
			Date start = sf.parse(nowTime[0] + " " + "13:00");  //区分上下午
			Date start2 = sf.parse(nowTime[0] + " " + "19:00");  //区分下午和晚上
		    Calendar calendar = Calendar.getInstance();
		    calendar.setTime(date);
			int week =calendar.get(Calendar.DAY_OF_WEEK) - 1;
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			con  = DBConnection.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1 , week);
			ps.setString(2 , classid);
			rs = ps.executeQuery();
			while(rs.next()) {
				courseid = rs.getString("courseid");
				coursename = rs.getString("coursename");
				startTime1 = rs.getString("time1");
				startTime2 = rs.getString("time2");
				if( hour <= 13 && (sf.parse(nowTime[0] + " " + startTime1).getTime() <= start.getTime())) {
				      list.add(new Course(courseid , coursename , startTime1 , startTime2 , week)); 
				} else if (hour <19 && hour > 13 && (sf.parse(nowTime[0] + " " + startTime1).getTime() > start.getTime())){
					  list.add(new Course(courseid , coursename , startTime1 , startTime2 , week)); 
				} else if(hour >= 19 && (sf.parse(nowTime[0] + " " + startTime1).getTime() > start2.getTime())) {
					  list.add(new Course(courseid , coursename , startTime1 , startTime2 , week)); 
				}
			}
			//session.setAttribute("time" , list);
			//session.setAttribute("nowDay" , nowTime[0]);
			if(!list.isEmpty() && (sf.parse(nowTime[0] + " " + list.get(0).getStartTime()).getTime() > date.getTime())) {
				mess = list.get(0).getStartTime() + "  即将签到";
			} else if(!list.isEmpty() && (sf.parse(nowTime[0] + " " + list.get(list.size()-1).getEndTime()).getTime() > date.getTime())) {
				mess = list.get(list.size()-1).getEndTime() + "  即将签退";
			} else {
			    mess = "目前无需考勤";
			}
			Time_Mess timeMess1 = null;
			if(!list.isEmpty())
			     timeMess1 = new Time_Mess(nowTime[0] , list.get(0).getStartTime() , list.get(list.size()-1).getEndTime() , mess);
			else
				timeMess1 = new Time_Mess("目前无需考勤" , "目前无需考勤" , "目前无需考勤" , mess);
			String timeMess2 = JSONObject.toJSONString(timeMess1);
			response.getWriter().println(timeMess2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , rs);
		}
	}

}
