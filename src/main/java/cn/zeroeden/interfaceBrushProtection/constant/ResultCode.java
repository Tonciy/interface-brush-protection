package cn.zeroeden.interfaceBrushProtection.constant;

/**
 * 公共返回码
 */
public enum ResultCode {


    SUCCESS(true, 10000, "操作成功！"),
    //---系统错误返回码-----
    FAIL(false, 10001, "操作失败"),
    UNAUTHENTICATED(false, 10002, "您还未登录"),
    TOKEN_LOSE_EFFICACY(false, 10003, "登录凭证已失效!"),
    UNAUTHORISE(false, 10004, "权限不足"),

    /**
     * 登录失败异常
     */
    USERNAME_PASSWORD_ERROR(false, 20001, "用户名或者密码错误"),

    REQUEST_PARARMETER_MISS(false, 30000, "请求参数缺失"),
    /**
     * 请求类型不支持
     */
    REQUEST_METHOD_NOT_SUPPORT(false, 40000, "不支持的请求类型"),

    /**
     * 用户访问某个接口过于频繁--接口防刷
     */
    ACCESS_FREQUENT(false, 50001, "访问过于频繁"),
    SERVER_ERROR(false, 99999, "抱歉，系统繁忙，请稍后重试！");
    //---其他操作返回码----


    //操作是否成功
    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;

    ResultCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }
}
