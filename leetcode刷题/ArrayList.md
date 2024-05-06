1. Basic
   ```java
   ArrayList<String> list = new ArrayList<String>();
   // Set & Remove
   list.add();	list.set(index, Value); list.remove(index);	list.clear();
   // Get
   list.get(index); list.size(); list.contains(); // 居然有contains(), 但是没有length()
   // Iterate
   for(Integer num : list) {}
   // Print
   System.out.println(list);	// [A, B, C, D] but not ["A", "B",...]
   ```
   
2. **Two-Dimensional** ArrayList **initialization**

   ```java
   ArrayList<ArrayList<Integer>> aList = new ArrayList<>(size);
   aList.add(new ArrayList<>());	// We should also decide the length of internal arrayList
   ```

3. **Sort**!!!
   `Collections.sort(aList);` // Don't forget Collection**s**;

4. **Array of the ArrayList**

   ```java
   List<Integer>[] map = new ArrayList[n];
   ```

5. **List<List<>> R add List<> r**

   ```java
   R.add(new ArrayList<>(r));	// 这样才OK, 这在Backtracking中很常用
   R.add(r);	// NO!
   ```

   Because `r` is a **reference** to the **array address**;
