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
            JMSContext jmsContext=cf.createContext("eligibilityuser","eligibilitypass"))
        {
            JMSConsumer consumer1=jmsContext.createConsumer(requestQueue);
            JMSConsumer consumer2=jmsContext.createConsumer(requestQueue);

            for(int i=1;i<=10;i=i+2)
            {
                System.out.println("Consumer 1 "+consumer1.receive());
                System.out.println("Consumer 2 "+consumer2.receive());
            }

        }
    }
}
