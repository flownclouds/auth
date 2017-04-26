package com.andlinks.controller;

import com.andlinks.Response;
import com.andlinks.utils.I18nUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 王凯斌 on 2017/4/26.
 */
public class BaseController {

    /**
     * 获得国际化消息的方法
     * @param messageId
     * @return
     */
    protected String getMessage(String messageId) {
        return I18nUtils.getMessage(messageId);
    }

    /**
     * 集中处理所有异常
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response handleError(Exception ex) {

        return Response.error(ex.getMessage());
    }

    //TODO not working
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Response handleVinNotFound() {

        return Response.error(getMessage("Common.Response.Error.NotFound"));
    }
}
