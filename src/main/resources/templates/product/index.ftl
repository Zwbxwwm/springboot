
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
                <form role="form" method="post" action="/WxSpringboot/seller/product/save">
                    <div class="form-group">
                        <label >商品名称</label>
                        <input name="productName" type="text" class="form-control" value="${(productInfo.productName)! ''}" />
                    </div>
                    <div class="form-group">
                        <label >商品价格</label>
                        <input name="productPrice" type="text" class="form-control" value="${(productInfo.productPrice)! ''}" />
                    </div>
                    <div class="form-group">
                        <label >商品库存</label>
                        <input name="productStock" type="number" class="form-control" value="${(productInfo.productStock)! ''}" />
                    </div>
                    <div class="form-group">
                        <label >商品描述</label>
                        <input name="productDescription" type="text" class="form-control" value="${(productInfo.productDescription)! ''}" />
                    </div>
                    <div class="form-group">
                        <label >商品图片</label>
                        <img src="${(productInfo.productIcon)! ''}" alt="" height="100" width="100">
                        <input name="productIcon" type="text" class="form-control" value="${(productInfo.productIcon)! ''}" />
                    </div>
                    <div class="form-group">
                        <label>类目</label>
                        <select name="categoryType" class="form-control">
                            <#list categoryList as category>
                                <option value="${category.categoryType}"
                                        <#if (productInfo.categoryType)?? && productInfo.categoryType == category.categoryType>
                                            selected
                                        </#if>
                                >${category.categoryName}
                                </option>
                            </#list>
                        </select>
                    </div>
                    <div class="checkbox">
                        <label><input type="checkbox" />Check me out</label>
                    </div>
                    <input hidden type="text" name="productId" value="${(productInfo.productId)!''}">
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
