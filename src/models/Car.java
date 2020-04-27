 
package models;
 
public class Car {
   private int id ,price,balance ,model;
    private String company ,kind,color;

    public Car() {
    }

    public Car(int price, int balance, int model, String company, String kind, String color) {
        this.price = price;
        this.balance = balance;
        this.model = model;
        this.company = company;
        this.kind = kind;
        this.color = color;
    }

    public Car(int id, int price, int balance, int model, String company, String kind, String color) {
        this.id = id;
        this.price = price;
        this.balance = balance;
        this.model = model;
        this.company = company;
        this.kind = kind;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Car{" + "id=" + id + ", price=" + price + ", balance=" + balance + ", model=" + model + ", company=" + company + ", kind=" + kind + ", color=" + color + '}';
    }
  
}
