<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../../favicon.ico">

    <title>shiming-tools</title>

    <!-- Custom styles for this template -->

</head>


<body>
<input type="hidden" id="path" name="path" value="${pageContext.request.contextPath}">
<!-- Fixed navbar -->
<%--<nav class="navbar navbar-inverse" id="index_menu">--%>
<%--<div class="container">--%>
<%--<div class="navbar-header">--%>
<%--<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"--%>
<%--aria-expanded="false" aria-controls="navbar">--%>
<%--<span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span--%>
<%--class="icon-bar"></span> <span class="icon-bar"></span>--%>
<%--</button>--%>
<%--<a class="navbar-brand" href="#">Shiming-Tools</a>--%>
<%--</div>--%>
<%--<div id="navbar" class="navbar-collapse collapse">--%>
<%--<ul class="nav navbar-nav">--%>
<%--<li class="active" id="menu_home"><a href="#">HOME</a></li>--%>
<%--<li id="menu_toword"><a href="#">生成Word</a></li>--%>
<%--<li id="menu_toexcel"><a href="#">生成Excel</a></li>--%>
<%--</ul>--%>
<%--</div>--%>
<%--<form class="navbar-form navbar-left" role="search">--%>
<%--<div class="form-group">--%>
<%--<input type="text" class="form-control" placeholder="Search">--%>
<%--</div>--%>
<%--<button type="submit" class="btn btn-default">Submit</button>--%>
<%--</form>--%>
<%--</div>--%>
<%--</nav>--%>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">Shiming-Tools</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <%--<ul id="menu" class="nav navbar-nav" style="display: none">--%>
            <%--<li onclick="changeFunc(this,'bands','toword','container')" name="bands"><a href="#">生成Word</a></li>--%>
            <%--<li onclick="changeFunc(this,'bands','toexcel','container')" name="bands"><a href="#">生成对比表</a></li>--%>
            <%--</ul>--%>
            <%--<form id="loginform" class="navbar-form navbar-left" role="search">--%>
            <%--<div class="form-group">--%>
            <%--<input type="text" id="user_name" class="form-control" placeholder="Username">--%>
            <%--</div>--%>
            <%--<button type="button" class="btn btn-default" onclick="login()">Login</button>--%>
            <%--</form>--%>
            <%--<ul class="nav navbar-nav">--%>
            <%--<li></li>--%>
            <%--<li style="display: none" id="logoutbtn"><a href="#" onclick="logout()">logout</a></li>--%>
            <%--</ul>--%>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="http://tool.oschina.net/encrypt?type=3" target="_blank">BASE64</a></li>
                <li><a href="http://tool.chinaz.com/Tools/unixtime.aspx" target="_blank">生成时间戳</a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>

<%--<div class="container" id="home_jumbotron">--%>
<%--<!-- Main component for a primary marketing message or call to action -->--%>
<%--<div class="jumbotron">--%>
<%--<h1>Shiming-Tools</h1>--%>
<%--<p>This Porject is a quick exercise to illustrate how the default, static and fixed to top navbar work. It--%>
<%--includes the responsive CSS and HTML, so it also adapts to your--%>
<%--viewport and device.</p>--%>
<%--<p>To see the difference between static and fixed top navbars, just scroll.</p>--%>
<%--<p>To see the difference between static and fixed top navbars, just scroll.</p>--%>
<%--</div>--%>
<%--</div>--%>
<!-- /container -->

