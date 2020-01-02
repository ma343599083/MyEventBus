package cn.com.shopec.busproject;

import java.lang.reflect.Method;

public class SubscirbeMethod {
    String label;
    Method method;
    Class<?>[] paramsTypes;

    public SubscirbeMethod() {
    }

    public SubscirbeMethod(String label, Method method, Class<?>[] paramsTypes) {
        this.label = label;
        this.method = method;
        this.paramsTypes = paramsTypes;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Class<?>[] getParamsTypes() {
        return paramsTypes;
    }

    public void setParamsTypes(Class<?>[] paramsTypes) {
        this.paramsTypes = paramsTypes;
    }
}
