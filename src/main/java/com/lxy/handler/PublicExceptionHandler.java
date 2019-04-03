package com.lxy.handler;

import com.lxy.common.BaseController;
import com.lxy.common.JsonResult;
import com.lxy.exception.ProgramException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局处理 Controller 层异常
 * Created by 李新宇
 * 2019-04-03 15:41
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class PublicExceptionHandler extends BaseController {

    //拦截program异常
    @ExceptionHandler(value = ProgramException.class)
    public JsonResult handlerProgramException(ProgramException e) {
        log.error("方法内部异常." + e.getMessage());
        return renderError(e.getMessage());
    }

    //拦截exception异常
    @ExceptionHandler(value = Exception.class)
    public JsonResult handlerException(Exception e) {
        log.error("接口调用系统异常." + e.getMessage());
        return renderError("接口调用系统异常");
    }
}

