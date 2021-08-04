### Kafka_test
### Environments
### Ubuntu 20.04

--------------------------------------------------------------------

### JDK installation
$ sudo apt-get update && sudo apt-get upgrade
$ sudo apt-get install openjdk-11-jdk
$ java -version
$ javac -version

### hosts setting
$ sudo vi /etc/hosts
{your public_ip} my-kafka

### Kafka installation
$ wget https://archive.apache.org/dist/kafka/2.5.0/kafka_2.12-2.5.0.tgz
$ tar xvf kafka_2.12-2.5.0.tgz
$ rm kafka_2.12-2.5.0.tgz

### JVM heap settings
$ vi ~/.bashrc

### .bashrc 제일 하단에 KAFKA_HEAP_OPTS 환경변수 선언
export KAFKA_HEAP_OPTS="-Xmx400m -Xms400m"

### Environments variables check
$ source ~/.bashrc
$ echo $KAFKA_HEAP_OPTS

### Zookeeper configuration
$ vi config/zookeeper.properties

# Broker configuration
$ vi config/server.properties
advertised.listeners=PLAINTEXT://my-kafka:9092

--------------------------------------------------------------------

### Start-up Zookeeper
$ bin/zookeeper-server-start.sh -daemon config/zookeeper.properties
$ jps -vm

### Start-up Broker
$ bin/kafka-server-start.sh -daemon config/server.properties
$ jps -m

### server.log
$ tail -f logs/server.log

--------------------------------------------------------------------

### Create a topic
$ bin/kafka-topic.sh --create --bootstarp-server my-kafka:9092 --topic {topic_name}
$ bin/kafka-topics.sh --create --bootstrap-server my-kafka:9092 --partitions 3 --replication-factor 1 --config retention.ms=172800000 --topic {topic_name_2}

### Topic list
$ bin/kafka-topics.sh --bootstrap-server my-kafka:9092 --list

### Topic describe
$ bin/kafka-topics.sh --bootstrap-server my-kafka:9092 --describe --topic {topic_name_2}

### Modify topic
$ bin/kafka-topics.sh --bootstrap-server my-kafka:9092 --topic {topic_name} --alter --partitions 4
$ bin/kafka-topics.sh --bootstrap-server my-kafka:9092 --topic {topic_name} --describe
$ bin/kafka-configs.sh --bootstrap-server my-kafka:9092 --entity-type topics --entity-name {topic_name} --alter --add-config retention.ms=86400000
$ bin/kafka-configs.sh --bootstrap-server my-kafka:9092 --entity-type topics --entity-name {topic_name} --describe

--------------------------------------------------------------------

### Producer
$ bin/kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic {topic_name}
$ bin/kafka-console-producer.sh --bootstrap-server my-kafka:9092 --topic {topic_name} --property "parse.key=true" --property "key.separator=:"

### Consumer
$ bin/kafka-console-consumer.sh --bootstrap-server my-kafka:9092 --topic {topic_name} --from-beginning
$ bin/kafka-console-consumer.sh --bootstrap-server my-kafka:9092 --topic {topic_name} \ --from-beginning

### Consumer groups
$ bin/kafka-console-consumer.sh --bootstrap-server my-kafka:9092 --topic {topic_name} --property print.key=true --property key.separator="-" --group hello-group --from-beginning
$ bin/kafka-consumer-groups.sh --bootstrap-server my-kafka:9092 --list hello-group
$ bin/kafka-consumer-groups.sh --bootstrap-server my-kafka:9092 --group hello-group --describe

### Verifiable producer, consumer
$ bin/kafka-verifiable-producer.sh --bootstrap-server my-kafka:9092 --max-message 10 --topic verify-test
$ bin/kafka-verifiable-consumer.sh --bootstrap-server my-kafka:9092 --topic verify-test --group-id test-group

### Delete records
$ vi delete-topic.json
{"partitions":[{"topic": "test", "partition": 0, "offset": 50}], "version":1}

$ bin/kafka-delete-records.sh --bootstrap-server my-kafka:9092 --offset-json-file delete-topic.json
