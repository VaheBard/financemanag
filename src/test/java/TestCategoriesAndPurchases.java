import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.CategoriesAndPurchases;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestCategoriesAndPurchases {

    CategoriesAndPurchases purchase1 = new CategoriesAndPurchases("мыло", "2023.04.23", 50);
    CategoriesAndPurchases purchase2 = new CategoriesAndPurchases("булка", "2023.04.22", 200);
    CategoriesAndPurchases purchase3 = new CategoriesAndPurchases("шапка", "2020.11.07", 20000);
    CategoriesAndPurchases purchase4 = new CategoriesAndPurchases("курица", "2021.05.16", 350);
    CategoriesAndPurchases purchase5 = new CategoriesAndPurchases("сухарики", "2022.04.28", 50);
    CategoriesAndPurchases purchase6 = new CategoriesAndPurchases("тапки", "2020.08.19", 3456);
    CategoriesAndPurchases purchase7 = new CategoriesAndPurchases("колбаса", "2022.10.20", 300);

    List<CategoriesAndPurchases> purchases = new ArrayList<>();

    {
        purchases.add(purchase1);
        purchases.add(purchase2);
        purchases.add(purchase3);
        purchases.add(purchase4);
        purchases.add(purchase5);
        purchases.add(purchase6);
        purchases.add(purchase7);
    }

    @Test
    public void testCategoriesAndPurchases() throws ParseException, JsonProcessingException {
        String maxCategoryName = CategoriesAndPurchases.getMaxCategory(purchases);

        JSONObject json = new JSONObject();
        json.put("одежда", 23456);

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(maxCategoryName), mapper.readTree(String.valueOf(json)));
    }

    @Test
    public void testNotFromFileCategory() throws JsonProcessingException {
        CategoriesAndPurchases any = new CategoriesAndPurchases("Кровать", "2023.04.23", 112345);
        purchases.add(any);
        String str = CategoriesAndPurchases.getMaxCategory(purchases);
        System.out.println(str);

        JSONObject json = new JSONObject();
        json.put("Другое", 112345);

        ObjectMapper mapper = new ObjectMapper();
        assertEquals(mapper.readTree(str), mapper.readTree(String.valueOf(json)));

    }
}
