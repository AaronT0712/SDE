1. **DFS** is implemented via **Recursion** or **Stack**, while **Queue** for the **BFS**;

2. **BFS** (Is **better** than DFS in many problems)

   ```java
   Queue<TreeNode> queue = new LinkedList<>();
   // Or Deque<TreeNode> queue = new ArrayDeque<>();
   queue.add(root);
   while(!queue.isEmpty()) {
     int levelLength = queue.size();
     for(int i = 0; i < levelLength; i++) {
       TreeNode cur = queue.remove();
       // Do something...
       // Add the node
       queue.add(root.left);
       queue.add(root.right);
     }
   }
   ```
   
3. Use **In-order DFS** can get a **sorted array** for the **BST**;