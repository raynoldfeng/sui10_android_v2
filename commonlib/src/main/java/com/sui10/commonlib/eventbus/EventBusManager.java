package com.sui10.commonlib.eventbus;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.util.AsyncExecutor;

public class EventBusManager {

    private static AsyncExecutor asyncExecutor = null;
    /**
     * 注册EventBus
     * @param object 订阅者:上下文
     */
    public static void register(Object object) {
        try {
            if (!EventBus.getDefault().isRegistered(object)) {
                EventBus.getDefault().register(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 反注册EventBus
     * @param object 订阅者:上下文
     */
    public static void unregister(Object object) {
        try {
            if (EventBus.getDefault().isRegistered(object)) {
                EventBus.getDefault().unregister(object);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 取消事件的分发,这个方法必须在onEvent方法中才起作用
     * @param event
     */
    public static void cancelEventDelivery(Object event) {
        EventBus.getDefault().cancelEventDelivery(event);
    }

    /**
     * 发布一个粘性事件,同类型的event只会保留最后一次post的值
     * @param event
     */
    public static void postSticky(Object event) {
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 发布事件,首先检测这个事件是否有订阅者,如果没有则不发布事件
     * @param event
     */
    public static void post(Object event) {
        if (EventBus.getDefault().hasSubscriberForEvent(event.getClass())) {
            EventBus.getDefault().post(event);
        }
    }

    /**
     * 根据事件类型获取粘性事件
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getStickyEvent(Class clazz) {
        return (T) EventBus.getDefault().getStickyEvent(clazz);
    }

    /**
     * 根据事件的具体对象移除粘性事件
     * @param object 具体的事件对象,这个方法一般在onEvent...中执行
     */
    public static void removeStickyEventByObject(Object object) {
        EventBus.getDefault().removeStickyEvent(object);
    }

    /**
     * 根据事件的类型移除粘性事件
     * @param clazz 事件的类型,这个方法可以在任何地方调用
     */
    public static void removeStickyEventByClass(Class clazz) {
        EventBus.getDefault().removeStickyEvent(clazz);
    }

    /**
     * 移除所有的粘性事件
     */
    public static void removeAllStickyEvents() {
        EventBus.getDefault().removeAllStickyEvents();
    }

    /**
     * 使用AsyncExecutor去执行任务
     * @param runnableEx 继承自RunnableEx的任务类
     */
    public static void execute(AsyncExecutor.RunnableEx runnableEx) {
        if (asyncExecutor == null) {
            asyncExecutor = AsyncExecutor.create();
        }
        asyncExecutor.execute(runnableEx);
    }


}
