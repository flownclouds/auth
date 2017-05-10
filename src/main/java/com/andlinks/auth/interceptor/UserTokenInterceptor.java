package com.andlinks.auth.interceptor;

import com.andlinks.auth.annotation.Authority;
import com.andlinks.auth.service.UserTokenService;
import com.andlinks.auth.entity.UserToken;
import com.andlinks.auth.utils.I18nUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 与权限校验有关的拦截器
 * Created by 王凯斌 on 2017/4/25.
 */
@Component
public class UserTokenInterceptor implements HandlerInterceptor {

    @Resource(name = "userTokenImpl")
    private UserTokenService userTokenService;

    @Value("${setting.auth.error.url}")
    private String unAuthUrl;

    @Value("${setting.devMode}")
    private Boolean devMode;

    @Value("${setting.loginExpireSeconds}")
    private int loginExpireSeconds;

    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object method) throws Exception {

        if (devMode) {
            return true;
        }

        Cookie userNameCookie = WebUtils.getCookie(request, "userName");
        Cookie tokenCookie = WebUtils.getCookie(request, "token");
        if (userNameCookie == null || tokenCookie == null || userNameCookie.getValue() == null || tokenCookie.getValue() == null) {
            request
                    .getRequestDispatcher(String.format("%s=%s", unAuthUrl, I18nUtils.getMessage("Common.Response.Token.Missing")))
                    .forward(request, response);
            return false;
        }
        UserToken userToken = userTokenService.find(userNameCookie.getValue());

        if (userToken == null) {
            request
                    .getRequestDispatcher(String.format("%s=%s", unAuthUrl, I18nUtils.getMessage("Common.Response.Token.UserMissing")))
                    .forward(request, response);
            return false;
        }
        if (!tokenCookie.getValue().equals(userToken.getToken())) {
            request
                    .getRequestDispatcher(String.format("%s=%s", unAuthUrl, I18nUtils.getMessage("Common.Response.Token.Invalid")))
                    .forward(request, response);
            return false;
        }
        if (userToken.getExpireDate().before(new Date())) {
            request
                    .getRequestDispatcher(String.format("%s=%s", unAuthUrl, I18nUtils.getMessage("Common.Response.Token.Expired")))
                    .forward(request, response);
            return false;
        }
        userToken.setExpireDate(DateUtils.addSeconds(new Date(), loginExpireSeconds));
        userTokenService.save(userToken);

        /** 根据调度器指向的方法，获得相应的Authority注解，并进行权限比对 */
        HandlerMethod handlerMethod = (HandlerMethod) method;
        Set<String> permissions = new HashSet<String>();
        Authority classAuthority = handlerMethod.getBeanType().getAnnotation(
                Authority.class);
        if (classAuthority != null && classAuthority.validate()) {
            permissions.add(classAuthority.name());
        }
        Authority methodAuthority = handlerMethod.getMethodAnnotation(Authority.class);
        if (methodAuthority != null && methodAuthority.validate()) {
            permissions.add(methodAuthority.name());
        }
        if (!userToken.getPermissions().containsAll(permissions)) {
            request
                    .getRequestDispatcher(String.format("%s=%s", unAuthUrl, I18nUtils.getMessage("Common.Response.Auth.Invalid")))
                    .forward(request, response);
            return false;
        }
        return true;
    }

}
