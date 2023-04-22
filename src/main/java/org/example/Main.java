package org.example;

import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        List<CategoriesAndPurchases> purchasesToList = new ArrayList<>();


        // Стартуем сервер один(!) раз
        try (ServerSocket serverSocket = new ServerSocket(8989);) {
            // В цикле(!) принимаем подключения
            while (true) {
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    // Принимаем от клиента строку
                    String getAnswer = in.readLine();
                    // Преобразуем строку формата json в Объект покупки
                    Gson gson = new Gson();
                    CategoriesAndPurchases purchase = gson.fromJson(getAnswer, CategoriesAndPurchases.class);
                    //Добавляем объект покупки в корзину
                    purchasesToList.add(purchase);
                    //Получаем
                    String str = CategoriesAndPurchases.getMaxCategory(purchasesToList);
                    out.println(str);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}