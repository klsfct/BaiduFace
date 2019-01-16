package wechat;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.DBConnection;
import com.alibaba.fastjson.JSONObject;

import aliyun.SendMess;
import wechat_class.Code_Phone;

/**
 * Servlet implementation class BindPhone
 */
@WebServlet("/SendCode")
public class SendCode extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SendCode() {
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
		String account = request.getParameter("account");
		String phone = request.getParameter("phone");
		// String sql = "UPDATE manager SET phoneNumber = ? WHERE userName LIKE ?";
		try {
			if (account == null) {
				String code = SendMess.send(phone);
				response.getWriter().print(code); // 发送验证码到小程序中
			} else {
                String sql = "SELECT phoneNumber FROM manager WHERE userName LIKE ?";
                Connection  con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1 , account);
                ResultSet rs = ps.executeQuery();
                String phoneNumber;
                if (rs.next()) {
                	phoneNumber = rs.getString("phoneNumber");
    				String code = SendMess.send(phoneNumber);
    				Code_Phone mess = new Code_Phone(phoneNumber , code);
    				response.getWriter().print(JSONObject.toJSON(mess)); 
                } else {
                	response.getWriter().print("null");
                }
    			DBConnection.free(con , ps , rs);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
