package com.mouse.moon.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Mahone Wu on 2017/4/11.
 */
public class TopicMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);

    public void onMessage(Message message) {
        System.out.println(message);
        TextMessage tm = (TextMessage)message;
        System.out.println(tm);
        try{
            logger.info("TopicMessageListener获取到的数据是:{}",tm.getText());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
