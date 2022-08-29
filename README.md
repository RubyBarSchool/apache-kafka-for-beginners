# <img src="/image/kafka_logo.png" alt="Kafka logo"> Apache Kafka Tutorial
#### Apache Kafka For Beginners
## [Website Apache Kafka](https://kafka.apache.org/)  
## [Course Udemy](https://www.udemy.com/course/apache-kafka/)

### Kafka Architecture
# <img src="/image/Kafka_Architecture.png" alt="Kafka Architecture">

### Kafka Topics

```bash
* Topics: là một luồng dữ liệu cụ thể.
* Topics giống như một bảng trong database nhưng không có ràng buộc nào cả
* Có thể gửi bất cứ điều gì tới Topics mà không cần xác minh dữ liệu
* Có thể có một hoặc nhiều topics trong kafka cluster nếu muốn
* Một topic được định nghĩa bởi name của nó
* Topics hỗ trợ bất kì loại định dạng tin nhắn nào (message format: Json, binary, ...)
* Trinhg tự của message được gọi là data stream
* Không thể truy vấn vào topics, sử dụng kafka producers gửi data và kafka consumers đọc data
```
<img src="/image/Kafka Theory/Kafka_Topics.png" alt="Kafka Topics">

### Partitions and offsets

```bash
* Topic có thể chia ra nhiều Partitions
* Các tin nhắn được gửi đến partitions được sắp xếp và đánh cho các mã ID tăng dần
* Các tin nhắn trong một partitions được gọi là offset
* Data được viết vào partitions thì sẽ không được thay đổi
* Data sẽ được giữ lại trong khoảng một thời gian giới hạn ( default is one week - configurable )
* Offset chỉ chưa một ý nghĩa cho từng partition cụ thể
* Offsets không sử dụng lại nếu message phía trước bị xóa mà nó cứ tiếp tục tăng dần
* Thứ tự chỉ được áp dụng trong một partition không phải trên các partitions
* Dữ liệu được chỉ định ngẫu nhiên đến một partitions, trừ khi có một khóa được cung cấp
* Có thể có rất nhiều partitions trong một topic nếu muốn
```
<img src="/image/Kafka Theory/Kafka_Topics_Partitions_Offset.png" alt="Kafka Partitions And Offsets">

### Producers

```bash
* Producers viết data tới các topics
* Producers biết phải viết cho partition nào (Kafka broker có điều đó)
* Nếu kafka broker bị sai, producers sẽ tự động chỉnh sửa
```
<img src="/image/Kafka Theory/Producers.png" alt="Kafka producers">


### Producers: Message Key
#### Message key Anatomy
<img src="/image/Kafka Theory/Kafka_Messages_Anatomy.png" alt="Kafka message key anatomy">

```bash
* Producers có thể chọn gửi một khóa cùng với message (Key có thể là string, number, binary, ...)
* Nếu key = null, data sẽ được gửi vòng quanh (Partition 0, then 1, then 2, ..)
* Nếu key != null, tất cả message sẽ luôn luôn được gửi đến same partition (Mã hashing)
* Đặt key khi mà chúng ta muốn message sắp xếp theo một trường cụ thể
```
<img src="/image/Kafka Theory/Producers_Message_Key.png" alt="Kafka producers message key">

### Kafka Message Serializer
```bash
* Kafka chỉ chấp nhận bytes là đầu vào từ producers và Kafka gửi bytes đầu ra cho consumers
* Message Serialization có nghĩa là chuyển đổi objects hoặc data sang bytes
* Serialization chỉ sử dụng giá trị và khóa
* Một số Serializer thông dụng
   * String (incl.JSON)
   * Int, Float
   * Avro
   * Protobuf
```
<img src="/image/Kafka Theory/Kafka_Message_Serializer.png" alt="Kafka message serializer">

### Kafka Message Key Hashing (Kafka partitioner)
```bash
* Kafka partitioner là một đoạn code dùng để ghi lại và xác định partition nào để gửi message đến.
* Key hasing là một quá trình xác định kết hợp key với một partiyion
* Default Kafka partitioner, keys sẽ được sử dụng thuật toán "murmur2 algorithm"
```
<img src="/image/Kafka Theory/Kafka_Message_Hashing.png" alt="Kafka message hashing">

### Kafka Consumers
```bash
* Consumers đọc data từ topic (xác định qua name của topic) - pull model
* Cách đọc dữ liệu theo kiểu pull model là consumer gửi yêu cầu đọc dữ liệu từ kafka brokers, server sao đó chúng sẽ trả data
* Consumers tự động biết được broker nào để đọc
* Nếu broker bị hỏng, consumers sẽ biết cách khôi phục
* Data được đọc lần lượt từ thấp tới cao trong một partitions
* Nếu consumers đọc dữ liệu từ 2 partitions trở lên thì sẽ không bảo toàn việc lấy từ thấp đến cao mà chỉ đảm bảo từ thấp đến
  cao trong 1 partitions
```
<img src="/image/Kafka Theory/Kafka_Consumers.png" alt="Kafka Consumer">

