1. **TreeMap**
   **Key 会自动排序**

   ```java
   TreeMap<Integer, String> treeMap = new TreeMap<>(Comparator.reverseOrder()); (从大到小)
   ```

2. **LinkedHashMap**
   **Key** 会 **保持插入时的顺序**
   
   ```java
   LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<>();
   ```
   
   

