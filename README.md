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
<img src="/image/Kafka Theory/Kafka_Topics.png" alt="Kafka logo" style="background-color: white;">
