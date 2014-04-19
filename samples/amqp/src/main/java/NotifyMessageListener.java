import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by undancer on 14-4-18.
 */
public class NotifyMessageListener implements MessageListener {

    private JmsTemplate jmsTemplate;

    @Override
    public void onMessage(Message message) {
        try {
            message.getStringProperty("username");
        } catch (JMSException e) {
            e.printStackTrace();
        }

        jmsTemplate.convertAndSend(new ActiveMQQueue("user:test"), new Object());

    }
}
