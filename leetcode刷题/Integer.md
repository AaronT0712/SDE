1. **String** --> **Integer** & **StringBuilder** --> **Integer** & **Char** --> **Integer**

   ```java
   Integer.parseInt(s);	// s
   Integer.parseInt(sb.toString());	// sb
   Character.getNumericValue(ch);	// ch
   ```

2. **十进制** 的 **数字** 变 **其它进制** 的 **字符串**

   ```java
   Integer.toHexString(n);
   Integer.toBinaryString(n);
   ```

3. **其它进制** 的 **字符串** 变 **十进制** 的 **数字**

   ```java
   Integer.parseInt(n, 进制);	// 进制 = 2，则是 二进制 变为 10进制
   ```

4. **long** 和 **int** 可以直接比较，不用转换；




