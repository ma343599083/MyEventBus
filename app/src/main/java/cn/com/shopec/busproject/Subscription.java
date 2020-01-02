package cn.com.shopec.busproject;

public class Subscription {

    private Object subscriber;
    private SubscirbeMethod subscriberMethod;

    public Subscription(Object subscriber, SubscirbeMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }

    public Object getSubscriber() {
        return subscriber;
    }

    public SubscirbeMethod getSubscriberMethod() {
        return subscriberMethod;
    }

}
