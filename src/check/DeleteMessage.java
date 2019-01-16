package check;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBConnection;

/**
 * Servlet implementation class DeleteMessage
 */
@WebServlet("/DeleteMessage")
public class DeleteMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteMessage() {
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
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		String name = request.getParameter("name");
		String course = request.getParameter("coursename");
		String sql = "delete from Checked where SName like ? and courseid in (select courseid from timetable where coursename like ?)";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1 , name);
			ps.setString(2 , course);
			ps.executeUpdate();
			request.getRequestDispatcher("/Checked.jsp").forward(request , response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con , ps , null);
		}
	}

}
