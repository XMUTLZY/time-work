$(function () {
    userRegisterJs.bindEvent();
});

var userRegisterJs = {
    bindEvent: function () {
        userRegisterJs.method.saveBtn();
    },
    event: {

    },
    method: {
        saveBtn: function () {
            $("#register-button").click(function () {
                var data = {};
                data.phone = $("#phone").val();
                data.age = $("#age").val();
                data.password = $("#password").val();
                data.school = $("#school").val();
                data.sex = $("#sex").val();
                data.user_name = $("#user-name").val();
                if (!data.phone) {
                    layer.msg("手机号必填");
                    return;
                }
                if (!data.age) {
                    layer.msg("年龄必填");
                    return;
                }
                if (!data.password) {
                    layer.msg("密码必填");
                    return;
                }
                if (!data.school) {
                    layer.msg("学校必填");
                    return;
                }
                if (!data.sex) {
                    layer.msg("年龄必填");
                    return;
                }
                if (!data.user_name) {
                    layer.msg("用户名必填");
                    return;
                }
                $.ajax({
                    url: '/user/register',
                    data: JSON.stringify(data),
                    contentType: 'application/json',
                    type: 'post',
                    success: function (result) {
                        if (result.status_code == 200) {
                            layer.msg('注册成功');
                            setTimeout(function () {
                                location.href = "/view/login";
                            }, 1000)
                        }
                    },
                    error: function () {
                        layer.msg('数据异常');
                    }
                })
            })
        }
    }
}
