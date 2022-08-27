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
