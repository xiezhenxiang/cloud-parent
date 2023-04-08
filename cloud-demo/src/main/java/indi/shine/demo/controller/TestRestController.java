package indi.shine.demo.controller;

import indi.shine.common.bean.exception.BizException;
import indi.shine.common.bean.response.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ReturnT<Object> getTest(@Validated @RequestBody Req req) {
        throw new BizException("nonono1");
    }

    @ApiOperation("rtt2")
    @PostMapping(value = "/get2")
    public Object getTest() {
        return ReturnT.fail();
    }
}