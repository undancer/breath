import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by undancer on 14-4-19.
 */
public class Main {
    public static void main(String[] args) throws JMSException {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:META-INF/spring.xml");
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        jmsTemplate.send("user.notifications", new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message message = session.createTextMessage("undancer");
                message.setStringProperty("name", "undancer");
                return message;
            }
        });
        Message message = jmsTemplate.receiveSelected("user.notifications", "name = 'undancer'");
        if (message instanceof TextMessage) {
            System.err.println(((TextMessage) message).getText());
        }
    }
}
