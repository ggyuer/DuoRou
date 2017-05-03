package com.wzq.duorou.utils;

import com.wzq.duorou.Constant;
import com.wzq.duorou.beans.CircleItem;
import com.wzq.duorou.beans.CommentItem;
import com.wzq.duorou.beans.FavortItem;
import com.wzq.duorou.beans.PhotoInfo;
import com.wzq.duorou.beans.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author yiw
 * @ClassName: DatasUtil
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2015-12-28 下午4:16:21
 */
public class DatasUtil {
    public static final String[] CONTENTS = {"",
            "每一个人，都是独立存在不可复制的个体；都以独特的方式演绎着自己的人生。第一次接触到“萌肉”，还是在大学的青葱岁月；“吉娃娃”打破了以往对植物的认知，也许当时并不懂得什么叫珍惜，就这样把它遗忘在了别处。",
            "富士、凤凰、金星共同的母本是岩莲华Orostachys Iwarenge，也叫青凤凰、玄海岩、玄海岩莲华。它们共同的特征是都隶属景天科瓦松属 (Orostachys)， 叶片匙型或者半圆型(半圆的状态是她们状态最好的时候)，容易萌生侧芽（脑补子持莲华，是的，它们都是近亲），高温和低温都会进入休眠，秋季开花时叶盘向上抽出花序，开花后通常母株会死亡，为了不让植株死亡，可以在开花初期去除花穗。以珍惜度排名是金星、富士、凤凰、岩莲华。\n" +
                    "　　它们四者的区别在于叶色不同，富士是白覆轮品种，即叶片外缘白斑中间绿；凤凰是黄中斑品种，即中间黄斑外缘绿；而金星则是更罕见的黄覆轮，外缘黄斑中间绿，至于它们的母亲岩莲华，无锦斑，叶片全绿，也因此叫青凤凰。",
            "方鳞绿塔 Crassula pyramidalis Thunb 景天科青锁龙属多肉植物，原产地南非（小卡鲁，纳马夸兰），属微型多肉植物，高可达10-25cm，叶小4-8mm，薄、扁平呈三角状，亮绿色至褐绿色，两两对生，上下紧密堆叠，形成一个完美的尖塔形四边形柱，光照充足的情况下，植株顶端叶片会呈现紫红色。方鳞绿塔春天到夏天开花，聚伞花序的花序，小花白色，较不起眼，开花后会死亡。",
            //"只有http|https|ftp|svn://开头的网址才能识别为网址，正则表达式写的不太好，如果你又更好的正则表达式请评论告诉我，谢谢！",
            "上次说的太啰嗦，把联萌大叔给累坏了，这次我尽量不说话，光发图。肉肉都是我粗养的，相片都是手机拍的。普通的手机下个拍照软件，相机360，万能相机，POCO相机，最美自拍，美颜相机，反正哪个好用就用哪个了。",
            //"图不错",
            "我勒个去"};
    /*public static final String[] PHOTOS = {
            "http://f.hiphotos.baidu.com/image/pic/item/faf2b2119313b07e97f760d908d7912396dd8c9c.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/4b90f603738da977c76ab6fab451f8198718e39e.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/902397dda144ad343de8b756d4a20cf430ad858f.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/a6efce1b9d16fdfa0fbc1ebfb68f8c5495ee7b8b.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/a71ea8d3fd1f4134e61e0f90211f95cad1c85e36.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/7dd98d1001e939011b9c86d07fec54e737d19645.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/f11f3a292df5e0fecc3e83ef586034a85edf723d.jpg",
            "http://cdn.duitang.com/uploads/item/201309/17/20130917111400_CNmTr.thumb.224_0.png",
            "http://pica.nipic.com/2007-10-17/20071017111345564_2.jpg",
            "http://pic4.nipic.com/20091101/3672704_160309066949_2.jpg",
            "http://pic4.nipic.com/20091203/1295091_123813163959_2.jpg",
            "http://pic31.nipic.com/20130624/8821914_104949466000_2.jpg",
            "http://pic6.nipic.com/20100330/4592428_113348099353_2.jpg",
            "http://pic9.nipic.com/20100917/5653289_174356436608_2.jpg",
            "http://img10.3lian.com/sc6/show02/38/65/386515.jpg",
            "http://pic1.nipic.com/2008-12-09/200812910493588_2.jpg",
            "http://pic2.ooopic.com/11/79/98/31bOOOPICb1_1024.jpg" };*/
    public static final String[] HEADIMG = {
            "http://tupian.qqjay.com/tou3/2015/1114/2feb8010fdee8ca7fc54c1b1996adcbf.jpg",
            "http://www.feizl.com/upload2007/2014_06/1406272351394618.png",
            "http://v1.qzone.cc/avatar/201308/30/22/56/5220b2828a477072.jpg%21200x200.jpg",
            "http://v1.qzone.cc/avatar/201308/22/10/36/521579394f4bb419.jpg!200x200.jpg",
            "http://v1.qzone.cc/avatar/201408/20/17/23/53f468ff9c337550.jpg!200x200.jpg",
            "http://cdn.duitang.com/uploads/item/201408/13/20140813122725_8h8Yu.jpeg",
            "http://img.woyaogexing.com/touxiang/nv/20140212/9ac2117139f1ecd8%21200x200.jpg",
            "http://p1.qqyou.com/touxiang/uploadpic/2013-3/12/2013031212295986807.jpg"};

