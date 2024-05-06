1. **.split()** 的一些坑
   在使用 *.split()* 对字符串进行分割时，可能需要 **转义字符**；

   ```java
   .split("\\.");	按 . 分割
   .split("\\\\"); 按 \ 分割
   .split("\\|");	按 | 分割
   .split("\\*");	按 * 分割
   .split("\\+");	按 + 分割
   .split("\\?");  按 ? 分割
   
   .split("/");		按 / 分割
   ```

2. **.contains()**

   + 可以用来检测 **子字符串哦**；

   + 如果问题是 *检测，是否存在长度>2的重复的 子字符串*，
     相当于 **只要有 长度==3 的重复**，那就是 *存在*

     ```java
     public static boolean noDuplicate(String str) {
       int start = 0, end = 3;
       while(end <= str.length()) {
         // 一格一格检测
         if (str.substring(end).contains(str.substring(start, end))) {
           return false;
         }
         start += 1;
         end += 1;
       }
       
       return true;
     }
     ```

     