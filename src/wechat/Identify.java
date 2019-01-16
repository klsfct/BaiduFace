package wechat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.DBConnection;
import com.alibaba.fastjson.JSONObject;

import baiduTools.Base64Util;
import baiduTools.FileUtil;
import baiduTools.HttpUtil;
import wechat_class.Access_token;
import wechat_class.Course;

/**
 * Servlet implementation class StudentRegister
 */
@WebServlet("/Identify")
public class Identify extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Identify() {
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
		System.out.println("考勤文件上传");
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload fileUpload = new ServletFileUpload(factory);
		fileUpload.setHeaderEncoding("UTF-8");
		List<FileItem> list;
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			list = fileUpload.parseRequest(request);
			System.out.println(list);
			Map<String, FileItem> param = new HashMap<String, FileItem>();
			ServletContext context = request.getServletContext();
			// 测试，测后，删
			String sql = "";
			String sql2 = "insert tableone values(?,?,?,?,?)";
			String sql3 = "insert tabletwo values(?,?,?,?,?)";
			String studentid = null; // 学生ID
			String studentName = null; // 学生姓名
			String classid = null; // 班级ID
			String courseid = null; //课程ID
			String fileName = null;
			String pictureType = null;
			String path = null;
			String name = null;
			File file = null;

			// 获取刷脸图片
			for (FileItem fileItem : list) {
				if (fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				} else {
					path = request.getSession().getServletContext().getRealPath("") + "face\\";
					//path = "F:\\Eclipse\\BaiduFace\\WebContent\\face\\";
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
			//判断目前是否需要考勤
			String startTime = (String) map.get("startTime");
            if(startTime.equals("目前无需考勤")) {
            	response.getWriter().println("目前无需考勤");
            	return;
            }
			// 读取班级ID
			classid = (String) map.get("classid");
			System.out.println("classid: " + classid);
			//courseid = (String) map.get("courseid");
			// 人脸识别
			String url = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
			String filePath = file.getAbsolutePath();
			byte[] imgData = FileUtil.readFileByBytes(filePath);
			String imgStr = Base64Util.encode(imgData);
			String imgParam = URLEncoder.encode(imgStr, "UTF-8");
			// 生成命令
			String param1 = "group_id=" + classid + "&user_top_num=" + "1" + "&face_top_num=" + "1" + "&images="
					+ imgParam + "&ext_fields=faceliveness";
			// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
			String accessToken = Access_token.getAuth();
			String result = HttpUtil.post(url, accessToken, param1);
			if(result.contains("face not found") || result.contains("no user in group")) {
				return;  //无人刷脸的状态
			}
			if(result.contains("error")) {
				response.getWriter().println("0");
				return;
			}
			JSONObject jb = JSONObject.parseObject(result);
			String result2 = jb.getString("result");
			String result3 = jb.getString("ext_info");
			JSONObject jb2 = JSONObject.parseObject(result3);
			result3 = jb2.getString("faceliveness");  //获取活体检测数据
			//System.out.println(Double.valueOf(result3) > 1.0);
			result2 = result2.substring(1, result2.length() - 1);
			jb = JSONObject.parseObject(result2);
			studentid = jb.getString("uid");  //获取匹配信息的学生ID
			String scores = jb.getString("scores");
			scores = scores.substring(1, scores.length() - 1);
			//System.out.println(scores);
			if (Double.valueOf(result3) < 0.393241 || Double.valueOf(scores) <= 80) {
				response.getWriter().println("0");
				return;
			} else {
				String endTime = (String) map.get("endTime");
				String day = (String) map.get("nowDay");
				response.getWriter().println("1");
				//AttendList.check(studentid);
				//HttpSession session = request.getSession();
				//ArrayList<Course> time = (ArrayList<Course>) session.getAttribute("time");
				String nowTime = null;
				Calendar calendar = Calendar.getInstance();
				Date date = new Date();  //获取当前时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				String flag = null;  //签到状态
				Date date1 = sdf.parse(day + " " + startTime); //获取签到时间
				Date date2 = sdf.parse(day + " " + endTime); //获取签退时间
			    //计算签到正常的时间范围
				int week =calendar.get(Calendar.DAY_OF_WEEK) - 1;  //为计算timeflag而用
				calendar.setTime(date1);	
		        calendar.add(Calendar.MINUTE , 10);  
		        int hour = calendar.get(Calendar.HOUR_OF_DAY);
		        int timeFlag = 0;
		        //计算timeFlag
		        if(hour < 13) {
		        	timeFlag = 1 + week * 3;
		        } else if (hour < 19) {
		        	timeFlag = 1 + week * 3 + 1;
		        } else {
		        	timeFlag = 1 + week * 3 + 2;
		        }
		        String hour1 = null;
		        String minute = null;
		        if(hour < 10) {
		        	hour1 = "0" + hour;
		        } else {
		        	hour1 = String.valueOf(hour);
		        }
		        if(calendar.get(Calendar.MINUTE) < 10) {
		        	minute = "0" + calendar.get(Calendar.MINUTE);
		        } else {
		        	minute = String.valueOf(calendar.get(Calendar.MINUTE));
		        }
		        String time1 = hour1 + ":" + minute;
		        Date date3 = sdf.parse(day + " " + time1);//获取最早一节课的开始时间
		        //计算迟到的时间范围
		        calendar.add(Calendar.MINUTE , 5);
		        String time2 = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
		        Date date4 = sdf.parse(day + " " + time2);  //上课开始到五分钟内签到为迟到
		        //计算当前的签到状态
		        if(date.getTime() < date3.getTime()) {
		        	 flag = "正常";
		        	 nowTime = day + " " + time1;
		        } else if(date.getTime() < date4.getTime()) {
		        	 flag = "迟到";
		        	 nowTime = day + " " + time1;
		        } else if(date.getTime() < date2.getTime()) {
		        	flag = "早退";
		        	nowTime = day + " " + endTime;
		        } else {
		        	flag = "正常签退";
		        	nowTime = day + " " + endTime;
		        }
		        Connection con = DBConnection.getConnection();
		        PreparedStatement ps = null;
		        if(flag.equals("正常") || flag.equals("迟到")) {
		        	ps = con.prepareStatement(sql2);
		        } else {
		        	if(flag.equals("正常签退"))
		        	     flag = "正常";
		        	ps = con.prepareStatement(sql3);
		        }
		        ps.setString(1 , studentid);
		        ps.setString(2 , nowTime);
		        ps.setString(3 , flag);
		        ps.setInt(4 , timeFlag);
		        ps.setString(5 , classid);
		        ps.executeUpdate();
				DBConnection.free(con , ps , null);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
