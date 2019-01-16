package check;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBConnection;

/**
 * Servlet implementation class MotifyMessage
 */
@WebServlet("/MotifyMessage")
public class MotifyMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MotifyMessage() {
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
	    String name = request.getParameter("name");
	    String course = request.getParameter("course");
	    String flag1 = request.getParameter("flag1");  
	    String flag2 = request.getParameter("flag2");
	    if(flag1.equals("0")) {
	    	flag1 = "È±ÇÚ";
	    } else if(flag1.equals("1")) {
	    	flag1 = "³öÇÚ";
	    } else {
	    	flag1 = "³Ùµ½";
	    }
	    if(flag2.equals("0")) {
	    	flag2 = "È±ÇÚ";
	    } else if(flag2.equals("1")) {
	    	flag2 = "³öÇÚ";
	    } else {
	    	flag2 = "ÔçÍË";
	    }
	    String sql = "UPDATE checked SET flag1=?,flag2=? WHERE SName LIKE ? AND courseid IN (SELECT courseid FROM timetable WHERE coursename LIKE ?)";
	    try {
	    	System.out.println(request.getParameter("time"));
	    	ps = con.prepareStatement(sql);
	    	ps.setString(1 , flag1);
	    	ps.setString(2 , flag2);
	    	ps.setString(3 , name);
	    	ps.setString(4 , course);
	    	ps.executeUpdate();
	    	request.getSession().setAttribute("message" , "Edit successfully!");
	    	request.getRequestDispatcher("/EditMessage.jsp").forward(request , response);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    } finally {
	    	DBConnection.free(con , ps , null);
	    }
	}

}
