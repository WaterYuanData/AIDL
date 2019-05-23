package com.yuan.teatdagger.dagger2.module;

import com.yuan.teatdagger.dagger2.Car;

import javax.inject.Inject;

public class Woman {
    @Inject
    Car car;

    public Woman() {
        DaggerWomanComponent.create().injectWoman(this);
    }

}
