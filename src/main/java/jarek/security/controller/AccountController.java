package jarek.security.controller;

import jarek.security.model.Account;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/user/")
public class AccountController {

    @GetMapping(path = "/register")
    public String registrationForm(Model model, Account account) {
        model.addAttribute("atr_account", account);

        return "registration-form";
    }

    @PostMapping(path = "/register")
    public String register(Model model, Account account, String passwordConfirm) {
        // tworzenie konta
        if (!account.getPassword().equals(passwordConfirm)) {
            model.addAttribute("atr_account", account);
            model.addAttribute("atr_errorMessage", "Passwords do not match.");

            return "registration-form";
        }

        return "redirect:/login";
    }
}
