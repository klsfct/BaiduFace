<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
						class="icon-user"></i> ${username} <i class="icon-caret-down"></i>
				</a>

					<ul class="dropdown-menu">
						<li><a tabindex="-1" href="#">My Account</a></li>
						<li class="divider"></li>
						<li class="divider visible-phone"></li>
						<li><a tabindex="-1" href="/BaiduFace/Logout">Logout</a></li>
					</ul></li>

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



	<div class="content">

		<div class="header">

			<h1 class="page-title">About our project</h1>
		</div>

		<ul class="breadcrumb">
			<li><a href="index.jsp">Home</a> <span class="divider">/</span></li>
			<li class="active">about</li>
		</ul>

		<div class="container-fluid">
			<div class="row-fluid">

				<span><div>
						<ol
							style="margin-bottom: 0mm; margin-left: 0mm; padding-left: 0pt;">
							<li
								style="margin-left: 15pt; margin-right: 0pt; padding-left: -18pt; text-indent: 0pt; font-family: ����; font-size: 15pt; font-weight: Bold; color: #010101;"><div>
									<h3>
										<span
											style="font-family: ����; color: #010101; font-size: 15pt; font-decoration: Normal; font-weight: bold;">项目产生原因</span>
									</h3>
								</div></li>
						</ol>
						<div
							style="margin-left: 7mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">目前许多高校在上课考勤方面遇到许多麻烦的问题。有些老师采用点名的方式考勤，但一堂课时间本来就不多，点名不能占用太多时间，于是有些老师采用抽样点名的方式考勤，但这样的话，有些同学便有了侥幸心理，或者一些同学没有被点到的可以帮忙喊到。在这个互联网时代，许多高校用上了手机APP来考勤，例如本项目团队的母校</span><span
								style="text-indent: 7mm; min-height: 13pt; font-family: Calibri; color: rgb(1, 1, 1); font-size: 10pt;">——</span><span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">东莞理工学院，有一部分的课程采用优学院来考勤，但这样起到的作用也不大，因为同学们可以通过登录缺席同学的账号来签到，或者直接带对方手机到教室签到即可，签到质量可谓不佳。现如今人工智能兴起，生活中许多场景都可以应用上智能化设备，我们团队便想到了在课堂考勤上应用上人脸识别。但由于目前市场上提供的许多签到系统收费较高，并且不是很适合校园使用，于是我们理工科的团队成员们便有了这个自己打造高校的人脸识别签到平台的想法。</span>
						</div>
						<ol start="2"
							style="margin-bottom: 0mm; margin-left: 0mm; padding-left: 0pt;">
							<li
								style="margin-left: 15pt; margin-right: 0pt; padding-left: -18pt; text-indent: 0pt; font-family: ����; font-size: 15pt; font-weight: Bold; color: #010101;"><div>
									<h3>
										<span
											style="font-family: ����; color: #010101; font-size: 15pt; font-decoration: Normal; font-weight: bold;">项目简介</span>
									</h3>
								</div></li>
						</ol>
						<div
							style="margin-left: 7mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">本项目是一个基于人脸识别的低成本教室签到系统，通过调用百度AI开放平台的人脸识别API，结合目前手机上的主流应用程序</span><span
								style="text-indent: 7mm; min-height: 13pt; font-family: Calibri; color: rgb(1, 1, 1); font-size: 10pt;">——</span><span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">微信小程序，把人工智能技术引入校园，改善高校原有的签到方式，解放老师的考勤工作。</span>
						</div>
						<div
							style="margin-left: 7mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">本系统的应用层次分为班级管理层和学校管理层，班级管理使用微信小程序，学校管理使用网站页面。微信小程序具有两大功能：刷脸签到和班级管理。</span>
						</div>
						<div
							style="margin-left: 7mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">使用时，班级管理员（本文默认为班长）登录微信小程序，通过刷脸模式进行刷脸签到，通过班级管理功能了解本班级的考勤情况，对有特殊情况的同学进行补签；学校考勤负责人登录本系统网站，通过同步系统上的记录，查看各班级的实时考勤情况。</span>
						</div>
						<ol start="3"
							style="margin-bottom: 0mm; margin-left: 0mm; padding-left: 0pt;">
							<li
								style="margin-left: 15pt; margin-right: 0pt; padding-left: -18pt; text-indent: 0pt; font-family: ����; font-size: 15pt; font-weight: Bold; color: #010101;"><div>
									<h3>
										<span
											style="font-family: ����; color: #010101; font-size: 15pt; font-decoration: Normal; font-weight: bold;">项目的目标和意义</span>
									</h3>
								</div></li>
						</ol>
						<div
							style="margin-left: 0mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">解决目前高校考勤中繁琐的考勤问题，让高校的考勤工作跟上时代脚步。</span>
						</div>
						<ol start="4"
							style="margin-bottom: 0mm; margin-left: 0mm; padding-left: 0pt;">
							<li
								style="margin-left: 15pt; margin-right: 0pt; padding-left: -18pt; text-indent: 0pt; font-family: ����; font-size: 15pt; font-weight: Bold; color: #010101;"><div>
									<h3>
										<span
											style="font-family: ����; color: #010101; font-size: 15pt; font-decoration: Normal; font-weight: bold;">项目启动时间</span>
									</h3>
								</div></li>
						</ol>
						<div
							style="margin-left: 0mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">2018
								年 4 月 13 号</span>
						</div>
						<ol start="5"
							style="margin-bottom: 0mm; margin-left: 0mm; padding-left: 0pt;">
							<li
								style="margin-left: 15pt; margin-right: 0pt; padding-left: -18pt; text-indent: 0pt; font-family: ����; font-size: 15pt; font-weight: Bold; color: #010101;"><div>
									<h3>
										<span
											style="font-family: ����; color: #010101; font-size: 15pt; font-decoration: Normal; font-weight: bold;">系统logo</span>
									</h3>
								</div></li>
						</ol>
						<div
							style="margin: 4.57mm 0mm; text-indent: 7mm; line-height: 172%;">
							<img src="/BaiduFace/images/logo.jpg" type="image/png"
								data-filename="Image.png" /><br />
						</div>
						<h3>
							<span
								style="font-family: 宋体; color: rgb(1, 1, 1); font-size: 15pt;">Logo释义：五根手指代表我们团队的五个人，组成的ok手势有表示签到成功的意思，也暗含着我们团队乐观自信的态度。</span>
						</h3>
						<ol start="6"
							style="margin-bottom: 0mm; margin-left: 0mm; padding-left: 0pt;">
							<li
								style="margin-left: 15pt; margin-right: 0pt; padding-left: -18pt; text-indent: 0pt; font-family: ����; font-size: 15pt; font-weight: Bold; color: #010101;"><div>
									<h3>
										<span
											style="font-family: ����; color: #010101; font-size: 15pt; font-decoration: Normal; font-weight: bold;">系统名称及释义</span>
									</h3>
								</div></li>
						</ol>
						<div
							style="margin-left: 0mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">名称：你来了</span>
						</div>
						<div
							style="margin-left: 0mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">释义：成功刷脸签到的你才是真正的来到了教室，所以，我们知道，你来了。</span>
						</div>
						<div
							style="border-style: none; position: relative; z-index: 1; left: 0px; width: 192px;"></div>
						<ol start="7"
							style="margin-bottom: 0mm; margin-left: 0mm; padding-left: 0pt;">
							<li
								style="margin-left: 15pt; margin-right: 0pt; padding-left: -18pt; text-indent: 0pt; font-family: ����; font-size: 15pt; font-weight: Bold; color: #010101;"><div>
									<h3>
										<span
											style="font-family: ����; color: #010101; font-size: 15pt; font-decoration: Normal; font-weight: bold;">团队简介</span>
									</h3>
								</div></li>
						</ol>
						<div
							style="margin-left: 0mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">团队成员来自计算机与网络安全学院和电子工程与智能化学院，都是大学二年级的学生。我们各有所长，各展其能地负责项目中不同模块的工作，志在改善校园教学、生活环境。下面是团队的基本信息：</span>
						</div>
						<div style="margin-left: -2mm;">
							<table cellpadding="0" cellspacing="0"
								style="border-collapse: collapse; border-width: 1px; border-color: #010101;"
								width="583">
								<colgroup>
									<col style="width: 104px;"></col>
									<col style="width: 153px;"></col>
									<col style="width: 142px;"></col>
									<col style="width: 183px;"></col>
								</colgroup>
								<tbody>
									<tr>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="103"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">团队名称</span>
											</div></td>
										<td align="center" colspan="3"
											style="border: solid #010101 1px;" valign="top" width="478"><div
												style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">Give
													me five</span>
											</div></td>
									</tr>
									<tr>
										<td align="center" rowspan="6"
											style="border: solid #010101 1px;" valign="top" width="103"><div
												style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">团队成员</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="152"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">所在专业</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="141"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">姓名</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="182"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">负责工作</span>
											</div></td>
									</tr>
									<tr>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="152"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">计算机科学与技术</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="141"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">李海锋</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="182"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">项目负责人</span>
											</div></td>
									</tr>
									<tr>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="152"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">软件工程</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="141"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">林源</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="182"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">后台设计与制作</span>
											</div></td>
									</tr>
									<tr>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="152"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">软件工程</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="141"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">林文滔</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="182"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">后台设计与制作</span>
											</div></td>
									</tr>
									<tr>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="152"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">软件工程</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="141"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">黄浩填</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="182"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">小程序设计与制作</span>
											</div></td>
									</tr>
									<tr>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="152"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">电子信息工程</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="141"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">陈志豪</span>
											</div></td>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="182"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">固定摄像头设计与制作</span>
											</div></td>
									</tr>
									<tr>
										<td align="center" height="23"
											style="border: solid #010101 1px;" valign="top" width="103"><div
												style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">团队成立时间</span>
											</div></td>
										<td align="center" colspan="3" height="23"
											style="border: solid #010101 1px;" valign="top" width="478"><div
												style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">2018
													年 4 月 13 号</span>
											</div></td>
									</tr>
									<tr>
										<td align="center" style="border: solid #010101 1px;"
											valign="top" width="103"><div style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">团队思想</span>
											</div></td>
										<td align="center" colspan="3"
											style="border: solid #010101 1px;" valign="top" width="478"><div
												style="min-height: 13pt;">
												<span
													style="min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">学以致用</span>
											</div></td>
									</tr>
								</tbody>
							</table>
						</div>
						<div
							style="margin-left: 0mm; margin-right: 0mm; text-indent: 7mm; margin-top: 0.00mm; margin-bottom: 0.00mm; min-height: 13pt;">
							<span
								style="text-indent: 7mm; min-height: 13pt; font-family: 宋体; color: rgb(1, 1, 1); font-size: 10pt;">（我们的团队）</span>
						</div>
						<div>
							<img src="/BaiduFace/images/1.png" type="image/png"
								data-filename="Image.png" width="553" />
						</div>
						<div>
							<br />
						</div>
					</div></span>



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