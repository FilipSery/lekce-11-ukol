package com.engeto.ukol11;
import java.math.BigDecimal;
import java.sql.*;
public class Main {

    public static void main(String[] args) {

        Database database = new Database();
    //1
        System.out.println("product by id:");
        System.out.println(database.loadItemById(2));
        System.out.println("-----------------\n");
    //2
        database.deleteAllOutOfStockItems();
        System.out.println("-----------------\n");
    //3
        System.out.println(database.loadAllAvailableItems());
    //4
        System.out.println("adding new item:");
        Item i = new Item(20,"dsf546ds", "ds5f4d64f", "boty","běžecké boty Adidas", 60, BigDecimal.valueOf(1200));
        database.saveItem(i);
        System.out.println("-----------------\n");
    //5
        System.out.println("updating price by id:");
        database.updatePrice(1, BigDecimal.valueOf(20000));
        System.out.println("-----------------\n");


        System.out.println("final state: ");
        System.out.println(database.loadAllAvailableItems());
    }

}