### Kafka Consumer Deserializer
```bash
* Consumer sẽ chuyển đổi bytes sang objects/data
* Consumer sử dụng key và value của message để chuyển
* Một số Deserializers thông dụng
   * String (incl.JSON)
   * Int, Float
   * Avro
   * Protobuf
* Kiểu Serialization/deserialization không được thay đổi trong vòng đời của một topic. Đó là lý do tại sao không được phép     thay đổi dữ liệu trong topic vì nếu consumer sử dụng deserialization int mà người dùng sửa dữ liệu thành chuỗi thì sẽ bị     lỗi
```
<img src="/image/Kafka Theory/Kafka_Consumers_Deserializer.png" alt="Kafka Consumer deserializer">


### Kafka Consumer Group
```bash
* Tất cả consumers trong hệ thống đều đọc data như một consumer groups
* Một consumer sẽ ở trong một nhóm để đọc dữ liệu từ partitions độc quyền
* Nếu tồn tại nhiều consumer hơn partitions thì một số consumer sẽ thành inactive
```
<img src="/image/Kafka Theory/Kafka_Consumers_Group.png" alt="Kafka Consumer group">

#### Kafka Consumer Group:  Consumer more than partitions.
```bash
* Nếu tồn tại nhiều consumer hơn partitions thì một số consumer sẽ thành inactive
```
<img src="/image/Kafka Theory/Kafka_Consumers_Group_Consumer_More_Than_Partitions.png" alt="Kafka Consumer group consumer more than partitions">

#### Kafka Consumer Group:  Multiple Consumers on one topic
```bash
* Trong apache kafka chấp nhận có nhiều consumer groups có cùng topic
* Tạo ra các consumer groups khác nhau, sử dụng consumer property group.id
```
<img src="/image/Kafka Theory/Multiple_Consumers_On_One_Topic.png" alt="Multiple Consumer on one Topic">

#### Kafka Consumer Group:  Cunsumer Offsets
```bash
* Kafka lưu trữ một offset nơi mà một consumer group có thể đọc được nó gọi là consumer offset
* Offsets này nằm trong kafka topic với tên là __consumer_offsets
* Khi một consumer trong group xử lý nhận data trả về từ kafka, nó phải periodically gửi consumer offset (Kafka broker sẽ viết __consumer_offsets) báo cho kafka là mình đã nhận thành công data
* Nếu có một consumers chết, sau đó quay trở lại và có thể đọc được tin nhắn từ committed consumer offsets
Lúc đó kafka sẽ nói veowis consumer là có thể đọc được dữ liệu bắt đầu từ đây
```
<img src="/image/Kafka Theory/Kafka_Consumers_Offsets.png" alt="Consumer Offsets">

#### Kafka Consumer Group:  Delivery semantics for consumers
```bash
* Mặc định, Java Consumer sẽ tự động xác nhận consumer offset (Ít nhất 1 lần)
* Nếu commit thủ công thì có 3 loại delivery semantics
  * At least once (usually preferred)
    * Offsets committed sau khi message xử lý xong
    * Nếu quá trình xử lý xảy ra lỗi, the message sẽ được đọc lại
    * Điều này làm cho hệ thống phải đọc 2 lần của tin nhắn đó. Cam kết quá trình đọc lại đó không ảnh hưởng tới hệ thống
  * At most once
    * Offsets committed khi messages được lấy về thành công
    * Nếu trong quá trình xử lý bị lỗi, một số message có thể bị mất (Vì commit đã được đẩy lên kaffka lên khi lấy lại sẽ lấy       message tiếp theo chứ ko lấy lại message trước đó.)
  * Exactly once
    * For Kafka => Kafka workflows: sử dụng Transactional API(dễ sử dụng với kafka streams API)
    * For Kafka => External System workflows: Sử dụng idempotent consumer
```
<img src="/image/Kafka Theory/Delivery_Semantics_For_Consumers.png" alt="Delivery semantics for consumers">

#### Kafka Brokers
```bash
* Một Kafka cluster là một composed của nhiều brokers (servers)
* Mỗi một broker được định danh bằng ID (Integer)
* Mỗi một broker chỉ chứa duy nhất 1 topic partitions => dữ liệu sẽ được phân tán ra toàn bộ brokers
* Sau khi kết nối với bất kì một broker nào (a bootstrap broker) thì clients or producers or consumers
  sẽ được connect hoặc biết cách connect đến toàn bộ kafka cluster. Đây là tiện thứ tiện lợi vì không cần phải biết toàn bộ
  kafka cluster mà chỉ cần kết nối với 1 broker khi đó sẽ có thể kết nối với tất cả.
```
<img src="/image/Kafka Theory/Kafka_Brokers.png" alt="Kafka Brokers">

