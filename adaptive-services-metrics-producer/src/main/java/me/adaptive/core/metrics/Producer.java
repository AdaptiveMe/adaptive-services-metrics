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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.util.FormatUtil;

@Component
public class Producer {

    // TODO: import JPA library
    // TODO: create all the JPA metrics
    // TODO: send the messages formated with entities

    @Autowired
    private JmsTemplate jmsTemplate;

    /*@Autowired
    AccountRepository accountRepository;*/

    @Scheduled(fixedRate = 5000)
    public void send() {

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        this.jmsTemplate.convertAndSend(FormatUtil.formatBytes(hal.getMemory().getAvailable()));
    }
}
