package com.cloud.common.utils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Classname JupiterPublicMethods
 * @Description <h1>数字验证码工具类</h1>
 * @Author yulj
 * @Date: 2020/04/22 10:29 上午
 */
public class JupiterPublicMethods {

    public JupiterPublicMethods() {
    }

    public static String CreateId() {
        Format format = new SimpleDateFormat("yyyyMMddHHmmss");
        int a = (int) (Math.random() * 10.0D);
        int b = (int) (Math.random() * 10.0D);
        int c = (int) (Math.random() * 10.0D);
        int d = (int) (Math.random() * 10.0D);
        String date = format.format(new Date());
        StringBuffer sd = new StringBuffer();
        sd.append(date).insert(a, b);
        sd.insert(c, d);
        String createdId = sd.toString();
        return createdId;
    }

    /**
     * 生成指定长度的数字验证码
     *
     * @param length
     * @return
     */
    public static String createCode(int length) {
        Random random = new Random();
        String validateCode = "";
        for (int i = 0; i < length; i++) {
            validateCode += random.nextInt(9);
        }
        return validateCode;
    }

}