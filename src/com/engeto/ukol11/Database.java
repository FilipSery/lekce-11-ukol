package com.engeto.ukol11;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements GoodsMethods {
    private final String adress = "jdbc:mysql://localhost:3306/item";
    @Override
    public String loadItemById(Integer id) {
        Item load = new Item(null,null,null,null,null,null,null);
        try (
            Connection c = DriverManager.getConnection(adress,"root","root")){
            Statement s = c.createStatement();
            s.executeQuery("SELECT * FROM item_ WHERE id = " + id);
            ResultSet loaded = s.getResultSet();
            if (loaded.next()) {
                load.setId(loaded.getInt("id"));
                load.setPartNo(loaded.getString("partNo"));
                load.setSerialNo(loaded.getString("serialNo"));
                load.setName(loaded.getString("name"));
                load.setDescription(loaded.getString("description"));
                load.setNumberInStock(loaded.getInt("numberInStock"));
                load.setPrice(loaded.getBigDecimal("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return load.getInfo();
    }

    @Override
    public void deleteAllOutOfStockItems() {
        try (Connection c = DriverManager.getConnection(adress,"root","root")) {
            PreparedStatement p = c.prepareStatement("DELETE FROM item_ WHERE numberInStock=0");

            p.executeUpdate();
            System.out.println("deleted all out of stock items");
            } catch (SQLException e) {
            e.printStackTrace();
            }
    }

    @Override
    public String loadAllAvailableItems() {
        String result = "All items in stock: \n";
        try (
            Connection c = DriverManager.getConnection(adress,"root","root")) {
            Statement s = c.createStatement();
            ResultSet loaded = s.executeQuery("SELECT id, name FROM item_ WHERE numberInStock>0");
            while (loaded.next()) {
                String x = "ID: "+loaded.getInt("id")+", name: "+loaded.getString("name")+"\n";
                result += x;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void saveItem(Item item) {
        try (Connection c = DriverManager.getConnection(adress,"root","root")) {
            PreparedStatement p = c.prepareStatement("INSERT into item_ (id, partNo, serialNo, name, description, numberInStock, price) VALUES (?,?,?,?,?,?,?)");
            p.setInt(1, item.getId());
            p.setString(2, item.getPartNo());
            p.setString(3, item.getSerialNo());
            p.setString(4, item.getName());
            p.setString(5, item.getDescription());
            p.setInt(6, item.getNumberInStock());
            p.setBigDecimal(7, item.getPrice());

            p.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updatePrice(Integer id, BigDecimal newPrice) {
        try (Connection c = DriverManager.getConnection(adress,"root","root")) {
            PreparedStatement p = c.prepareStatement("UPDATE item_ SET price = \"" + newPrice + "\" WHERE id= \"" + id+"\"");
            p.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
