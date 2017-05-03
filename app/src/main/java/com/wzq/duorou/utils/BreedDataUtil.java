package com.wzq.duorou.utils;

import android.content.Context;

import com.wzq.duorou.R;
import com.wzq.duorou.beans.Breed;
import com.wzq.duorou.mvp.modle.BreedDao;
import com.wzq.duorou.mvp.modle.BreedDaoImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ggyuer on 2017/5/2.
 */

public class BreedDataUtil {

    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public static void savaBreeds(){
        BreedDao dao = new BreedDaoImpl();
        dao.saveBreedList(initData());
    }

    public static List<Breed> initData(){
        List<Breed> breeds = new ArrayList<>();
        Breed breed0 = new Breed(0,"多肉如此美丽，让我为她如此着迷！",
                                "http://image.drzj.net/data/attachment/portal/201703/22/234216xly9nmm8oedeglml.jpg",
                                "2017-3-29",23,"一诺","江苏启东",
                                "file:///android_asset/index0.html", R.drawable.index0);
        Breed breed1 = new Breed(1,"我的多肉卡斯特，相信每个人心中都想有个卡斯特庄园！",
                                "http://image.drzj.net/data/attachment/portal/201703/16/121911hhcy9yh6zfihrft1.jpg",
                                "2017-3-29",290,"柳絮轻风","苏州太仓",
                                "file:///android_asset/index1.html",R.drawable.index1);
        Breed breed2 = new Breed(2,"多肉植物唐印的逆袭！",
                                "http://image.drzj.net/data/attachment/portal/201703/16/120758w4ekcc5z2ncsnssx.jpg",
                                "2017-3-28",90,"小C同学","深圳",
                                "file:///android_asset/index2.html",R.drawable.index2);
        Breed breed3 = new Breed(3,"湖北大武汉的多肉励志记录！",
                                "http://image.drzj.net/data/attachment/portal/201703/15/081453nxex6xxbbbxid4i5.jpg",
                                "2017-3-28",83,"阿九","湖北武汉",
                                "file:///android_asset/index3.html",R.drawable.index3);
        Breed breed4 = new Breed(4,"我的第4个多肉小棚，离高大上的多肉阳光房还远吗？",
                                "http://image.drzj.net/data/attachment/portal/201703/10/092550njcnnna11y1w8ony.jpg",
                                "2017-3-28",213,"多肉迷@倩倩","湖北省应城市",
                                "file:///android_asset/index4.html",R.drawable.index4);
        Breed breed5 = new Breed(5,"逆向开挂区的多肉，照样也能色彩斑斓！",
                                "http://image.drzj.net/data/attachment/portal/201703/08/122944h9fbjbqvvsfj2xlx.jpg",
                                "2017-3-27",113,"一只独立行走的猪","福建厦门",
                                "file:///android_asset/index5.html",R.drawable.index5);
        Breed breed6 = new Breed(6,"小萌肉上盆记，简单粗暴有内涵！",
                                "http://image.drzj.net/data/attachment/portal/201703/08/122944h9fbjbqvvsfj2xlx.jpg",
                                "2017-3-27",323,"一只独立行走的猪","福建厦门",
                                "file:///android_asset/index6.html",R.drawable.index6);
        Breed breed7 = new Breed(7,"养肉不到一年，露养了一千多盆是种怎样的体验？",
                                "http://image.drzj.net/data/attachment/portal/201703/07/094655hkbi0u9ucgz3cbdi.jpg",
                                "2017-3-26",63,"婷小婷","广西南宁",
                                "file:///android_asset/index7.html",R.drawable.index7);
        Breed breed8 = new Breed(8,"时间整形师又出马了，喜欢励志贴的花友们别错过~",
                                "http://image.drzj.net/data/attachment/portal/201703/03/105513gfuhr5pee4w5ofrw.jpg",
                                "2017-3-26",41,"ccy804","江西南昌",
                                "file:///android_asset/index8.html",R.drawable.index8);
        Breed breed9 = new Breed(9,"多肉阳台，家中最美的风景！",
                                "http://image.drzj.net/data/attachment/portal/201703/01/113241bi3zm84zbb6xjo5c.jpg",
                                "2017-3-25",76,"海边的石头妈咪","山东青岛",
                                "file:///android_asset/index9.html",R.drawable.index9);

        breeds.add(breed0);
        breeds.add(breed1);
        breeds.add(breed2);
        breeds.add(breed3);
        breeds.add(breed4);
        breeds.add(breed5);
        breeds.add(breed6);
        breeds.add(breed7);
        breeds.add(breed8);
        breeds.add(breed9);
        return breeds;
    }
}
