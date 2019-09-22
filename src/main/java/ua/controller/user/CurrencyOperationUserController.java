package ua.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.model.TotalAmountBuyAndSale;
import ua.service.CurrencyOperationService;
import ua.util.RequestUtil;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;

@Controller
@PreAuthorize("hasRole(T(ua.model.User).ROLE_USER)")
public class CurrencyOperationUserController {

    private final CurrencyOperationService currencyOperationService;

    @Autowired
    public CurrencyOperationUserController(CurrencyOperationService currencyOperationService){
        this.currencyOperationService = currencyOperationService;
    }

    @ResponseBody
    @PostMapping(value = "/currency/buy/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sddOperationBuyAjax(@PathVariable("code") String codeCurrency,
                                          @RequestParam("currency-amount") BigDecimal amountCurrency,
                                          HttpServletRequest request){

        if(RequestUtil.isAjax(request)){
            currencyOperationService.addOperationBuy(codeCurrency, amountCurrency);
            String json = "{\"success\":" + true + "}";
            return ResponseEntity.ok(json);
        }
        return ResponseEntity.badRequest().build();
    }

    @ResponseBody
    @PostMapping(value = "/currency/sell/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sddOperationSellAjax(@PathVariable("code") String codeCurrency,
                                          @RequestParam("currency-amount") BigDecimal amountCurrency,
                                          HttpServletRequest request){

        if(RequestUtil.isAjax(request)){
            currencyOperationService.addOperationSell(codeCurrency, amountCurrency);
            String json = "{\"success\":" + true + "}";
            return ResponseEntity.ok(json);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/operations")
    public String currencyOperationList(@Nullable @RequestParam("groupBy") String groupBy, Model model, Principal principal){
        String username = principal.getName();
        Map<LocalDate, Map<String, Map<String, TotalAmountBuyAndSale>>> groupCurrencyOperation =
                currencyOperationService.getAllByUsername(username, groupBy);
        model.addAttribute("groupBy", groupBy);
        model.addAttribute("groupCurrencyOperation", groupCurrencyOperation);
        return "user/operations";
    }
}
