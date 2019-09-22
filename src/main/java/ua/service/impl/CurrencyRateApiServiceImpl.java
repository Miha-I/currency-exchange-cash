package ua.service.impl;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ua.model.CurrencyRate;
import ua.repository.CurrencyRateRepository;
import ua.service.CurrencyRateApiService;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CurrencyRateApiServiceImpl implements CurrencyRateApiService {

    private final CurrencyRateRepository currencyRateRepository;

    @Autowired
    public CurrencyRateApiServiceImpl(CurrencyRateRepository currencyRateRepository){
        this.currencyRateRepository = currencyRateRepository;
    }

    @Override
    public String getAllXML(){
        String currencyRateXMLString = null;
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element exchangerates = document.createElement("exchangerates");
            document.appendChild(exchangerates);
            for (CurrencyRate currencyRate : currencyRateList){
                Element row = document.createElement("row");
                exchangerates.appendChild(row);
                Element exchangerate = document.createElement("exchangerate");
                row.appendChild(exchangerate);
                exchangerate.setAttribute("ccy", currencyRate.getCurrencyCode());
                exchangerate.setAttribute("base_ccy", CurrencyRate.BASE_CCY);
                exchangerate.setAttribute("buy", currencyRate.getBuyRate().toString());
                exchangerate.setAttribute("sale", currencyRate.getSellRate().toString());
            }

            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            currencyRateXMLString = writer.getBuffer().toString();
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
        return currencyRateXMLString;
    }

    @Override
    public String getAllJSON(){
        Gson gson = new Gson();
        Map<String,String> currencyRateJSON;
        List<Map<String, String>> result = new ArrayList<>();
        List<CurrencyRate> currencyRateList = currencyRateRepository.findAll();
        for (CurrencyRate currencyRate : currencyRateList){
            currencyRateJSON = new LinkedHashMap<>();
            currencyRateJSON.put("ccy", currencyRate.getCurrencyCode());
            currencyRateJSON.put("base_ccy", CurrencyRate.BASE_CCY);
            currencyRateJSON.put("buy", currencyRate.getBuyRate().toString());
            currencyRateJSON.put("sale", currencyRate.getSellRate().toString());
            result.add(currencyRateJSON);
        }
        return gson.toJson(result);
    }
}
