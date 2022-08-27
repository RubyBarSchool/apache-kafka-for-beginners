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
