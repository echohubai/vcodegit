/**
 * Created by hubai on 2017/7/24.
 */
function Login() {
        var username = $("#box_name");
        var password = $("#box_pass");
        var validate = $("#Validate");
        var token = $("#Token");
        if (validate.val()!=="") {


            $.ajax({
                type: "POST",
                url: "/Login",
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
            alert("请先滑动验证！");
        }
    }
    $(document).ready(function () {
        ajaxRequest();
    });
    function  ajaxRequest() {
        var ts=Math.random();
        var type ="puzzle";
        var url = window.location.toString();
        $.ajax({
        url: "/getToken",
        type: "GET",
        data:{
            ts:ts,
            type:type
        },
        success: function (result) {
           var data = JSON.parse(result);
            var flag = data.flag;
            var AppID = data.AppID;
            var leftTopY = data.leftTopY;
            if (flag) {
                $('#Token').val(data.token);
                $('#AppID').val(data.AppID);
                $('#bgPicture').attr("src",data.bgPicture);
                $('#slicePicture').attr("src",data.slicePicture);
                changeStyle(leftTopY);
               // pictureRandByInit(token,AppID);
            }
        },
    });
}
    function changeStyle(lefTopY) {
    var yOffset =-180*(12-lefTopY)/12;
  /*  var obj = document.getElementById("slide_bg");
        alert(obj.width);*/

    $("#puzzle-the-piece").css("top",yOffset+"px");
}