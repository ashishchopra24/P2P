package com.p2p.project;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.*;

public class ClinicalApp {
    public static void main(String[] args) throws NamingException, JMSException {

        InitialContext initialContext=new InitialContext();
        Queue requestQueue=(Queue) initialContext.lookup("queue/inboundQueue");
        Queue replyQueue=(Queue) initialContext.lookup("queue/outboundQueue");

        try(ActiveMQConnectionFactory cf=new ActiveMQConnectionFactory();
        JMSContext jmsContext=cf.createContext())
        {
            JMSProducer producer=jmsContext.createProducer();

            ObjectMessage msg=jmsContext.createObjectMessage();
            Patient patient=new Patient();
            patient.setId(1);
            patient.setName("Jack Ma");
            patient.setInsuranceProvider("Aditya Birla");
            patient.setCopay(30d);
            patient.setAmountToBePayed(1000d);
            msg.setObject(patient);

            for(int i=1;i<=10;i++)
            producer.send(requestQueue,msg);

            //JMSConsumer consumer=jmsContext.createConsumer(replyQueue);
            //MapMessage replyMsg=(MapMessage)consumer.receive(30000);
            //System.out.println("Patient eligibility is "+replyMsg.getBoolean("eligible"));



        }
    }
}
