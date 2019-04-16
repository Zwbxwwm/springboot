<html>
<head>
    <meta charset="UTF-8">
    <title>成功页面</title>
    <link href="https://cdn.bootcss.com/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="alert alert-dismissable alert-success">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    注意!
                </h4> <strong>${success_msg}</strong> 完美！ <a href=${url} class="alert-link">三秒后自动跳转</a>
            </div>
        </div>
    </div>
</div>
</body>
<script>
    setTimeout(location.href=${url},3000)
</script>
</html>