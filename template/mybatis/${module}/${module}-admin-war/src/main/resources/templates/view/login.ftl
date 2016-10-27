<#assign path="../../../" />
<!DOCTYPE html>
<html lang="zh_cn">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>Login Page - Ace Admin</title>

		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="../assets/bak/css/bootstrap.min.css" />
		<link rel="stylesheet" href="../assets/bak/css/font-awesome.min.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="../assets/bak/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="../assets/bak/css/ace.min.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="../assets/bak/css/ace-part2.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="../assets/bak/css/ace-rtl.min.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="../assets/bak/css/ace-ie.min.css" />
		<![endif]-->
		<link rel="stylesheet" href="../assets/bak/css/ace.onpage-help.css" />

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="../assets/bak/js/html5shiv.js"></script>
		<script src="../assets/bak/js/respond.min.js"></script>
		<![endif]-->
	</head>

	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center" style="margin-top: 100px;">
								<#--<h1>-->
									<#--<i class="ace-icon fa fa-leaf green"></i>-->
									<#--<span class="red">Ace</span>-->
									<#--<span class="white" id="id-text2">Application</span>-->
								<#--</h1>-->
								<#--<h4 class="blue" id="id-company-text">&copy; Company Name</h4>-->
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
                                                欢迎登录
											</h4>

											<div class="space-6"></div>

											<#--<form >-->
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" name="Username" id="Username"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" name="Password" id="Password"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<label class="inline">
															<input type="checkbox" class="ace" />
															<span class="lbl">记住我</span>
														</label>

														<button id="loginBtn" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">登录</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											<#--</form>-->



											<div class="space-6"></div>


										</div><!-- /.widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="#" data-target="#forgot-box" class="forgot-password-link">
													<i class="ace-icon fa fa-arrow-left"></i>
													忘记密码?
												</a>
											</div>

											<div>
												<a href="#" data-target="#signup-box" class="user-signup-link">
													注册
													<i class="ace-icon fa fa-arrow-right"></i>
												</a>
											</div>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->

								<div id="forgot-box" class="forgot-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header red lighter bigger">
												<i class="ace-icon fa fa-key"></i>
												找回密码
											</h4>

											<div class="space-6"></div>
											<p>
												输入您的邮件地址
											</p>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<div class="clearfix">
														<button type="button" class="width-35 pull-right btn btn-sm btn-danger">
															<i class="ace-icon fa fa-lightbulb-o"></i>
															<span class="bigger-110">找回密码</span>
														</button>
													</div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												返回登录
												<i class="ace-icon fa fa-arrow-right"></i>
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.forgot-box -->

								<div id="signup-box" class="signup-box widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header green lighter bigger">
												<i class="ace-icon fa fa-users blue"></i>
												新用户注册
											</h4>

											<div class="space-6"></div>


											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="email" class="form-control" placeholder="Email" />
															<i class="ace-icon fa fa-envelope"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="用户名" />
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="密码" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="重复密码" />
															<i class="ace-icon fa fa-retweet"></i>
														</span>
													</label>

													<label class="block">
														<input type="checkbox" class="ace" />
														<span class="lbl">
															同意
															<a href="#">用户注册协议</a>
														</span>
													</label>

													<div class="space-24"></div>

													<div class="clearfix">
														<button type="reset" class="width-30 pull-left btn btn-sm">
															<i class="ace-icon fa fa-refresh"></i>
															<span class="bigger-110">重置</span>
														</button>

														<button type="button" class="width-65 pull-right btn btn-sm btn-success">
															<span class="bigger-110">注册</span>

															<i class="ace-icon fa fa-arrow-right icon-on-right"></i>
														</button>
													</div>
												</fieldset>
											</form>
										</div>

										<div class="toolbar center">
											<a href="#" data-target="#login-box" class="back-to-login-link">
												<i class="ace-icon fa fa-arrow-left"></i>
												返回登录
											</a>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.signup-box -->
							</div><!-- /.position-relative -->

							<div class="navbar-fixed-top align-right">
								<br />
								&nbsp;
								<a id="btn-login-dark" href="#">Dark</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-blur" href="#">Blur</a>
								&nbsp;
								<span class="blue">/</span>
								&nbsp;
								<a id="btn-login-light" href="#">Light</a>
								&nbsp; &nbsp; &nbsp;
							</div>
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='../assets/bak/js/jquery.min.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='../assets/bak/js/jquery1x.min.js'>"+"<"+"/script>");
</script>

