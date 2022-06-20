package jarek.security.controller;

import jarek.security.model.Account;
import jarek.security.model.AccountRole;
import jarek.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping(path = "/user/")
public class AccountController {

    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(path = "/register")
    public String registrationForm(Model model, Account account) {
        model.addAttribute("atr_account", account);

        return "registration-form";
    }

    @PostMapping(path = "/register")
    public String register(@Valid Account account,
                           BindingResult result,
                           String passwordConfirm,
                           ModelMap modelMap,
                           Model model) {
        // tworzenie komunikatu o błędach jeśli nie są spełnione warunki Account (NotEmpty, Size)
        // Uwaga: BindingResult musi być bezpośrednio po @Valid Account
        if(result.hasErrors()) {
            return registrationError(model, account, result.getFieldError().getDefaultMessage());
        }

        // tworzenie konta
        if (!account.getPassword().equals(passwordConfirm)) {
            return registrationError(model, account, "Passwords do not match.");
        }

        if (!accountService.register(account)) {
            return registrationError(model, account, "User with given username already exists.");
        }

        return "redirect:/login";
    }

    private String registrationError(Model model, Account account, String message) {
        model.addAttribute("atr_account", account);
        model.addAttribute("atr_errorMessage", message);

        return "registration-form";
    }
}
