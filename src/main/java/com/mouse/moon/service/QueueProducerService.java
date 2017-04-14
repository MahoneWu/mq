package com.mouse.moon.service;

import javax.jms.Destination;

/**
 * Created by Mahone Wu on 2017/4/11.
 */
public interface QueueProducerService {

    /**向某目的地发送消息*/
    void sendMessage(Destination destination, final String msg);

    /**发送消息*/
    void sendMessage(final String msg);

    void sendMessage(String destinationName, final String msg);
}
