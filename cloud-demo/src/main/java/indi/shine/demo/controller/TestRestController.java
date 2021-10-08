package indi.shine.demo.controller;

import indi.shine.web.bean.response.ReturnT;
import indi.shine.web.exception.BizException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiezhenxiang 2020/5/8
 */
@Slf4j
@Validated
@RestController
@RequestMapping(value = "/test")
@Api(value = "响应自定义",tags = "响应自定义")
public class TestRestController {

    @ApiOperation("rtt")
    @PostMapping(value = "/get")
    public ReturnT<Object> getTest(String test) {
        throw new BizException("nonono1");
    }

    @ApiOperation("rtt2")
    @PostMapping(value = "/get2")
    public Object getTest() {
        return 1;
    }
}