package br.com.utfpr.distribuidoatividadeum.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

    @Bean
    public Queue filaCep() {
        return new Queue("fila.cep", true);
    }

    @Bean
    public Queue filaPagamento() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", "");
        args.put("x-dead-letter-routing-key", "fila.pagamento.dlq");
        return new Queue("fila.pagamento", true, false, false, args);
    }

    @Bean
    public Queue filaPagamentoDlq() {
        return new Queue("fila.pagamento.dlq", true);
    }

    @Bean
    public Queue filaFiscal() {
        return new Queue("fila.fiscal", true);
    }

    @Bean
    public Queue filaEstoque() {
        return new Queue("fila.estoque", true);
    }

    @Bean
    public Queue filaEntrega() {
        return new Queue("fila.entrega", true);
    }

    @Bean
    public Queue filaEmail() {
        return new Queue("fila.email", true);
    }
}
