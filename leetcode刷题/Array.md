1. Use **'array.length**' to get size;
2. **Slide-window** is suitable for **valid sub-array**;
   + When using **slide-window** to calculate the **number of sub-array**, the number is **right - left + 1**;
3. **Prefix** Problem
   + Use **long** instead of **int** for **prefix sum** to avoid *overflow*;
4. **Batch initialization**
   `Arrays.fill(ar, -1)` : Fill the array with **-1**;
6. Array **Sorting**
   + `Arrays.sort(nums)`: Sort from **small to large**;
   + `Arrays.sort(nums, Comparator.reverseOrder())`: Sort from **large to small**;
   + More, can be seen in **Lambda Expression**;
6. **ArrayList to Array**
   Say, we have `ArrayList<int[]> arrL` , we want to convert it to `int[][]` arr:
   + `arr = arrL.toArray(new int[arrL.size()][2])`: It would create a `int[n][2]` array;

