package com.example.backend.service;

import cn.hutool.core.util.StrUtil;
import com.example.backend.entity.Captcha;
import com.example.backend.utils.CaptchaUtil;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class CaptchaService {
    /**
     * 拼图验证码允许偏差
     **/
    private static final Integer ALLOW_DEVIATION = 3;

    /**
     * 存储验证码的Map
     */
    private final Map<String, String> imageCodeMap = new HashMap<>();

    /**
     * 校验验证码
     * @param imageKey
     * @param imageCode
     * @return boolean
     **/
    public String checkImageCode(String imageKey, String imageCode) {
        String text = imageCodeMap.get("imageCode:" + imageKey);
        if (StrUtil.isBlank(text)) {
            return "验证码已失效";
        }
        // 根据移动距离判断验证是否成功
        if (Math.abs(Integer.parseInt(text) - Integer.parseInt(imageCode)) > ALLOW_DEVIATION) {
            return "验证失败，请控制拼图对齐缺口";
        }
        return null;
    }

    /**
     * 缓存验证码，有效期15分钟
     * @param key
     * @param code
     **/
    public void saveImageCode(String key, String code) {
        imageCodeMap.put("imageCode:" + key, code);
        // 设置过期时间
        new Thread(() -> {
            try {
                TimeUnit.MINUTES.sleep(15);
                imageCodeMap.remove("imageCode:" + key);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    /**
     * 获取验证码拼图（生成的抠图和带抠图阴影的大图及抠图坐标）
     **/
    public Object getCaptcha(Captcha captcha) {
        // 参数校验
        CaptchaUtil.checkCaptcha(captcha);
        // 获取画布的宽高
        int canvasWidth = captcha.getCanvasWidth();
        int canvasHeight = captcha.getCanvasHeight();
        // 获取阻塞块的宽高/半径
        int blockWidth = captcha.getBlockWidth();
        int blockHeight = captcha.getBlockHeight();
        int blockRadius = captcha.getBlockRadius();
        // 获取资源图
        BufferedImage canvasImage = CaptchaUtil.getBufferedImage(captcha.getPlace());
        // 调整原图到指定大小
        canvasImage = CaptchaUtil.imageResize(canvasImage, canvasWidth, canvasHeight);
        // 随机生成阻塞块坐标
        int blockX = CaptchaUtil.getNonceByRange(blockWidth, canvasWidth - blockWidth - 10);

        int blockY = CaptchaUtil.getNonceByRange(10, canvasHeight - blockHeight + 1);
        // 阻塞块
        BufferedImage blockImage = new BufferedImage(blockWidth, blockHeight, BufferedImage.TYPE_4BYTE_ABGR);
        // 新建的图像根据轮廓图颜色赋值，源图生成遮罩
        CaptchaUtil.cutByTemplate(canvasImage, blockImage, blockWidth, blockHeight, blockRadius, blockX, blockY);
        // 移动横坐标
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "");
        // 缓存
        saveImageCode(nonceStr, String.valueOf(blockX));
        // 设置返回参数
        captcha.setNonceStr(nonceStr);
        captcha.setBlockX(blockX);
        captcha.setBlockY(blockY);
        captcha.setBlockSrc(CaptchaUtil.toBase64(blockImage, "png"));
        captcha.setCanvasSrc(CaptchaUtil.toBase64(canvasImage, "png"));
        return captcha;
    }
}
