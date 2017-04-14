package amq;

import com.mouse.moon.message.topic.TopicProvider;
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

/**
 * Created by Mahone Wu on 2017/4/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class SpringJmsTopicTest {


    private Logger logger = LoggerFactory.getLogger(SpringJmsTopicTest.class);

    @Resource
    private TopicProvider topicProvider;

    @Resource
    private Destination topicDestination;

    @Before
    public void before(){
        logger.info("开始时间{}",System.currentTimeMillis());
    }

    @After
    public void after(){
        logger.info("结束时间{}",System.currentTimeMillis());
    }


    @Test
    public void testProduceTopic(){
        topicProvider.publish(topicDestination,"话题来了");
    }

}
