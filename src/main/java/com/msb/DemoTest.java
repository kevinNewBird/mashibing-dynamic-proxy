package com.msb;

/**
 * description  DemoTest <BR>
 * <p>
 * author: zhao.song
 * date: created in 11:10  2022/9/30
 * company: TRS信息技术有限公司
 * version 1.0
 */
public class DemoTest {
    private Object delegate;

    public static void main(String[] args) {
        DemoTest instance = new DemoTest();
        Object parent =instance.delegate;
        instance.delegate = new Object();
        System.out.println(parent);
        System.out.println(instance.delegate);

        instance.delegate = parent;
        System.out.println(instance.delegate);
    }
}
