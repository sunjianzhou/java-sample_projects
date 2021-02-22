package net.xdclass.sp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Video {

    private int id;

    private String title;

    // 这里chapters和videoMap只是为了演示List和Map的注入方式。
    private List<String> chapters;

    private Map<Integer, String> videoMap;

    public Video(String title) {
        this.title = title;
        System.out.println("Current title: " + title);
    }

    public void init() {
        System.out.println("Video类的初始化操作。");
    }

    public void destroy() {
        System.out.println("Video类的销毁操作。");
    }

    public Video() {
        System.out.println("Video 的无参构造。");
    }
}
