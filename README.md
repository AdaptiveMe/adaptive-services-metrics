## adaptive-services-metrics-consumer

#### Pre-requisites
- JDK 1.7+
- Maven 3+
- [Apache ActiveMQ](http://activemq.apache.org/getting-started.html) 
- [adaptive-services-dashbar.jar](https://github.com/AdaptiveMe/adaptive-services-dashbar)

#### Installation & Start consumer


- ```cd adaptive-services-metrics-consumer```
- ```mvn clean package```
- ```./adaptive-services-metrics-consumer.sh start```

#### Stop consumer

- ```./adaptive-services-metrics-consumer.sh stop```

#### Status and Logs

- ```./adaptive-services-metrics-consumer.sh status```
- ```tail -f target/adaptive-services-metrics-consumer-1.0.log```


## adaptive-services-metrics-producer

#### Pre-requisites
- JDK 1.7+
- Maven 3+
- [adaptive-services-dashbar.jar](https://github.com/AdaptiveMe/adaptive-services-dashbar)

#### Installation & Start consumer


- ```cd adaptive-services-metrics-producer```
- ```mvn clean package```
- ```./adaptive-services-metrics-producer.sh start```

#### Stop consumer

- ```./adaptive-services-metrics-producer.sh stop```

#### Status and Logs

- ```./adaptive-services-metrics-producer.sh status```
- ```tail -f target/adaptive-services-metrics-producer-1.0.log```