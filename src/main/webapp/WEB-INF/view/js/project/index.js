/**
 * Created by fengh on 2016/5/15.
 */
var path = $("#path").val();

var login = function () {
    var username = $("#user_name").val();
    if (username === "") {
        return;
    }

    var url = path + "/login?callback=?";

    $.post(url, {username: username}, function (result) {
        if (result.error === "") {
            $("#menu").show();
            $("#loginform").hide();
            $("#logoutbtn").show();
        }
        $("#user_name").val("");
    }, "json");
};

var logout = function () {
    var url = path + "/logout?callback=?";

    $.post(url, {}, function (result) {
        $("#menu").hide();
        $("#loginform").show();
        $("#logoutbtn").hide();

        $("#toword").hide();
        $("#toexcel").hide();

        $.each($("li[name='bands']"),function (i,one) {
            $(one).attr("class", "");
        });

    }, "json");
}


var createWord=function (btn) {
    //$(this).attr("disabled","disabled");
    var allinput=$("#toword").find("input");
    var params={};
    $.each(allinput,function (i,one) {
        var tone=$(one);
        if(tone.val()===""&&(tone.attr("id")!=="end_row")){
            tone.focus();
        }

        params[tone.attr("id")]=tone.val();

    });

    params.if_file=files.if_file;
    params.word_template=files.word_template;
    params.input_file=files.input_file;

    var url=path+"/excel2word?callback=?"
    $.post(url, params, function (result) {
        window.location.href = path+"/temp/"+result.filename;
    }, "json");
}


var files={
    if_file:"",
    word_template:"",
    input_file:""
}

//-----------------dododododo
var readBase64=function(input, reader) {
    if (input.files && input.files[0]) {
        reader.readAsDataURL(input.files[0]);
    }
}

function getFileData(fileinput,thefileKey) {
    var reader = new FileReader();
    reader.onload = function (e) {
        files[thefileKey]=(e.target.result).split(",")[1];
    };

    fileinput.change(function () {
        readBase64(this, reader);
    });
}
