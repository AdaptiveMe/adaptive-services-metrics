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

import me.adaptive.core.data.domain.MetricServerEntity;
import me.adaptive.core.metrics.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.Processor;
import oshi.software.os.OSFileStore;
import oshi.util.FormatUtil;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ServerMetricsProducer {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 30000)
    public void sendServerMetrics() {

        MetricServerEntity metricServer = new MetricServerEntity();
        Map<String,String> attributes = new HashMap<String, String>();

        // Hostname
        try {
            metricServer.setHostname(InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            metricServer.setHostname("undefined");
            System.out.println(e.getMessage());
        }

        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();

        // Memory
        Long memAvailable = hal.getMemory().getAvailable();
        Long memUsed = hal.getMemory().getTotal() - memAvailable;
        attributes.put(Constants.MEM_USED, String.valueOf(memUsed));
        attributes.put(Constants.MEM_AVAILABLE, String.valueOf(memAvailable));

        // CPU
        // Get the first CPU to get the system load at that time
        Double systemLoad = hal.getProcessors()[0].getSystemCpuLoad();
        attributes.put(Constants.SYSTEM_CPU_LOAD, String.valueOf(systemLoad));

        // FileSystem
        // Get the first FileSystem supposing there is only one disk
        Long diskAvailable = hal.getFileStores()[0].getUsableSpace();
        Long diskUsed = hal.getFileStores()[0].getTotalSpace() - diskAvailable;
        attributes.put(Constants.DISK_USED, String.valueOf(diskUsed));
        attributes.put(Constants.DISK_AVAILABLE, String.valueOf(diskAvailable));

        metricServer.setAttributes(attributes);

        this.jmsTemplate.convertAndSend(metricServer);
    }
}
