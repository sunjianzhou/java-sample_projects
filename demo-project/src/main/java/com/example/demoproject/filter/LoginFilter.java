package com.example.demoproject.filter;

import com.example.demoproject.domain.User;
import com.example.demoproject.service.impl.UserServiceImpl;
import com.example.demoproject.utils.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/27 14:50
 */
//@WebFilter(urlPatterns = "/api/v1/pri/*", filterName = "LoginFilter")  // 这里一注释就不再生效了。
public class LoginFilter implements Filter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 容器初始化。
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("===============Login filter Init.===============");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter doFilter.");

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = req.getHeader("token");

        if (StringUtils.isEmpty(token)){
            token = req.getParameter("token");
        }

        if (!StringUtils.isEmpty(token)) {
            // 判断token是否合法。
            User user = UserServiceImpl.sessionMap.get(token);
            if (null != user) {
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                JsonData jsonData = JsonData.buildError(-2,"无效的Token");
                String jsonStr = objectMapper.writeValueAsString(jsonData);
                renderJson(response, jsonStr);
            }
        }else {
            JsonData jsonData = JsonData.buildError(-3,"Token为空。");
            String jsonStr = objectMapper.writeValueAsString(jsonData);
            renderJson(response, jsonStr);
        }
    }

    private void renderJson(HttpServletResponse response, String json)  {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        try(PrintWriter writer = response.getWriter()){
            writer.print(json);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 容器销毁。
     */
    @Override
    public void destroy() {
        System.out.println("===============Login filter destroy.===============");
    }
}
