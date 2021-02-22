package com.example.demoproject.intercepter;

import com.example.demoproject.domain.User;
import com.example.demoproject.service.impl.UserServiceImpl;
import com.example.demoproject.utils.JsonData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Write description here.
 *
 * @author sunjianzhou
 * @date 2021/1/27 19:23
 */
public class LoginIntercepter implements HandlerInterceptor {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("拦截器，preHandle。");

        String token = request.getHeader("token");

        if (StringUtils.isEmpty(token)){
            token = request.getParameter("token");
        }

        if (!StringUtils.isEmpty(token)) {
            // 判断token是否合法。
            User user = UserServiceImpl.sessionMap.get(token);
            if (null != user){
                return true;
            }else {
                JsonData jsonData = JsonData.buildError(-2,"无效的Token");
                String jsonStr = objectMapper.writeValueAsString(jsonData);
                renderJson(response, jsonStr);
                return false;
            }
        }else {
            JsonData jsonData = JsonData.buildError(-3,"Token为空。");
            String jsonStr = objectMapper.writeValueAsString(jsonData);
            renderJson(response, jsonStr);
            return false;
        }

//        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("拦截器，postHandle。");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("拦截器，afterCompletion。");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
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
}
