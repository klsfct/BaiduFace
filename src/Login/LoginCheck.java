package Login;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.DBConnection;

/**
 * Servlet implementation class LoginCheck
 */
@WebServlet("/LoginCheck")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginCheck() {
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
		// 读取前端数据
		ServletRequest req = (ServletRequest) request;
		ServletResponse res = (ServletResponse) response;
		String name = request.getParameter("form-username");
		String password = request.getParameter("form-password");
		HttpSession session = request.getSession();
		// 输入为空时，返回前端
		if (name == null || name.equals("") || password.equals("")) {
			RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
			rd.forward(req , res);
			return;
		}
		String sql = "select * from manager where userName like " + "'" + name + "'";
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				if (password.equals(rs.getString("password"))) {
					if(rs.getInt("flag")!=0) {
						session.setAttribute("message", "班长无权登陆！");
						DBConnection.free(con, preparedStatement, rs);
						RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
						rd.forward(req , res);
						return;						
					}
					session.setAttribute("username", name);
					RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
					rd.forward(req , res);
					return;
				} else {
					session.setAttribute("message", "密码错误！");
					DBConnection.free(con, preparedStatement, rs);
					RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
					rd.forward(req , res);
					return;
				}
			} else {
				session.setAttribute("message", "输入账号有误！");
				DBConnection.free(con, preparedStatement, rs);
				RequestDispatcher rd = request.getRequestDispatcher("/Login.jsp");
				rd.forward(req , res);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
