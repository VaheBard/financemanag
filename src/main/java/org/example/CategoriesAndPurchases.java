package org.example;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CategoriesAndPurchases {
    private final String title;
    private final String date;
    private final int sum;

    public CategoriesAndPurchases(String title, String date, int sum) {
        this.title = title;
        this.date = date;
        this.sum = sum;
    }

    public int getSum() {
        return sum;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public static Map<String, String> productAndCategory;

    //создали пустую мапу для того чтобы в него загружать продукты с соответствующими категориями
    static {
        try {
            productAndCategory = loadCategoriesFromTSV();//процес загрузки продуктов с категориями
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Map<String, String> loadCategoriesFromTSV() throws IOException {
        /*Этот метод загружает товар(ключ) с соответсвующей категорией(значение) в мапу, из фпйла tsv*/
        Map<String, String> temp = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("categories.tsv"))) {

            while (true) {
                String lineByLine = br.readLine();
                if (lineByLine == null) {
                    break;
                }
                String[] lineToArray = lineByLine.split("\t");
                temp.put(lineToArray[0], lineToArray[1]);
            }
        }
        return temp;
    }

    public static String getMaxCategory(List<CategoriesAndPurchases> purchases) {
        //этот метод принимает лист покупок("тапки", "2023.04.22", 98) и возвращает название категории по которой выполнено больше всех покупок

        Map<String, String> categories = getProductAndCategory();//в эту мапу загружаем существующие товары с соответствующими категориями(из tsv файла)

        //заводим мапу куда будем положить ключ категория, значение сумма всех покупок по данной категории
        Map<String, Integer> purchasesSumByCategory = new HashMap<>();//key = "category", value = sumAllPurchasesByThisCategory

        for (CategoriesAndPurchases c : purchases) {//в цикле перебираем лист покупок

            String category;//ниже к этой переменной присвоится название категории данного товара

            String title = c.getTitle();//здесь название товара данной покупки

            //значение category получится из мапы categories ключом title, а при отсутствии такого ключа по умолчанию будет "Другое"
            category = categories.getOrDefault(title, "Другое");

            if (purchasesSumByCategory.containsKey(category)) {
                int amount = purchasesSumByCategory.get(category);
                purchasesSumByCategory.replace(category, amount + c.getSum());
            } else {
                purchasesSumByCategory.put(category, c.getSum());
            }
        }

        int maxSum = 0;
        String key = null;
        for (Map.Entry<String, Integer> entryMap : purchasesSumByCategory.entrySet()) {
            //в этом цикле определяем название категории по которой выполнено больше всех в сумме покупок
            if (entryMap.getValue() > maxSum) {
                key = entryMap.getKey();//сюда передается название категории
                maxSum = entryMap.getValue();//здесь будет сумма всех покупок по данной категории
            }
        }

        JSONObject maxSumByCategory = new JSONObject();
        maxSumByCategory.put(key, maxSum);

        return maxSumByCategory.toJSONString();
    }

    private static Map<String, String> getProductAndCategory() {
        return productAndCategory;
    }
}
