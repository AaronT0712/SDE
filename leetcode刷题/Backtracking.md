1. If the **constaint**: Array Length is **Less than 50**, A strong signal of `backtracking`;

2. By **removing the last state** after **recursion**, then **move to next state**, and do recursion again;

3. We can use **index** in the `backtracking` function, to **avoid duplication**;
   ```java
   backtrack(new ArrayList<>(), 0, ans, nums);	// 下一次就是 0 变成 1
   backtrack(new ArrayList<>(), 1, ans, n, k);	// 下一次就是 1 变成 2
   ```

4. 跳过已重复的点，需要两个要求：

   - 已经排序
   - if (i != start && nums[i] == nums[i-1])

5. 对于permutation来说，甚至不需要 startIndex, 一个全局set[] 既可避免重复；