虽然状态依赖较小的状态，但如果**不能重复使用**元素，你应该使用**倒序**遍历！！！！

## 1. 一维DP

1. **从前往后 & 从后向前**

   ```java
   dp[i] = dp[i-1]; 	// 从前向后
   for(int i = 1; i <= length; i++) {}	// 因此是 从前向后
   return dp[length-1];	// 返回最后一项，因为是从前向后 增加
   
   dp[i] = dp[i+1];	// 从后向前
   for(int i = length-1; i >= 0; i--) {} // 从后向前
   return dp[0]; 	// 返回第一项，因为是从后往前 增加
   
   注意，这只是为了点明 顺序！
   ```

2. **DP** 的大小，可能为 `length` 也可能为 `length+1`，注意嗷！
   这个要根据 **dp等式判断**：

   + 如果出现了 `dp[i] = dp[i+2]` 或者 `dp[i] = dp[i-2]`，则 **dp的长度需要 多2！**防止越界

3. 具体例子

   ------

   ```java
   有股票 [3,2,6,5,0,3], 限定购买次数 k = 2, 一次只能 拥有一个股票
   ```

   1. State分析：

   + 当前是 *第几个股票*，记为 **i**；

   + 当前 *是否持有股票*，记为 **hold**；(0 表示未持有，1 表示持有）

   + 当前 *剩下几次购买*，记为 **remain**；

   2. DP等式：
   + 不买不卖 **SKIP**：`dp[i][hold][remain] = dp[i+1][hold][remain]`；

   + 买：`dp[i][hold][remain] = -prices[i] + dp[i+1][1][remain]`，**remain不会减哦！**；

   + 卖：`dp[i][hold][remain] = prices[i] + dp[i+1][0][remain-1]`；

   + 所以，**DP = MAX(SKIP, 买, 卖)**；

   3. 小点：
   + 可见，*SKIP* 是 **从后向前 (i = i+1)**，所以 **for循环是 i = length-1**；

   + 而 *卖* 是 **从前向后 (remain = remain - 1)**，所以 **for循环是 i = 0**；
   + 使用 `int [i] [hold] [remain]` 来表示；

   ------

   有两种写法，*Iteration* 和 *Recursion*: 

   ```java
   class Solution {
       public int maxProfit(int k, int[] prices) {
           int[][][] dp = new int[prices.length+1][2][k+1];
   
           for(int i = prices.length-1; i >= 0; i --) {
               for(int remain = 1; remain <= k; remain ++) {
                   for(int hold = 0; hold < 2; hold ++) {
                       int ans = dp[i+1][hold][remain];   // Skip
                       if (hold == 0) {    // Not holding a stock, Buy or Skip
                           dp[i][hold][remain] = Math.max(ans, -prices[i] + dp[i+1][1][remain]);
                       } else {
                           dp[i][hold][remain] = Math.max(ans, prices[i] + dp[i+1][0][remain-1]);
                       }
                   }
               }
           }
           return dp[0][0][k];
       }
   }
   ```

   ```java
   class Solution {
       int[][][] memo; // Use it as a HashMap
   
       public int maxProfit(int k, int[] prices) {
           memo = new int[prices.length+1][k+1][2];    // index, remain, hold
   
           for(int i = 0; i < memo.length; i++) {
               for(int j = 0; j < memo[0].length; j++) {
                   Arrays.fill(memo[i][j], -1);    // Set the value to -1
                                                   // To know if we store sth. into it.
               }
           }
           int ans = dp(0, k, 0, prices);
           return ans;
       }
   
       public int dp(int index, int remain, int hold, int[] prices) {
           // Base Case: Reach 
           if (index == prices.length || remain <= 0) {
               return 0;
           }
   
           if (memo[index][remain][hold] != -1) {
               return memo[index][remain][hold];
           }
   
           int ans = dp(index+1, remain, hold, prices);    // NOT BUT NOT SELL, JUST SKIP;
           if (hold == 0) {    // Not holding, BUY sth.
               ans = Math.max(ans, -prices[index] + dp(index+1, remain, 1, prices));
           } else { // Holding, SELL sth.
               ans = Math.max(ans, +prices[index] + dp(index+1, remain-1, 0, prices));
           }
   
           memo[index][remain][hold] = ans;
           return ans;
       }
   }
   ```

   ------



## 2. 矩阵DP

### 1. PATH数量问题

```java
假设是从左上点出发，到右下的矩阵
dp[0][0] = 1;	// Base Case, 因为出发点也算一个path!!!!

// 此题说，只有两种移动，向下 & 向右
if (row > 0) {
  dp[row][col] += dp[row-1][col];
}

if (col > 0) {
  dp[row][col] += dp[row][col-1];
}
```

### 2. PATH SUM问题

有两种 **Base Case**:

+ 如果 **从左上出发**：`dp[0][0] = matrix[0][0]`；
+ 如果 **从最上行出发**：`将 dp第一行 = matrix第一行`；

```java
dp[0][0] = matrix[0][0];
-------------------------------------------
for(int i = 0; i < matrix[0].length; i++) {
    dp[0][i] = matrix[0][i];
}
// 这题说 有三种移动方式，向下 & 向左下 & 向右下
for(int row = 1; row < matrix.length; row++) {
    for(int col = 0; col < matrix[0].length; col++) {
        int small = dp[row-1][col];		// 向下
        if (col - 1 >= 0) {	// 不越界
            small = Math.min(small, dp[row-1][col-1]);	// 向左下
        }
        if (col + 1 < matrix[0].length) {
            small = Math.min(small, dp[row-1][col+1]);	// 向右下
        }
        dp[row][col] = small + matrix[row][col];
    }
}
```

