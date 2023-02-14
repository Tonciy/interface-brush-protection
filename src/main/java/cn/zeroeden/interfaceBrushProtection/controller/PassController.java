package cn.zeroeden.interfaceBrushProtection.controller;

import cn.zeroeden.interfaceBrushProtection.annotation.AccessLimit;
import cn.zeroeden.interfaceBrushProtection.constant.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
//    表示此接口 3 秒内最大访问次数为 2，禁用时长为 40 秒
    @AccessLimit(second = 3, maxTime = 2, forbiddenTime = 40L)
    public Result get(){
        log.info("执行【pass】-get()方法");
        return Result.SUCCESS();
    }
    @PostMapping("/post")
    //    表示此接口 20 秒内最大访问次数为 5，禁用时长为 60 秒
    @AccessLimit(second = 20, maxTime = 5, forbiddenTime = 60L)
    public Result post(){
        log.info("执行【pass】-post()方法");
        return Result.SUCCESS();
    }

    @PutMapping("/put")
    public Result put(){
        log.info("执行【pass】-put()方法");
        return Result.SUCCESS();
    }

    @DeleteMapping("/delete")
    public Result delete(){
        log.info("执行【pass】-delete()方法");
        return Result.SUCCESS();
    }
}
