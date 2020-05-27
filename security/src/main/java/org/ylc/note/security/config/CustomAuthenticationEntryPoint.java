package org.ylc.note.security.config;

import cn.hutool.json.JSONUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.ylc.note.security.common.HttpResult;
import org.ylc.note.security.constant.ConfigConstants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 认证失败，Token错误或者没有，在这里处理结果，返回 JSON，默认返回Html
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/27
 */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=UTF-8");
        HttpResult<String> result = new HttpResult<>(ConfigConstants.Return.AUTHENTICATION_FAIL, "认证失败", null);
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }

}
