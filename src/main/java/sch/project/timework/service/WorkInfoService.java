package sch.project.timework.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sch.project.timework.constant.CommonConstants;
import sch.project.timework.domain.UserApplyEntity;
import sch.project.timework.domain.UserEntity;
import sch.project.timework.domain.WorkInfoEntity;
import sch.project.timework.http.response.BaseResponse;
import sch.project.timework.http.response.LayerResponse;
import sch.project.timework.http.vo.WorkInfo;
import sch.project.timework.repository.UserApplyRepository;
import sch.project.timework.repository.UserRepository;
import sch.project.timework.repository.WorkInfoRepository;
import sch.project.timework.util.CommonUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WorkInfoService {
    @Autowired
    private WorkInfoRepository workInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserApplyRepository userApplyRepository;

    public LayerResponse workList(Pageable pageable, HttpServletRequest request, String type) {
        LayerResponse response = new LayerResponse();
        Page<WorkInfoEntity> workInfoEntityPage;
        int total;
        // 我的发布
        if (CommonConstants.WORK_LIST_MY.equals(type)) {
            UserEntity userEntity = CommonUtils.getCurrentUserInfo(request);
            workInfoEntityPage = workInfoRepository.findAllByUserId(pageable, userEntity.getId());
            total = workInfoRepository.countAllByUserId(userEntity.getId());
        } else {
            workInfoEntityPage = workInfoRepository.findAll(pageable);
            total = (int) workInfoRepository.count();
        }
        List<WorkInfo> workInfoList = new ArrayList<>();
        for (WorkInfoEntity workInfoEntity : workInfoEntityPage) {
            WorkInfo workInfo = new WorkInfo();
            BeanUtils.copyProperties(workInfoEntity, workInfo);
            Optional<UserEntity> optional = userRepository.findById(workInfoEntity.getUserId());
            if (optional.isPresent()) {
                workInfo.setUserName(optional.get().getUserName());
                workInfo.setContact(optional.get().getPhone());
            }
            workInfoList.add(workInfo);
        }
        response.setData(workInfoList);
        response.setCount(total);
        return response;
    }

    public BaseResponse workApply(Integer workId, HttpServletRequest request) {
        UserEntity userEntity = CommonUtils.getCurrentUserInfo(request);
        List<UserApplyEntity> userApplyEntityList = userApplyRepository.findAllByWorkIdAndUserId(workId, userEntity.getId());
        if (!CollectionUtils.isEmpty(userApplyEntityList)) {
            return new BaseResponse(BaseResponse.FAILD_CODE, "您已经报名啦");
        }
        Optional<WorkInfoEntity> workInfoEntityOptional = workInfoRepository.findById(workId);
        if (workInfoEntityOptional.isPresent() && workInfoEntityOptional.get().getUserId().equals(userEntity.getId())) {
            return new BaseResponse(BaseResponse.FAILD_CODE, "您无法报名自己发布的兼职信息");
        }
        UserApplyEntity userApplyEntity = new UserApplyEntity();
        userApplyEntity.setUserId(userEntity.getId());
        userApplyEntity.setWorkId(workId);
        userApplyEntity.setCreateTime(new Date());
        userApplyRepository.save(userApplyEntity);
        return new BaseResponse();
    }
}
