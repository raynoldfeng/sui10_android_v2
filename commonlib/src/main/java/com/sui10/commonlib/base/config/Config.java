package com.sui10.commonlib.base.config;

//TODO...待完善，需要添加localbuild等判断
public class Config<T> {
    public final T value;
    public final T test;
    public final T prod;


    public Config(T prod, T test) {
        this.test = test;
        this.prod = prod;

        if(BuildHelper.isTest())
            this.value = test;
        else
            this.value = prod;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
