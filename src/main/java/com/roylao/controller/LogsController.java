package com.roylao.controller;

import com.roylao.common.Result;
import com.roylao.service.LogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class LogsController {

    @Autowired
    LogsService logsService;

    @RequestMapping("/")
    public String index() {
        return "Hello Spring Boot 2.0!";
    }

    @RequestMapping(value = "list")
    public Result list() {
        try {
            log.info("LogsController -- list");
            return logsService.List();
        } catch (Exception e) {
            log.error("upload file failed", e);
        }
        return Result.ok();
    }

}