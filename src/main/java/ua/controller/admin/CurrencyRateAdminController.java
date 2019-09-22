package ua.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.form.CurrencyRateForm;
import ua.model.CurrencyRate;
import ua.service.CurrencyRateOtherService;
import ua.service.CurrencyRateService;

import java.util.List;

@Controller
@PreAuthorize("hasRole(T(ua.model.User).ROLE_ADMIN)")
@SuppressWarnings("Duplicates")
public class CurrencyRateAdminController {

    private final CurrencyRateService currencyRateService;

    private final CurrencyRateOtherService currencyRateOtherService;

    @Autowired
    public CurrencyRateAdminController(CurrencyRateService currencyRateService,
                                       CurrencyRateOtherService currencyRateOtherService){
        this.currencyRateService = currencyRateService;
        this.currencyRateOtherService = currencyRateOtherService;
    }

    @GetMapping("/currency/edit")
    public String currencyEditPage(Model model){
        List<CurrencyRate> currencyRateList = currencyRateService.getAll();
        CurrencyRateForm currencyRateForm = new CurrencyRateForm();
        currencyRateForm.setCurrencyRateList(currencyRateList);
        model.addAttribute("currencyRateForm", currencyRateForm);
        return "admin/currencyEdit";
    }

    @PostMapping("/currency/save")
    public String currencySaveAction(@ModelAttribute("currencyRateForm") CurrencyRateForm currencyRateForm){
        List<CurrencyRate> currencyRateList = currencyRateForm.getCurrencyRateList();
        currencyRateService.saveAll(currencyRateList);
        return "redirect:/currency/edit";
    }

    @RequestMapping("/currency/edit/load-nbu")
    public String currencyEditLoadNBUPage(Model model){
        List<CurrencyRate> currencyRateOtherLis = currencyRateOtherService.getAllNBU();
        CurrencyRateForm currencyRateForm = new CurrencyRateForm();
        currencyRateForm.setCurrencyRateList(currencyRateOtherLis);
        model.addAttribute("currencyRateForm", currencyRateForm);
        return "admin/currencyEdit";
    }

    @RequestMapping("/currency/edit/load-other")
    public String currencyEditLoadOtherPage(Model model){
        List<CurrencyRate> currencyRateOtherLis = currencyRateOtherService.getAllOther();
        CurrencyRateForm currencyRateForm = new CurrencyRateForm();
        currencyRateForm.setCurrencyRateList(currencyRateOtherLis);
        model.addAttribute("currencyRateForm", currencyRateForm);
        return "admin/currencyEdit";
    }
}
