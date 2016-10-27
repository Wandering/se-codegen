<!DOCTYPE html>
<html>


<!-- Mirrored from www.zi-han.net/theme/hplus/login.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:18:23 GMT -->
<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>登录</title>
    <meta name="keywords" content="admin后台">
    <meta name="description" content="admin后台">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="../../assets/css/bootstrap.min14ed.css?v=3.3.6" rel="stylesheet">
    <link href="../../assets/css/font-awesome.min93e3.css?v=4.4.0" rel="stylesheet">

    <link href="../../assets/css/animate.min.css" rel="stylesheet">
    <link href="../../assets/css/style.min862f.css?v=4.1.0" rel="stylesheet">
    <!--[if lt IE 9]>
	<meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->
    <script>if(window.top !== window.self){ window.top.location = window.location;}</script>
</head>

<body class="gray-bg">

<div class="middle-box text-center loginscreen  animated fadeInDown">
    <div>
        <div>

            <h3 class="logo-name">如易云</h3>

        </div>
        <h3>欢迎使用 后台管理系统</h3>

        <div class="m-t" >
            <div class="form-group">
                <input type="username" id="Username"  class="form-control" placeholder="用户名" required="">
            </div>
            <div class="form-group">
                <input type="password" id="Password" class="form-control" placeholder="密码" required="">
            </div>
            <button type="submit" id="loginBtn" class="btn btn-primary block full-width m-b">登 录</button>


            <p class="text-muted text-center"> <a href="login.html#"><small>忘记密码了？</small></a> | <a href="register.html">注册一个新账号</a>
            </p>

        </div>
    </div>
</div>
<script src="../../assets/js/jquery.min.js?v=2.1.4"></script>
<script src="../../assets/js/bootstrap.min.js?v=3.3.6"></script>
<script src="../../assets/plugins/fullavatareditor/scripts/jQuery.Cookie.js"/>
<script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
</body>

<script>
    jQuery(function ($) {
        $('#loginBtn').on('click', function (e) {

            var data = 'j_username=' + encodeURIComponent($('#Username').val()) +
                    '&j_password=' + encodeURIComponent($('#Password').val()) +
                    '&remember-me=true&submit=Login';
            var csrfToken = $.Cookie('CSRF-TOKEN');

            return $.ajax({
                url: 'api/authentication?' + data,
                type: 'post',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-CSRF-TOKEN': csrfToken
                },
                success: function authSucess(response) {
                    var expiredAt = new Date();
                    expiredAt.setSeconds(expiredAt.getSeconds() + response.expires_in);
                    response.expires_at = expiredAt.getTime();
                    localStorage.setItem("authenticationToken", response);

                    window.location.href = "admin/ucm/index";
                    return response;
                }
            });

        });

        function encode(input) {
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

        function Base64() {
            var keyStr = 'ABCDEFGHIJKLMNOP' +
                    'QRSTUVWXYZabcdef' +
                    'ghijklmnopqrstuv' +
                    'wxyz0123456789+/' +
                    '=';

            var service = {
                decode: decode,
                encode: encode
            };

            return service;


            function decode(input) {
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

        $('#btn-login-dark').on('click', function (e) {
            $('body').attr('class', 'login-layout');
            $('#id-text2').attr('class', 'white');
            $('#id-company-text').attr('class', 'blue');

            e.preventDefault();
        });
        $('#btn-login-light').on('click', function (e) {
            $('body').attr('class', 'login-layout light-login');
            $('#id-text2').attr('class', 'grey');
            $('#id-company-text').attr('class', 'blue');

            e.preventDefault();
        });
        $('#btn-login-blur').on('click', function (e) {
            $('body').attr('class', 'login-layout blur-login');
            $('#id-text2').attr('class', 'white');
            $('#id-company-text').attr('class', 'light-blue');

            e.preventDefault();
        });

    });
</script>
<!-- Mirrored from www.zi-han.net/theme/hplus/login.html by HTTrack Website Copier/3.x [XR&CO'2014], Wed, 20 Jan 2016 14:18:23 GMT -->
</html>
