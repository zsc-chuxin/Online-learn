package com.example.backend.controller;


import com.example.backend.entity.Captcha;
import com.example.backend.entity.Result;
import com.example.backend.service.CaptchaService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "http://localhost:5173",allowCredentials = "true")
public class CaptchaController {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Autowired
    private CaptchaService captchaService;

    /*--------------获取验证码-------------*/
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {

        // 定义response输出类型为image/jpeg
        response.setDateHeader("Expires", 0);
        // 设置http标准
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET,POST");
        // 设置请求头
        response.addHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        // 响应返回的是image/jpeg类型
        response.setContentType("image/jpeg");

        /*--------------生成验证码-------------*/
        String code = defaultKaptcha.createText();  // 获取验证码文本内容

        // 获取session
        HttpSession session = request.getSession(true);

        // 将验证码文本内容放入session
        session.setAttribute("captcha", code);

        // 根据文本验证码内容创建图形验证码
        BufferedImage image = defaultKaptcha.createImage(code);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 输出流输出文件格式为jpg
            ImageIO.write(image, "jpg", outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*--------------验证验证码-------------*/
    @PostMapping(value = "/verifyCaptcha")
    public Result verifyCaptcha(@RequestParam("captcha") String userCaptcha, HttpServletRequest request) {

        // 从session中获取生成的验证码
        HttpSession session = request.getSession();
        if (session == null) {
            return Result.error("请先获取验证码");
        }
        String generatedCaptcha = (String) session.getAttribute("captcha");

        // 比较用户输入的验证码和生成的验证码
        if (generatedCaptcha != null && generatedCaptcha.equalsIgnoreCase(userCaptcha)) {
            // 验证成功，清除session中的验证码
            session.removeAttribute("captcha");
            return Result.success();
        } else {
            // 验证失败
            return Result.error("验证失败");
        }
    }

    @PostMapping("/generateSliderCaptcha")
    public Result captcha(@RequestBody Captcha captcha) {
        return Result.success(captchaService.getCaptcha(captcha));
    }

}
