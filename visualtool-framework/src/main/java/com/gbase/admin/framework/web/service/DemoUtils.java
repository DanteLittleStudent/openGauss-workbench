package com.gbase.admin.framework.web.service;

import com.gitee.starblues.spring.extract.ExtractCoordinate;
import com.gitee.starblues.spring.extract.ExtractFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


@Component
public class DemoUtils {
    @Autowired
    private ExtractFactory extractFactory;

    public <T> T invoke(ExtractCoordinate coordinate, Class targetClazz, String methodName, Object[] param){
        Object target  = extractFactory.getExtractByCoordinate(coordinate);
        if(null != target){
            try {
                Method method = target.getClass().getMethod(methodName, String.class);
                return (T) method.invoke(target, param);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}