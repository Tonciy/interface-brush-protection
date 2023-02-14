package cn.zeroeden.interfaceBrushProtection.constant;


import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Zero
 * @time: 2023/2/14
 * @description: 统一返回结果封装
 */

@Data
@NoArgsConstructor
public class Result {

    /**
     * 是否成功
     */

    private boolean success;
    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回信息
     */
    private Object data;

    public Result(ResultCode code) {
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
    }

    public Result(ResultCode code, Object data) {
        this.success = code.success();
        this.code = code.code();
        this.message = code.message();
        this.data = data;
    }

    public Result(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }

    public static Result SUCCESS() {
        return new Result(ResultCode.SUCCESS);
    }
    public static Result SUCCESS(Object data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result ERROR() {
        return new Result(ResultCode.SERVER_ERROR);
    }
    public static Result ERROR(Object data) {
        return new Result(ResultCode.SERVER_ERROR, data);
    }

    public static Result FAIL() {
        return new Result(ResultCode.FAIL);
    }
}
