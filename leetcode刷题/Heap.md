1. Basic Knowledge
   Can be seem as **a tree** that **parents are larger than children**;

   ```java
   - Add: O(logn);
   - Remove: O(logn);
   - Find the Min/Max element: O(1);	(Root of the tree)
   ```

2. Initialization

   ```java
   // MinHeap & MaxHeap
   PriorityQueue<Integer> heap = new PriorityQueue<>();
   PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Comparator.reverseOrder());
   																// 等于 <>((a,b) -> b.compareTo(a))   lambda exp
   heap.add();  heap.remove();  heap.size();  heap.peek();
   ```

3. Heap can **follow some rules**
   ```java
   // Heap according to their freq
   for(int num : nums) {
     map.put(num, map.getOrDefault(num, 0) + 1);
   }
   PriorityQueue<Integer> heap = new PriorityQueue<>((a,b) -> map.get(b)-map.get(a));
   ```

   Also, they can have **multiple rules**
   ```java
   PriorityQueue<Integer> heap = new PriorityQueue<>((a,b) -> {
   	if (Math.abs(a-10) == Math.abs(b-10)) {
   		return a - b;		// If |a-10|==|b-10|, return the LESSER one
   	} else {
       return Math.abs(a-10) - Math.abs(b-10);	// return the closer(to 10) ones
     }
   });
   ```

   They can also store **int[]**
   ```java
   PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> a[1]-b[1]);
   ```

   

