/**
 * Created by hubai on 2017/7/27.
 */
function slideLogin() {
    var username = $("#box_name");
    var password = $("#box_pass");
    var validate = $("#Validate");
    var token = $("#Token");
    if (validate.val()!=="") {


        $.ajax({
            type: "post",
            url: "/slideLogin",
            data: {
                username: username.val(),
                password: password.val(),
                validate: validate.val(),
                token:token.val()
            },
            success: function (result) {
                var data = result;
                var flag = data.flag;
                //alert(data.flag);
                // alert(data.userName);
                // alert(result.message);
                if (flag) {
                    alert("登录成功");
                }
                else {
                    alert("用户名或密码错误");
                }


            },
            error: function (e) {
                alert("cannot to sever");
            }
        });
    }
    else {
        alert("请滑动验证！")
    }
}
$(document).ready(function () {
    ajaxRequest();
});
function  ajaxRequest() {
    var ts=Math.random();
    var type = "slide";
    var url = window.location.toString();
    $.ajax({

        // 获取id，challenge，success（是否启用failback）
        url: url+"/getToken", // 进行二次验证
        type: "GET",
        data:{
            ts:ts,
            type:type
        },
        success: function (result) {
            var data = JSON.parse(result);
            var flag = data.flag;
            var AppID = data.AppID;
            if (flag) {
                $('#Token').val(data.token);
                $('#AppID').val(data.AppID);

            }
        }
    });
}
