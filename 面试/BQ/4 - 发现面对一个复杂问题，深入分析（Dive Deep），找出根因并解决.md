### **4「发现/面对一个复杂问题，深入分析（Dive Deep），找出根因并解决」**

​	•	**核心看点**：复杂问题 -> 数据/信息收集 -> 深入分析 -> 对策 -> 后续跟进。

​	•	**可覆盖的领导力题目**：

​	•	**Dive Deep**（所有关于深度挖掘数据、发现根因的题）

​	•	**Deliver Results**（如果最后解决了问题，达成某些业务目标）

​	•	**Are Right, A Lot**（如果故事里包含决策过程，以及为什么它是正确或错误）

​	•	**Earn Trust**（如果你在此过程中和团队/经理积极沟通，建立了信赖）

------

**Situation:**
It was the AaronFlow project and I finished most part of development. However, during stress testing with **wrk** (测flowsvr), I noticed that the performance wasn’t as high as expected and also, the CPU usage was highly unstable. After using commands and checking the performance manager, I found that there were sudden spikes in CPU load, indicating that something deeper was affecting the system.

**Task:**
Although I had already applied standard optimizations like adjusting MySQL connection pools, the problem still exist. I was curious about this issue and I decided to dive deep into this and identify the root cause that affecting CPU stability and performance.

**Action:**

1. **Data Gathering & Initial Hypothesis:**
   - I started by analyzing the server side, since the worker part(Aaron) processed tasks rapidly and its operation was light.
   - After reviewing system metrics and checking various forums about this situation, I thought the problem may occur during multi-computer competition. (API call一般很快，那基本问题出现在与DB的交互，这是经验) Then I look into the code for this part and identified the use of the **for-update** may be the potential problem.
2. **Deep Technical Analysis:**
   - After reading the inner workings of the **for-update** command, I discovered that it can generate **gap locks**. It may delay query execution and affect performance, which it is also not recommended in the book called *High Performance MySQL*.
   - I then focused on the unusual CPU spikes. Because the system showed that, the CPU load spike when servers tried to fetched tasks. This led me to dive deeper into the MySQL. I found that MySQL used an automatic deadlock detection that checks for dependencies between queries. As the number of concurrent tasks increased, this mechanism caused high load on the CPU.
3. **Implementing the Solution:**
   - To address both the gap-lock issue and the detection overhead, I dropped the lock produced by the MySQL and decided to implement by myself.
   - After reviewing multiple forums and articles about multi-computer competition, I introduced a **distributed locking mechanism** to better manage the consistency under competition. Also, to address CPU load, I introduced **randomized delays** in task invocations to spread out the load.

**Result:**
The adjustments led to a great performance improvement: CPU became stable under load, and the system throughput greatly increased. By diving deep into the problem of **for-update** and the deadlock detection, I not only solved the immediate performance issues but also gained valuable experience.



可以添加一些合作的内容

------

S：在我做Flow的项目时，我通过修改MySQL的链接池，提升了框架的性能。但是我在用wrk做压力测试的时候，发现性能没有预期的这么高，而且CPU压力非常不稳定 (linux top，性能管理器查看都可以)。
T：我很好奇这个原因，我认为我已经把常见的调优做完了，那么出现问题的，肯定是一些深入的点。

A：我首先依然从MySQL的地方入手，因为aaron端的任务非常快，所以基本不出现在aaron端。肯定是server处有问题。我在阅读论坛和一些企业写的常见MySQL使用指南，发现这个很可能是for-update语句导致的。我开始深入for-update的原理，才发现这个语句会产生 gap-lock的问题，会影响一些语句的执行。这个可能导致了性能上不去，这也是 《高性能MySQL》不推荐的做法

然后我又开始研究CPU压力突然上升的原因，通过测试，我发现arron端一获取任务，cpu压力就会突然上升，这显然不正常。我于是继续看for-update的原理，才发现MySQL会有死锁检测机制，会自动识别当前语句和其它语句有没有dependency，这个过程就像是一个 O(N^2) 的算法，当Aaron数量上升了，就会导致cpu压力突然上升。

R：最后我决定使用分布式锁+随机时间来调用任务，这显著提升性能并解决的cpu压力突然上升的问题。
