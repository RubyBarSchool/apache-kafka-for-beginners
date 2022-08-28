# <img src="/image/kafka_logo.png" alt="Kafka logo"> Apache Kafka Tutorial
#### Apache Kafka For Beginners
## [Website Apache Kafka](https://kafka.apache.org/)  
## [Course Udemy](https://www.udemy.com/course/apache-kafka/)

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
* Nếu cọn commit thử công thì có 3 loại delivery semantics
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
