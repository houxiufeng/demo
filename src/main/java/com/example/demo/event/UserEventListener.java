package com.example.demo.event;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.example.demo.config.KafkaConfiguration;
import com.example.demo.service.ESUserService;
import com.example.demo.service.KafkaService;
import com.example.demo.vo.ESUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {

    private static Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    @Autowired
    private KafkaService kafkaService;
    @Autowired
    private KafkaConfiguration kafkaConfiguration;
    @Autowired
    private ESUserService esUserService;

    @EventListener
    public void method1(UserEvent userEvent) {
        //do something.
        kafkaService.send(kafkaConfiguration.getMyTopic1(), JSONUtil.toJsonStr(userEvent.getUser()));
        ESUserInfo esUserInfo = Convert.convert(ESUserInfo.class, userEvent.getUser());
        if (esUserInfo != null && "add".equals(userEvent.getOpt())) {
            esUserService.save(esUserInfo);
        }
        logger.info("{} user {}", userEvent.getOpt(), userEvent.getUser());

    }

}
