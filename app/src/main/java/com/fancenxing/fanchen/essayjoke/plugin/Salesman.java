package com.fancenxing.fanchen.essayjoke.plugin;

/**
 * 功能描述：
 * Created by 孙中宛 on 2018/5/31.
 */

public class Salesman implements IBank{

    private IBank man;

    public Salesman(IBank man) {
        this.man = man;
    }

    @Override
    public void applyBank() {
        System.out.print("处理流程");
        man.applyBank();
        System.out.print("办理完毕");
        throw new RuntimeException("处理成功");
    }
}
