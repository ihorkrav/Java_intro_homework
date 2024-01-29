package org.example;

import java.sql.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //variables to set a server
        String username = "root";
        String password = "";
        String host = "localhost";
        String port = "3306";
        String database = "java";
        String strconn = "jdbc:mariadb://"+host+":"+port+"/"+database;
        workWithDb(strconn, username, password);
    }
    private static void workWithDb(String strconn, String username, String password){
        int choise =0;
        Scanner scanner = new Scanner(System.in);
        while(choise!=5) {
            System.out.println("What to do?:");
            System.out.println("1.Show all categories\n2.Add new category\n3.Remove a category\n4.Change a category\n5.Exit");
            choise = scanner.nextInt();
            switch (choise) {
                case 1:
                    showCategoryList(strconn, username, password);
                    break;
                case 2:
                    insertCategory(strconn, username, password);
                    break;
                case 3:
                    deleteCategory(strconn, username, password);
                    break;
                case 4:
                    editCategory(strconn, username, password);
                    break;
            }
        }
    }
    private static void showCategoryList(String strConn, String userName, String password){
        try(Connection conn = DriverManager.getConnection(strConn,userName,password)) {
            Statement statement = conn.createStatement();
            String selectStatement = "SELECT * FROM categories";
            // Execute the SQL query
            ResultSet result = statement.executeQuery(selectStatement);
            //output every element in result
            while(result.next()){
                System.out.println(""+result.getInt("id")+"\t"+result.getString("name")+"\t"+result.getString("description"));
            }
            // Close the resources
            result.close();
            System.out.println("Success");
        }
        catch(Exception ex) {
            System.out.println("Output error: "+ex.getMessage());
        }
    }
    private static void insertCategory(String strConn, String userName, String password) {
        Scanner input = new Scanner(System.in);
        //setting parameters
        CategoryCreate categoryCreate = new CategoryCreate();
        System.out.println("Вкажіть назву категорії:");
        categoryCreate.setName(input.nextLine());
        System.out.println("Вкажіть опис категорії:");
        categoryCreate.setDescription(input.nextLine());

        try(Connection conn = DriverManager.getConnection(strConn,userName,password)) {
            Statement statement = conn.createStatement();
            String insertQuery = "INSERT INTO categories (name, description) VALUES (?, ?)";
            // Create a PreparedStatement
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            // Set values for the placeholders
            preparedStatement.setString(1, categoryCreate.getName());
            preparedStatement.setString(2, categoryCreate.getDescription());
            // Execute the SQL query
            int rowsAffected = preparedStatement.executeUpdate();
            // Close the resources
            preparedStatement.close();
            System.out.println("Rows affected: " + rowsAffected);
            System.out.println("Category inserted successfully.");
        }
        catch(Exception ex) {
            System.out.println("Помилка створення категорії: "+ex.getMessage());
        }
    }
    private static void editCategory(String strConn, String userName, String password){
        Scanner scanner = new Scanner(System.in);
        //setting parameters
        System.out.println("Enter id of a category you want to edit");
        int edit = scanner.nextInt();
        scanner.nextLine();
        CategoryCreate categoryCreate = new CategoryCreate();
        System.out.println("Вкажіть назву категорії:");
        categoryCreate.setName(scanner.nextLine());
        System.out.println("Вкажіть опис категорії:");
        categoryCreate.setDescription(scanner.nextLine());

        try(Connection conn = DriverManager.getConnection(strConn,userName,password)) {
            Statement statement = conn.createStatement();
            //sql command to update element with certain id
            String insertQuery = String.format("UPDATE categories SET name= '%s', description= '%s' WHERE id = %d",categoryCreate.getName(), categoryCreate.getDescription(), edit);
            // Create a PreparedStatement
            PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
            // Set values for the placeholders
            preparedStatement.setString(1, categoryCreate.getName());
            preparedStatement.setString(2, categoryCreate.getDescription());
            // Execute the SQL query
            int rowsAffected = preparedStatement.executeUpdate();
            // Close the resources
            preparedStatement.close();
            System.out.println("Rows affected: " + rowsAffected);
            System.out.println("Category edited successfully.");
        }
        catch(Exception ex) {
            System.out.println("Помилка створення категорії: "+ex.getMessage());
        }
    }
    private static void deleteCategory(String strConn, String userName, String password){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id of a category to remove");
        //id of an element to delete
        int remove = scanner.nextInt();
        try(Connection conn = DriverManager.getConnection(strConn,userName,password)) {
            Statement statement = conn.createStatement();
            //sql command to delete element with set id
            String selectStatement = String.format("DELETE FROM categories WHERE id = %d",remove);
            // Create a PreparedStatement
            ResultSet result = statement.executeQuery(selectStatement);
            // Close the resources
            result.close();
            System.out.println("Success");
        }
        catch(Exception ex) {
            System.out.println("Output error: "+ex.getMessage());
        }
    }
    private static void createNewTable(String strConn, String userName, String password) {
        //checking if connection is working
        try(Connection conn = DriverManager.getConnection(strConn,userName,password)){
            Statement statement = conn.createStatement();
            //sql command to create a new table with set parameters
            String sql = "CREATE TABLE IF NOT EXISTS categories ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY,"
                    + "name VARCHAR(255) NOT NULL,"
                    + "description TEXT"
                    + ")";
            statement.execute(sql);
            statement.close();
            System.out.println("-----Таблицю 'categories' успішно створено-------");

        }catch(Exception ex) {
            System.out.println("Помилка підключення: "+ex.getMessage());
        }
    }
    private static void workingWithArray(){
        int n =10;
        int [] mas = new int[n];

        Random rand = new Random();
        int value = rand.nextInt();
        System.out.println(getRandom(5,20));
        for(int i=0; i<n; i++){
            mas[i] = getRandom(20,50);
        }
        System.out.println("Array:");
        for(var item : mas){
            System.out.printf("%d\t", item);
        }
        System.out.println("Sorted array:");
        Arrays.sort(mas);
        for(var item : mas){
            System.out.printf("%d\t", item);
        }
    }
    private static void inputName(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("What``s your name?");
        String name = scanner.nextLine();
        System.out.printf("Hello %s!\n",name);
        System.out.printf("Hello "+name+"!\n");
    }
    private static int getRandom(int min, int max){
        Random rand = new Random();
        return rand.nextInt(max - min)+min;
    }

    private static void sortPerson(){
        //making a list of a class elements
        Person[] list={
                new Person("Marcus", "Aluminus"),
                new Person("Shcipper", "Pinguinus"),
                new Person("Kovalski", "Pinguinus"),
                new Person("Rico", "Kaboom"),
                new Person("Privat", "Pinguinus")
        };
        System.out.println("Array");
        for (var item: list){
            System.out.println(item);
        }
        System.out.println("Sorted array");
        Arrays.sort(list);
        for (var item: list){
            System.out.println(item);
        }

    }
}