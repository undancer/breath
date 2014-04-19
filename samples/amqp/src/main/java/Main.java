import com.google.common.collect.Lists;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.List;
import java.util.Random;


/**
 * Created by undancer on 14-4-18.
 */
public class Main {

    public static final String channel = "user.notifications";

    public static void main(String[] args) throws JMSException, InterruptedException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        final List<String> names = Lists.newArrayList("undancer", "sign");
        System.err.println("发送");
        {
            for (int index = 0; index < 10; index++) {
                String text = "text#" + index;
                String name = names.get(new Random().nextInt(names.size()));
                Message message = jmsTemplate.execute((SessionCallback<Message>) (session) -> {
                    TextMessage textMessage = session.createTextMessage(text);
                    textMessage.setStringProperty("name", name);
                    return textMessage;
                });
                jmsTemplate.convertAndSend(channel, message);
                System.err.println("send text index#" + (index) + " -> " + name);
            }

        }
        System.err.println("接收");
        {
            jmsTemplate.setReceiveTimeout(50);
            Message message = null;
            do {
                message = jmsTemplate.receiveSelected(channel, "name = 'sign'");
                if (message instanceof TextMessage) {
                    System.err.println("收到：" + ((TextMessage) message).getText());
                }

            } while (message != null);


        }
    }
}
