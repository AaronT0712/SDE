1. Queue (Tail in, Head out) *FIFO*
   ```java
   Queue<Integer> queue = new LinkedList<>(Arrays.asList(1,2,3));	// LinkedList!
   // Also, we can use <>()
   // Add & Remove
   queue.offer() == queue.add();	queue.poll() == queue.remove();
   // Check
   queue.isEmpty();  queue.peek();	 queue.size();
   ```

2. Deque
   ```java
   Deque<Integer> deque = new ArrayDeque<>();
   [Front/Head] ..... [End/Tail]
   
   // Add
   deque.add(); deque.addLast(); deque.offer();				// Tail	
   deque.push(); deque.addFirst();	deque.offerFirst();	// Head
   // Remove
   deque.removeLast();	deque.pop();	// remove Tail
   deque.removeFirst();	// remove Head;
   // Check
   deque.getFirst();		deque.getLast();
   deque.peekFirst();	deque.peekLast();
   
   deque.size();	deque.isEmpty();
   ```
   
3. **Monotonic Deque** [Monotonic](https://leetcode.com/explore/interview/card/leetcodes-interview-crash-course-data-structures-and-algorithms/706/stacks-and-queues/4517/)
   Very **important idea !** : When adding, follow the rules of monotonic.
   (1. When **keep** finding ***regional*** **MAX/MIN**)

   ```java
   // Monotonic decreasing: Head big, Tail small
   while(!deque.isEmpty() && deque.getLast() < num) {
     deque.removeLast();
   }
   ```

   
   
   