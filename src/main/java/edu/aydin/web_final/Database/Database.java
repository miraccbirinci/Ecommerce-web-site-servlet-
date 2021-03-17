package edu.aydin.web_final.Database;

import edu.aydin.web_final.Model.Box;
import edu.aydin.web_final.Model.Product;
import edu.aydin.web_final.Model.User;

import java.sql.*;
import java.util.ArrayList;

public class Database {

    private static Database instance = null;
    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    private Database() {
    }

    /**
     * @return Database instance, singleton design pattern has added to the class
     * for allocating the same instance every time.
     */
    public static Database getInstance() { //singleton
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }


    /**
     * Creates the MySql connection with given connection string
     * @return Connection
     */
    public Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager
                    .getConnection("jdbc:mysql://sql7.freemysqlhosting.net/sql7391671?"
                            + "user=sql7391671&password=lGKN47Wsd9");
            System.out.println("database is up");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Creating a user in Users table.
     *
     * @param name that user has entered
     * @param password that user has entered
     */
    public void registerUser(String name, String password) {
        connection = getConnection(); // get the connection
        try {
            String query = "insert into Users "
                    + "(name,password) "
                    + "values (?,?)";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            // executing the query.
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


    /**
     * Inserts into Box table, with given userId and productId
     *
     * @param userId User ıd that will be inserted to the box
     * @param productId Product id that will be inserted to the box
     */
    public void addToBox(String userId, String productId){
        try{
            // get the connection
            connection = getConnection();
            String query = "INSERT INTO Box (UserId,ProductId) values (?,?)";
            preparedStatement = connection.prepareStatement(query);
            // setting the statement with parameters.
            preparedStatement.setInt(1, Integer.parseInt(userId));
            preparedStatement.setInt(2, Integer.parseInt(productId));
            // execute
            preparedStatement.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // close the connection if it is in use.
            if (connection != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


    /**
     * Deletes the record related the given boxID
     * @param boxId that will be deleted in the box table
     */
    public void clearBox (int boxId){
        try{
            // get connection
            connection = getConnection();
            String query = "Delete From Box WHERE BoxId =?";
            preparedStatement = connection.prepareStatement(query);
            // set the query
            preparedStatement.setInt(1,boxId);
            // execute the query
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // close the connection if it is in use.
            if (connection != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates the stock value of the product.
     * @param productId that will be updated with the new stock value.
     */
    public void updateStock(int productId){
        try{
            connection = getConnection(); // get the connection
            String query = "UPDATE Products SET ProductStock = (ProductStock -1) WHERE ID =?";
            preparedStatement = connection.prepareStatement(query);
            // set the query
            preparedStatement.setInt(1,productId);
            // execute
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // close the connection if it is in use.
            if (connection != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
    }


    /**
     * @param userId that will be used for having the products that has been added to the user shopping box.
     * @return List of Box
     */
    public ArrayList<Box> getBoxIds(String userId){
        ArrayList<Box> boxIds = new ArrayList<>();
        try{
            connection = getConnection(); // get the connection
            String query ="SELECT * FROM Box INNER JOIN Users U on Box.UserId = U.ID " +
                    "WHERE UserId=?";
            preparedStatement = connection.prepareStatement(query);
            // setting the prepare statement
            preparedStatement.setInt(1, Integer.parseInt(userId));
            // executing the statement and assigning the resultSet with returned query.
            resultSet = preparedStatement.executeQuery();

            // iterating the resultSet
            while (resultSet.next()){
                int boxId = resultSet.getInt("BoxId");
                int UserId = resultSet.getInt("UserId");
                int productId = resultSet.getInt("ProductId");
                // Adding the Box instance that created with result set credentials
                boxIds.add(new Box(boxId,UserId,productId));

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            // closing the connection if it in use.
            if (connection != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return boxIds; // return the list of Boxes.
    }

    /**
     * Finds the products that given user has been added to his box.
     *
     * @param userId that will be used for getting the products related the User.
     * @return List of products that includes the all products which user has added to the box.
     */
    public ArrayList<Product> getBoxByUserId(String userId){
        ArrayList<Product> products = new ArrayList<>();
        try {
            connection = getConnection(); // creating the connection
            String query ="SELECT * FROM Box INNER JOIN Users U on Box.UserId = U.ID " +
                    "INNER JOIN Products P ON Box.ProductId = P.ID WHERE UserId=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.parseInt(userId));
            // executing the query
            resultSet = preparedStatement.executeQuery();

            // iterating the result set
            while(resultSet.next()){
                String productId = resultSet.getString("P.ID");
                String productName = resultSet.getString("ProductName");
                int productPrice = resultSet.getInt("ProductPrice");
                int productStock = resultSet.getInt("ProductStock");
                String productDescription = resultSet.getString("ProductDescription");
                String imagePath = resultSet.getString("ImagePath");
                // creating the product with parameters that obtained in database.
                products.add(new Product(productId,productName, productPrice, productStock,productDescription, imagePath));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return products; // return the list of products.
    }


    /**
     * Returns the products.
     *
     * @return List of whole products in the database.
     */
    public ArrayList<Product> getProducts() {
        ArrayList<Product> products = new ArrayList<>();
        try {
            connection = getConnection(); // getting connection
            String query = "Select * from Products";
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query); // executing the query.

            // iterating the resultset
            while (resultSet.next()) {
                String productId = resultSet.getString("ID");
                String productName = resultSet.getString("ProductName");
                int productPrice = resultSet.getInt("ProductPrice");
                int productStock = resultSet.getInt("ProductStock");
                String productDescription = resultSet.getString("ProductDescription");
                String imagePath = resultSet.getString("ImagePath");
                // creating the product with parameters that obtained in database.
                products.add(new Product(productId,productName, productPrice, productStock,productDescription, imagePath));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                    statement.close();
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return products;
    }

    /**
     * Checks the if the user exist in the database.
     *
     * @param username that will be checking for existence.
     * @return User if it exist, otherwise null
     */
    public User getUser(String username) {
        try {
            connection = getConnection(); // getting the connection
            String query = "Select * from Users where Name =? ";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery(); // executing the query

            if (resultSet.next()) { // if the user exist in the database
                // creating the user
                User user = new User(resultSet.getString("ID"),resultSet.getString("Name"), resultSet.getString("Password"));
                return user;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                    preparedStatement.close();
                    resultSet.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return null; // user is not found in the database
    }
}