#### Kafka Brokers And Topics
```bash
* Tất cả các topic dẽ được phân phối theo dạng dàn đều ra các brokers nhưng không theo thứ tự nào cả.
* Một broker có thể chứ nhiều topics
* Broker không có tất cả dữ liệu, nó chỉ có dữ liệu mà nó có như ví dụ broker 103 khoogn có data của topic B chỉ có data của   topic A
```
<img src="/image/Kafka Theory/Kafka_Brokers_And_Topics.png" alt="Kafka Brokers And Topics">

#### Kafka Brokers Discovery
```bash
* Mọi kafka brker trong kafka cluster đểu được gọi là bootstrap serve
* Chỉ cần connect tới 1 broker khi đó kafka client sẽ được biết cách connect tới tất cả các broker có trong kafka cluster       (smart client)
* Mỗi một broker trong kafka cluster đều biết tất cả brokers, topic và partitions (metadata) còn lại trong kafka cluster
```
<img src="/image/Kafka Theory/Kafka_Brokers_Discovery.png" alt="Kafka Brokers Discovery">

#### Kafka Topic replication factor
```bash
* Topics nên có nhiều hơn 1 bản sao (usually between 2 and 3)
* Mục đích dùng để nếu broker bị hỏng, thì có một broker khác để có thể thay thế

* Trong cùng một thời gian thì chỉ có duy nhất 1 broker có thể làm leader cho một partition
* Producers chỉ có thể send data cho broker là leader của partition
```
<img src="/image/Kafka Theory/Kafka_Topic_Replication_Factor.png" alt="Kafka Topic replication factor">


#### Kafka Concept of Leader for a Partition
```bash
* Trong cùng một thời gian thì chỉ có duy nhất 1 broker có thể làm leader cho một partition
* Producers chỉ có thể send data cho broker là leader của partition
* Các brokers sẽ sao chép data từ broker leader
* Một partition chỉ có 1 lead và có nhiều multiple ISR (in-sync replica)
* Từ Kafka v2.4+ thì consummer được thêm một tính năng là có thể đọc data từ ISR bản sao thay vì đọc dữ liệu mặc định
```
<img src="/image/Kafka Theory/Kafka_Concept_Of_Leader_For_A_Partition.png" alt="Kafka Concept of Leader for a Partition">
<img src="/image/Kafka Theory/Kafka_Consumers_Replica_Fetching_V2-4.png" alt="Kafka Consumers Replica Fetching">

#### Kafka Producer Acknowledgements (acks)
```bash
* Producers có thể nhận được xác thực dữ liệu đã ghi
* Producers sẽ được Kafka broker báo là dữ liệu đã ghi thành công
  * acks = 0: Producer không đợi xác thực báo lại đó (Trường hợp này rất dễ bị mất dữ liệu)
  * acks = 1: Producer sẽ đợi broker leader ghi dữ liệu xong báo lại (Giới hạn trường hợp mất data)
  * acks = all: Producer sẽ đơị broker leader and replica(ISR) ghi dữ liệu xong báo lại (Không mất được data)
```
<img src="/image/Kafka Theory/Kafka_Producer_Acknowledgements.png" alt="Kafka Producer Acknowledgements">

#### Zookeeper
```bash
* Zookeeper quản lý các brokers
* Zookeeper giúp xác định là leader cho các partitions
* Zookeeper giúp gửi thông báo đến Kafka trong các trường hợp thay đổi
  (Ví dụ: tạo mới topic, broker dies, broker comes up, delete topics, etc...)
* Kafka 2.x không làm việc với zookeeper
* Kafka 3.x có thể làm việc với Zookeeper (KIP-500) - Kafka Raft
* Kafka 4.x sẽ không có Zookeeper
```
<img src="/image/Kafka Theory/Zookeeper.png" alt="Zookeeper">

#### Kafka KRaft
```bash
* 2020, Apache Kafka project bắt đầu xóa bỏ Zookeeper dependency 
* Zookeeper có một vấn đề liên quan đến scaling khi Kafka clusters chứa hown 100,000 partitions
* Loại bỏ Zookeeper Apache Kafka có thể Sale tới hàng triệu partitions, và dễ dàng maintain và set-up
* Cài thiện stability, và làm cho dễ dàng monitor, support and administer
* Đồng nhất một mô hình security cho cả hệ thống
* Dễ dàng hơn khi bắt đầu với Kafka
* Thời gian tắt và khởi động nhanh hơn đáng kể
* Từ Kafka 3.x đã thay thế Zookeeper bằng The Raft Protocol (KRaft)
```
<img src="/image/Kafka Theory/Kafka_KRaft.png" alt="Kafka KRaft">
<img src="/image/Kafka Theory/Kafka_KRaft_Architecture.png" alt="Kafka KRaft Architecture">
<img src="/image/Kafka Theory/Kafka_KRaft_Performance.png" alt="Kafka KRaft Performance">