    public static List<User> users = new ArrayList<User>();
    public static List<PhotoInfo> PHOTOS = new ArrayList<>();
    /**
     * 动态id自增长
     */
    private static int circleId = 0;
    /**
     * 点赞id自增长
     */
    private static int favortId = 0;
    /**
     * 评论id自增长
     */
    private static int commentId = 0;
    public static final User curUser = new User("0", "自己", PreferenceManager.getInstance().getValueFromPreferences(Constant.USER_AVATAR, ""));

    static {
        User user1 = new User("1", "张三", HEADIMG[1]);
        User user2 = new User("2", "李四", HEADIMG[2]);
        User user3 = new User("3", "隔壁老王", HEADIMG[3]);
        User user4 = new User("4", "赵六", HEADIMG[4]);
        User user5 = new User("5", "田七", HEADIMG[5]);
        User user6 = new User("6", "Naoki", HEADIMG[6]);
        User user7 = new User("7", "这个名字是不是很长，哈哈！因为我是用来测试换行的", HEADIMG[7]);

        users.add(curUser);
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);


        PhotoInfo p1 = new PhotoInfo();
        p1.url = "http://www.drlmeng.com/wp-content/uploads/2016/07/tujian2.jpg";
        p1.w = 640;
        p1.h = 792;

        PhotoInfo p2 = new PhotoInfo();
        p2.url = "http://www.drlmeng.com/wp-content/uploads/2016/06/xiangji4.jpg";
        p2.w = 640;
        p2.h = 792;

        PhotoInfo p3 = new PhotoInfo();
        p3.url = "http://www.drlmeng.com/wp-content/uploads/2016/06/xiangji12.jpg";
        p3.w = 950;
        p3.h = 597;

        PhotoInfo p4 = new PhotoInfo();
        p4.url = "http://www.drlmeng.com/wp-content/uploads/2016/06/xiangji7.jpg";
        p4.w = 533;
        p4.h = 800;

        PhotoInfo p5 = new PhotoInfo();
        p5.url = "http://www.drlmeng.com/wp-content/uploads/2016/06/xiangji24.jpg";
        p5.w = 700;
        p5.h = 467;

        PhotoInfo p6 = new PhotoInfo();
        p6.url = "http://www.drlmeng.com/wp-content/uploads/2016/06/xiangji14.jpg";
        p6.w = 700;
        p6.h = 467;

        PhotoInfo p7 = new PhotoInfo();
        p7.url = "http://www.drlmeng.com/wp-content/uploads/2016/06/xiangji17.jpg";
        p7.w = 1024;
        p7.h = 640;

        PhotoInfo p8 = new PhotoInfo();
        p8.url = "http://www.drlmeng.com/wp-content/uploads/2016/06/xiangji21.jpg";
        p8.w = 1024;
        p8.h = 768;

        PhotoInfo p9 = new PhotoInfo();
        p9.url = "http://www.drlmeng.com/wp-content/uploads/2016/02/pyramidalis2.jpg";
        p9.w = 1024;
        p9.h = 640;

        PhotoInfo p10 = new PhotoInfo();
        p10.url = "http://www.drlmeng.com/wp-content/uploads/2016/01/sanzhe.jpg";
        p10.w = 1024;
        p10.h = 768;

