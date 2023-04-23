package com.gbase.admin.framework.security.handle;

import com.alibaba.fastjson.JSON;
import com.gbase.admin.common.core.domain.AjaxResult;
import com.gbase.admin.common.core.domain.model.LoginUser;
import com.gbase.admin.common.enums.ResponseCode;
import com.gbase.admin.common.utils.ServletUtils;
import com.gbase.admin.common.utils.StringUtils;
import com.gbase.admin.framework.web.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Custom exit success processing class
 *
 * @author xielibo
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    /**
     * logout
     *
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            tokenService.delLoginUser(loginUser.getToken());
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(ResponseCode.SUCCESS.code(), "Logout Success")));
    }
}
