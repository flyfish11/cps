package com.cloud.oauth.controller;


import com.cloud.common.constants.CommonConstants;
import com.cloud.common.utils.R;
import com.cloud.oauth.utils.ImageUtil;
import com.google.code.kaptcha.Producer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 验证码控制器
 *
 * @author CaiRui
 * @date 2018-12-10 12:13
 */


@RestController
public class VerifyCodeController
{
    @Autowired
    private Producer captchaProducer;

    @Autowired
    private StringRedisTemplate redisTemplate;



    /**
     * 登录图片验证码
     *
     * @param session
     * @return
     */
    @GetMapping("/getVerifyImage")
    public void getVerifyImage(HttpServletRequest request, HttpServletResponse res, HttpSession session) throws IOException {

        res.setContentType("image/jpeg");
        //禁止图像缓存
        res.setHeader("Pragma","no-cache");
        res.setHeader("Cache-Control", "no-cache");
        res.setDateHeader("Expires", 0);
        ImageUtil imageUtil = new ImageUtil(120, 40, 5,80);
        session.setAttribute("code", imageUtil.getCode());
        imageUtil.write(res.getOutputStream());

    }

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/captcha-image")
    public void getKaptchaImage(HttpServletRequest request,
                                        HttpServletResponse response) throws Exception {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        redisTemplate.opsForValue().set(CommonConstants.KAPTCHA_CODE,capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    /**
     * 校验验证码
     *
     * @param code
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/verifyCode")
    public R verifyCode(@RequestParam("code") String code) throws Exception {
        if(StringUtils.isBlank(code)){
            redisTemplate.opsForValue().set(CommonConstants.KAPTCHA_CODE,"");
            return R.failed("验证码不能为空");
        }
        String redisCode = redisTemplate.opsForValue().get(CommonConstants.KAPTCHA_CODE);
        if(StringUtils.equalsIgnoreCase(code,redisCode)){
            redisTemplate.opsForValue().set(CommonConstants.KAPTCHA_CODE,"");
            return R.ok().setMsg("验证码验证成功");
        }else{
            redisTemplate.opsForValue().set(CommonConstants.KAPTCHA_CODE,"");
            return R.failed("验证码错误");
        }


    }


    @GetMapping("/getCheckCode")//该方法为验证码值的获取方法
    public Object getCheckCode(HttpSession session) {
        return session.getAttribute("checkCode");
    }

}