        PHOTOS.add(p1);
        PHOTOS.add(p2);
        PHOTOS.add(p3);
        PHOTOS.add(p4);
        PHOTOS.add(p5);
        PHOTOS.add(p6);
        PHOTOS.add(p7);
        PHOTOS.add(p8);
        PHOTOS.add(p9);
        PHOTOS.add(p10);
    }

    public static List<CircleItem> createCircleDatas() {
        List<CircleItem> circleDatas = new ArrayList<CircleItem>();
        for (int i = 0; i < 15; i++) {
            CircleItem item = new CircleItem();
            User user = getUser();
            item.setId(String.valueOf(circleId++));
            item.setUser(user);
            item.setContent(getContent());
            item.setCreateTime("12月24日");

            item.setFavorters(createFavortItemList());
            item.setComments(createCommentItemList());
            int type = getRandomNum(10) % 2;
            if (type == 0) {
                item.setType("1");// 链接
                item.setLinkImg("http://www.drlmeng.com/wp-content/uploads/2016/01/fushi.jpg");
                item.setLinkTitle("爱多肉");
            } else if (type == 1) {
                item.setType("2");// 图片
                item.setPhotos(createPhotos());
            } else {
                item.setType("3");// 视频
                String videoUrl = "http://yiwcicledemo.s.qupai.me/v/80c81c19-7c02-4dee-baca-c97d9bbd6607.mp4";
                String videoImgUrl = "http://yiwcicledemo.s.qupai.me/v/80c81c19-7c02-4dee-baca-c97d9bbd6607.jpg";
                item.setVideoUrl(videoUrl);
                item.setVideoImgUrl(videoImgUrl);
            }
            circleDatas.add(item);
        }

        return circleDatas;
    }

    public static User getUser() {
        return users.get(getRandomNum(users.size()));
    }

    public static String getContent() {
        return CONTENTS[getRandomNum(CONTENTS.length)];
    }

    public static int getRandomNum(int max) {
        Random random = new Random();
        int result = random.nextInt(max);
        return result;
    }

    public static List<PhotoInfo> createPhotos() {
        List<PhotoInfo> photos = new ArrayList<PhotoInfo>();
        int size = getRandomNum(PHOTOS.size());
        if (size > 0) {
            if (size > 9) {
                size = 9;
            }
            for (int i = 0; i < size; i++) {
                PhotoInfo photo = PHOTOS.get(getRandomNum(PHOTOS.size()));
                if (!photos.contains(photo)) {
                    photos.add(photo);
                } else {
                    i--;
                }
            }
        }
        return photos;
    }

    public static List<FavortItem> createFavortItemList() {
        int size = getRandomNum(users.size());
        List<FavortItem> items = new ArrayList<FavortItem>();
        List<String> history = new ArrayList<String>();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                FavortItem newItem = createFavortItem();
                String userid = newItem.getUser().getId();
                if (!history.contains(userid)) {
                    items.add(newItem);
                    history.add(userid);
                } else {
                    i--;
                }
            }
        }
        return items;
    }

    public static FavortItem createFavortItem() {
        FavortItem item = new FavortItem();
        item.setId(String.valueOf(favortId++));
        item.setUser(getUser());
        return item;
    }

    public static FavortItem createCurUserFavortItem() {
        FavortItem item = new FavortItem();
        item.setId(String.valueOf(favortId++));
        item.setUser(curUser);
        return item;
    }

    public static List<CommentItem> createCommentItemList() {
        List<CommentItem> items = new ArrayList<CommentItem>();
        int size = getRandomNum(10);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                items.add(createComment());
            }
        }
        return items;
    }

    public static CommentItem createComment() {
        CommentItem item = new CommentItem();
        item.setId(String.valueOf(commentId++));
        item.setContent("哈哈");
        User user = getUser();
        item.setUser(user);
        if (getRandomNum(10) % 2 == 0) {
            while (true) {
                User replyUser = getUser();
                if (!user.getId().equals(replyUser.getId())) {
                    item.setToReplyUser(replyUser);
                    break;
                }
            }
        }
        return item;
    }

    /**
     * 创建发布评论
     *
     * @return
     */
    public static CommentItem createPublicComment(String content) {
        CommentItem item = new CommentItem();
        item.setId(String.valueOf(commentId++));
        item.setContent(content);
        item.setUser(curUser);
        return item;
    }

    /**
     * 创建回复评论
     *
     * @return
     */
    public static CommentItem createReplyComment(User replyUser, String content) {
        CommentItem item = new CommentItem();
        item.setId(String.valueOf(commentId++));
        item.setContent(content);
        item.setUser(curUser);
        item.setToReplyUser(replyUser);
        return item;
    }


    public static CircleItem createVideoItem(String videoUrl, String imgUrl) {
        CircleItem item = new CircleItem();
        item.setId(String.valueOf(circleId++));
        item.setUser(curUser);
        //item.setContent(getContent());
        item.setCreateTime("12月24日");

        //item.setFavorters(createFavortItemList());
        //item.setComments(createCommentItemList());
        item.setType("3");// 图片
        item.setVideoUrl(videoUrl);
        item.setVideoImgUrl(imgUrl);
        return item;
    }
}
