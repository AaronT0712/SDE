1. Typical used loop

   ```java
   while(cur != null || cur.next != null) {	// Do sth.}
   ```

   Because `null` don't have the `.next`, it would occurs error;
   
2. **Reverse**

   + Don't need to **change the .next**, try to **change the value!**;

   ```java
   ListNode cur = head, prev = null;
   while(cur != null) {
     ListNode nextNode = cur.next;
     cur.next = prev;
     prev = cur;
     cur = nextNode;
   }
   return prev;	// 注意嗷！
   ```
   
   