package ua.parser.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import ua.model.CurrencyRate;
import ua.parser.CurrencyParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CurrencyParserNBU implements CurrencyParser {

    private Set<String> availableCurrencyRate;

    private String url;

    public CurrencyParserNBU(String url){
        this.url = url;
    }

    @Override
    public void setAvailableCurrencyRate(Set<String> availableCurrencyRate) {
        this.availableCurrencyRate = availableCurrencyRate;
    }

    @Override
    public Set<String> getAvailableCurrencyRate() {
        return availableCurrencyRate;
    }

    @Override
    public List<CurrencyRate> getCurrencyRateList() {
        List<CurrencyRate> currencyRateList = new ArrayList<>();
        if(availableCurrencyRate == null) return currencyRateList;

        try{
            InputStream inputStream = new URL(url).openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            JsonElement jsonElement = new JsonParser().parse(bufferedReader);
            if(jsonElement != null){
                JsonArray jsonArray = jsonElement.getAsJsonArray();
                if(jsonArray != null){
                    for (JsonElement jelement : jsonArray){
                        JsonObject jsonObject = jelement.getAsJsonObject();
                        JsonElement ccy = jsonObject.get("cc");
                        if(ccy == null) continue;
                        String ccyName = ccy.getAsString();
                        if(ccyName.equals("RUB"))
                            ccyName = "RUR";
                        if(availableCurrencyRate.contains(ccyName)){
                            JsonElement rateElement = jsonObject.get("rate");
                            if(rateElement == null)
                                continue;

                            BigDecimal rate = rateElement.getAsBigDecimal();
                            CurrencyRate currencyRate = new CurrencyRate(ccyName, rate, rate);
                            currencyRateList.add(currencyRate);
                        }
                    }
                }
            }
        } catch (IOException ignore){}
        return currencyRateList;
    }
}
