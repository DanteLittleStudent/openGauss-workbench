package com.gbase.admin.common.core.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gbase.admin.common.constant.SqlConstants;
import com.gbase.admin.common.core.domain.AjaxResult;
import com.gbase.admin.common.core.domain.model.LoginUser;
import com.gbase.admin.common.core.page.PageDomain;
import com.gbase.admin.common.core.page.TableDataInfo;
import com.gbase.admin.common.core.page.TableSupport;
import com.gbase.admin.common.enums.ResponseCode;
import com.gbase.admin.common.utils.DateUtils;
import com.gbase.admin.common.utils.SecurityUtils;
import com.gbase.admin.common.utils.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * base controller
 *
 * @author xielibo
 */
public class BaseController {

    /**
     * Automatically convert the date format string passed from the front desk to Date type
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * Set request pagination params
     */
    protected Page startPage() {
        Page page = new Page<>();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        String orderBy = pageDomain.getOrderBy();
        String orderColumn = pageDomain.getOrderByColumn();
        String isAsc = pageDomain.getIsAsc();

        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize)) {
            page.setCurrent(pageNum);
            page.setSize(pageSize);
            page.setOptimizeCountSql(false);
            page.setMaxLimit(500L);
        }

        if (StringUtils.isNotBlank(orderColumn)) {
            if (SqlConstants.ASC.equalsIgnoreCase(isAsc)) {
                page.addOrder(OrderItem.asc(orderColumn));
            } else {
                page.addOrder(OrderItem.desc(orderColumn));
            }
        }

        return page;
    }

    /**
     * Set request pagination params
     */
    protected Page startPage(Integer pageNum, Integer size, String orderColumn, String order) {
        Page page = new Page();
        page.setCurrent(pageNum);
        page.setSize(size);
        page.setOptimizeCountSql(false);
        page.setMaxLimit(500L);

        if (StringUtils.isNotBlank(orderColumn)) {
            if (SqlConstants.ASC.equalsIgnoreCase(order)) {
                page.addOrder(OrderItem.asc(orderColumn));
            } else {
                page.addOrder(OrderItem.desc(orderColumn));
            }
        }
        return page;
    }

    /**
     * Set response pagination params
     *
     * @param page
     */
    protected TableDataInfo getDataTable(IPage<?> page) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(ResponseCode.SUCCESS.code());
        rspData.setMsg(ResponseCode.SUCCESS.msg());
        rspData.setRows(page.getRecords());
        rspData.setTotal(page.getTotal());
        return rspData;
    }

    /**
     * response result
     */
    protected AjaxResult toAjax(int rows) {
        return rows > 0 ? AjaxResult.success() : AjaxResult.error();
    }

    /**
     * response result
     */
    protected AjaxResult toAjax(boolean result) {
        return result ? success() : error();
    }

    /**
     * response success result
     */
    public AjaxResult success() {
        return AjaxResult.success();
    }

    /**
     * response error result
     */
    public AjaxResult error() {
        return AjaxResult.error();
    }

    /**
     * response success result
     */
    public AjaxResult success(String message) {
        return AjaxResult.success(message);
    }

    /**
     * response error result
     */
    public AjaxResult error(String message) {
        return AjaxResult.error(message);
    }


    /**
     * Get the currently logged in user
     */
    public LoginUser getLoginUser() {
        return SecurityUtils.getLoginUser();
    }

    /**
     * Get the currently logged in userId
     */
    public Integer getUserId() {
        return getLoginUser().getUser().getUserId();
    }

    /**
     * Get the currently logged in username
     */
    public String getUsername() {
        return getLoginUser().getUsername();
    }
}
