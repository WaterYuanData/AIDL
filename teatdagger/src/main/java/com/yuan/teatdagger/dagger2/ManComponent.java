package com.yuan.teatdagger.dagger2;

import dagger.Component;

@Component
public interface ManComponent {
    void injectMan(Man man);  // 注入 man 所需要的依赖
}
