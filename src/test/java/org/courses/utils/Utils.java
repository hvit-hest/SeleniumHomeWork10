package org.courses.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.courses.testdata.MenuItemModel;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.math.BigDecimal.ROUND_HALF_UP;

public class Utils {

    public void printTestingProperties() {
        Properties prop = loadPropertiesFile("testconfig.properties");
        prop.forEach((k, v) -> System.out.println(k + " " + v));
    }

    private Properties loadPropertiesFile(String filePath) {
        Properties prop = new Properties();
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
            prop.load(resourceAsStream);
        } catch (IOException ioe) {
            System.err.println("Unable to load properties file:" + filePath);
        }
        return prop;
    }

    public Properties getTestProperties() {
        return loadPropertiesFile("testconfig.properties");
    }

    public List<MenuItemModel> readMenuFromJson (String jsonArrayFile) {
        Type MenuType = new TypeToken<List<MenuItemModel>>() {}.getType();
        Gson gson = new Gson();
        JsonReader reader = null;
        try  {
             InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(jsonArrayFile);
             reader = new JsonReader(new InputStreamReader(resourceAsStream));
        } catch (NullPointerException ioe) {
            System.err.println("Unable to read json file:" + jsonArrayFile);
        }
        return gson.fromJson(reader, MenuType);
    }

    public static List<Integer> getNumberOfColor(String color) {
        Pattern pattern = Pattern.compile("\\d+");
        List<Integer> list = new ArrayList<Integer>();
        Matcher m = pattern.matcher(color);
        while (m.find()) {
            list.add(Integer.parseInt(m.group()));
        }
        return  list;
    }

    public static int  comparePrices (String priceOne, String priceTwo) {
        BigDecimal firstPrice = new BigDecimal(priceOne.replaceAll("[^0-9.]", "")).
                setScale(2, ROUND_HALF_UP );
        BigDecimal secondPrice = new BigDecimal(priceTwo.replaceAll("[^0-9.]", "")).
                setScale(2, ROUND_HALF_UP );

        return firstPrice.compareTo(secondPrice);
    }

    public static int  compareFonts (String priceOneFont, String priceTwoFont) {
        //Using comparePrices since no difference for the case. We just need numbers to compare
        return comparePrices (priceOneFont, priceTwoFont);
    }

}
