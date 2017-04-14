package com.mouse.moon.service.impl;

import com.mouse.moon.service.QueueConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

/**
 * Created by Mahone Wu on 2017/4/11.
 */
@Service
public class QueueConsumerServiceImpl implements QueueConsumerService {

    private Logger logger = LoggerFactory.getLogger(QueueConsumerServiceImpl.class);

    @Resource
    private  JmsTemplate jmsTemplate;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void receive(Destination destination) throws  Exception {

        TextMessage tm = (TextMessage)jmsTemplate.receive(destination);
        logger.info("{}",destination.toString());
        logger.info("从{},接收到消息{}",destination.toString(),tm.getText());
    }

    /**
     * 按照名称接收
     * @param destinationName
     */
    public void receive(String destinationName) {
        TextMessage tm = (TextMessage)jmsTemplate.receive(destinationName);
        logger.info("{}",destinationName);
        try{
            logger.info("根据名字接收到的消息{}",tm.getText());
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