#### SETUP Kafka Environment
```bash
* Setup wsl2 in windows

* Setup java 11 

wget -O- https://apt.corretto.aws/corretto.key | sudo apt-key add - 
sudo add-apt-repository 'deb https://apt.corretto.aws stable main'
sudo apt-get update; sudo apt-get install -y java-11-amazon-corretto-jdk

* Download Apache Kafka
  Go to page: https://kafka.apache.org/quickstart
  
* Extract

  tar xzf kafka_(version).tgz
  mv kafka_(version) ~
* Set up $PATH envuronment vảiable
  pwd: get path
  PATH="$PATH:x/x/x/bin"

* Run Zookeeper

  zookeeper-server-start.sh ~/(path folder apache kafka)/config/zookeeper.properties
  (-daemon if run Zookeeper in deamon mode)
  
* Run Kafka

  kafka-server-start.sh ~/(path folder apache kafka)/config/server.properties
```

#### CLI (Command Line Interface) 101
#### Kafka topics

```bash
* kafka-topics.sh : show all description of kafka topic cli

* kafka-topic.sh --bootstrap-server localhost:9092 --list : get all topic in server kafka host: localhost, port: 9092

* kafka-topic.sh --bootstrap-server localhost:9092 --create --topic <name topic> : create new topic with <name> in kafka server

* kafka-topic.sh --bootstrap-server localhost:9092  --create --topic <name topic>  --partitions 3 : create new topic with <name> in kafka server have 3 partitions

* kafka-topic.sh --bootstrap-server localhost:9092  --create --topic <name topic>  --partitions 3 --replication-factor 2 : create new topic with <name> in kafka server have 3 partitions and 2 replication <Note: cant create number replication more than number brokers>

* kafka-topic.sh --bootstrap-server localhost:9092 --describe --topic <name topic> : show all description of topic <name topic> (name, partition, leader, replicas, Isr)

* kafka-topic.sh --bootstrap-server localhost:9092 --describe: show all description of all topic in kafka server (name, partition, leader, replicas, Isr)
 
* kafka-topic.sh --bootstrap-server localhost:9092 --delete --topic <name topic> : delete topic <name topic>
```

#### Kafka Producer

```bash
* kafka-console-producer.sh: show all description of kafka producer cli

* kafka-console-producer.sh --bootstrap-server localhost:9092 --topic <name topic>: write message to topic 
Note: 
- if you want exit, you use Ctrl + C to exit the producer
- if producer to a non existing topic then kafka server create topic before send message

* kafka-console-producer.sh --bootstrap-server localhost:9092 --topic <name topic> --producer-property acks=all: write message to topic with property of producer (acks)


* kafka-console-producer.sh --bootstrap-server localhost:9092 --topic <name topic> --property parse.key=true --property key.separator=:  : write message to topic with property key is : in message should have :
Note: if message dont have key then thrown exception 
```

#### Kafka Consumer

```bash
* kafka-console-consumer.sh: show all description of kafka consumer cli

* kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <name topic>: read message of <name topic>
Note: the first subscribe topic, its going to read at the end of the topic. all message send after time consumer subscribe to send consumer

* kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <name topic> --from-beginning: read all message in topic from topic created

* kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <name topic> --formatter kafka.tools.DefaultMessageFormatter --property print.timestamp=true --property print.key=true --property print.value=true --from-beginning: read all message in topic from topic created with format (time,key,value)

* kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic <name topic> --group <name group>: read message of <name topic> with group consumer

Note: 
- Khi có biến động bất kỳ nào trong group consumer thì kafka sẽ chủ động rebalancing để bảo toàn dữ liệu không bị mất hoặc ảnh hưởng
- Khi đã khai báo consumer dạng group thì không được cho property --from-beginning bở vì group consumer chỉ được phép đọc từ offset
- Nếu có các group khác nhau cùng đọc dữ liệu từ một topic thì topic đó sẽ send song song message tới cả 2 group. Khác với việc send lần lượt các consumer trong cùng một group
```


#### Kafka Consumer Group

```bash
* kafka-consumer-groups.sh: show all description of consumer groups cli

* kafka-consumer-groups.sh --bootstrap-server localhost:9092 --list: get all consumer group in kafka server

* kafka-consumer-groups.sh --bootstrap-server localhost:9092 --describe --group <name consumer groups>: get description consumer group <name consumer group> (group, topic, partition, current-offset, log-end-offset, lag, consumer-id, host, client-id)
```
