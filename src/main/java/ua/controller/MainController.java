package ua.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.model.CurrencyRate;
import ua.service.CurrencyRateOtherService;
import ua.service.CurrencyRateService;

import java.util.List;

@Controller
public class MainController {

    private final CurrencyRateService currencyRateService;

    private final CurrencyRateOtherService currencyRateOtherService;

    @Autowired
    public MainController(CurrencyRateService currencyRateService, CurrencyRateOtherService currencyRateOtherService){
        this.currencyRateService = currencyRateService;
        this.currencyRateOtherService = currencyRateOtherService;
    }

    @GetMapping("/")
    public String indexPage(Model model){
        List<CurrencyRate> currencyRateList = currencyRateService.getAll();
        List<CurrencyRate> currencyRateOtherLis = currencyRateOtherService.getAllNBU();
        model.addAttribute("currencyRateList", currencyRateList);
        model.addAttribute("currencyRateOtherLis", currencyRateOtherLis);
        return "index";
    }
}
