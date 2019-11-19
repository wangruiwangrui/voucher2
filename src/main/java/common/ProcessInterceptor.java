package common;

import cn.hutool.http.HttpUtil;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lz
 * @description
 * @date 2019/6/24
 */
public class ProcessInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //允许任何跨域请求
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

        //httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,content-type,Content-Length, Authorization, Accept,X-Requested-With,X-Token");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        //
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE,OPTIONS");

        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        //httpServletResponse.setHeader("X-Powered-By","Jetty");

        //请求类型
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(200);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}