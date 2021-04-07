package com.tigercard;

import com.tigercard.controller.TigerCardController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Scanner;

@SpringBootApplication
public class TigerCardApplication implements CommandLineRunner {

    @Autowired
    private TigerCardController tigerCardController;

    public static void main(String[] args) throws IOException, ParseException {
        SpringApplication.run(TigerCardApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        menu(scanner);
        int input =scanner.nextInt();
        while(input != 4){

            switch (input){
                case 1:
                    String dayPath = ResourceUtils.getFile("classpath:day.txt").getPath();
                    tigerCardController.processDayTransaction(Paths.get(dayPath).toAbsolutePath());
                    menu(scanner);
                    input =scanner.nextInt();
                    break;
                case 2:
                    String weeklyPath = ResourceUtils.getFile("classpath:weekly.txt").getPath();
                    tigerCardController.processWeeklyTransaction(Paths.get(weeklyPath).toAbsolutePath());
                    menu(scanner);
                    input =scanner.nextInt();
                    break;
                case 3:
                    String bothTxPath = ResourceUtils.getFile("classpath:data.txt").getPath();
                    tigerCardController.runAllTransaction(Paths.get(bothTxPath).toAbsolutePath());
                    menu(scanner);
                    input =scanner.nextInt();
                    break;
                default:
                    System.out.println("Please select the right option.");
                    menu(scanner);
                    input =scanner.nextInt();
                    break;
            }
        }
        scanner.close();
        System.out.println("Thank you! \n Good Bye...!!!");
    }

    private void menu(Scanner scanner){
        System.out.println("Please select the item." +
                "\n" +
                "1. Process Daily Capping.(./day.txt)" +
                "\n" +
                "2. Process Weekly Capping.(./weekly.txt)" +
                "\n" +
                "3. Process Both.(./data.txt)" +
                "\n" +
                "4. Exit");
    }








}