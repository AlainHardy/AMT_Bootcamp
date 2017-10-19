package ch.heigvd.amt.bootcamp.model;

/**
 *
 * @author Alain
 */
public class Car {
   private final String brand;
   private String color;
   private int horsePower;
   private int price;

   public Car(String brand, String color, int horsePower, int price) {
      this.brand = brand;
      this.color = color;
      this.horsePower = horsePower;
      this.price = price;
   }

   public String getBrand() {
      return brand;
   }

   public String getColor() {
      return color;
   }

   public int getHorsePower() {
      return horsePower;
   }

   public int getPrice() {
      return price;
   }

   public void setColor(String color) {
      this.color = color;
   }

   public void setHorsePower(int horsePower) {
      this.horsePower = horsePower;
   }

   public void setPrice(int price) {
      this.price = price;
   }   
   
}
