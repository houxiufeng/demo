package com.example.demo.event;

import com.example.demo.po.User;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class UserEvent extends ApplicationEvent {

    private User user;
    private String opt;

    public UserEvent(Object source, User user, String opt) {
        super(source);
        this.user = user;
        this.opt = opt;
    }

}
