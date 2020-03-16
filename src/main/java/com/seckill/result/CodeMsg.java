package com.seckill.result;

/**
 * Created by Albert on 2020/3/8.
 */
public class CodeMsg {
    private int code;
    private String message;

    public static CodeMsg SUCCESS_MSG = new CodeMsg(0, "success");
    public static CodeMsg ERROR_MSG = new CodeMsg(500100, "server error");

    //login
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空！");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空！");
    public static CodeMsg MOBILE_FORMAT_ERROR = new CodeMsg(500213, "请输入正确的手机号码！");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "查询不到该用户！");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误！");
    public static CodeMsg SESSION_ERROR = new CodeMsg(500216, "session不存在或已失效！");

    //exception
    public static CodeMsg BIND_EXCEPTION = new CodeMsg(501200, "bind exception");

    //seckill
    public static CodeMsg SECKILL_EMPTY = new CodeMsg(500500, "秒杀已经结束。");
    public static CodeMsg SECKILL_DUPLICATED = new CodeMsg(500501, "不能重复秒杀");

    //user
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500600, "用户不存在");

    //order
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500700, "订单不存在");

    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String msg = String.format(this.message, args);
        return new CodeMsg(code, msg);
    }
}
