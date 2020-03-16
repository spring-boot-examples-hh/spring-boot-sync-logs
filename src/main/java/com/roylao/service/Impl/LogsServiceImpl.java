package com.roylao.service.Impl;


import com.roylao.common.Result;
import com.roylao.service.LogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component(value = "logsService")
@Slf4j
public class LogsServiceImpl implements LogsService {


    @Override
    public Result List() {
        log.info("list logs:"+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return Result.ok("success");
    }
}
