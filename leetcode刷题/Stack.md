1. Stack has **add()** and **push()**, which are **different**;
   + Add(): to the **end**;
   + Push(): to the **top**;
2. When use **Enhanced For Loop** (for-each loop), the order of **stack** will be **From Bottom To Top (FIFO)**. `for(String str : stack)`; (即，从 **0** 开始)
3. 1 & 2 is because **Stack extends Vector**;
4. Check whether **Stack** & **Queue** & **Deque** is empty:
   ```java
   stack.isEmpty();	// 三者都可以使用
   stac.size() == 0;	// 这样也行
   ```
   
5. Use **StringBuilder** as a **Stack**
   ```java
   StringBuilder stack = new StringBuilder();
   stack.length();	// 相当于.size()
   stack.deleteCharAt(stack.length()-1);	// Pop out
   stack.append(Element);	// Push
   ```

6. Stack also have **peek()**;

7. Stack can also **save int[]**; (Great for mapping)
   ```java
   Stack<int[]> stack = new Stack<>();
   stack.push(new int[]{a, b});		// Don't forget new int[]{}
   ```

8. 