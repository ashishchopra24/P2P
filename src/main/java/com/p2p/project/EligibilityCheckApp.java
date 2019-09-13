package com.p2p.project;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EligibilityCheckApp {
    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

        InitialContext initialContext=new InitialContext();
        Queue requestQueue=(Queue) initialContext.lookup("queue/inboundQueue");

        try(ActiveMQConnectionFactory cf=new ActiveMQConnectionFactory();
            JMSContext jmsContext=cf.createContext())
        {
            JMSConsumer consumer=jmsContext.createConsumer(requestQueue);
            consumer.setMessageListener(new EligibilityCheckListener());
            Thread.sleep(10000);
        }
    }
}
