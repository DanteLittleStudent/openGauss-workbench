package com.gbase.admin.framework.interceptor.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.benmanes.caffeine.cache.Cache;
import com.gbase.admin.common.annotation.RepeatSubmit;
import com.gbase.admin.common.constant.Constants;
import com.gbase.admin.common.filter.RepeatedlyRequestWrapper;
import com.gbase.admin.common.utils.StringUtils;
import com.gbase.admin.common.utils.http.HttpHelper;
import com.gbase.admin.framework.interceptor.BaseRepeatSubmitInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Prevent Duplicate Data Submission
 *
 * @author xielibo
 */
@Component
public class SameUrlDataInterceptor extends BaseRepeatSubmitInterceptor {
    public final String REPEAT_PARAMS = "repeatParams";

    public final String REPEAT_TIME = "repeatTime";

    @Value("${token.header}")
    private String header;

    @Autowired
    private Cache<String, Object> caffeineCache;

    @SuppressWarnings("unchecked")
    @Override
    public boolean isRepeatSubmit(HttpServletRequest request, RepeatSubmit annotation) {
        String nowParams = "";
        if (request instanceof RepeatedlyRequestWrapper) {
            RepeatedlyRequestWrapper repeatedlyRequest = (RepeatedlyRequestWrapper) request;
            nowParams = HttpHelper.getBodyString(repeatedlyRequest);
        }

        if (StringUtils.isEmpty(nowParams)) {
            nowParams = JSONObject.toJSONString(request.getParameterMap());
        }
        Map<String, Object> nowDataMap = new HashMap<String, Object>();
        nowDataMap.put(REPEAT_PARAMS, nowParams);
        nowDataMap.put(REPEAT_TIME, System.currentTimeMillis());

        String url = request.getRequestURI();

        String submitKey = request.getHeader(header);
        if (StringUtils.isEmpty(submitKey)) {
            submitKey = url;
        }

        String cacheRepeatKey = Constants.REPEAT_SUBMIT_KEY + submitKey;
        Object sessionObj = caffeineCache.asMap().get(cacheRepeatKey);
        if (sessionObj != null) {
            Map<String, Object> sessionMap = (Map<String, Object>) sessionObj;
            if (sessionMap.containsKey(url)) {
                Map<String, Object> preDataMap = (Map<String, Object>) sessionMap.get(url);
                if (compareParams(nowDataMap, preDataMap) && compareTime(nowDataMap, preDataMap, annotation.interval())) {
                    return true;
                }
            }
        }
        Map<String, Object> cacheMap = new HashMap<String, Object>();
        cacheMap.put(url, nowDataMap);
        caffeineCache.put(cacheRepeatKey, cacheMap);
        return false;
    }

    /**
     * Determine whether the parameters are the same
     */
    private boolean compareParams(Map<String, Object> nowMap, Map<String, Object> preMap) {
        String nowParams = (String) nowMap.get(REPEAT_PARAMS);
        String preParams = (String) preMap.get(REPEAT_PARAMS);
        return nowParams.equals(preParams);
    }

    /**
     * Determine the interval between two requests
     */
    private boolean compareTime(Map<String, Object> nowMap, Map<String, Object> preMap, int interval) {
        long time1 = (Long) nowMap.get(REPEAT_TIME);
        long time2 = (Long) preMap.get(REPEAT_TIME);
        if ((time1 - time2) < interval) {
            return true;
        }
        return false;
    }
}
