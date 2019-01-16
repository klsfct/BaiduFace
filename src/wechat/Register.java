package wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.DBConnection;

import baiduTools.Base64Util;
import baiduTools.FileUtil;
import baiduTools.HttpUtil;
import wechat_class.Access_token;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Register() {
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "*");
		/*允许所有的异域访问*/
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		/*获取微信小程序的get参数并打印*/	
		System.out.println("文件上传");
		System.out.println(request.getSession().getServletContext().getRealPath("") + "face\\");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		fileUpload.setHeaderEncoding("UTF-8");
		List<FileItem> list;
		try {
			list = fileUpload.parseRequest(request);
			System.out.println(list);			
			Map<String, FileItem> param = new HashMap<String, FileItem>();
			ServletContext context = request.getServletContext();
			Connection con = DBConnection.getConnection();
			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "insert into student values (?,?,?,?,?)";
			String sql2 = "select class.MID cid,major.DID dId from class , major where class.CID like ? and class.MID = major.MID";
			String sql3 = "SELECT SID id FROM student ORDER BY SID+0 DESC";
			String studentName = null;  //学生姓名
			String studentId = null;    //学生ID
			String classid = null;      //班级ID
			String majorid = null;  //班级ID
			String dId = null;  //院系ID
			String fileName = null;
			String pictureType = null;
			String path = null;
			String name = null;
			File file = null;
			Map<String, Object> map = new HashMap<String, Object>();
			//接收小程序端的图片，并且暂存本地
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				}
				else{
					path = request.getSession().getServletContext().getRealPath("") + "face\\";

					//path = "F:\\Eclipse\\BaiduFace\\WebContent\\face\\";
					
					//路径名
					if ("file".equals(fileItem.getFieldName())) {
						fileName = fileItem.getName();
						pictureType = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
						//pictureType获取图片格式
						//name是自己定义的名字
					    name = "face"+pictureType;
						file = new File(path+name);
						System.out.println(path+name);
						file.createNewFile();
						System.out.println(file.getAbsolutePath());
						InputStream inputStream = fileItem.getInputStream();
						FileOutputStream fileOutputStream = new FileOutputStream(file);
						byte[] b = new byte[1024];
						while(inputStream.read(b)!=-1){
							fileOutputStream.write(b);
						}
						inputStream.close();
						fileOutputStream.close();
					}
				}
			} 
			//录入人脸到人脸库中
            // 本地文件路径
			studentName = (String) map.get("name");
			classid = (String) map.get("classid");
			ps = con.prepareStatement(sql3);
			rs = ps.executeQuery();
			if(rs.next()) {
				if(rs.getString("id") != null)
				   studentId = String.valueOf(Integer.parseInt(rs.getString("id"))+1);
				else
				   studentId = "1";
			}
			else
				studentId = "1";
			String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
            String filePath = file.getAbsolutePath();
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            //生成录入命令
            String param1 = "uid=" + studentId + "&user_info=" + "userInfo5" + "&group_id=" + classid + "&images=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = Access_token.getAuth();
            String result = HttpUtil.post(url, accessToken, param1);
            System.out.println(result);  //输出结果 JSON格式
            if(result.contains("error")) {
            	response.getWriter().println(false);
            }
            else {
    			//获取班级信息，学生姓名;
    			ps = con.prepareStatement(sql2);
    			ps.setString(1 , classid);
    			rs = ps.executeQuery();
    			while(rs.next()) {
    				majorid = rs.getString("dId");
    				dId = rs.getString("cid");
    			}
    			//存储学生信息到数据库中
    			ps = con.prepareStatement(sql);
    			ps.setString(1 , studentName);
    			ps.setString(2 , studentId);
    			ps.setString(3 , classid);
    			ps.setString(4 , majorid);
    			ps.setString(5 , dId);
    			ps.executeUpdate();   
    			response.getWriter().println(true);
    			DBConnection.free(con , ps , rs);
            }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
