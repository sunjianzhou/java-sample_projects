package net.xdclass.sp;

import net.xdclass.sp.domain.Video;
import net.xdclass.sp.domain.VideoOrder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String args[]){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        testScopeSingleTon(applicationContext);
        System.out.println("================");
        testScopePrototype(applicationContext);
        System.out.println("================");
        testWithConstruct(applicationContext);
        System.out.println("================");
        testInjectCollection(applicationContext);
    }

    public static void testScopeSingleTon(ApplicationContext context) {
        Video video1 = (Video)context.getBean("video");
        Video video2 = (Video)context.getBean("video");
        System.out.println(video1.getTitle());
        System.out.println(video2.getTitle());
        // 因为没有对对象重写其equals方法，所以直接用等号判断的话即判断的两个对象的地址。
        // 如果地址一致，则表示属于一个对象。这里主要测试Bean属性里scope为singleton的。
        System.out.println(video1 == video2);
    }

    public static void testScopePrototype(ApplicationContext context) {
        VideoOrder videoOrder1 = (VideoOrder)context.getBean("videoOrder");
        VideoOrder videoOrder2 = (VideoOrder)context.getBean("videoOrder");
        System.out.println(videoOrder1.getVideo().getTitle());
        System.out.println(videoOrder2.getVideo().getTitle());
        // 因为没有对对象重写其equals方法，所以直接用等号判断的话即判断的两个对象的地址。
        // 如果地址一致，则表示属于一个对象。这里主要测试Bean属性里scope为Prototype的。
        System.out.println(videoOrder1 != videoOrder2);
    }

    public static void testWithConstruct(ApplicationContext context) {
        Video video = (Video)context.getBean("videoWithConstruct");
        System.out.println(video.getTitle());
    }

    public static void testInjectCollection(ApplicationContext context) {
        Video video = (Video)context.getBean("videoMapAndList");
        System.out.println(video.getChapters().get(2)); // List中第三个值
        System.out.println(video.getVideoMap().get(2)); // ValueTwo
    }
}
