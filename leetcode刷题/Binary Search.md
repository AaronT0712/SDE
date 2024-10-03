1. Use `left + (right-left)/2` to **prevent overflow**;

2. What if **there are duplicates**?

   + return the **left-most**
     ```java
     int left = 0;
     int right = arr.length-1;
     
     while(left <= right) {
       int mid = left + (right - left) / 2;
       if (arr[mid] >= target) {
     		right = mid - 1;
       } else {
         left = mid + 1;
       }
     }
     
     return left;
     ```

   + return the **right-most**
     ```java
     if (arr[mid] <= target) {	// 变成 <=
     		left = mid + 1;	// 调整左边
       } else {
         right = mid - 1;
       }
     ```

3. BFS, return the **minimum** or **maximun** condition:
   return ***Left / Right*** ;

4. ```java
   public int binarySearch(int[] nums, int target) {
       int left = 0, right = nums.length - 1;
       
       while (left <= right) {
           int mid = left + (right - left) / 2;
           
           if (nums[mid] == target) {
               return mid; // 找到目标
           } else if (nums[mid] < target) {
               left = mid + 1; // 向右查找
           } else {
               right = mid - 1; // 向左查找
           }
       }
       
       return -1; // 未找到
   }
   ```

5. ```
   public int findPeakElement(int[] nums) {
       int left = 0, right = nums.length - 1;
   
       while (left < right) {
           int mid = left + (right - left) / 2;
           
           // 如果 mid 比 mid+1 大，说明在左侧或 mid 本身存在峰值
           if (nums[mid] > nums[mid + 1]) {
               right = mid; // 峰值可能在 mid 及其左侧
           } else {
               left = mid + 1; // 峰值在 mid 右侧
           }
       }
   
       return left; // left == right 时，即为峰值所在的位置
   }
   
   ```

6. ### **区别总结：**

   1. **`while (left <= right)`**
      - 最后一次循环仍需检查 `left == right` 的情况，所以使用 <=。
      - 通常用于**查找具体的元素**，如二分查找一个目标值。
   2. **`while (left < right)`**
      - 不需要检查 `left == right` 的情况，因为这个位置就是解，所以使用 <。
      - 通常用于**寻找区间内的某种特性**，如**峰值、最小值、最大值**等。