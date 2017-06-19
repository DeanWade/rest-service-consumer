package hello.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
	
	public final static String exchangeName = "spring-boot-exchange";
	
	public final static String sendQueueName = "spring-boot-send";
    
	public final static String sendReceiveQueueName = "spring-boot-send-receive";
    
	public final static String replyQueueName = "spring-boot-reply";
    
	@Autowired
	private ConnectionFactory connectionFactory;

    @Bean
    Queue sendQueue() {
        return new Queue(sendQueueName, false);
    }
    
    @Bean
    Queue sendReceiveQueue() {
        return new Queue(sendReceiveQueueName, false);
    }
    
    @Bean
    Queue replyQueue() {
        return new Queue(replyQueueName, false);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    Binding bindingSendQueue(TopicExchange exchange) {
        return BindingBuilder.bind(sendQueue()).to(exchange).with(sendQueueName);
    }
    
    @Bean
    Binding bindingSendReceiveQueue(TopicExchange exchange) {
        return BindingBuilder.bind(sendReceiveQueue()).to(exchange).with(sendReceiveQueueName);
    }

    @Bean
    MessageConverter messageConverter(){
    	return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate amqpTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setExchange(exchangeName);
        rabbitTemplate.setReplyAddress(replyQueueName);
        rabbitTemplate.setReplyTimeout(5000);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
    
    @Bean
    SimpleMessageListenerContainer replyListenerContainer(RabbitTemplate amqpTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(replyQueueName);
        container.setMessageListener(amqpTemplate);
        container.setMessageConverter(messageConverter());
        return container;
    }
}
