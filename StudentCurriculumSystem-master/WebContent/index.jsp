<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>

	<title>登录</title>

	<meta charset="UTF-8">
	<meta name="robots" content="index,follow">

	<meta name="description" content="User login page" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

	<link rel="stylesheet" href="assets/css/bootstrap.min.css" />
	<link rel="stylesheet" href="assets/font-awesome/4.5.0/css/font-awesome.min.css" />

	<link rel="stylesheet" href="assets/css/fonts.googleapis.com.css" />

	<link rel="stylesheet" href="assets/css/ace.min.css" />

	<link rel="stylesheet" href="assets/css/ace-rtl.min.css" />
	<script type="text/javascript" src="assets/js/login.js"></script>

</head>

<body class="login-layout">
	<div class="main-container">
		<div class="main-content">
			<div class="row">
				<div class="col-sm-10 col-sm-offset-1">
					<div class="login-container">
						<div class="center">
							<h1>

								<span class="red">高校</span>
								<span class="white" id="id-text2">选课管理系统</span>
							</h1>
						</div>

						<div class="space-6"></div>

						<div class="position-relative">
							<div id="login-box" class="login-box visible widget-box no-border">
								<div class="widget-body">
									<div class="widget-main">

										<h4 class="header blue bigger">
											<i class="ace-icon fa fa-sign-in green"></i>
											用户登录
										</h4>
										
										<div class="space-6"></div>
										<h5>
											${fail}
										</h5>

										<form action="Login" method="POST">
											<fieldset>
												<label class="block clearfix">
													<span class="block input-icon input-icon-right">
														<input type="text" class="form-control" placeholder="编号"
															id="login-name" name="num" />
														<i class="ace-icon fa fa-list-ol "></i>
													</span>
												</label>

												<label class="block clearfix">
													<span class="block input-icon input-icon-right">
														<input type="password" class="form-control" placeholder="密码"
															id="login-password" name="password" />
														<i class="ace-icon fa fa-lock"></i>
													</span>
												</label>


												<label class="block clearfix">
													<span class="block input-icon input-icon-right">
															<select name="status" class="col-xs-12" id="login-id">
																	<option value="学生">学生</option>
																	<option value="教师">教师</option>
															</select>

													</span>
												</label>

												<div class="space"></div>

												<div class="clearfix">
													<label class="inline">
														<input type="checkbox" class="ace" id="login-save"
															name="login-save" />
														<span class="lbl"> 记住我</span>
													</label>


													<button type="submit"
														class="width-35 pull-right btn btn-sm btn-primary"
														id="login-btn">
														<i class="ace-icon fa fa-key"></i>
														<span class="bigger-110">登录</span>
													</button>
												</div>
												
												<div class="space-4"></div>
											</fieldset>
										</form>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script src="assets/js/jquery-2.1.4.min.js"></script>

	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement) document.write(
			"<script src='assets/js/jquery.mobile.custom.min.js'>" + "<" + "/script>");
	</script>

	<script type="text/javascript">
		jQuery(function ($) {
			$(document).on('click', '.toolbar a[data-target]', function (e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible'); //hide others
				$(target).addClass('visible'); //show target
			});
		});

		jQuery(function ($) {
			$('body').attr('class', 'login-layout light-login');
			$('#id-text2').attr('class', 'grey');
			$('#id-company-text').attr('class', 'blue');

			e.preventDefault();
		});
	</script>
</body>

</html>