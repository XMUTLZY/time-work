package sch.project.timework.util;

import sch.project.timework.constant.CommonConstants;
import sch.project.timework.domain.UserEntity;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {
    private static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static UserEntity getCurrentUserInfo(HttpServletRequest request) {
        return (UserEntity) request.getSession().getAttribute(CommonConstants.USER_INFO_KEY);
    }

    public static String formatDate(Date date) {
        return new SimpleDateFormat(DATE_FORMAT_DATETIME).format(date);
    }
}
