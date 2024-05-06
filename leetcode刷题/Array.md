1. Use **'array.length**' to get size;
2. **Slide-window** is suitable for **valid sub-array**;
   + When using **slide-window** to calculate the **number of sub-array**, the number is **right - left + 1**;
3. **Pre-fix** is suitable for **sum of sub-array**;
   + Use **long** instead of **int** for **prefix list**;
4. `int[] counts = new int[1001];`  all values would be **0 by default**;
5. `Arrays.fill(ar, -1)`: Fill the array with **-1**;
6. `Arrays.sort(nums)`: Sort from **small to large**;
   `Arrays.sort(nums, Comparator.reverseOrder())`: Sort from **large to small**;
7. 

