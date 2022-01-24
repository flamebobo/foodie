package com.flame.controller.interceptor;

import com.flame.utils.FlameJSONResult;
import com.flame.utils.JsonUtils;
import com.flame.utils.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 *<p>Title:</p>
 *<p>Description:</p>
 *<p>Copyright: Copyright (c) 2021</p>
 *<p>Company：PCCW</p>
 *
 *@description:
 *@author: Flame.Lai
 *@version 1.0
 *@time: 2021/8/12 15:47
 *
 */
@Slf4j
public class UserTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisOperator redisOperator;

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    /**
     * 拦截请求，在访问controller之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String userId = request.getHeader("headerUserId");
        String userToken = request.getHeader("headerUserToken");
        if (StringUtils.isNotBlank(userId) && StringUtils.isNotBlank(userToken)){
            String uniqueToken = redisOperator.get(REDIS_USER_TOKEN + ":" +userId);
            if (StringUtils.isBlank(uniqueToken)) {
//                log.info("请登录...");
                returnErrorResponse(response, FlameJSONResult.errorMsg("请登录..."));
                return false;
            }
            if (!uniqueToken.equals(userToken)) {
//                log.info("账号在异地登录...");
                returnErrorResponse(response, FlameJSONResult.errorMsg("账号在异地登录..."));
                return false;
            }
        } else {
            returnErrorResponse(response, FlameJSONResult.errorMsg("请登录..."));
//            log.info("请登录...");
            return false;
        }

        /**
         * false：请求被拦截，被驳回，验证出现问题
         * true：请求在经过验证校验以后，是ok的，是可以放行的
         *
         */
        return true;
    }

    /**
     * 请求访问controller之后，渲染视图之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 请求访问controller之后，渲染视图之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

    public void returnErrorResponse(HttpServletResponse response, FlameJSONResult result){
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            out.write(JsonUtils.objectToJson(result).getBytes(StandardCharsets.UTF_8));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
