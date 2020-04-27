package models;

public class User {

    private int id, salary,carsSold;
    private String name, email, role, password;
    private boolean blocked;

    public User() {
    }

    public User(int salary, String name, String email, String role, String password, boolean blocked) {
        this.salary = salary;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.blocked = blocked;
    }

    public User(int id, int salary, String name, String email, String role, String password, boolean blocked) {
        this.id = id;
        this.salary = salary;
        this.name = name;
        this.email = email;
        this.role = role;
        this.password = password;
        this.blocked = blocked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getCarsSold() {
        return carsSold;
    }

    public void setCarsSold(int carsSold) {
        this.carsSold = carsSold;
    }

    
    @Override
    public String toString() {
        return "User{" + "id=" + id + ", salary=" + salary + ", name=" + name + ", email=" + email + ", role=" + role + ", password=" + password + ", blocked=" + blocked + '}';
    }

}
