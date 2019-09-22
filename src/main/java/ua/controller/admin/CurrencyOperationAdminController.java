package ua.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.model.CurrencyOperation;
import ua.service.CurrencyOperationService;

import java.util.List;

@Controller
@PreAuthorize("hasRole(T(ua.model.User).ROLE_ADMIN)")
public class CurrencyOperationAdminController {

    private final CurrencyOperationService currencyOperationService;

    @Autowired
    public CurrencyOperationAdminController(CurrencyOperationService currencyOperationService){
        this.currencyOperationService = currencyOperationService;
    }

    @GetMapping("/operations/edit")
    public String currencyOperationList(Model model){
        List<CurrencyOperation> currencyOperationList = currencyOperationService.getAll();
        model.addAttribute("currencyOperationList", currencyOperationList);
        return "admin/operations";
    }

    @RequestMapping("/operation/delete/{id}")
    public String deleteCurrencyOperationAction(@PathVariable("id") int id){
        currencyOperationService.delete(id);
        return "redirect:/operations/edit";
    }
}
