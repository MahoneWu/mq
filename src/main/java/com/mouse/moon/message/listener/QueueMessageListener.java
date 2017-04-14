package com.mouse.moon.message.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Mahone Wu on 2017/4/11.
 */
public class QueueMessageListener implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(QueueMessageListener.class);

    public void onMessage(Message message) {
        TextMessage tm = (TextMessage)message;
        try{
            logger.info("QueueMessageListener 收到消息{}",tm.getText());
        }catch (JMSException e){
            logger.error("监听的bug"+e);
            e.printStackTrace();
        }
    }
}
