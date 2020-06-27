package covid.tracing.rabbitmq.consumer;

import covid.tracing.rabbitmq.domain.UserPositionMessage;
import covid.tracing.utils.constant.RabbitMQConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class UserPositionMessageConsumer {
    @RabbitListener(queues = {RabbitMQConstants.USER_POSITION_QUEUE})
    public void consumeSampleMessage(final UserPositionMessage userPositionMessage) {
        System.out.println(userPositionMessage);
    }
}
