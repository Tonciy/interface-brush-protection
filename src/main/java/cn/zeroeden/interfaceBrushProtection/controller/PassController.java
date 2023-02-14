package cn.zeroeden.interfaceBrushProtection.controller;

import cn.zeroeden.interfaceBrushProtection.constant.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Zero
 * @time: 2023/2/14
 * @description:
 */

@RestController
@RequestMapping("/pass")
@Slf4j
public class PassController {

    @GetMapping("/get")
    public Result get(){
        log.info("执行【pass】-get()方法");
        return Result.SUCCESS();
    }
}
