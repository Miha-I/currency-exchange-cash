package ua.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.service.CurrencyRateApiService;

@RestController
public class CurrencyRateApiController {

    private final CurrencyRateApiService currencyRateApiService;

    @Autowired
    public CurrencyRateApiController(CurrencyRateApiService currencyRateApiService){
        this.currencyRateApiService = currencyRateApiService;
    }

    @RequestMapping(value = "/api/currency/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<?> getCurrencyXML(){
        String xml = currencyRateApiService.getAllXML();
        return ResponseEntity.ok(xml);
    }

    @RequestMapping(value = "/api/currency/json", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> getCurrencyJSON(){
        String json = currencyRateApiService.getAllJSON();
        return ResponseEntity.ok(json);
    }
}
