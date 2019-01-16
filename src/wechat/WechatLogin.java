package wechat;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.alibaba.fastjson.JSON;

import wechat_class.LoginMessage;

/**
 * Servlet implementation class wechatLogin
 */
@WebServlet("/WechatLogin")
public class WechatLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WechatLogin() {
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
		/*������Ӧͷ����ajax�������*/
		response.setHeader("Access-Control-Allow-Origin", "*");
		/*�������е��������*/
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		/*��ȡ΢��С�����get��������ӡ*/
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		PrintWriter out = response.getWriter();
		String name = request.getParameter("account");
		String password = request.getParameter("password");
		String classid = null;
		String classname = null;
		String department = null;
		String error = null;
		String phone = null;
		boolean flag = false;
		LoginMessage mess;
		String sql = "select * from manager where userName like " + "'" + name + "'";
		try {
			Connection con = DBConnection.getConnection();
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			ResultSet rs = preparedStatement.executeQuery();
			HttpSession session = req.getSession();
			if (rs.next()) {
				if (password.equals(rs.getString("password"))) {
					name = rs.getString("userName");
					classid = rs.getString("flag");
					phone = rs.getString("phoneNumber");
					String sql2 = "select  CName , DName from class a , department b  where a.CID like ? and a.DID = b.DID ";
					flag = true;
					preparedStatement = con.prepareStatement(sql2);
					preparedStatement.setString(1 , classid);
					rs = preparedStatement.executeQuery();
					while(rs.next()) {
						classname = rs.getString("CName");
						department = rs.getString("DName");
					}
					DBConnection.free(con, preparedStatement , rs);
					mess = new LoginMessage(name , classname , department , classid , flag , error , phone);
					String message = JSON.toJSONString(mess);
					out.print(message);
					return;
				} else {
					DBConnection.free(con, preparedStatement , rs);
					error = "�������";
					mess = new LoginMessage(name , classname , department , classid , flag , error , phone);
					String message = JSON.toJSONString(mess);
					out.print(message);
					return;
				}
			}  else {
				DBConnection.free(con, preparedStatement , rs);
				error = "�˺Ų�����";
				mess = new LoginMessage(name , classname , department , classid , flag , error , phone);
				String message = JSON.toJSONString(mess);
				out.print(message);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
