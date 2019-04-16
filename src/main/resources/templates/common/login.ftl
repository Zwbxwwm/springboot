
<html>
<#include "../common/header.ftl">
<body>
<div id = "wrapper" class="toggled">
    <#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-12 column" >
                    <form role="form" method="get" action="/WxSpringboot/seller/user/login">
                        <div class="form-group">
                            <label>用户名</label><input type="text" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label>密码</label><input name="openid" type="password" class="form-control" />
                        </div>
                        <div class="checkbox">
                        </div> <button type="submit" class="btn btn-default">登录</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>