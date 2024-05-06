1. **正则表达式**基本代码
   
   ```java
   import java.util.*;
   import java.util.regex.*;
   
   Pattern p1 = Pattern.compile("[A-Z]");	// 编译 regex
   p1.matcher(str).find(): Boolean, 只要 **局部str符合条件**即可
   p1.matcher(str).matches();	Boolean, 必须 **整个str符合条件**
   ```
   
2. **表达式的基本规则**

   + **指定字符**
     + **[A-C0-8a-c]**：匹配 A-C, 0-8, a-c 任意条件都可以；
     + **[^A-C]**：**不包括** A-C 即可；
   + **预定义的字符类别**
     + **\\d**：数字 （*Digital*）
     + **\\w**：任意 单词 （*Word*）相当于 *[A-Z0-9a-z]*
     + **\\s**：任意空白字符，包括空格、制表符 (**\\t**)、换行符 (**\\n**)等。
   + **量词**：
     - `*`：**零次或多次**。
     - `+`：**一次或多次**。
     - `?`：**零次或一次**。
     - `{n}`：**恰好 n 次**。
     - `{n,}`：**至少 n 次**。
     - `{n,m}`：**至少 n 次，最多 m 次**。