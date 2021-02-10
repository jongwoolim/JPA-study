package me.jongwoo.springdata;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;

public class PostListener implements ApplicationListener<PostPublishedEvent> {

    //리스너 클래스를 만들고 싶지 않으면 빈등록에서 구현해주면 된다..

    //@EventListener 빈으로 등록 후에 야너테이션으로 가능
    @Override
    public void onApplicationEvent(PostPublishedEvent event) {

        System.out.println("-------------");
        System.out.println(event.getPost().getTitle()+" is published!1");
        System.out.println("-------------");
    }

}
