package sch.project.timework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import sch.project.timework.domain.UserEntity;
import sch.project.timework.util.CommonUtils;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/view")
public class ViewController {
    @RequestMapping("/index")
    public String userIndex(Model model, HttpServletRequest request) {
        return commonView(request, model);
    }

    @RequestMapping("/login")
    public String userLogin(HttpServletRequest request, Model model) {
        return commonView(request, model);
    }

    private String commonView(HttpServletRequest request, Model model) {
        UserEntity userEntity = CommonUtils.getCurrentUserInfo(request);
        // 当前没有用户登录
        if (userEntity == null) {
            return "/userLogin";
        } else {
            model.addAttribute("user", userEntity);
            return "/userIndex";
        }
    }

}
