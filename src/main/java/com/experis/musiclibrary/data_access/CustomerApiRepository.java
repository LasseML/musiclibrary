package com.experis.musiclibrary.data_access;

import com.experis.musiclibrary.models.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class CustomerApiRepository {
    // Setting up the connection object we need.
    private String URL = ConnectionHelper.CONNECTION_URL;
    private Connection conn = null;

    //returns all customers in the database as a list of customers
    public ArrayList<Customer> getAllCustomers(){
        ArrayList<Customer> customers = new ArrayList<>();
        try {
            //connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("SELECT CustomerID, FirstName, LastName, Country, " +
                            "PostalCode, Phone, Email FROM Customer");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                customers.add(
                        new Customer(
                                resultSet.getInt("CustomerId"),
                                resultSet.getString("FirstName"),
                                resultSet.getString("LastName"),
                                resultSet.getString("Country"),
                                resultSet.getString("PostalCode"),
                                resultSet.getString("Phone"),
                                resultSet.getString("Email")
                        )
                );
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return customers;
    }

    //Used to create a customer in the database, not sending a customer id makes the private key auto increment
    public Boolean createCustomer(Customer customer){
        Boolean success = false;
        try{
            //connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("INSERT  INTO customer(FirstName, LastName, Country, " +
                            "PostalCode, Phone, Email ) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getCountry());
            preparedStatement.setString(4, customer.getPostalCode());
            preparedStatement.setString(5, customer.getPhoneNumber());
            preparedStatement.setString(6, customer.getEmail());

            int result = preparedStatement.executeUpdate();
            success = (result != 0);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try{
                conn.close();
            }catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return success;
    }

    //updates a customer in the database, returnes true if successful
    public Boolean updateCustomer(Customer customer){
        Boolean success = false;
        try{
            //connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("UPDATE customer SET CustomerId = ?, FirstName = ?, LastName = ?," +
                            " Country = ?, PostalCode = ?, Phone = ?, Email = ? WHERE CustomerId = ?");
            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.setString(2, customer.getFirstName());
            preparedStatement.setString(3, customer.getLastName());
            preparedStatement.setString(4, customer.getCountry());
            preparedStatement.setString(5, customer.getPostalCode());
            preparedStatement.setString(6, customer.getPhoneNumber());
            preparedStatement.setString(7, customer.getEmail());
            preparedStatement.setInt(8, customer.getCustomerId());

            int result = preparedStatement.executeUpdate();
            success = (result != 0);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try{
                conn.close();
            }catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return success;
    }

    public LinkedHashMap<String, Integer> getPopularCountry(){
        LinkedHashMap<String, Integer> popularCountry = new LinkedHashMap<>();
        try {
            //connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("""
                            SELECT Country, count(Country) as CountryCount
                            FROM Customer
                            GROUP BY Country
                            ORDER BY CountryCount desc""");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                popularCountry.put(resultSet.getString("Country"),
                        resultSet.getInt("CountryCount"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return popularCountry;
    }

    //finds the highest spends and returns a list og all customers that have made an order, according to the total spend
    //Linked list used to keep the order
    public LinkedHashMap<Integer, String> getHighestSpender(){
        LinkedHashMap<Integer, String> highestSpender = new LinkedHashMap<>();
        try {
            //connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("""
                            SELECT CustomerId, round(sum(Total), 2) as Total
                            FROM Invoice
                            GROUP BY CustomerId
                            ORDER BY Total desc""");
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                highestSpender.put(resultSet.getInt("CustomerId"),
                        resultSet.getString("Total"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return highestSpender;
    }

    //returnes a list of the customers favorite genre, returnes all that tie
    public LinkedHashMap<String, Integer> getPopularGenre(int id){
        LinkedHashMap<String, Integer> popularGenre = new LinkedHashMap<>();
        try {
            //connection
            conn = DriverManager.getConnection(URL);

            PreparedStatement preparedStatement =
                    conn.prepareStatement("""
                           WITH Countquery AS (
                           SELECT  Customer.FirstName, Customer.LastName, Genre.Name, count(Genre.GenreId) as GenreCount
                           FROM Customer
                                   INNER JOIN Invoice, InvoiceLine, Track, Genre
                                   WHERE Customer.CustomerId = Invoice.CustomerId
                                   AND  Invoice.InvoiceId = InvoiceLine.InvoiceId
                                   AND  InvoiceLine.TrackId = Track.TrackId
                                   AND  Genre.GenreId = Track.GenreId
                                   AND Customer.CustomerId=?
                           group by Genre.GenreId
                           ORDER BY GenreCount)
                           select Name, GenreCount
                           FROM Countquery
                           where (select max(GenreCount) from Countquery) = GenreCount
                           """);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                popularGenre.put(resultSet.getString("Name"),
                        resultSet.getInt("GenreCount"));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        finally {
            try {
                conn.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return popularGenre;
    }
}
