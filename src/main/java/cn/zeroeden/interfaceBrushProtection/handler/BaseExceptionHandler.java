package cn.zeroeden.interfaceBrushProtection.handler;


import cn.zeroeden.interfaceBrushProtection.constant.Result;
import cn.zeroeden.interfaceBrushProtection.constant.ResultCode;
import cn.zeroeden.interfaceBrushProtection.exception.CommonException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: Zero
 * @time: 2023/2/14
 * @description: 统一异常处理
 */
@RestControllerAdvice
public class BaseExceptionHandler {

    /**
     * 通用自定义异常捕获(登录状态/权限验证、接口防刷等)
     *
     * @return
     */
    @ExceptionHandler(value = CommonException.class)
    public Result commonException(CommonException exception) {
//        if (exception.getMessage() != null && exception.getMessage().equals(ResultCode.REQUEST_PARARMETER_MISS.message())) {
//            // 请求参数缺失
//            return new Result(ResultCode.REQUEST_PARARMETER_MISS);
//        }
//        if (exception.getMessage() != null && exception.getMessage().equals(ResultCode.UNAUTHENTICATED.message())) {
//            // 未登录/token非法
//            return new Result(ResultCode.UNAUTHENTICATED);
//        }
//        if (exception.getMessage() != null && exception.getMessage().equals(ResultCode.TOKEN_LOSE_EFFICACY.message())) {
//            // 登录凭证token已经失效
//            return new Result(ResultCode.TOKEN_LOSE_EFFICACY);
//        }
//        if (exception.getMessage() != null && exception.getMessage().equals(ResultCode.UNAUTHORISE.message())) {
//            // 访问权限不足
//            return new Result(ResultCode.UNAUTHORISE);
//        }
//        if (exception.getMessage() != null && exception.getMessage().equals(ResultCode.REQUEST_METHOD_NOT_SUPPORT.message())) {
//            // 不支持的请求方法类型
//            return new Result(ResultCode.REQUEST_METHOD_NOT_SUPPORT);
//        }
        if(exception.getMessage() != null && exception.getMessage().equals(ResultCode.ACCESS_FREQUENT.message())){
            // 访问过于频繁
            return new Result(ResultCode.ACCESS_FREQUENT);
        }
        if (exception.getMessage() != null) {
            // 给定异常信息
            return new Result(10001, exception.getMessage(), false);
        }
        // 请求失败
        return new Result(ResultCode.FAIL);
    }


    /**
     * 服务器异常统一返回
     *
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public Result error() {
        return new Result(ResultCode.SERVER_ERROR);
    }
}
