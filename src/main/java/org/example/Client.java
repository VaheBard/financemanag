package org.example;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 8989;

        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // Объекты покупок

            CategoriesAndPurchases purchase = new CategoriesAndPurchases("тапк", "2023.04.22", 24298);


            // Перевод объекта в json-формат
            Gson gson = new Gson();
            String categoryToJson = gson.toJson(purchase);

            // Отправка покупки в json-формате на сервер
            out.println(categoryToJson);

            //Вывод в консоль результата
            String getMaxCategory = in.readLine();
            System.out.println("Максимальная по абсолютным тратам категория за весь период: " + getMaxCategory);

        }
    }
}
