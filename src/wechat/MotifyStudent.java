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
 * Servlet implementation class MotifyStudent
 */
@WebServlet("/MotifyStudent")
public class MotifyStudent extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MotifyStudent() {
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
		/* �������е�������� */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		/* ��ȡ΢��С�����get��������ӡ */
		Connection con = DBConnection.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		// ��ȡ�޸�ͼƬ�������µ���������
		System.out.println("�ļ��ϴ�");
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
			// ����С����˵�ͼƬ�������ݴ汾��
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				} else {
					path = context.getRealPath("");
					path = "F:\\Eclipse\\BaiduFace\\WebContent\\face\\";
					// ·����
					if ("file".equals(fileItem.getFieldName())) {
						fileName = fileItem.getName();
						pictureType = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
						// pictureType��ȡͼƬ��ʽ
						// name���Լ����������
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
			// ��ȡѧ��������ID�Լ��༶ID
			String studentname = (String) map.get("name");
			String classid = (String) map.get("classid");
			String studentid = (String) map.get("studentid");
			if (studentname != null) {
				String sql = "update student set SName = ? where SID like ?";
				ps = con.prepareStatement(sql);
				ps.setString(1, studentname);
				ps.setString(2, studentid);
				ps.executeUpdate();
			}
			String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update";
			// �����ļ�·��
			String filePath = file.getAbsolutePath();
			byte[] imgData = FileUtil.readFileByBytes(filePath);
			String imgStr = Base64Util.encode(imgData);
			String imgParam = URLEncoder.encode(imgStr, "UTF-8");
			// ���ɸ�������
			String param1 = "uid=" + studentid + "&user_info=" + "userInfo5" + "&group_id=" + classid + "&images="
					+ imgParam;
			// ע�������Ϊ�˼򻯱���ÿһ������ȥ��ȡaccess_token�����ϻ���access_token�й���ʱ�䣬 �ͻ��˿����л��棬���ں����»�ȡ��
			String accessToken = Access_token.getAuth();
			String result = HttpUtil.post(url, accessToken, param1);
			System.out.println(result);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.free(con, ps, rs);
		}
	}

}
