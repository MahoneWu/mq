package amq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.jms.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Mahone Wu on 2017/4/12.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-v.xml")
public class TopicTest {

    private Logger logger = LoggerFactory.getLogger(SpringJmsTopicTest.class);

    ActiveMQConnectionFactory factoryA;

    Session session;


    @Before
    public void init(){
        try{
            factoryA = getAMQConnectionFactory();
            ActiveMQConnection conn = (ActiveMQConnection) factoryA.createConnection();
            conn.start();
            session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void testNormalTopic(){
        try {
            ActiveMQTopic queue = new ActiveMQTopic(getNormalTopicName());
            MessageConsumer consumer1 = session.createConsumer(queue);
            MessageConsumer consumer2 = session.createConsumer(queue);
            final AtomicInteger count = new AtomicInteger(0);
            MessageListener listenerA = new MessageListener() {
                public void onMessage(javax.jms.Message message) {

                    try{
                        int index =count.incrementAndGet();
                        logger.info("index={}---------->receive from {},消息={}",index,getNormalTopicName(),((TextMessage)message).getText());
                        Thread.sleep(10L);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            consumer1.setMessageListener(listenerA);
            consumer2.setMessageListener(listenerA);

            MessageProducer producer = session.createProducer(new ActiveMQTopic(getNormalTopicName()));
            int index = 0;
            while (index++ < 10) {
             //   logger.info("{},{}",index < 100,index + " message.");
                TextMessage message = session.createTextMessage(index
                        + " message.");
                producer.send(message);
                Thread.sleep(5L);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            System.in.read();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



    @Test
    public void testNormalVirtualTopic(){
        try{
            Queue queue = new ActiveMQQueue(getVirtualTopicConsumerName());
            MessageConsumer consumer1 = session.createConsumer(queue);
            MessageConsumer consumer2 = session.createConsumer(queue);
            final AtomicInteger count = new AtomicInteger(0);

            MessageListener listenerA = new MessageListener() {
                public void onMessage(javax.jms.Message message) {
                    try {
                        int index = count.getAndIncrement();
                        logger.info("index={}---------->receive from {},消息={}", index, getNormalTopicName(), ((TextMessage) message).getText());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            };
            consumer1.setMessageListener(listenerA);
            consumer2.setMessageListener(listenerA);
            MessageProducer producer = session.createProducer(new ActiveMQTopic(getNormalVTopicName()));
            int index = 0;
            while (index++ < 10) {
                TextMessage message = session.createTextMessage(index
                        + " message.");
                producer.send(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void testVirtualTopic(){
        try {
           // ActiveMQTopic queue = new ActiveMQTopic(getVirtualTopicConsumerNameA());
            Queue queue = new ActiveMQQueue(getVirtualTopicConsumerNameA());
            MessageConsumer consumer1 = session.createConsumer(queue);
            MessageConsumer consumer2 = session.createConsumer(queue);
            MessageConsumer consumer3 = session.createConsumer(new ActiveMQQueue(getVirtualTopicConsumerNameB()));

            final AtomicInteger countA = new AtomicInteger(0);
            MessageListener listenerA = new MessageListener() {
                public void onMessage(javax.jms.Message message) {
                    try {
                        int index = countA.getAndIncrement();
                        logger.info("A index={}---------->receive from {},消息={}", index, getNormalTopicName(), ((TextMessage) message).getText());
                    }catch (Exception e){
                        logger.error(""+e);
                        e.printStackTrace();
                    }
                }
            };
            consumer1.setMessageListener(listenerA);
            consumer2.setMessageListener(listenerA);
            final AtomicInteger countB = new AtomicInteger(0);
            MessageListener listenerB = new MessageListener() {
                public void onMessage(javax.jms.Message message) {
                    try {
                        int index = countB.getAndIncrement();
                        logger.info("B index={}---------->receive from {},消息={}", index, getNormalTopicName(), ((TextMessage) message).getText());
                    }catch (Exception e){
                        e.printStackTrace();
                        logger.error(""+e);
                    }
                }
            };
            consumer3.setMessageListener(listenerB);
            MessageProducer producer = session.createProducer(new ActiveMQTopic(getVirtualTopicName()));
            int index = 0;
            while (index++ < 10) {
                TextMessage message = session.createTextMessage(index
                        + " message.");
                producer.send(message);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private ActiveMQConnectionFactory getAMQConnectionFactory(){
        return new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
    }

    private static String getNormalTopicName(){
        return "normal.TEST";
    }

    private static String getNormalVTopicName(){
        return "VirtualTopic.NORMAL";
    }

    private static String getVirtualTopicName(){
        return "VirtualTopic.TEST";
    }

    private static String getVirtualTopicConsumerName(){
        return "Consumer.normal.VirtualTopic.NORMAL";
    }

    private static String getVirtualTopicConsumerNameA(){
        return "Consumer.A.VirtualTopic.TEST";
    }

    private static String getVirtualTopicConsumerNameB(){
        return "Consumer.B.VirtualTopic.TEST";
    }

}
