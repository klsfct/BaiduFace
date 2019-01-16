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
import com.alibaba.fastjson.JSONObject;

import baiduTools.Base64Util;
import baiduTools.FileUtil;
import baiduTools.HttpUtil;
import wechat_class.Access_token;

/**
 * Servlet implementation class FaceDetect
 */
@WebServlet("/FaceDetect")
public class FaceDetect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FaceDetect() {
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
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 允许所有的异域访问 */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		/* 获取微信小程序的get参数并打印 */
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
			String fileName = null;
			String pictureType = null;
			String path = null;
			String name = null;
			File file = null;
			Map<String, Object> map = new HashMap<String, Object>();
			// 接收小程序端的图片，并且暂存本地
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				} else {
					path = request.getSession().getServletContext().getRealPath("") + "face\\";

					// path = "F:\\Eclipse\\BaiduFace\\WebContent\\face\\";

					// 路径名
					if ("file".equals(fileItem.getFieldName())) {
						fileName = fileItem.getName();
						pictureType = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
						// pictureType获取图片格式
						// name是自己定义的名字
						name = "face" + pictureType;
						file = new File(path + name);
						System.out.println(path + name);
						file.createNewFile();
						System.out.println(file.getAbsolutePath());
						InputStream inputStream = fileItem.getInputStream();
						FileOutputStream fileOutputStream = new FileOutputStream(file);
						byte[] b = new byte[1024];
						while (inputStream.read(b) != -1) {
							fileOutputStream.write(b);
						}
						inputStream.close();
						fileOutputStream.close();
					}
				}
			}
			String url = "https://aip.baidubce.com/rest/2.0/face/v1/detect";
			String filePath = file.getAbsolutePath();
			byte[] imgData = FileUtil.readFileByBytes(filePath);
			String imgStr = Base64Util.encode(imgData);
			String imgParam = URLEncoder.encode(imgStr, "UTF-8");
			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
			String accessToken = Access_token.getAuth();
			String param1 = "max_face_num=" + 5 + "&face_fields="
					+ "age,beauty,expression,faceshape,gender,glasses,landmark,race,qualities,completeness" + "&image="
					+ imgParam;

			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
			String result = HttpUtil.post(url, accessToken, param1);
			JSONObject jb = JSONObject.parseObject(result);
			String result2 = jb.getString("result_num");
			response.getWriter().println(result2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
