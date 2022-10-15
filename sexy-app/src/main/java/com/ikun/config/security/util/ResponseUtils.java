package com.ikun.config.security.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ikun.reponse.ResponseEntity;
import com.ikun.result.Result;
import com.ikun.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletResponse;
import java.io.PrintWriter;

@Slf4j
public class ResponseUtils {
    public static void out(ServletResponse response, ResponseEntity result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    public static void out2(ServletResponse response, Result result) {
        PrintWriter out = null;
        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            out = response.getWriter();
            out.println(JSON.toJSONString(result,SerializerFeature.WriteMapNullValue));
        } catch (Exception e) {
            log.error(e + "输出JSON出错");
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }
}
