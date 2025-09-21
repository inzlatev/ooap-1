1. FileNotFoundException — частный случай IOException.
   Любой "catch (IOException e)" обязан ловить и FileNotFoundException, потому что это тот же самый вид ошибки, только конкретнее. Если бы мы сделали объект, который содержит IOException внутри (обёртка), такой catch его бы не поймал — по типу он уже не IOException.

2. LinkedHashMap — тот же HashMap, только с запоминанием порядка.
   Там сохраняются все свойства HashMap, но с добавлением порядка обхода. Поэтому метод, который принимает HashMap, может принять и LinkedHashMap:

   ```java
   void accept(HashMap<String,Integer> m) { }
   accept(new LinkedHashMap<>());
   ```

   Если же сделать класс, который содержит поле HashMap (class X { HashMap<K,V> map; }), он уже не будет являться HashMap, не пройдёт instanceOf HashMap и не подойдет методам, где нужен именно этот тип.
