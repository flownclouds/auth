package com.andlinks.auth.controller;

import com.andlinks.auth.Response;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 王凯斌 on 2017/4/25.
 */
@RestController
@RequestMapping("/common")
public class CommonController extends BaseController{

    /**
     * 权限有关错误的统一返回接口，在UserTokenInterceptor拦截器处统一判断
     * @param info 错误信息
     * @return
     */
    @RequestMapping(value = "/auth", method = RequestMethod.GET)
    public Response auth(String info) {

        return Response.unAuth(info);
    }
}
