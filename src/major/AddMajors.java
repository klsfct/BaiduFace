package major;

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
 * Servlet implementation class AddMajors
 */
@WebServlet("/AddMajors")
public class AddMajors extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddMajors() {
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
		String name = request.getParameter("name");
		String DID = String.valueOf(request.getParameter("DepartmentChosen"));
		String MID = null;
		String sql = "SELECT MID id FROM major ORDER BY MID+0 DESC";
		String sql2 = "insert into major values (?,?,?)";
		String sql3 = "select MName from major where MName like '" + name + "'";
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();

		if (name.equals("")) {
			session.setAttribute("message", "Can not be empty!");
			request.getRequestDispatcher("/AddMajors.jsp").forward(request, response);
			return;
		}

		try {
			ps = con.prepareStatement(sql3);
			rs = ps.executeQuery();
			while (rs.next()) {
				session.setAttribute("message", "The major has already existed!");
				request.getRequestDispatcher("/AddMajors.jsp").forward(request, response);
				DBConnection.free(con, ps, rs);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				if (rs.getString("id") == null)
					MID = "1";
				else {
					MID = String.valueOf(Integer.parseInt(rs.getString("id")) + 1);
				}
			}
			ps = con.prepareStatement(sql2);
			ps.setString(1 , name);
			ps.setString(2 , MID);
			ps.setString(3 , DID);
			ps.executeUpdate();
			session.setAttribute("message", "Added successfully!");
			request.getRequestDispatcher("/AddDepartments.jsp").forward(request, response);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}

	}

}
