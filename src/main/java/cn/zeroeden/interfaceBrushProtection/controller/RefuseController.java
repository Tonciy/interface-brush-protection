package cn.zeroeden.interfaceBrushProtection.controller;

import cn.zeroeden.interfaceBrushProtection.constant.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author: Zero
 * @time: 2023/2/14
 * @description:
 */

@RestController
@RequestMapping("/refuse")
@Slf4j
public class RefuseController {

    @GetMapping("/get")
    public Result get(){
        log.info("执行【refuse】-get()方法");
        return Result.SUCCESS();
    }
    @PostMapping("/post")
    public Result post(){
        log.info("执行【refuse】-post()方法");
        return Result.SUCCESS();
    }

    @PutMapping("/put")
    public Result put(){
        log.info("执行【refuse】-put()方法");
        return Result.SUCCESS();
    }

    @DeleteMapping("/delete")
    public Result delete(){
        log.info("执行【refuse】-delete()方法");
        return Result.SUCCESS();
    }
}
