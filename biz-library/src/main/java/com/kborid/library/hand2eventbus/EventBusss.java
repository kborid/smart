package com.kborid.library.hand2eventbus;

import android.os.Looper;

import com.kborid.library.util.LogUtils;
import com.orhanobut.logger.Logger;
import com.thunisoft.common.tool.MultiTaskHandler;
import com.thunisoft.common.tool.UIHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventBusss {

    private static Map<Object, List<SubscribeMethod>> cacheMap;

    private static EventBusss instance = new EventBusss();

    private EventBusss() {
        cacheMap = new HashMap<>();
    }

    public static EventBusss getDefault() {
        return instance;
    }

    public void register(Object object) {
        if (!cacheMap.containsKey(object)) {
            List<SubscribeMethod> list = findSubscribeMethods(object);
            cacheMap.put(object, list);
        }
    }

    private List<SubscribeMethod> findSubscribeMethods(Object object) {
        List<SubscribeMethod> list = new ArrayList<>();
        Class clazz = object.getClass();
        while (clazz != null) {
            String className = clazz.getName();
            if (className.startsWith("java.") || className.startsWith("javax.") || className.startsWith("android.")) {
                break;
            }

            Method[] method = clazz.getDeclaredMethods();
            for (Method m : method) {
                Subscribe subscribe = m.getAnnotation(Subscribe.class);
                if (subscribe == null) {
                    continue;
                }
                Class[] paramTypes = m.getParameterTypes();
                if (paramTypes.length != 1) {
                    Logger.t("==>eventbus").d("参数列表数>1");
                } else {
                    SubscribeMethod subscribeMethod = new SubscribeMethod(m, subscribe.threadMode(), paramTypes[0]);
                    list.add(subscribeMethod);
                }
            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    public void unRegister(Object object) {
        cacheMap.remove(object);
    }

    public void post(final Object typeParams) {
        Set<Object> set = cacheMap.keySet();
        for (final Object obj : set) {
            final Class<?> type = typeParams.getClass();
            List<SubscribeMethod> list = cacheMap.get(obj);
            for (final SubscribeMethod subscribeMethod : list) {
                if (subscribeMethod.getType().isAssignableFrom(type)) {
                    switch (subscribeMethod.getThreadMode()) {
                        case MAIN:
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                invoke(subscribeMethod.getMethod(), obj, typeParams);
                            } else {
                                UIHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        invoke(subscribeMethod.getMethod(), obj, typeParams);
                                    }
                                });
                            }
                            break;
                        case BACKGROUND:
                            MultiTaskHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    invoke(subscribeMethod.getMethod(), obj, typeParams);
                                }
                            });
                            break;
                    }
                }
            }
        }
    }

    private void invoke(Method method, Object object, Object type) {
        try {
            method.invoke(object, type);
        } catch (Exception e) {
            LogUtils.e("EventBusss", "反射调用失败", e);
        }
    }
}
