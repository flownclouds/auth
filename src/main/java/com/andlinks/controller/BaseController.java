package com.andlinks.controller;

import com.andlinks.Response;
import com.andlinks.utils.I18nUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by 王凯斌 on 2017/4/26.
 */
public class BaseController {


    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获得国际化消息的方法
     *
     * @param messageId
     * @return
     */
    protected String getMessage(String messageId) {
        return I18nUtils.getMessage(messageId);
    }

    /**
     * 集中处理所有异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Response handleError(Exception ex) {

        logger.error(getMessage("System.Log.Controller.Exception.Info"), ex);
        return Response.error(ex.getMessage());
    }

    //TODO not working
//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    public Response handleVinNotFound() {
//
//        return Response.error(getMessage("Common.Response.Error.NotFound"));
//    }
}
