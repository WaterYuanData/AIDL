package com.yuan.teatdagger.dagger2.module;

import dagger.Component;

@Component(modules = CarModule.class)
public interface WomanComponent {
    void injectWoman(Woman woman);  // 注入 man 所需要的依赖
}
