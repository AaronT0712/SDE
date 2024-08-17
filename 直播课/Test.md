1. `util` package 下，**TestUtil.java** 里面 记录了 **多种test cases**;
   其中，我们对 `events` 和 `item_record` 进行定义，而 `events` **包括了** `item_record`，因此我们需要注意他们的关系；

2. 写好 cases，我们需要在 `test` package 下，`unit.com.kafka.shoppingeventproducer` 下的 `EventControllerUnitTest.java` 中，**模拟这个 test case !**
   ``` java
   // It would not actually call `shoppingEventsProducer.kafkaSendEvent`
   // It simulates a perfect environment to just test THIS function (EventControlller)
   @Test
   void postShoppingEvent() throws Exception {
       //given
       var json = objectMapper.writeValueAsString(TestUtil.shoppingEventRecord());
       when(shoppingEventsProducer.kafkaSendEvent(isA(ShoppingEvent.class)))
               .thenReturn(null);  // For Defining
   
       //then, it would run this part
       mockMvc
               .perform(MockMvcRequestBuilders.post("/v1/shoppingevent/create")
                       .content(json)
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isCreated());
   }
   ```

   