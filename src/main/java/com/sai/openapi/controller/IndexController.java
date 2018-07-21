package com.sai.openapi.controller;

import com.sai.core.dto.ResultCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping({"", "login"})
    public ResultCode index() {
        return ResultCode.successMsg("login");
    }

    @RequestMapping("testChange")
    public ResultCode index2() {
        return ResultCode.fail();
    }

    @RequestMapping("notFound")
    public ResultCode notFound() {
        return ResultCode.fail("页面失踪了");
    }
}
