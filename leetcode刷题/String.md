1. Use **StringBuilder** class, **O(N)**;
   ```java
   StringBuilder sb = new StringBuilder();
   for(int i = 0; i < s.length(); i++) {
     sb.append(s.charAt(i));	// Append! Not Add()
     sb.deleteCharAt(i);		// 删除字符, 唯一一种删除方式！！
   }
   return sb.toString();	// Don't forget
   
   sb.length();	// GET SIZE
   sb.charAt();	// Get
   ```
   
2. String is **not list**, use **s.length()** to get size, and **s.charAt(i)** to get single element;

3. `String[] strs = str.split("/");`

4. `String.valueOf(x) + "," + String.valueOf(y)`: To add string together;

5. `(""+x)` can quickly turn **Integer to String**;

6. **.substring()** but not *.sub**S**tring*!!

7. **Compare Problem**

   + **==**: Will compare **Address**;

     ```java
     String str1 = "Hello";
     String str2 = "Hello";
     str1 == str2 : True
     ```

     This is because **JVM** will search the **string pool** to find the **similar content** and **reference it**, so **str1&str2** have **same address**;

     When str2 is **changed**, they will **no longer same address**;

   + **A.equals(B)**: Will compare **Contents**;

   