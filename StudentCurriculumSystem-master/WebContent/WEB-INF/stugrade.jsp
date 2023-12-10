<%@page import="java.util.ArrayList"%>
<%@page import="bean.StudentBean"%>
<%@page import="bean.CourseBean"%>
<%@page import="bean.TeacherBean"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.OutputStreamWriter"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.BufferedWriter"%>

<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="zh-CN">

<head>
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta charset="utf-8" />
	<title>高校选课管理系统</title>
	<meta charset="UTF-8">
	<meta name="description" content="overview &amp; stats" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

	<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="assets/font-awesome/4.5.0/css/font-awesome.min.css" />

	<link rel="stylesheet" href="assets/css/fonts.googleapis.com.css" />

	<link rel="stylesheet" href="assets/css/ace.min.css" class="ace-main-stylesheet" id="main-ace-style" />

	<link rel="stylesheet" href="assets/css/ace-skins.min.css" />
	<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />



	<script src="assets/js/ace-extra.min.js"></script>

</head>

<body class="no-skin">

	<div id="navbar" class="navbar navbar-default          ace-save-state">
		<div class="navbar-container ace-save-state" id="navbar-container">
			<button type="button" class="navbar-toggle menu-toggler pull-left" id="menu-toggler" data-target="#sidebar">
				<span class="sr-only">Toggle sidebar</span> <span class="icon-bar"></span>

				<span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>

			<div class="navbar-header pull-left">
				<a href="index.html" class="navbar-brand"> <small> <i class="fa fa-graduation-cap"></i> 选课管理系统 &nbsp;

					</small>
				</a>
			</div>

		</div>
	</div>

	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container')
			} catch (e) {}
		</script>

		<div id="sidebar" class="sidebar                  responsive                    ace-save-state">
			<script type="text/javascript">
				try {
					ace.settings.loadState('sidebar')
				} catch (e) {}
			</script>

			<ul class="nav nav-list">

				<li class=""><a href="StuProfile"> <i class="menu-icon fa fa-user"></i> <span class="menu-text">
							个人信息 </span> 
					</a> <b class="arrow"></b>
				<li class=""><a href="StuCourse"> <i class="menu-icon fa fa-table"></i> <span class="menu-text">
							个人课程 </span>
					</a> <b class="arrow"></b>
				<li class=""><a href="StuChoose"> <i class="menu-icon fa fa-info"></i> <span class="menu-text">
							选课 </span>
					</a> <b class="arrow"></b>
			    <li class=""><a href="StuNochoose"> <i class="menu-icon fa fa-info"></i> <span class="menu-text">
							未选课 </span>
					</a> <b class="arrow"></b>
				<li class="active"><a href="StuGrade"> <i class="menu-icon fa fa-pencil"></i> <span class="menu-text">
							个人成绩 </span>
					</a> <b class="arrow"></b>
				<li class=""><a href="StuCPassword"> <i class="menu-icon fa fa-cog"></i> <span class="menu-text">
							修改密码 </span>
					</a> <b class="arrow"></b>
				<li class=""><a href="Logout"> <i class="menu-icon fa fa-cog"></i> <span class="menu-text">
							退出 </span>
					</a> <b class="arrow"></b>
			</ul>

			<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
				<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state"
					data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
			</div>
		</div>

		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<div class="page-header">
						<h1>个人成绩 </h1>
					</div>

					<div class="row">
						<form action="StuGrade" method="GET">
							<div class="row">
								<div class="col-xs-1"></div>
								<div class="col-xs-4">
									<label class="block"> <span
										class="block input-icon input-icon-right"> <select
											name="year" class="col-xs-10">
												<%
												String year = (String) request.getAttribute("year");
												int y=2018; 
												if (year!=null){
													y=Integer.parseInt(year);
												}
													for (int i = 2000; i <= 2050; i++) {
													if(i==y){
														%> 
														<option value="<%=i%>" selected = "selected"><%=i%></option>
														<% 
													}
													else{
												%>
												<option value="<%=i%>"><%=i%></option>

												<%
													}}
												%>
										</select>

									</span>
									</label>
								</div>
								<div class="col-xs-4">
									<label class="block"> <span
										class="block input-icon input-icon-right"> <select
											name="term" class="col-xs-10">
												<%
												String term = (String) request.getAttribute("term");
												String x = null;
													for (int i =0; i <2; i++) {
													if(i == 0) {x = "上学期";}
													else {x = "下学期";}
													if(x.equals(term)){
														%> 
														<option value="<%=x%>" selected = "selected"><%=x%></option>
														<% 
													}
													else{
												%>
												<option value="<%=x%>"><%=x%></option>

												<%
													}}
												%>
										</select>

									</span>
									</label>
								</div>
								<div class="col-xs-2"></div>
								<div class="col-xs-2">
									<button type="submit"
										class="width-35 pull-right btn btn-sm btn-primary"
										id="login-btn">

										<span class="bigger-110">查询</span>
									</button>
								</div>
								<div class="col-xs-1"></div>
							</div>
						</form>
					</div>
					<div class="space-6"></div>
					<div class="row">
						<div class="col-xs-12">


							<div>
								<table id="dynamic-table"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center">课程编号</th>
											<th class="center">课程名称</th>
											<th class="center">任课教师</th>
											<th class="center">开课学年</th>
											<th class="center">学期</th>
											<th class="center">成绩</th>

										</tr>
									</thead>
									<tbody>
										<%
											ArrayList<CourseBean> clist = (ArrayList<CourseBean>) request.getAttribute("clist");
										
											for (int i = 0; i < clist.size(); i++) {
										%>
										<tr>
											<td class="center"><%=clist.get(i).getCoursenum()%></td>
											<td class="center"><%=clist.get(i).getCoursename()%></td>		
											<td class="center"><%=clist.get(i).getTeacher() %></td>	
											<td class="center"><%=clist.get(i).getYear() %></td>	
											<td class="center"><%=clist.get(i).getTerm() %></td>	
											<td class="center"><%=clist.get(i).getGrade() %></td>								
											
											<%
												}
											%>
										
									</tbody>
								</table>
								<h2 class="center">${flag }</h2>
							</div>
						</div>
					</div>
					<div class="page-header">
						<h1>个人选修总学分数 </h1>
					</div>
					<div class="space-6"></div>
					<div class="row">
						<div class="col-xs-12">
							<div>
								<table id="dynamic-table"
									class="table table-striped table-bordered table-hover">
									<thead>
										<tr>
											<th class="center">总学分数</th>

										</tr>
									</thead>
									<tbody>
										<%
											float sum = (float)request.getAttribute("sum");
										    int j = 0;
										    if(sum != 0) j = 1;
											for(int i = 0; i < j; i ++) {
										%>
										<tr>
											<td class="center"><%=sum%></td>							
										<%
											}
										%>
									</tbody>


								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="footer">
			<div class="footer-inner">
				<div class="footer-content">
					<span class="bigger-120"> </span>><span class="blue bolder"></span>
				</div>
			</div>
		</div>

		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse"> <i
				class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>
	</div>
	<script src="assets/js/jquery-2.1.4.min.js"></script>

	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document
			.write("<script src='assets/js/jquery.mobile.custom.min.js'>" +
				"<" + "/script>");
	</script>
	<script src="assets/js/bootstrap.min.js"></script>

	<script src="assets/js/jquery-ui.custom.min.js"></script>
	<script src="assets/js/jquery.ui.touch-punch.min.js"></script>
	<script src="assets/js/jquery.easypiechart.min.js"></script>
	<script src="assets/js/jquery.sparkline.index.min.js"></script>
	<script src="assets/js/jquery.flot.min.js"></script>
	<script src="assets/js/jquery.flot.pie.min.js"></script>
	<script src="assets/js/jquery.flot.resize.min.js"></script>

	<script src="assets/js/ace-elements.min.js"></script>
	<script src="assets/js/ace.min.js"></script>



</body>

</html>