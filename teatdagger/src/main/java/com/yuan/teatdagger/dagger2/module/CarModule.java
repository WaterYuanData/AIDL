package com.yuan.teatdagger.dagger2.module;

import com.yuan.teatdagger.dagger2.Car;

import dagger.Module;
import dagger.Provides;

@Module
public class CarModule {
    @Provides
    static Car provideCar() {
        return new Car();
    }
}
