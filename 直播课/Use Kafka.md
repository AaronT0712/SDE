1. Use **Docker** to run **Kafka**, it requires ***docker-compose.yml*** file to *configure Kafka*;

2. Use **Terminal** to **create Producer & Consumer** terminal
   ```bash
   docker exec -it kafka1 bash	// 开始运行 Kafka 指令
   
   // 创建 Topic
   kafka-topics --bootstrap-server kafka1:19092 \			// port 号
                --create \															// 创建 topic
                --topic shopping-event \								// topic 名称
                --replication-factor 1 --partitions 1	// topic 的参数
   
   kafka-topics --bootstrap-server kafka1:19092 \
                --create \
                --topic shopping-event \
                --replication-factor 1 --partitions 1
   
   // 创建 Producer
   kafka-console-producer --bootstrap-server kafka1:19092 \	
                          --topic shopping-events				// topic 名称对齐
   
   // 创建 Consumer
   kafka-console-consumer --bootstrap-server kafka1:19092 \
                          --topic shopping-events \
                          --from-beginning
                          
   kafka-console-consumer --bootstrap-server kafka1:19092 --topic shopping-events --from-beginning
   ```
   
   然后，Producer 输入消息，Consumer 就会显示消息
   
3. 所以基本顺序就是
   Step 1: create new topic, name align with our project;
   Step 2: open producer port (``); 
   Step 3: open consumer port;
   Step 4: send evets and see if our consumer will receive or not;



1. 也可以使用 `curl` 来send events
1. 