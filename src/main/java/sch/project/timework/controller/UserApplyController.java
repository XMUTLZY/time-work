package sch.project.timework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sch.project.timework.http.response.BaseResponse;
import sch.project.timework.http.response.LayerResponse;
import sch.project.timework.service.UserApplyService;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user-apply")
public class UserApplyController {
    @Autowired
    private UserApplyService userApplyService;

    /**
     * 我的申请列表
     */
    @RequestMapping(value = "/my-list", method = RequestMethod.GET)
    @ResponseBody
    public LayerResponse myList(@RequestParam Integer limit, @RequestParam Integer page, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        return userApplyService.myList(pageable, request);
    }

    @RequestMapping(value = "/audit", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse audit(@RequestParam Integer id, @RequestParam Integer status) {
        return userApplyService.audit(id, status);
    }

    /**
     * 向我提交申请的列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public LayerResponse list(@RequestParam Integer limit, @RequestParam Integer page, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        return userApplyService.list(pageable, request);
    }


}
