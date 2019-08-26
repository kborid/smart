package com.kborid.library.util;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @Title ReflectUtil
 * @Description 反射工具类
 */

public class ReflectUtil {

    /**
     * 判断类是否存在
     *
     * @param className
     * @return
     */
    public static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 反射调用静态方法
     *
     * @param className
     * @param methodName
     * @param paramTypes
     * @param paramValues
     * @return
     */
    public static Object invokeStaticMethod(String className, String methodName, Class[] paramTypes, Object[] paramValues) {
        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(null, paramValues);
        } catch (Exception e) {
            Logger.e(e, "反射调用方法出错");
        }
        return null;
    }

    /**
     * 反射调用非静态方法
     *
     * @param className
     * @param methodName
     * @param paramTypes
     * @param paramValues
     * @return
     */
    public static Object invokeMethod(String className, String methodName, Class[] paramTypes, Object[] paramValues) {
        try {
            Class clazz = Class.forName(className);
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(obj, paramValues);
        } catch (Exception e) {
            Logger.e(e, "反射调用方法出错");
        }
        return null;
    }

    /**
     * 反射实例化对象
     *
     * @param className
     * @param paramType
     * @param param
     * @param <T>
     * @return
     */
    public static <T> T newInstance(String className, Class paramType, Object param) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
            Constructor<?> cons = clazz.getDeclaredConstructor(paramType);
            if (cons == null) {
                Logger.w("新建实例失败(%s)，没有指定构造函数(%s)", className, paramType);
                return null;
            }
            return (T) cons.newInstance(param);
        } catch (Exception e) {
            Logger.e(e, "新建实例失败(%s)", className);
            return null;
        }
    }
}
