package org.ylc.note.security.config;

import cn.hutool.json.JSONUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.ylc.note.security.common.HttpResult;
import org.ylc.note.security.constant.ConfigConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 代码千万行，注释第一行，
 * 注释不规范，同事泪两行。
 * <p>
 * 权限不足，无权访问
 *
 * @author YuLc
 * @version 1.0.0
 * @date 2020/5/19
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        HttpResult<String> result = new HttpResult<>(ConfigConstants.Return.ACCESS_RESTRICTED, "没有操作权限", null);
        response.getWriter().write(JSONUtil.toJsonStr(result));
    }
}
