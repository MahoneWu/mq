package com.mouse.moon.message.topic;

import com.mouse.moon.message.listener.QueueMessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by xn066797 on 2017/4/11.
 */
@Service
public class TopicProvider {

    private Logger logger = LoggerFactory.getLogger(TopicProvider.class);

    @Resource
    @Qualifier("topicJmsTemplate")
    private JmsTemplate topicJmsTemplate;

    public void publish(final Destination topic, final String msg){
        topicJmsTemplate.send(topic, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                logger.info("话题内容{},消息内容{}",topic.toString(),msg);
                return session.createTextMessage(msg);
            }
        });
    }

}
