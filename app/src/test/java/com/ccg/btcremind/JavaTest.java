package com.ccg.btcremind;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author : C4_雍和
 * 描述 :
 * 主要功能 :
 * 维护人员 : C4_雍和
 * date : 20-4-25 下午8:46
 */
public class JavaTest {
    ArrayList allPrice = new ArrayList<Double>();


    @Test
    public void addition_isCorrect() {
        double currentPrice = Double.valueOf("40.5");

        int siz = allPrice.size();
        if (siz == 0) {
            //如果总数据是空的，把最新得到的数据添加进总数据中
            System.out.println("250:    " + currentPrice);
            allPrice.add(currentPrice);
        } else {
            //剩余个数
            int shengyu = siz % 100;
            //天数
            int tianshu = siz / 100;
            double minPrice = (double) Collections.min(allPrice);
            if (currentPrice <= minPrice) {
                System.out.println("" + (tianshu + 1) + "天内最低价已经达到,当前价格是： " + currentPrice);
            } else {
                sendInfo(allPrice.subList(shengyu, allPrice.size()), currentPrice, tianshu );
            }
            //把新数据添加到总数据中
            if(allPrice.size()>=500){
                allPrice.remove(0);
                allPrice.add(currentPrice);
            }
        }
    }


    public void sendInfo(List<Double> allPrice, double currentPrice, int tianshu) {
        if (tianshu > 0) {
            if (currentPrice <= Collections.min(allPrice)) {
                System.out.println("" + tianshu + "天内最低价已经达到,当前价格是： " + currentPrice);
                return;
            }
            sendInfo(allPrice.subList(100, allPrice.size()), currentPrice, tianshu - 1);
        } else {
            return;
        }

    }
    @Before
    public void  sdfs(){
        Random random = new Random();
        for (int i = 0; i < 430; i++) {
            int a = random.nextInt(10000) + 1;
            allPrice.add(Double.valueOf(a));
        }
    }
}
