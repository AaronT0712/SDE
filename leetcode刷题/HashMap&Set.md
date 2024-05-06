1. **Definition**

   + Key must be **Immutable**; (*Array/List, Immutable String* cannot be key);
   
2. How to **Iterate** HashMap & HashSet？

   ```java
   for(Map.Entry<K,V> en : map.entrySet()) {
     en.getValue();
     em.getKey();
   }
   
   for(int element : set) {	// Traverse HashSet
     // do sth.
   }
   ```

   You can also iterate like `map.keySet()` and `map.values()`!!!!
   **贼几把重要**

3. A better way to **Count Occurance**
   ```java
   for(char ch : s.toCharArray()) {
     int count = map.getOrDefault(c, 0) + 1;	// get(C) or default value
     map.put(c, count);
   }
   // getOrDefault(c, num) return the VALUE of C
   // If none, return NUM as default
   ```

4. Hash**Set** and Hash**Map** Coding Difference
   ```java
   Map<Integer, Character> map = new HashMap<>();
   map.put(K,V); map.remove(K);
   map.containsKey(K); map.get(K);
   map.size();
   
   Set<String> set = new HashSet<>();
   set.add(); 
   map.contains();
   set.size();
   ```

   - **Set**: Don't have *key, value*;
   - **Both** have **.size()**;

4. 批量初始化
   ```java
   Map<Character, String> map = new HashMap<>();
   map = Map.of(
   	'2', "abc", '3', "def", '4', "ghi", '5', "jkl", 
     '6', "mno", '7', "pqrs", '8', "tuv", '9', "wxyz"
   );
   ```
   
5. 满足**Fix Constraint**的Sub-Array与HashMap

   1.  譬如，满足Prefix中，*sub-array包含同样数量的 0/1*，sub-array的最大长度 ： **利用Map, 记录每种prefix*第一次出现*的index**;
      ```java
      1. 设 0 == -1， 1 == +1, Use Prefix
      2. 问题于是等于：prefix[r] - prefix[l] == 0
      
      if (map.containsKey(sum)) {	// Another sum appear 表示 p[r]-p[l] == 0
        length = i - map.get(sum);	// So, length = r - l;
      } else {
        map.put(sum, i);	// Record the first occurance
      }
      ```

   2. 譬如，找出，满足prefix中，*sub-array的和 == K*时， sub-array的数量：**利用Map, 记录每个prefix *出现的次数*，找Complement**；
      ```java
      1. Use Prefix
      2. 问题于是等于：prefix[r] - prefix[l] == k
        
      for (int num: nums) {
          curr += num;	// Prefix
          ans += counts.getOrDefault(curr - k, 0);	// Complement
          counts.put(curr, counts.getOrDefault(curr, 0) + 1);
      }
      ```

6. 

