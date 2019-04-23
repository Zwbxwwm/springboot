
<html>
<#include "../common/header.ftl">
<body>
<div id = "wrapper" class="toggled">
    <#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container"...>
        </div>
    </div>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <form role="form" method="post" action="/WxSpringboot/seller/category/save">
                    <div class="form-group">
                        <label >类目名字</label>
                        <input name="categoryName" type="text" class="form-control" value="${(category.categoryName)! ''}" />
                    </div>
                    <div class="form-group">
                        <label >类目Type</label>
                        <input name="categoryType" type="number" class="form-control" value="${(category.categoryType)! ''}" />
                    </div>
                    <div class="checkbox">
                        <label><input type="checkbox" />Check me out</label>
                    </div>
                    <input hidden type="text" name="categoryId" value="${(category.categoryId)!''}">
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
