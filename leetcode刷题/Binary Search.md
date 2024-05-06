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