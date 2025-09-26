# Наследование подтипов

  ```java
  abstract class Vehicle { void move(); }
  class Car extends Vehicle { }
  class Boat extends Vehicle { }
  ```

  ```java
  abstract class Document { abstract void render(); }
  final class PdfDocument extends Document { void render() {} }
  final class HtmlDocument extends Document { void render() {} }
  ```

# Наследование с ограничением

  ```java
  class Rectangle { int w, h; }
  class Square extends Rectangle { /* w = h */ }
  ```

  ```java
  class Account { void deposit(int x){} void withdraw(int x){} }
  class ReadOnlyAccount extends Account {
    @Override void deposit(int x){ throw new UnsupportedOperationException(); }
    @Override void withdraw(int x){ throw new UnsupportedOperationException(); }
  }
  ```

# Наследование с расширением

  ```java
  class Car { void drive(){ } }
  class AutonomousCar extends Car {
    void updateAutopilotFirmware(){ }
    void startAutopilot(){ }
  }
  ```
