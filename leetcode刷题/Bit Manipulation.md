1. **XOR**
   + **Difference** = **1**，Vice Versa;
   + **0 XOR N** = **N**;
2. **Time Complexity**
   It is `quite strange`，If using **>>** or **<<** to the number in **Java**，The Time Complexity is **O(1)**, Not **O(N)**;
   |
   ^ Ans: 这与 O(N) 的定义有关，N 是指 题目element 的数量！
3. **Count the 1-bits**
   + Using `Integer.bitCount(n)`;
   + Count **RIGHT-MOST bit** and **>> by 1** until **N != 0**;
4. 