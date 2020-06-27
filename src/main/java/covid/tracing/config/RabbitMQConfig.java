package covid.tracing.config;

import covid.tracing.utils.constant.RabbitMQConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

@Configuration
public class RabbitMQConfig {

    @Bean
    TopicExchange userPositionExchange() {
        return new TopicExchange(RabbitMQConstants.USER_POSITION_EXCHANGE);
    }

    @Bean
    Queue userPositionQueue() {
        return new Queue(RabbitMQConstants.USER_POSITION_QUEUE, false);
    }

    @Bean
    Binding userPositionBinding(@Qualifier("userPositionQueue") Queue queue, @Qualifier("userPositionExchange") TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMQConstants.USER_POSITION_ROUTE_KEY);
    }



    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
}
