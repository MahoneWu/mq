package com.mouse.moon.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by Mahone Wu on 2017/4/11.
 */
@Component
public class QueueMessageListenerAnno extends MessageListenerAdapter {

    private Logger logger = LoggerFactory.getLogger(QueueMessageListenerAnno.class);

    @JmsListener(destination = "mouse-one",concurrency = "5-10")
    public void onMessageMahone(Message message, Session session){
        TextMessage tm = (TextMessage)message;
        try{
            logger.info("注解QueueMessageListenerAnno 收到消息{}",tm.getText());
        }catch (JMSException e){
            logger.error("监听的bug"+e);
            e.printStackTrace();
        }
    }


}
