/*
 * Copyright 2014-2015. Adaptive.me.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.adaptive.core.metrics;

import me.adaptive.core.data.config.JpaConfiguration;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJms
@EnableScheduling
@Configuration
@Import(JpaConfiguration.class)
@SpringBootApplication
public class ActiveMQApplication {

    public static void main(String[] args) {
        SpringApplication.run(ActiveMQApplication.class, args);
    }

    @Bean(name = "amqConnectionFactory")
    public ActiveMQConnectionFactory amqConnectionFactory() {
        return new ActiveMQConnectionFactory("tcp://my.adaptive.me:61616");
    }

    @Bean(name = "connectionFactory")
    public CachingConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(amqConnectionFactory());
    }

    @Bean(name = "defaultDestination")
    public ActiveMQQueue defaultDestination() {
        return new ActiveMQQueue("adaptive.metrics.queue.server");
    }

    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate();

        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setDefaultDestination(defaultDestination());

        return jmsTemplate;
    }
}
