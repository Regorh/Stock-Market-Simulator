/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package stock;


import stock.controller.*;
import stock.model.GameManager;
import stock.model.Market;
import stock.model.Stock;
import stock.model.User;
import stock.view.Gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        GameController controller = new GameController();
         System.out.println(new App().getGreeting());


    }
}
