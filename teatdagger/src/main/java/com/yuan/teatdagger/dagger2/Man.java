package com.yuan.teatdagger.dagger2;

import javax.inject.Inject;

public class Man {
    @Inject
    public Car car;

    public Man() {
        DaggerManComponent.create().injectMan(this);
    }
}
