1. Sort the **Array** / **ArrayList**
   Say, We have `int[][] arr` and `ArrayList<ArrayList<Integer>> arrL`:

   + Array: `Arrays.sort(arr, (a, b) -> a[0] - b[0])`;
   + ArrayList: `Collections.sort(arrL, (a, b) -> a.get(0) - b.get(0))`;

   