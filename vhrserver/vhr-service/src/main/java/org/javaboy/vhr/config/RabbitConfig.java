package org.javaboy.vhr.config;

import org.javaboy.vhr.model.MailConstants;
import org.javaboy.vhr.service.MailSendLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public final static Logger logger = LoggerFactory.getLogger(RabbitConfig.class);
    @Autowired
    CachingConnectionFactory cachingConnectionFactory;
    @Autowired
    MailSendLogService mailSendLogService;

    @Bean
    RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(cachingConnectionFactory);
        // 生产者通过将消息发布到一个交换机，如果交换机成功接收了这个消息，RabbitMQ 会向生产者发送一个确认消息，触发这个回调函数，其中ack为true
        // 如果生产者在规定的时间内没有收到来自RabbitMQ的确认消息，就会触发回调函数，ack为false
        // 消息发送成功触发的回调函数
        rabbitTemplate.setConfirmCallback((data, ack, cause) -> {
            String msgId = data.getId();
            if (ack) {
                logger.info(msgId + ":消息发送成功");
                mailSendLogService.updateMailSendLogStatus(msgId, 1);//修改数据库中的记录，消息投递成功
            } else {
                logger.info(msgId + ":消息发送失败");
            }
        });
        // 这里设置生产者发送到RabbitMQ无法路由到队列时设置的回调函数
        // 需要将publisher-returns 设置为true还有消失生产时设置了mandatory
        rabbitTemplate.setReturnCallback((msg, repCode, repText, exchange, routingkey) -> {
            logger.info("消息发送失败");
        });
        return rabbitTemplate;
    }

    @Bean
    Queue mailQueue() {
        return new Queue(MailConstants.MAIL_QUEUE_NAME, true);
    }

    @Bean
    DirectExchange mailExchange() {
        return new DirectExchange(MailConstants.MAIL_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding mailBinding() {
        return BindingBuilder.bind(mailQueue()).to(mailExchange()).with(MailConstants.MAIL_ROUTING_KEY_NAME);
    }

}