<![endif]-->
        <#--<script src="../assets/js/ext/sha1.js"/>-->
        <script src="../assets/bak/js/jquery.cookie.js"/>
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='../assets/bak/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
			 $(document).on('click', '.toolbar a[data-target]', function(e) {
				e.preventDefault();
				var target = $(this).data('target');
				$('.widget-box.visible').removeClass('visible');//hide others
				$(target).addClass('visible');//show target
			 });
			});



			//you don't need this, just used for changing background
			jQuery(function($) {
             $('#loginBtn').on('click', function(e){

                 var data = 'j_username=' + encodeURIComponent($('#Username').val()) +
                         '&j_password=' + encodeURIComponent($('#Password').val()) +
                         '&remember-me=true&submit=Login';
				 var csrfToken = $.cookie('CSRF-TOKEN');

                 return $.ajax({url:'api/authentication?' + data,
                     type: 'post',
                     headers: {
                         'Content-Type': 'application/x-www-form-urlencoded',
						 'X-CSRF-TOKEN': csrfToken
                     },
                     success:function authSucess (response) {
                         var expiredAt = new Date();
                         expiredAt.setSeconds(expiredAt.getSeconds() + response.expires_in);
                         response.expires_at = expiredAt.getTime();
                         localStorage.setItem("authenticationToken", response);

                         window.location.href="/api/test";
                         return response;
                     }
                 });

//                 var data = 'username=' +  encodeURIComponent($('#Username').val()) + '&password=' +
//                         encodeURIComponent($('#Password').val()) + '&grant_type=password&scope=read%20write&' +
//                         'client_secret=seuaaapp&client_id=seuaaapp';
//
//                 return $.ajax({url:'http://localhost:9999/oauth/token?' + data,
//					            type: 'post',
////                                dataType:'JSONP',
//                                headers: {
//									 'Content-Type': 'application/x-www-form-urlencoded',
//									 'Accept': 'application/json',
//									 'Authorization': 'Basic ' + encode('seuaaapp' + ':' + 'seuaaapp')
//								 },
//                                success:function authSucess (response) {
//                                    var expiredAt = new Date();
//                                    expiredAt.setSeconds(expiredAt.getSeconds() + response.expires_in);
//                                    response.expires_at = expiredAt.getTime();
//                                    localStorage.setItem("authenticationToken", response);
//
//                                    window.location.href="/api/test";
//                                    return response;
//                                }
//                 });






//                 var hashObj = new jsSHA($('#Password').val(), "TEXT");
//                 var encryptPwd = hashObj.getHash("SHA-1", "HEX");
//                 $.post("/login/do", {Username:$('#Username').val(), Password:encryptPwd}, function(data){alert(data) }, "json");
             });

                function encode (input) {
                    var keyStr = 'ABCDEFGHIJKLMNOP' +
                            'QRSTUVWXYZabcdef' +
                            'ghijklmnopqrstuv' +
                            'wxyz0123456789+/' +
                            '=';
                    var output = '',
                            chr1, chr2, chr3 = '',
                            enc1, enc2, enc3, enc4 = '',
                            i = 0;

                    while (i < input.length) {
                        chr1 = input.charCodeAt(i++);
                        chr2 = input.charCodeAt(i++);
                        chr3 = input.charCodeAt(i++);

                        enc1 = chr1 >> 2;
                        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
                        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
                        enc4 = chr3 & 63;

                        if (isNaN(chr2)) {
                            enc3 = enc4 = 64;
                        } else if (isNaN(chr3)) {
                            enc4 = 64;
                        }

                        output = output +
                                keyStr.charAt(enc1) +
                                keyStr.charAt(enc2) +
                                keyStr.charAt(enc3) +
                                keyStr.charAt(enc4);
                        chr1 = chr2 = chr3 = '';
                        enc1 = enc2 = enc3 = enc4 = '';
                    }

                    return output;
                }

                function Base64 () {
                    var keyStr = 'ABCDEFGHIJKLMNOP' +
                            'QRSTUVWXYZabcdef' +
                            'ghijklmnopqrstuv' +
                            'wxyz0123456789+/' +
                            '=';

                    var service = {
                        decode : decode,
                        encode : encode
                    };

                    return service;



                    function decode (input) {
                        var output = '',
                                chr1, chr2, chr3 = '',
                                enc1, enc2, enc3, enc4 = '',
                                i = 0;

                        // remove all characters that are not A-Z, a-z, 0-9, +, /, or =
                        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, '');

                        while (i < input.length) {
                            enc1 = keyStr.indexOf(input.charAt(i++));
                            enc2 = keyStr.indexOf(input.charAt(i++));
                            enc3 = keyStr.indexOf(input.charAt(i++));
                            enc4 = keyStr.indexOf(input.charAt(i++));

                            chr1 = (enc1 << 2) | (enc2 >> 4);
                            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
                            chr3 = ((enc3 & 3) << 6) | enc4;

                            output = output + String.fromCharCode(chr1);

                            if (enc3 !== 64) {
                                output = output + String.fromCharCode(chr2);
                            }
                            if (enc4 !== 64) {
                                output = output + String.fromCharCode(chr3);
                            }

                            chr1 = chr2 = chr3 = '';
                            enc1 = enc2 = enc3 = enc4 = '';
                        }

                        return output;
                    }
                }

			 $('#btn-login-dark').on('click', function(e) {
				$('body').attr('class', 'login-layout');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'blue');

				e.preventDefault();
			 });
			 $('#btn-login-light').on('click', function(e) {
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');

				e.preventDefault();
			 });
			 $('#btn-login-blur').on('click', function(e) {
				$('body').attr('class', 'login-layout blur-login');
				$('#id-text2').attr('class', 'white');
				$('#id-company-text').attr('class', 'light-blue');

				e.preventDefault();
			 });

			});
		</script>
	</body>
</html>
