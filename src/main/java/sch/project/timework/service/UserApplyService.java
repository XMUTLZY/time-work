package sch.project.timework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sch.project.timework.constant.CommonConstants;
import sch.project.timework.domain.UserApplyEntity;
import sch.project.timework.domain.UserEntity;
import sch.project.timework.domain.WorkInfoEntity;
import sch.project.timework.http.response.BaseResponse;
import sch.project.timework.http.response.LayerResponse;
import sch.project.timework.http.vo.UserApply;
import sch.project.timework.repository.UserApplyRepository;
import sch.project.timework.repository.UserRepository;
import sch.project.timework.repository.WorkInfoRepository;
import sch.project.timework.util.CommonUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApplyService {
    @Autowired
    private UserApplyRepository userApplyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkInfoRepository workInfoRepository;

    public LayerResponse myList(Pageable pageable, HttpServletRequest request) {
        UserEntity userEntity = CommonUtils.getCurrentUserInfo(request);
        Page<UserApplyEntity> userApplyEntityPage = userApplyRepository.findAllByUserId(pageable, userEntity.getId());
        return buildCommon(userApplyEntityPage);
    }

    public BaseResponse audit(Integer id, Integer status) {
        Optional<UserApplyEntity> userApplyEntityOptional = userApplyRepository.findById(id);
        if (!userApplyEntityOptional.isPresent()) {
            return new BaseResponse(BaseResponse.FAILD_CODE, "查询不到当前申请");
        }
        UserApplyEntity userApplyEntity = userApplyEntityOptional.get();
        userApplyEntity.setStatus(status);
        userApplyRepository.save(userApplyEntity);
        return new BaseResponse();
    }

    public LayerResponse list(Pageable pageable, HttpServletRequest request) {
        UserEntity userEntity = CommonUtils.getCurrentUserInfo(request);
        // 获取当前用户发布的所有兼职信息id
        List<WorkInfoEntity> workInfoEntityList = workInfoRepository.findAllByUserId(userEntity.getId());
        List<Integer> idList = workInfoEntityList.stream().map(WorkInfoEntity::getId).collect(Collectors.toList());
        Page<UserApplyEntity> userApplyEntityPage = userApplyRepository.findAllByWorkIdIn(pageable, idList);
        return buildCommon(userApplyEntityPage);
    }

    private LayerResponse buildCommon(Page<UserApplyEntity> userApplyEntityPage) {
        LayerResponse response = new LayerResponse();
        List<UserApply> userApplyList = new ArrayList<>();
        for (UserApplyEntity userApplyEntity : userApplyEntityPage) {
            Optional<UserEntity> userEntityOptional = userRepository.findById(userApplyEntity.getUserId());
            if (!userEntityOptional.isPresent()) {
                continue;
            }
            Optional<WorkInfoEntity> workInfoEntityOptional = workInfoRepository.findById(userApplyEntity.getWorkId());
            if (!workInfoEntityOptional.isPresent()) {
                continue;
            }
            UserEntity user = userEntityOptional.get();
            WorkInfoEntity workInfo = workInfoEntityOptional.get();
            UserApply userApply = new UserApply();
            userApply.setAge(user.getAge());
            userApply.setId(userApplyEntity.getId());
            userApply.setPhone(user.getPhone());
            userApply.setSexStr(user.getSex() == 1 ? "男" : "女");
            userApply.setSchool(user.getSchool());
            userApply.setUserName(user.getUserName());
            userApply.setCreateTime(userApplyEntity.getCreateTime());
            userApply.setWorkInfoId(userApplyEntity.getWorkId());
            userApply.setTitle(workInfo.getTitle());
            if (CommonConstants.STATUS_NO.equals(userApplyEntity.getStatus())) {
                userApply.setStatusStr("未通过");
            } else if (CommonConstants.STATUS_WAIT.equals(userApplyEntity.getStatus())) {
                userApply.setStatusStr("审核中");
            } else {
                userApply.setStatusStr("已通过");
            }
            userApplyList.add(userApply);
        }
        response.setData(userApplyList);
        response.setCount((int) userApplyRepository.count());
        return response;
    }
}
