package com.sai.openapi.controller;

import com.sai.core.dto.ResultCode;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @RequestMapping({"","/index"})
    public ResultCode index() {
        return ResultCode.success();
    }
}
