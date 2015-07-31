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

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Consumer {

    // TODO: import JPA library
    // TODO: create all the JPA metrics
    // TODO: store metrics information on the database

    private static final SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

    @JmsListener(destination = "adaptive.metrics.queue")
    public void processMessage(String message) {
        System.out.println("{" + format.format(new Date()) + "} " + message);
    }
}
