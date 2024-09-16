package com.chenx.thinking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DemoThinkingController {
    @Autowired
    private ApplicationContext context;
    // 设置响应码
//    @ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
    @GetMapping("/**")
    public String matchAll(HttpServletRequest request){
        return request.getRequestURL().toString();
    }
}
