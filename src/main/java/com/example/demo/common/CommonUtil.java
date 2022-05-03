package com.example.demo.common;

public class CommonUtil {

    public static int error() {
        return 1/0;
    }

    public static void bizException() {
        throw new BizException("just test biz exception");
    }
}
