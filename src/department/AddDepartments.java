package department;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DBConnection;

/**
 * Servlet implementation class AddDepartments
 */
@WebServlet("/AddDepartments")
public class AddDepartments extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddDepartments() {
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
	@SuppressWarnings("null")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection con = DBConnection.getConnection();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		HttpSession session = request.getSession();
		String sql = "SELECT DID id FROM department ORDER BY DID+0 DESC";
		String name = request.getParameter("name");
		String sql3 = "select * from department where DName like " + "'" + name + "'";

		if (name.equals("")) {
			session.setAttribute("message", "Can not be empty!");
			request.getRequestDispatcher("/AddDepartments.jsp").forward(request, response);
			return;
		}

		try {
			preparedStatement = con.prepareStatement(sql3);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				session.setAttribute("message", "The department has already existed!");
				request.getRequestDispatcher("/AddDepartments.jsp").forward(request, response);
				DBConnection.free(con , preparedStatement , rs);
			    return;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			preparedStatement = con.prepareStatement(sql);
			rs = preparedStatement.executeQuery();
			long num = 0;
			if (rs.next()) {
				num = Integer.valueOf(rs.getInt("id"));
			} else {
				num = 1;
			}
			String sql2 = "insert into department values(?,?)";
			preparedStatement = con.prepareStatement(sql2);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, String.valueOf(num + 1));
			preparedStatement.executeUpdate();
			session.setAttribute("message", "Added successfully!");
			request.getRequestDispatcher("/AddDepartments.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, preparedStatement, rs);
		}
	}

}
