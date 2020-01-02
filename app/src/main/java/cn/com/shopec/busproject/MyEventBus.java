package cn.com.shopec.busproject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MyEventBus {

    private static final MyEventBus ourInstance = new MyEventBus();

    private Map<Class, List<SubscirbeMethod>> CACHE_MAP = new HashMap<Class, List<SubscirbeMethod>>();

    private Map<String, List<Subscription>> SUBSCRIBES = new HashMap<String, List<Subscription>>();

    private MyEventBus() {
    }

    public static MyEventBus getInstance() {
        return ourInstance;
    }

    public void register(Object obj) {
        Class<?> objClass = obj.getClass();
        cacheMethodMap(objClass);
        generateExecuteMap(obj);

    }

    /**
     * 生成执行表
     */
    private void generateExecuteMap(Object obj) {
        List<SubscirbeMethod> subscirbeMethods = CACHE_MAP.get(obj.getClass());
        if (subscirbeMethods != null) {
            for (SubscirbeMethod subscirbeMethod : subscirbeMethods) {
                List<Subscription> subscriptions = SUBSCRIBES.get(subscirbeMethod.getLabel());
                if (subscriptions == null) {
                    subscriptions = new ArrayList<>();
                }
                subscriptions.add(new Subscription(obj, subscirbeMethod));
                SUBSCRIBES.put(subscirbeMethod.getLabel(),subscriptions);
            }
        }
    }

    /**
     * 遍历这个要注册的类中所有的方法，找到Subscribe
     * 将这个方法的一些信息封装成SubscirbeMethod存入缓存表
     */
    private void cacheMethodMap(Class objClass) {
        List<SubscirbeMethod> subscirbeMethods = CACHE_MAP.get(objClass);
        if (subscirbeMethods == null) {
            subscirbeMethods = new ArrayList<>();
        }

        Method[] declaredMethods = objClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            Subscribe subscribe = declaredMethod.getAnnotation(Subscribe.class);
            if (subscribe != null) {
                String[] labels = subscribe.value();
                for (String label : labels) {
                    SubscirbeMethod subscirbeMethod = new SubscirbeMethod();
                    subscirbeMethod.setLabel(label);
                    subscirbeMethod.setParamsTypes(declaredMethod.getParameterTypes());
                    subscirbeMethod.setMethod(declaredMethod);
                    subscirbeMethods.add(subscirbeMethod);
                }
                CACHE_MAP.put(objClass, subscirbeMethods);
            }

        }
    }

    public void post(String label,Object... params){
        List<Subscription> subscriptions = SUBSCRIBES.get(label);
        if(subscriptions == null){
            return;
        }

        for (Subscription subscription : subscriptions) {
            Method method = subscription.getSubscriberMethod().getMethod();
            Class<?>[] parameterTypes = method.getParameterTypes();
            if(params.length != parameterTypes.length){
                continue;
            }
            boolean isMatch = true;
            for (int i = 0; i < params.length; i++) {
                if(!(parameterTypes[i].isInstance(params[i]))){
                    isMatch = false;
                    break;
                }
            }
            //如果方法参数匹配，执行调用
            if(isMatch){
                try {
                    method.invoke(subscription.getSubscriber(),params);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 反注册
     * @param subscriber
     */
    public void unregister(Object subscriber) {
        List<SubscirbeMethod> subscirbeMethods = CACHE_MAP.get(subscriber.getClass());
        if(subscirbeMethods != null){
            for (SubscirbeMethod subscirbeMethod : subscirbeMethods) {
                //从订阅表中删除对应的订阅者
                List<Subscription> subscriptions = SUBSCRIBES.get(subscirbeMethod.getLabel());
                if(subscirbeMethods != null){
                    Iterator<Subscription> iterator = subscriptions.iterator();
                    while (iterator.hasNext()){
                        Subscription subscription = iterator.next();
                        if(subscriber.equals(subscription.getSubscriber())){
                            iterator.remove();
                        }
                    }
                }
            }
        }
    }
}
