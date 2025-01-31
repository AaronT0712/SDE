1. **Get all the state**

   - 如果我们需要全部的状态，则每次backtrack，**都添加**当前状态
     ```java
     [1,2,3] => [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     ```

   - 如果只需要，满足条件的状态，则设置收束条件，**再添加**状态
     ```java
     if (build.size() == nums.length) {
     	ans.add(new ArrayList<>(build));
       return
     }
     n = 3
     => 123, 132, 213 231, 312, 321
     ```

2. 获取全部**permutation**，且不重复

   - 利用**arrays.contains**()，判断是否已存在该数字
     ```java
     for(int i=0; i<n; i++) {
       if (!build.contains(i)) {// Do sth.}
     }
     ```

   - 利用 **Swap**, 交换 start, end
     ```
     for(int end = start) {
     	swap(nums, start, end);	// 换过去
     	btr(nums, start+1);			// 切换到下一个start
     	swap(nums, start, end); // 换回来
     }
     ```

3. **N皇后**

   - 核心就是：从第一行往下，逐层Recursive

   - 因为一层一层往下，所以只需要看：左上方45，右上方45，正上方是否为Q
     ```java
     // 左上角
     for(int i = row-1, j = col-1; i >= 0 && j >= 0; i--, j--) {
           if (board[i][j] == 'Q') {return false;}
     }
     ```

   - 如果需要记录，可以在递归参数里，加一个 **char[][\] board**

   - char[] 可以直接 new String(xxx) 转化成String

4. **分割问题**

   - 参数包括（nums, start, build）
     ```java
     btr(nums, start, build);
     for(int end = start; end < nums.length; end++) {// Do sth.}
     调用时，start+1
     ```

   - 可以用boolean[][\] 记录 [start\][end\] **是否是Palindrome**

