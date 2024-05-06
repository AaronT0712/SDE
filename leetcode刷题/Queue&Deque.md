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
   // Add
   deque.add(); deque.addLast(); deque.offer();				// Tail	
   deque.addFirst();	deque.push();	deque.offerFirst();	// Head
   // Remove
   deque.removeLast();	// return Integer, Tail
   deque.removeFirst();	// return Integer, Head;
   // Check
   deque.getFirst();		deque.getLast();
   deque.peekFirst();	deque.peekLast();
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

   
   
   