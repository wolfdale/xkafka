# xkafka
A Kafka pipeline to handle huge volume of credit card transactions & perform real time data analysis. Card Transaction Producer is implemented in Spring boot with kafka dependency to produce credit card transaction with [Approved, Rejected] status. Kafka consumer is also impelemnted in Spring boot with kafka dependency to consume the data from kafka broker and persist in Elastic Serach, which later will be available to perform data analysis. 
## Building with Maven
To build kafka producer or consumer 
```
mvn clean install
```
## Up and Running Kafka producer
To run Kafka producer with default configuration. Execute following commands.
```
mvn clean install
java -jar target/producer-<RELEASE>.jar
```

You can edit `application.properties` file which contains various producer configuration.
These configuration can be evolved over time with different releases.
```
server.port=5000
kafka.bootstrap.server=localhost:9092
producer.client.type=java-client
transaction.generation.rate=1000
kafka.metadata.request=60000
kafka.topic.name=transactions
kafka.enable.custom.partitioner=false
```
* `server.port` Kafka producer server port.
* `kafka.bootstrap.server` Comma separated list of Kafka bootstrap server. `<IP>:<PORT>` 
* `producer.client.type` Client type to specify where transaction originated.
* `transaction.generation.rate` Rate at which transactions to be generated.
* `kafka.metadata.request` Request time for Kafka Admin client to fetch cluster metadata.
* `kafka.topic.name` Kafka topic name.
* `kafka.enable.custom.partitioner` Set this to enable kafka custom partitioner.

## Up and Running Kafka consumer
To run Kafka consumer with default configuration. Execute following commands.
```
mvn clean install
java -jar target/consumer-<RELEASE>.jar
```
You can edit `application.properties` file which contains various consumer configuration. Kafka consumer is integrated with Elastic search to index & persist data. The index `transaction` is automatically created and data is persisted after it is consumed by the kafka consumer.
These configuration can be evolved over time with different releases.
```
server.port=6000
kafka.bootstrap.server=localhost:9092
kafka.topic.name=transactions
kafka.group.id=G1
elastic.search.enabled=true
elastic.bootstrap.server=localhost:9200
```
* `server.port` Kafka consumer server port.
* `kafka.bootstrap.server` Comma separated list of Kafka bootstrap server. `<IP>:<PORT>`
* `kafka.topic.name` Kafka topic name.
* `kafka.group.id` Kafka consumer group ID.
* `elastic.search.enabled` Set this to enable elastic serach integration.
* `elastic.bootstrap.server` Elastic search bootstrap server.

