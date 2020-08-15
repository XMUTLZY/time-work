package sch.project.timework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sch.project.timework.constant.CommonConstants;
import sch.project.timework.domain.UserEntity;
import sch.project.timework.http.request.UserRequest;
import sch.project.timework.http.response.BaseResponse;
import sch.project.timework.repository.UserRepository;
import sch.project.timework.util.CommonUtils;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public BaseResponse login(UserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse response) {
        UserEntity currentUserInfo = CommonUtils.getCurrentUserInfo(httpServletRequest);
        // 暂时不做加密
        UserEntity userEntity = userRepository.findDistinctByPhoneAndPassword(request.getPhone(), request.getPassword());
        if (userEntity == null) {
            return new BaseResponse(BaseResponse.FAILD_CODE, "账户不存在");
        } else {
            // 登录成功 设置缓存
            httpServletRequest.getSession().setAttribute(CommonConstants.USER_INFO_KEY, userEntity);
            return new BaseResponse();
        }
    }

    public BaseResponse register(UserRequest request) {
        UserEntity userEntity = new UserEntity();
        BaseResponse response = new BaseResponse();
        userEntity.setAge(request.getAge());
        userEntity.setCreateTime(new Date());
        userEntity.setUpdateTime(new Date());
        userEntity.setPassword(request.getPassword());
        userEntity.setPhone(request.getPhone());
        userEntity.setSchool(request.getSchool());
        userEntity.setUserName(request.getUserName());
        if (request.getSex().equals("男")) {
            userEntity.setSex(1);
        } else {
            userEntity.setSex(0);
        }
        userRepository.save(userEntity);
        return response;
    }

}
