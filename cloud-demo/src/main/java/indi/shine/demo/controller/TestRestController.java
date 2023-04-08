package indi.shine.demo.controller;

import indi.shine.common.bean.response.ReturnT;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

/**
 * @author xiezhenxiang 2020/5/8
 */
@Slf4j
@Validated
@Controller
@RequestMapping(value = "/test")
@Api(value = "响应自定义",tags = "响应自定义")
public class TestRestController {


    @PostConstruct
    public void init() {
        log.info("11111111111111");
    }

    @ApiOperation("rtt2")
    @PostMapping(value = "/get2")
    public Object getTest() {
        return ReturnT.fail();
    }
}