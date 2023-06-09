package com.gbase.admin.common.filter;

import com.gbase.admin.common.utils.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Filters to prevent XSS attacks
 *
 * @author xielibo
 */
public class XssAttacksFilter implements Filter {

    public List<String> excludes = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String excludesArrStr = filterConfig.getInitParameter("excludes");
        if (StringUtils.isNotEmpty(excludesArrStr)) {
            String[] urlArr = excludesArrStr.split(",");
            for (int i = 0; urlArr != null && i < urlArr.length; i++) {
                excludes.add(urlArr[i]);
            }
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (doExcludeUrlHandler(req)) {
            chain.doFilter(request, response);
            return;
        }
        XssAttacksHttpServletRequestWrapper xssWrapper = new XssAttacksHttpServletRequestWrapper((HttpServletRequest) request);
        chain.doFilter(xssWrapper, response);
    }

    private boolean doExcludeUrlHandler(HttpServletRequest request) {
        String url = request.getServletPath();
        String method = request.getMethod();
        if (method == null || method.matches("GET") || method.matches("DELETE")) {
            return true;
        }
        return StringUtils.matches(url, excludes);
    }
}