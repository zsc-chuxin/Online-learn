package com.example.backend.controller;

import com.example.backend.utils.JwtUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
@Validated
@CrossOrigin
public class TokenController {

    @PostMapping("/validateToken")
    public boolean validateToken(@RequestParam String token) {
        boolean isvalid = JwtUtil.verifyToken(token);
        return isvalid;
    }
}