<div id="toword" name="container">

    <div class="container">
        <!-- Main component for a primary marketing message or call to action -->
        <div class="jumbotron">
            <div class="container-fluid">
                <div class="form-group">
                    <button type="button" class="btn btn-danger" onclick="createWord()">生成</button>
                </div>
                <br>
                <div class="form-group">
                    <label class="col-md-2 control-label">输入LICENSE:</label>
                    <input type="text" class="col-md-4" id="license" name="license"
                           value=""><!-- MTQ4MTQ3MjAwMCPllYrllYrllYo= -->
                    <label class="col-md-2 control-label">输入Excel文档:</label>
                    <input type="file" class="col-md-2" id="input_file" name="input_file">

                </div>
                <br><br>
                <div class="form-group">
                    <div class="btn-group col-md-6" role="group" aria-label="...">
                        <button type="button" class="btn btn-primary" id="domethod-1" onclick="change_do('1')">生成三年结果数据</button>
                        <button type="button" class="btn btn-default" id="domethod-2" onclick="change_do('2')">生成模板数据</button>
                        <button type="button" class="btn btn-default" id="domethod-3" onclick="change_do('3')">生成前两年Excel数据</button>
                    </div>
                </div>
            </div>
            <%--</div>--%>
        </div>
    </div>

    <div class="container">
        <!-- Main component for a primary marketing message or call to action -->
        <div class="jumbotron">
            <div class="container-fluid">
                <div class="form-group">
                    <div id="word_template_div">
                        <label class="col-md-2 control-label">输出Word文档模板(word_template.docx):</label>
                        <input type="file" class="col-md-4" id="word_template" name="word_template">
                    </div>
                    <div id="if_template_div">
                        <label class="col-md-2 control-label">条件判断文件(if_template.if):</label>
                        <input type="file" class="col-md-4" id="if_file" name="if_file">
                    </div>
                </div>
            </div>
            <%--</div>--%>
        </div>
    </div>

    <%--<div class="container">--%>
    <%--<!-- Main component for a primary marketing message or call to action -->--%>
    <%--<div class="jumbotron">--%>
    <%--<div class="container-fluid">--%>
    <%--<div class="form-group">--%>
    <%--<button type="button" class="btn btn-default" onclick="createWord()">生成</button>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--&lt;%&ndash;</div>&ndash;%&gt;--%>
    <%--</div>--%>
    <%--</div>--%>

    <div class="container">
        <!-- Main component for a primary marketing message or call to action -->
        <div class="jumbotron">
            <div class="container-fluid">
                <div class="form-group">
                    <div class="input-prepend col-md-4">
                        <span class="add-on">前年时间</span>
                        <input id="last2_date" name="last2_date" type="text" readonly class="form_datetime ">
                    </div>
                    <div class="input-prepend col-md-4">
                        <span class="add-on">去年时间</span>
                        <input id="last_date" name="last_date" type="text" readonly class="form_datetime ">
                    </div>
                    <div class="input-prepend col-md-4">
                        <span class="add-on">今年时间</span>
                        <input id="this_date" name="this_date" type="text" readonly class="form_datetime ">
                    </div>
                    <br/>
                    <br/>
                    <div class="input-prepend col-md-4">
                        <span class="add-on">签名时间</span>
                        <input id="sign_date" name="sign_date" type="text" readonly class="form_datetime ">
                    </div>
                    <%--<div class="input-prepend col-md-4">--%>
                    <%--<span class="add-on">签名内容</span>--%>
                    <%--<input id="sign_str" name="sign_str" type="text" value="我试试签名" >--%>
                    <%--</div>--%>

                </div>
            </div>
        </div>
    </div>

    <div class="container">
        <!-- Main component for a primary marketing message or call to action -->
        <div class="jumbotron">
            <div class="container-fluid">

                <div class="form-group">
                    <label class="col-md-2 control-label">Sheet页名字:</label>
                    <input type="text" class="col-md-4" id="sheet_no" name="sheet_no" value="Sheet1">
                    <label class="col-md-2 control-label">学生名字列号:</label>
                    <input type="number" class="col-md-4" id="student_name_no" name="student_name_no" value="2">
                </div>
                <br/>
                <div class="form-group">
                    <label class="col-md-2 control-label">年级列:</label>
                    <input type="number" class="col-md-4" id="mgrade" name="mgrade" value="3">
                    <label class="col-md-2 control-label">班级列:</label>
                    <input type="number" class="col-md-4" id="mclass" name="mclass" value="4">
                </div>
                <br/>
                <div class="form-group">
                    <label class="col-md-2 control-label">测试组列:</label>
                    <input type="number" class="col-md-4" id="mgroup" name="mgroup" value="5">
                    <label class="col-md-2 control-label">学校名称:</label>
                    <input type="number" class="col-md-4" id="mschool" name="mschool" value="6">
                </div>
                <br/>
                <div class="form-group">
                    <label class="col-md-2 control-label">起始行:</label>
                    <input type="number" class="col-md-4" id="start_row" name="start_row" value="2">
                    <label class="col-md-2 control-label">结束行:</label>
                    <input type="number" class="col-md-4" id="end_row" name="end_row" value="">
                </div>
                <br/>
                <div class="form-group">
                    <label class="col-md-12 control-label">视力列位置(右,左):</label>
                </div>
                <div class="form-group">
                    <div class="input-prepend col-md-4">
                        <span class="add-on">第一次</span>
                        <input class="span2" id="last2_check" type="number" value="7">
                    </div>
                    <div class="input-prepend col-md-4">
                        <span class="add-on">第二次</span>
                        <input class="span2" id="last1_check" type="number" value="9">
                    </div>
                    <div id="this_check_div" class="input-prepend col-md-4">
                        <span class="add-on">第三次</span>
                        <input class="span2" id="this_check" type="number" value="11">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="toexcel" name="container" style="display: none">
    tttt
</div>
</body>


</html>
<script type="text/javascript" src="resources/lib.js"></script>
<script type="text/javascript">
    $(function () {
        initDatepicker("last2_date", -2);
        initDatepicker("last_date", -1);
        initDatepicker("this_date", 0);
        initDatepicker("sign_date", 0);

        getFileData($("#input_file"), "input_file");
        getFileData($("#word_template"), "word_template");
        getFileData($("#if_file"), "if_file");

    });

    var initDatepicker = function (id, addyear) {
        var mdate = new Date().addYears(addyear).toDateStr("yyyy年MM月");
        $("#" + id).datetimepicker({
            format: "yyyy年mm月",
            weekStart: 0,
            todayBtn: true,
            autoclose: true,
            todayHighlight: 1,
            startView: 'month',
            minView: 'month',
        });
        $("#" + id).val(mdate);
    }

    //    var changeFunc=function (theDiv,theDivName,theContainer,theContainerName) {
    //        $.each($("li[name='"+theDivName+"']"),function (i,one) {
    //            $(one).attr("class", "");
    //        });
    //        $(theDiv).attr("class","active");
    //
    //        $.each($("div[name='"+theContainerName+"']"),function (i,one) {
    //            $(one).hide();
    //        });
    //        $("#"+theContainer).show();
    //    }
</script>

