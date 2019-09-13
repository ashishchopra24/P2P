package com.p2p.project;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EligibilityCheckListener implements MessageListener {


    @Override
    public void onMessage(Message message) {
        ObjectMessage objectMessage=(ObjectMessage)message;
        try (ActiveMQConnectionFactory cf=new ActiveMQConnectionFactory();
             JMSContext jmsContext=cf.createContext()){
            InitialContext initialContext=new InitialContext();
            Queue replyQueue=(Queue)initialContext.lookup("queue/outboundQueue");
            MapMessage mapMessage=jmsContext.createMapMessage();
            Patient patient=(Patient)objectMessage.getObject();
            String insuranceProvider=patient.getInsuranceProvider();
            if(insuranceProvider.equals("Aditya Birla"))
            {
                if(patient.getCopay()<50d & patient.getAmountToBePayed()<2000)
                    mapMessage.setBoolean("eligible",true);
            }
            else
                mapMessage.setBoolean("eligible",false);

            JMSProducer producer=jmsContext.createProducer();
            producer.send(replyQueue,mapMessage);

        } catch (JMSException | NamingException e) {
            e.printStackTrace();
        }

    }
}
