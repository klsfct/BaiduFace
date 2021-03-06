<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" import="check.AttendenceMessage"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>人脸签到后端管理平台</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<link rel="stylesheet" type="text/css"
	href="lib/bootstrap/css/bootstrap.css">

<link rel="stylesheet" type="text/css" href="stylesheets/theme.css">
<link rel="stylesheet" href="lib/font-awesome/css/font-awesome.css">

<script src="lib/jquery-1.7.2.min.js" type="text/javascript"></script>


<style type="text/css">
#line-chart {
	height: 300px;
	width: 800px;
	margin: 0px auto;
	margin-top: 1em;
}

.brand {
	font-family: georgia, serif;
}

.brand .first {
	color: #ccc;
	font-style: italic;
}

.brand .second {
	color: #fff;
	font-weight: bold;
}
</style>

<link rel="shortcut icon" href="../assets/ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="../assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="../assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="../assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="../assets/ico/apple-touch-icon-57-precomposed.png">
</head>
<body class="">
	<!--<![endif]-->

	<div class="navbar">
		<div class="navbar-inner">
			<ul class="nav pull-right">

				<li id="fat-menu" class="dropdown"><a href="#" role="button"
					class="dropdown-toggle" data-toggle="dropdown"> <i
						class="icon-user"></i> ${username } <i class="icon-caret-down"></i>
				</a>

					<ul class="dropdown-menu">
						<li><a tabindex="-1" href="#">My Account</a></li>
						<li class="divider"></li></li>
				<li class="divider visible-phone"></li>
				<li><a tabindex="-1" href="/BaiduFace/Logout">Logout</a></li>
			</ul>
			</li>

			</ul>
			<a class="brand" href="index.jsp"><span class="first">人脸识别签到</span>
				<span class="second">后端管理系统</span></a>
		</div>
	</div>

    <div class="sidebar-nav">
        <form class="search form-inline">
            <input type="text" placeholder="Welcome...">
        </form>

        <a href="#dashboard-menu" class="nav-header" data-toggle="collapse"><i class="icon-dashboard"></i>信息管理<i class="icon-chevron-up"></i></a>
        <ul id="dashboard-menu" class="nav nav-list collapse in">
            <li><a href="/BaiduFace/index.jsp">首页</a></li>
            <li ><a href="/BaiduFace/Departments.jsp">院系管理</a></li>
            <li ><a href="/BaiduFace/Majors.jsp">专业管理</a></li>            
            <li ><a href="/BaiduFace/Classes.jsp">班级管理</a></li>
            <li ><a href="/BaiduFace/Manager.jsp">管理员账号管理</a></li>            
        </ul>

        <a href="#accounts-menu" class="nav-header" data-toggle="collapse"><i class="icon-briefcase"></i>考勤信息管理<i class="icon-chevron-up"></i></a>
        <ul id="accounts-menu" class="nav nav-list collapse in">
            <li ><a href="/BaiduFace/Checked.jsp">全校考勤信息查看</a></li>
            <li ><a href="/BaiduFace/CheckClass.jsp">班级考勤信息查看</a></li>
        </ul>

        <a href="#legal-menu" class="nav-header" data-toggle="collapse"><i class="icon-legal"></i>项目介绍<i class="icon-chevron-up"></i></a>
        <ul id="legal-menu" class="nav nav-list collapse">
            <li ><a href="/BaiduFace/AboutProject.jsp">关于项目</a></li>
        </ul>

        <a href="/BaiduFace/Description.jsp" class="nav-header" ><i class="icon-question-sign"></i>说明文档</a>
        <a href="mailto:1139914806@qq.com" class="nav-header" ><i class="icon-comment"></i>联系我们</a>
    </div>

	<!--content-->
	<div class="content">

		<div class="header">

			<h1 class="page-title">Attendance Sheet</h1>
		</div>

		<ul class="breadcrumb">
			<li><a href="index.html">Home</a> <span class="divider">/</span></li>
			<li class="active">Attendance Sheet</li>
		</ul>
		<form action="/BaiduFace/BrowseAM" method="post" style="margin-left:20px;">
			<select name="classid">
				<c:forEach var="p" items="${Clist}">
					<option value="${p.classID}">${p.className}</option>
				</c:forEach>
			</select>
			<div class="btn-toolbar">
				<button action="submit" class="btn btn-primary">
					<i class="icon-save"></i> 查询
				</button>
			</div>
		</form>
		<div class="container-fluid">
			<div class="row-fluid">

				<div class="well">
					<table class="table">
						<thead>
							<tr>
								<th>#</th>
								<th>Student id</th>
								<th>Student name</th>
								<th>Class name</th>
								<th>Course</th>
								<th>Time to sign in</th>
								<th>State</th>
								<th>Time to sign back</th>
								<th>State</th>
								<th style="width: 26px;"></th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="p" items="${Alist}" varStatus="status">
								<tr>
									<td>${status.count}</td>
									<td>${p.sid}</td>
									<td>${p.name}</td>
									<td>${p.classname}</td>
									<td>${p.coursename}</td>
									<td>${p.time1}</td>
									<td>${p.flag1}</td>
									<td>${p.time2}</td>
									<td>${p.flag2}</td>
									<td><a
										href="/BaiduFace/EditMessage.jsp?name=${p.name}&course='${p.coursename}'"><i
											class="icon-pencil"></i></a> <a href="#myModal" role="button"
										data-toggle="modal"><i class="icon-remove"></i></a></td>
								</tr>

								<div class="modal small hide fade" id="myModal" tabindex="-1"
									role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal"
											aria-hidden="true">×</button>
										<h3 id="myModalLabel">Delete Confirmation</h3>
									</div>
									<div class="modal-body">
										<p class="error-text">
											<i class="icon-warning-sign modal-icon"></i>Are you sure you
											want to delete the record?
										</p>
									</div>
									<div class="modal-footer">
										<button class="btn" data-dismiss="modal" aria-hidden="true">Cancel</button>
										<button class="btn" data-dismiss="modal"
											onClick="window.open('/BaiduFace/DeleteMessage?name=${p.name}&coursename=${p.coursename}')">Delete</button>
									</div>
								</div>

							</c:forEach>
						</tbody>
					</table>
				</div>
				<div class="pagination">
					<ul>
						<li><a href="#">Prev</a></li>
						<li><a href="#">Next</a></li>
					</ul>
				</div>
				<footer>
				<hr>

				<p class="pull-right">
					<a href="#" title="" target="_blank">解释权归本小组所有</a>
				</p>

				<p>
					&copy; 2018 <a href="#" target="_blank">Xiao</a>
				</p>
				</footer>
			</div>
		</div>
	</div>

	<script src="lib/bootstrap/js/bootstrap.js"></script>
	<script type="text/javascript">
		$("[rel=tooltip]").tooltip();
		$(function() {
			$('.demo-cancel-click').click(function() {
				return false;
			});
		});
	</script>

</body>
</html>