### Kafka_test
### Environments
### Ubuntu 20.04
--------------------------------------------------------------------
# JDK installation
$ sudo apt-get update && sudo apt-get upgrade
$ sudo apt-get install openjdk-11-jdk
$ java -version
$ javac -version

# Kafka installation
$ wget https://archive.apache.org/dist/kafka/2.5.0/kafka_2.12-2.5.0.tgz
$ tar xvf kafka_2.12-2.5.0.tgz
$ rm kafka_2.12-2.5.0.tgz

# JVM heap settings
$ vi ~/.bashrc

## .bashrc 제일 하단에 KAFKA_HEAP_OPTS 환경변수 선언
export KAFKA_HEAP_OPTS="-Xmx400m Xms400m"

# Environments variables check
$ source ~/.bashrc
$ echo $KAFKA_HEAP_OPTS

# Zookeeper configuration
$ vi config/zookeeper.properties

# Broker configuration
$ vi config/server.properties
advertised.listeners=PLAINTEXT://{your public_ip}:9092
--------------------------------------------------------------------
# Start-up Zookeeper
