package com.kingmeter.mvc;


import com.kingmeter.common.KingMeterException;
import com.kingmeter.common.ResponseCode;
import com.kingmeter.common.ResponseData;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

@ControllerAdvice
public class MyResponseBodyAdvice implements ResponseBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Class aClass) {
        return true;
    }


    @Override
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType,
                                  Class aClass, ServerHttpRequest request,
                                  ServerHttpResponse response) {

        if (response.getHeaders().containsKey("KM_ERROR")) {
            return o;
        } else {
            return new ResponseData(ResponseCode.SUCCESS,o == null ? "" : o);
        }
    }


    /**
     * catch Exception
     *
     * @param e
     * @return json
     */
    @ResponseBody
    @ExceptionHandler({KingMeterException.class}) //指定拦截异常的类型
    public ResponseData kingMeterExceptionHandler(KingMeterException e, HttpServletResponse response) {
        response.setHeader("KM_ERROR", "0");
        return new ResponseData(e.getCode(), e.getMsg(), "");
    }

    /**
     * deal with parameter validation error
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseData methodArgumentValidExceptionHandler(MethodArgumentNotValidException e) {
        long code = 10000l;//static error code
        StringBuffer message = new StringBuffer();
        if (e.getBindingResult().getErrorCount() > 0) {
            for (ObjectError error : e.getBindingResult().getAllErrors()) {
                message.append(error.getDefaultMessage()).append(";");
            }
            message.deleteCharAt(message.length() - 1);
        }
        return new ResponseData(code, message.toString(), new ArrayList<>());
    }
}
