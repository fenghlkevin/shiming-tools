/**
 * Created by fengh on 2016/5/15.
 */
var path = $("#path").val();

// var login = function () {
//     var username = $("#user_name").val();
//     if (username === "") {
//         return;
//     }
//
//     var url = path + "/login?callback=?";
//
//     $.post(url, {username: username}, function (result) {
//         if (result.error === "") {
//             $("#menu").show();
//             $("#loginform").hide();
//             $("#logoutbtn").show();
//         }
//         $("#user_name").val("");
//     }, "json");
// };
//
// var logout = function () {
//     var url = path + "/logout?callback=?";
//
//     $.post(url, {}, function (result) {
//         $("#menu").hide();
//         $("#loginform").show();
//         $("#logoutbtn").hide();
//
//         $("#toword").hide();
//         $("#toexcel").hide();
//
//         $.each($("li[name='bands']"), function (i, one) {
//             $(one).attr("class", "");
//         });
//
//     }, "json");
// }


var createWord = function (btn) {
    //$(this).attr("disabled","disabled");
    var allinput = $("#toword").find("input");
    var params = {};
    $.each(allinput, function (i, one) {
        var tone = $(one);
        if (tone.val() === "" && (tone.attr("id") !== "end_row")&&(tone.attr("id") !== "sign_str")) {
            if(domethod==="2"&&tone.attr("id")=='if_file'){

            }else{
                tone.focus();
            }

        }

        params[tone.attr("id")] = tone.val();

    });

    params["domethod"] = domethod;
    params["license"] = $("#license").val();

    params.if_file = files.if_file;
    params.word_template = files.word_template;
    params.input_file = files.input_file;


    var url = path + "/excel2word?callback=?"
    $.post(url, params, function (result) {
        if(result.error===""){
            $.each(result.files,function (i,one) {
                window.open(path + "/temp/" + one);
                //window.open(path + "/temp/" + result.files[1]);
            });

        }else{
            //TODO show error
        }

    }, "json");
}
var domethod = "1";
var change_do = function (method) {
    domethod = method;
    if (domethod === "2") {
        $("#this_check_div").hide();
        $('#if_template_div').hide();
        $('#domethod-1').attr('class','btn btn-success');
        $('#domethod-2').attr('class','btn btn-primary');


    } else {
        $("#this_check_div").show();
        $('#if_template_div').show();
        $('#domethod-1').attr('class','btn btn-primary');
        $('#domethod-2').attr('class','btn btn-success');
    }
}


var files = {
    if_file: "",
    word_template: "",
    input_file: ""
}

//-----------------dododododo
var readBase64 = function (input, reader) {
    if (input.files && input.files[0]) {
        reader.readAsDataURL(input.files[0]);
    }
}

function getFileData(fileinput, thefileKey) {
    var reader = new FileReader();
    reader.onload = function (e) {
        files[thefileKey] = (e.target.result).split(",")[1];
    };

    fileinput.change(function () {
        readBase64(this, reader);
    });
}


//--------------------

var times, sign_str

// var readLicense = function (fileinput) {
//     var temp = fileinput.val();
//     temp = $.base64.decode(temp);
//     console.log(temp);
//     times = temp[0];
//     sign_str = temp[1];
// }

// function dec2Bin(d) {
//     var b = '';
//
//     for (var i = 0; i < 8; i++) {
//         b = (d%2) + b;
//         d = Math.floor(d/2);
//     }
//
//     return b;
// }