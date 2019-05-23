package com.yuan.teatdagger;

import com.yuan.teatdagger.dagger2.Man;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void dagger() {
        System.out.println("验证car是否已注入:" + new Man().car.toString());
    }
}