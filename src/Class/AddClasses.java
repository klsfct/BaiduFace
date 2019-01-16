package Class;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DBConnection;

/**
 * Servlet implementation class AddClasses
 */
@WebServlet("/AddClasses")
public class AddClasses extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddClasses() {
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
	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        Connection con = DBConnection.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        HttpSession session = request.getSession();
        String sql = "SELECT CID id FROM class ORDER BY CID+0 DESC";
        String sql2 = "select major.DID as id from major , department where major.DID=department.DID AND major.MID like ?";
        String sql3 = "insert into class values (?,?,?,?)";
        String sql4 = "select MID as id from class where CName like ?";
        String name = request.getParameter("name");
        String MID = request.getParameter("MajorChosen");
        String CID = null;
        String DID = null;
        try {
    		if (name.equals("")) {
    			session.setAttribute("message", "Can not be empty!");
    			request.getRequestDispatcher("/AddClasses.jsp").forward(request, response);
    			return;
    		}
        	ps = con.prepareStatement(sql4);
        	ps.setString(1 , name);
        	rs = ps.executeQuery();
        	if(rs.next()) {
        		session.setAttribute("message" , "The class has already existed!");
        		request.getRequestDispatcher("/AddClasses.jsp").forward(request , response);
        		return;
        	}
        	ps = con.prepareStatement(sql);
        	rs = ps.executeQuery();
        	if(rs.next()) {
        		if(rs.getString("id") != null)
        		    CID = String.valueOf((Integer.parseInt(rs.getString("id"))+1));
        		else
        			CID = "1";
        	} 
        	else
        		CID = "1";
        	ps = con.prepareStatement(sql2);
        	ps.setString(1 , MID);
        	rs = ps.executeQuery();
        	while(rs.next()) {
        		DID = rs.getString("id");
        	}
        	ps = con.prepareStatement(sql3);
            ps.setString(1 , name);
            ps.setString(2 , CID);
            ps.setString(3 , MID);
            ps.setString(4 , DID);
            ps.executeUpdate();
    		session.setAttribute("message" , "Added successfully!");
    		request.getRequestDispatcher("/AddClasses.jsp").forward(request , response);
            
        } catch(Exception e) {
        	e.printStackTrace();
        } finally {
        	DBConnection.free(con , ps , rs);
        }
	}

}
