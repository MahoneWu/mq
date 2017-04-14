package com.mouse.moon.service;

import javax.jms.Destination;

/**
 * Created by Mahone Wu on 2017/4/11.
 */
public interface QueueConsumerService {

     void receive(Destination destination) throws  Exception;

     void receive(String destinationName);

}
