package amq;

import com.mouse.moon.service.QueueConsumerService;
import com.mouse.moon.service.QueueProducerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.util.Date;
import java.util.Random;

/**
 * Created by Mahone Wu on 2017/4/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SpringJmsQueueTest {

    private Logger logger = LoggerFactory.getLogger(SpringJmsQueueTest.class);

    @Resource
    private QueueProducerService producerService;

    @Resource
    private QueueConsumerService consumerService;

    @Resource
    private Destination queueDestination;

    @Resource
    private Destination queueDestination2;



    @Before
    public void init(){
        logger.info("开始时间{}",System.currentTimeMillis());
    }

    @Test
    public void testProduce(){
        for(int i = 0;i < 5; i++){
            producerService.sendMessage(queueDestination,"测试消息发送了哈哈哈哈哈哈哈" + i+new Date()+new Random().nextInt(100));
        }
       // producerService.sendMessage(queueDestination,"测试消息发送了哈哈哈哈哈哈哈" );
    }

    @Test
    public void testConsumer() throws  Exception{
        consumerService.receive(queueDestination);
    }


    @Test
    public void testListener(){
        for(int i = 0;i < 5; i++){
            producerService.sendMessage(queueDestination2,"测试消息发送了哈哈哈哈哈哈哈" + i);
        }
    }

    @Test
    public void testConsumer2() throws  Exception{
        consumerService.receive(queueDestination2);
    }






    @After
    public void end(){
        logger.info("结束时间{}",System.currentTimeMillis());
    }

}
