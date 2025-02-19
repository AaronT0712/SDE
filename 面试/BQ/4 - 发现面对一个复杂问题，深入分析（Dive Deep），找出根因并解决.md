### **4「发现/面对一个复杂问题，深入分析（Dive Deep），找出根因并解决」**

​	•	**核心看点**：复杂问题 -> 数据/信息收集 -> 深入分析 -> 对策 -> 后续跟进。

​	•	**可覆盖的领导力题目**：

​	•	**Dive Deep**（所有关于深度挖掘数据、发现根因的题）

​	•	**Deliver Results**（如果最后解决了问题，达成某些业务目标）

​	•	**Are Right, A Lot**（如果故事里包含决策过程，以及为什么它是正确或错误）

​	•	**Earn Trust**（如果你在此过程中和团队/经理积极沟通，建立了信赖）

------

**Situation:**
It was the AaronFlow project and I finished most part of development. However, during stress testing with **wrk** (测flowsvr), I noticed that the QPS was only 200, which wasn’t as high as expected and also, the CPU usage was highly unstable. After using commands and checking the performance manager, I found that there were sudden spikes in CPU load, indicating that something deeper was affecting the system.

**Task:**
Although I had introduced and adjusted MySQL connection pools to deal with connection overhead, the problem still exist. I was curious about this issue and I decided to dive deep into this and identify the root cause that affecting CPU stability and performance.

**Action:**

1. **Data Gathering & Initial Hypothesis:**
   - I started by analyzing the server side, (since the worker part(Aaron) processed tasks rapidly and its operation was light.)
   - After reviewing system metrics and checking various forums about this situation, I thought the problem may occur during multi-computer competition. Then I look into the code for this part and identified the use of the **for-update** may be the potential problem.
2. **Deep Technical Analysis:**
   - After reading the inner workings of the **for-update** command, I discovered that it can generate **gap locks**. It may delay query execution and affect performance, which it is also not recommended in the book called *High Performance MySQL*.
   - I then focused on the unusual CPU spikes. Because performance manger showed that, the CPU load spike when servers tried to fetched tasks. This led me to dive deeper into the MySQL. I found that MySQL used an automatic deadlock detection that checks for dependencies between queries. As the number of concurrent tasks increased, this mechanism caused high load on the CPU.
3. **Implementing the Solution:**
   - To address both the gap-lock issue and the detection overhead, I dropped the lock produced by the MySQL and decided to implement by myself.
   - After reviewing multiple forums and articles about multi-computer competition, I introduced a **distributed locking mechanism** to better manage competition. Also, I introduced **randomized delays** in task invocations to spread out the load.

**Result:**
The adjustments led to a great performance improvement: CPU became stable under load, and the system throughput improved by 5 times. By diving deep into the problem of **for-update** and the deadlock detection, I not only solved the immediate performance issues but also gained valuable experience.
