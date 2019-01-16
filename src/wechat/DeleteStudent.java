package wechat;

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

import baiduTools.HttpUtil;
import wechat_class.Access_token;

/**
 * Servlet implementation class DeleteStudent
 */
@WebServlet("/DeleteStudent")
public class DeleteStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteStudent() {
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
		String studentid = request.getParameter("studentid");
		String classid = request.getParameter("classid");
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		String sql = "delete from student where SID like ?";
		String sql2 = "delete from tableone where SID like ?";
		String sql3 = "delete from tabletwo where SID like ?";
		String sql4 = "delete from tablethree where SID like ?";
		try {
			ps = con.prepareStatement(sql);
			ps.setString(1, studentid);
			ps.executeUpdate();
			ps = con.prepareStatement(sql2);
			ps.setString(1, studentid);
			ps.executeUpdate();
			ps = con.prepareStatement(sql3);
			ps.setString(1, studentid);
			ps.executeUpdate();
			ps = con.prepareStatement(sql4);
			ps.setString(1, studentid);
			ps.executeUpdate();
			String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";
			String param = "uid=" + studentid + "&group_id=" + classid;

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
			String accessToken = Access_token.getAuth();

			String result = HttpUtil.post(url, accessToken, param);
			System.out.println(result);
			response.getWriter().println("删除成功！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, null);
		}
	}

}
