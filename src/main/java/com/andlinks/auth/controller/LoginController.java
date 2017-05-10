package com.andlinks.auth.controller;

import com.andlinks.auth.Response;
import com.andlinks.auth.service.UserService;
import com.andlinks.auth.utils.HttpUtils;
import com.andlinks.auth.entity.PermissionDO;
import com.andlinks.auth.entity.RoleDO;
import com.andlinks.auth.entity.UserDO;
import com.andlinks.auth.entity.UserToken;
import com.andlinks.auth.service.UserTokenService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by 王凯斌 on 2017/4/25.
 */
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Resource(name = "userTokenImpl")
    private UserTokenService userTokenService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Value("${setting.loginExpireSeconds}")
    private int loginExpireSeconds;

    @Value("${setting.token.cookie.timeout}")
    private int tokenTimeout;

    /**
     * 登陆方法
     *
     * @param userName
     * @param password
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Response login(String userName, String password, HttpServletResponse response) {

        //获得UserDO实体并进行校验
        UserDO userDO = userService.findByUserName(userName);
        if (userDO == null) {
            return Response.error(getMessage("Login.Error.CantFindUser"));
        }
        if (!userDO.getPassword().equals(DigestUtils.md5Hex(password))) {
            return Response.error(getMessage("Login.Error.PasswordError"));
        }

        //校验通过，添加cookie和redis缓存，以供之后校验token
        StringBuffer tokenString = new StringBuffer(userDO.getPassword());
        tokenString.append(new Date().getTime());

        //添加cookie
        response.addCookie(HttpUtils
                .getCookie("userName", userDO.getUserName(), tokenTimeout, "/", false));
        response.addCookie(HttpUtils
                .getCookie("token", DigestUtils.md5Hex(tokenString.toString()), tokenTimeout, "/", false));

        //计算权限set
        Set<String> permissions = new HashSet<String>();
        //TODO 待优化
        for (RoleDO role : userDO.getRoles()) {
            for (PermissionDO permissionDO : role.getPermissions()) {
                permissions.add(permissionDO.getPermissionNameEN());
            }
        }

        //添加redis
        userTokenService.save(new UserToken(userDO.getUserName(),
                DigestUtils.md5Hex(tokenString.toString()),
                permissions,
                DateUtils.addSeconds(new Date(), loginExpireSeconds)));

        return Response.success();
    }
}
