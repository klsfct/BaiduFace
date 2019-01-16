package check;

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
import javax.servlet.http.HttpSession;

import com.DBConnection;

import wechat_class.AttendMessage;

/**
 * Servlet implementation class BrowseAM
 */
@WebServlet("/BrowseAM")
public class BrowseAM extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseAM() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		String classid = request.getParameter("classid");
		String classname = null;
		String coursename = null;
		String name = null;
		String studentid = null;
		String time1 = null;
		String time2 = null;
		String flag1 = null;
		String flag2 = null;
		String sql = "SELECT SName,SID,coursename,CName,checktime1,checktime2,flag1,flag2 FROM checked,timetable,class WHERE checked.courseid = timetable.courseid AND checked.CID = ? AND class.CID = ?";
		HttpSession session = request.getSession();
		ArrayList<AttendenceMessage> list = new ArrayList<AttendenceMessage>();
		try {
			ps = con.prepareStatement(sql);
            ps.setString(1 , classid);
            ps.setString(2 , classid);
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
			session.setAttribute("Alist" , list);
			request.getRequestDispatcher("/CheckClass.jsp").forward(request , response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , rs);
		}
	}

}
