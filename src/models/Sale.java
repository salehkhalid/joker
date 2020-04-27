package models;

public class Sale {

    private int id, price, quantity, carId, userId,total;
    private String customer, saleDate,company,employee;
    
    public Sale() {
    }

    public Sale(int id, int price, int quantity, int carId, int userId, String customer, String saleDate ,int total ,String company  ) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.carId = carId;
        this.userId = userId;
        this.customer = customer;
        this.saleDate = saleDate;
        this.total=total;
        this.company=company;
        
    }

    public Sale(int id, int price, int quantity, int carId, int userId, int total, String customer, String saleDate, String company, String employee) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
        this.carId = carId;
        this.userId = userId;
        this.total = total;
        this.customer = customer;
        this.saleDate = saleDate;
        this.company = company;
        this.employee = employee;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }
 
   
 
 

}
