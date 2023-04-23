package com.gbase.admin.common.annotation;


import com.gbase.admin.common.enums.BusinessType;
import com.gbase.admin.common.enums.OperatorType;

import java.lang.annotation.*;

/**
 * log annotation
 *
 * @author xielibo
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * title
     */
    public String title() default "";

    /**
     * businessType
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * operator type
     */
    public OperatorType operatorType() default OperatorType.OTHER;

    /**
     * is save request data
     */
    public boolean isSaveRequestData() default true;

    /**
     * is save response data
     */
    public boolean isSaveResponseData() default true;
}
