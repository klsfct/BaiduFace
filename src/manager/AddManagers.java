package manager;

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
 * Servlet implementation class AddManagers
 */
@WebServlet("/AddManagers")
public class AddManagers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddManagers() {
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
		String sql = "insert into manager values (?,?,?,?)";
		String sql2 = "select userName from manager where userName like ?";
		String name = null;
		String phone = null;
		String password = null;
		String type = null;
		HttpSession session = request.getSession();
		try {
			name = request.getParameter("name");
			if(name.equals("")) {
				session.setAttribute("message", "Can not be empty!");
				request.getRequestDispatcher("/AddManagers.jsp").forward(request , response);	
				return;
			}
			ps = con.prepareStatement(sql2);
			ps.setString(1 , name);
			rs = ps.executeQuery();
			while(rs.next()) {
				session.setAttribute("message", "The user has already existed!");
				request.getRequestDispatcher("/AddManagers.jsp").forward(request , response);	
				return;				
			}
			phone = request.getParameter("phone");
			password = request.getParameter("password");
			type = request.getParameter("type");
			ps = con.prepareStatement(sql);
			ps.setString(1 , name);
			ps.setString(2 , password);
			ps.setString(3 , phone);
			ps.setString(4 , type);
			ps.executeUpdate();
			session.setAttribute("message", "Added Successfully!");
			request.getRequestDispatcher("/AddManagers.jsp").forward(request , response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , null);
		}
	}

}
