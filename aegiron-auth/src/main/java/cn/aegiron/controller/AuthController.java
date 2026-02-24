package cn.aegiron.controller;

import cn.aegiron.api.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panda
 * @version 1.0
 * @ClassName AuthController.java
 * @Description
 * @since 2026/2/24 13:22
 */
@RestController
@RequestMapping("/auth")
@Tag(name = "认证中心", description = "认证登录接口")
@RequiredArgsConstructor
public class AuthController {

    @Operation(summary = "登录接口")
    @PostMapping("/login")
    public CommonResult<String> login() {
        return CommonResult.success("登录成功");
    }
}
