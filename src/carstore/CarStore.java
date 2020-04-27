package carstore;
import java.sql.DriverManager;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import models.Car;
import models.Sale;
import models.User;

public class CarStore extends Application {

    public static Stage stage;
    public static ObservableList<User> userList = FXCollections.observableArrayList();
    public static FilteredList<User> filterUserList = new FilteredList(userList, p -> true);
    public static ObservableList<String> roleList = FXCollections.observableArrayList();
    public static ObservableList<String> kindList = FXCollections.observableArrayList();
    public static ObservableList<String> colorList = FXCollections.observableArrayList();
    public static ObservableList<Car> carList = FXCollections.observableArrayList();
    public static ObservableList<Sale> saleList = FXCollections.observableArrayList();

    public static ObservableList<String> employeeList = FXCollections.observableArrayList();
    public static ObservableList<Number> employeeSaleList = FXCollections.observableArrayList();

    public static User currentUser = null;

    @Override
    public void start(Stage stage) throws Exception {
        kindList.addAll("Sedan", "Jeep", "Cope");
        colorList.addAll("RED", "BLUE", "BLACK", "GREEN", "YELLOW", "BROWN", "WHITE");
        roleList.add("Admin");
        roleList.add("Employee");

        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));

        Scene scene = new Scene(root);
        this.stage = stage;
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);

    }

    public static void change(String file, String title) {
        try {
            Parent root = FXMLLoader.load(CarStore.class.getClass().getResource(file));

            Scene scene = new Scene(root);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.hide();
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showError(String message) {

        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText(message);
        alert.setTitle("Error");
        alert.showAndWait();
    }

    /**
     * * ******************************************************
     */
    static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {

            connection = DriverManager.getConnection("jdbc:sqlite:cardb.db");
            return connection;

        } catch (Exception e) {
            System.out.println(e);

        }
        return null;
    }
//////////////////////////////

    public static int addUser(String name, String email, String role, String password, int salary, boolean blocked) {
        int result = 0;
        try {
            Connection con = getConnection();//1
            /* 
            Statement s = con.createStatement( );
            String query = "INSERT INTO users ( name, email,role, password,blocked,salary ) VALUES ( ";//2
            query+="'"+name+"',";
            query+="'"+email+"',";
            query+="'"+password+"',";
            query+=salary+",";
            query+=blocked+")";
            result=s.executeUpdate(query);
             */
            String query = "INSERT INTO users ( name, email,role, password,blocked,salary ) VALUES (?,?,?,?,?,?)";//2

            PreparedStatement ps = con.prepareStatement(query);//3
            ps.setString(1, name);//4
            ps.setString(2, email);
            ps.setString(3, role);
            ps.setString(4, password);
            ps.setBoolean(5, blocked);
            ps.setInt(6, salary);

            result = ps.executeUpdate();//5
            return result;//6

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;//0
    }

    public static int login(String email, String password) {

        try {
            Connection con = getConnection();
            String query = "SELECT  *  FROM  users  WHERE email=? AND password=? AND blocked==0 ";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet result = ps.executeQuery();
            
            if (result.next()) {
                int id = result.getInt("id");
                int salary = result.getInt("salary");
                String name = result.getString("name");
                String role = result.getString("role");
                boolean blocked = result.getBoolean("blocked");

                currentUser = new User(id, salary, name, email, role, password, blocked);
                System.out.println(1);
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return 0;
    }

    public static void loadUsers() {
        userList.clear();

        try {
            Connection con = getConnection();
            String query = "SELECT *  FROM  users  ";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                int salary = result.getInt("salary");
                String name = result.getString("name");
                String email = result.getString("email");
                String password = result.getString("password");
                String role = result.getString("role");
                boolean blocked = result.getBoolean("blocked");
                //  public User(int id, int salary, String name, String email, String role, String password, boolean blocked) 
                User user = new User(id, salary, name, email, role, password, blocked);

                userList.add(user);
            }
        } catch (Exception e) {

        }

    }

    public static void loadEmployeesSales() {
        employeeList.clear();
        employeeSaleList.clear();
        try {
            Connection con = getConnection();
            String query = " select sum(sales.quantity) as CarSold  ,users.name as employee from sales  ";
            query += "inner join users on sales.userid=users.id Group by users.name ";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {

                int cars = result.getInt("CarSold");
                String employee = result.getString("employee");
              //  System.out.println("name " + employee);
             //   System.out.println("cars " + cars);
                employeeList.add(employee);
                employeeSaleList.add(cars);

            }
        } catch (Exception e) {

        }

    }

    public static int updateUser(int id, String name, String email, String role, String password, int salary, boolean blocked) {
        int result = 0;
        try {
            Connection con = getConnection();//1
            String query = "update   users set name=?, email=?,role=?, password=?,blocked=?,salary=? where id=?  ";//2
            PreparedStatement ps = con.prepareStatement(query);//3
            ps.setString(1, name);//4
            ps.setString(2, email);
            ps.setString(3, role);
            ps.setString(4, password);
            ps.setBoolean(5, blocked);
            ps.setInt(6, salary);
            ps.setInt(7, id);
            result = ps.executeUpdate();//5
            return result;//6

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;//0
    }

    public static int deleteUser(int id) {
        int result = 0;
        try {
            Connection con = getConnection();//1
            String query = "delete from users  where id=?  ";//2
            PreparedStatement ps = con.prepareStatement(query);//3

            ps.setInt(1, id);
            result = ps.executeUpdate();//5
            return result;//6

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;//0
    }

/////////////////////////////////////////////////
    public static void loadCars() {
        carList.clear();

        try {
            Connection con = getConnection();
            String query = "SELECT *  FROM  Cars  ";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet result = ps.executeQuery();
            while (result.next()) {

                int id = result.getInt("id");
                int price = result.getInt("price");
                String company = result.getString("company");
                String kind = result.getString("kind");
                String color = result.getString("color");
                int model = result.getInt("model");

                int balance = result.getInt("balance");
                //  public Car(int id, int salary, String name, String email, String role, String password, boolean blocked) 
                Car Car = new Car(id, price, balance, model, company, kind, color);

                carList.add(Car);
            }
        } catch (Exception e) {

        }

    }

    public static int addCar(int price, int balance, int model, String company, String kind, String color) {

        int result = 0;
        try {
            Connection con = getConnection();//1
            String query = "INSERT INTO cars( company,  kind, color,  model,price,balance) values(?,?,?,?,?,?)";//2
            PreparedStatement ps = con.prepareStatement(query);//3
            ps.setString(1, company);//4
            ps.setString(2, kind);
            ps.setString(3, color);
            ps.setInt(4, model);
            ps.setInt(5, price);
            ps.setInt(6, balance);

            result = ps.executeUpdate();//5
            return result;//6

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;//0
    }

    public static int updateCar(int id, int price, int balance, int model, String company, String kind, String color) {

        int result = 0;
        try {
            Connection con = getConnection();//1
            String query = "UPDATE cars SET company = ?, kind =?,color =?, model = ?,price =?, balance =?  where id=?  ";//2
            PreparedStatement ps = con.prepareStatement(query);//3
            ps.setString(1, company);//4
            ps.setString(2, kind);
            ps.setString(3, color);
            ps.setInt(4, model);
            ps.setInt(5, price);
            ps.setInt(6, balance);
            ps.setInt(7, id);
            result = ps.executeUpdate();//5
            return result;//6

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;//0
    }

    public static int deleteCar(int id) {
        int result = 0;
        try {
            Connection con = getConnection();//1
            String query = "delete from cars  where id=?  ";//2
            PreparedStatement ps = con.prepareStatement(query);//3

            ps.setInt(1, id);
            result = ps.executeUpdate();//5
            return result;//6

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;//0
    }

    //////////////////////////////////////////////////////////
    public static void loadSales(int userId) {
        saleList.clear();
//id, price, quantity, carId, userId customer, saleDate;
        try {
            Connection con = getConnection();
            String query = "select sales.*,cars.company from sales inner join cars on sales.carid=cars.id  where userid=? ";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, userId);
            ResultSet result = ps.executeQuery();
            while (result.next()) {

                int id = result.getInt("id");
                int price = result.getInt("price");
                String customer = result.getString("customer");
                String saleDate = result.getString("saleDate");
                String company = result.getString("company");

                int quantity = result.getInt("quantity");

                int carId = result.getInt("carId");
                int total = quantity * price;
                Sale sale = new Sale(id, price, quantity, carId, userId, customer, saleDate, total, company);

                saleList.add(sale);
            }
        } catch (Exception e) {

        }

    }

    public static void loadSales() {
        saleList.clear();
//id, price, quantity, carId, userId customer, saleDate;
        try {
            Connection con = getConnection();
            String query = "select sales.*,cars.company from sales inner join cars on sales.carid=cars.id    ";
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet result = ps.executeQuery();
            while (result.next()) {

                int id = result.getInt("id");
                int price = result.getInt("price");
                int userId = result.getInt("userId");
                String customer = result.getString("customer");
                String saleDate = result.getString("saleDate");
                String company = result.getString("company");

                int quantity = result.getInt("quantity");

                int carId = result.getInt("carId");
                int total = quantity * price;
                Sale sale = new Sale(id, price, quantity, carId, userId, customer, saleDate, total, company);

                saleList.add(sale);
            }
        } catch (Exception e) {

        }

    }

    public static void loadSales(String fromDate, String toDate) {
        saleList.clear();
//id, price, quantity, carId, userId customer, saleDate;
        try {
            Connection con = getConnection();
            String query = "select sales.* ,users.name  as employee,cars.company  from sales  ";
            query += "  inner join users on sales.userid=users.id inner join cars ";
            query += "  on sales.carid=cars.id ";
            query += "  where saledate between ? AND ?  ";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, fromDate);
            ps.setString(2, toDate);
            ResultSet result = ps.executeQuery();
            while (result.next()) {

                int id = result.getInt("id");
                int price = result.getInt("price");
                int userId = result.getInt("userId");
                String customer = result.getString("customer");
                String saleDate = result.getString("saleDate");
                String company = result.getString("company");
                String employee = result.getString("employee");
                int quantity = result.getInt("quantity");

                int carId = result.getInt("carId");
                int total = quantity * price;
                //   public Sale(int id, int price, int quantity, int carId, int userId, int total, String customer, String saleDate, String company, String employee) {

                Sale sale = new Sale(id, price, quantity, carId, userId, total, customer, saleDate, company, employee);

                saleList.add(sale);
            }
        } catch (Exception e) {

        }

    }

    public static int addSale(int price, int quantity, int carId, int userId, String customer, String saleDate) {

        int result = 0;
        try {
            Connection con = getConnection();//1
            String query = "INSERT INTO Sales( price,  quantity, carId,  userId,customer,saleDate) values(?,?,?,?,?,?)";//2
            PreparedStatement ps = con.prepareStatement(query);//3
            ps.setInt(1, price);//4
            ps.setInt(2, quantity);
            ps.setInt(3, carId);
            ps.setInt(4, userId);
            ps.setString(5, customer);
            ps.setString(6, saleDate);

            result = ps.executeUpdate();//5
            return result;//6

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;//0
    }

   
   
}
