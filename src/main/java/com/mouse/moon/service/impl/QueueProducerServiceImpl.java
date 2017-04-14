package com.mouse.moon.service.impl;

import com.mouse.moon.service.QueueProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by Mahone Wu on 2017/4/11.
 */
@Service
public class QueueProducerServiceImpl implements QueueProducerService {

    private Logger logger = LoggerFactory.getLogger(QueueProducerServiceImpl.class);

    @Resource
    private JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * 向指定的目的发送消息
     * @param destination
     * @param msg
     */
    public void sendMessage(Destination destination, final String msg) {
        logger.info("向"+destination.toString()+"发送了消息"+msg);
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    /**
     * 发送消息
     * @param msg
     */
    public void sendMessage(final String msg) {
       String destination = jmsTemplate.getDefaultDestinationName();
        logger.info("向{0}，发送消息{1}",destination,msg);
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }

    /**
     * 按照名称推送
     * @param destinationName
     * @param msg
     */
    public void sendMessage(String destinationName, final String msg) {
        jmsTemplate.send(destinationName, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(msg);
            }
        });
    }
}
