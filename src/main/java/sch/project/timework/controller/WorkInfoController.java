package sch.project.timework.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sch.project.timework.http.request.WorkRequest;
import sch.project.timework.http.response.BaseResponse;
import sch.project.timework.http.response.LayerResponse;
import sch.project.timework.service.WorkInfoService;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/work")
public class WorkInfoController {
    @Autowired
    private WorkInfoService workInfoService;

    /**
     * 兼职信息列表
     * @param type 全部("all")/我的("my")
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public LayerResponse workList(@RequestParam Integer limit, @RequestParam Integer page,
                                  @RequestParam String type, HttpServletRequest request) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.Direction.ASC, "id");
        return workInfoService.workList(pageable, request, type);
    }

    /**
     * 报名兼职
     */
    @RequestMapping(value = "/apply", method = RequestMethod.GET)
    @ResponseBody
    public BaseResponse workApply(@RequestParam Integer workId, HttpServletRequest request) {
        return workInfoService.workApply(workId, request);
    }

    /**
     * 添加报名
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse add(@RequestBody WorkRequest request, HttpServletRequest httpServletRequest) {
        return workInfoService.add(request, httpServletRequest);
    }
}
