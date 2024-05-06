1. We can use **HashMap** to represent the graph for finding;
   ```java
   Map<Integer, List<Integer>> graph = new HashMap<>();
   ```

   In some cases, we may need **visited** array **to stop DFS**;
   However, we can also **change the original graph** to do this!

   ```java
   Finding the #islands
   Input: grid = [
     ["1","1","1","1","0"],
     ["1","1","0","1","0"],
     ["1","1","0","0","0"],
     ["0","0","0","0","0"]
   ] Answer = 1
     
   To do this, we can flip '1' to '0' to stop DFS!!
   if (grid[i][j] == '1') {
       grid[i][j] = '0';
       islands += 1;
       dfs(i, j, grid);
   }
   ```

2. For **undirect - acyclic** graph, finding the number of **connected components** can be done:

   + **DFS** with **HashMap** (Store the mapping from **both nodes**);

   + **Union-Find**

     ```java
     class Solution {
         public int countComponents(int n, int[][] edges) {
             int linked = n;
             // Union-Find
             int[] parents = new int[n];
             for(int i = 0; i < n; i++) {
                 parents[i] = i; // At first, they are their own parents
             }
             // Traverse the edges
             for(int i = 0; i < edges.length; i++) {
                 int node1 = findParent(parents, edges[i][0]);
                 int node2 = findParent(parents, edges[i][1]);
     						// Combine these two Parents
                 if (node1 != node2) {	// If no
                     parents[node1] = node2; // Combine these 2 parents!
                     linked -= 1;    // So that the number -= 1
                 }
             }
             return linked;
         }
     		// Iterate faster than recursion? YES
         public int findParent(int[] parents, int node) {
             while (parents[node] != node) {
                 parents[node] = parents[parents[node]];
                 node = parents[node];
             }
             return node;
         }
     }
     ```

3. **BFS**

   + Traverse the nodes, **according to their distances to root**;
   
   + Use a **designed class** to store **distance info**
     ```java
     class Cell{
     	int row;
       int col;
       int step;
       Cell(int row, int col, int step) {
         this.row = row;
         this.col = col;
         this.step = step;
       }
     }
     ```
   
     And use **Queue** <*Cell*> to perform **BFS**;
   
4. For **bi-directional chart**, use HashMap to record **A->B** & **B->A**;