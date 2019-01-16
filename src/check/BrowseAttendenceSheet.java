package check;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.DBConnection;

/**
 * Servlet Filter implementation class BrowseAttendenceSheet
 */
@WebFilter(dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, urlPatterns = { "/Checked.jsp" })
public class BrowseAttendenceSheet implements Filter {

    /**
     * Default constructor. 
     */
    public BrowseAttendenceSheet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		String sql = "SELECT SName,SID,coursename,CName,checktime1,checktime2,flag1,flag2 FROM checked,timetable,class WHERE checked.courseid = timetable.courseid AND checked.CID = class.CID";;
		String classname = null;
		String coursename = null;
		String name = null;
		String studentid = null;
		String time1 = null;
		String time2 = null;
		String flag1 = null;
		String flag2 = null;
		ArrayList<AttendenceMessage> list = new ArrayList<AttendenceMessage>();
		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			while(rs.next()) {
				name = rs.getString("SName");
				studentid = rs.getString("SID");
				classname = rs.getString("CName");
				coursename = rs.getString("coursename");
				time1 = rs.getString("checktime1");
				time2 = rs.getString("checktime2");
				flag1 = rs.getString("flag1");
				flag2 = rs.getString("flag2");
				if(time1 == null) {
					time1 = String.valueOf('нч');
				}
				if(time2 == null) {
					time2 = String.valueOf('нч');
				}				
				list.add(new AttendenceMessage(name , studentid , classname , coursename , time1 , time2 , flag1 , flag2));
			}
			session.setAttribute("alist" , list);
			chain.doFilter(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , rs);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
