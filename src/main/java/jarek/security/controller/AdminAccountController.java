package jarek.security.controller;

import jarek.security.model.Account;
import jarek.security.model.AccountRole;
import jarek.security.model.dto.AccountResetPasswordRequest;
import jarek.security.model.dto.AccountRoleChangeRequest;
import jarek.security.service.AccountRoleService;
import jarek.security.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin/account")
@PreAuthorize(value = "hasRole('ADMIN')")
public class AdminAccountController {

    private AccountService accountService;
    private AccountRoleService accountRoleService;

    @Autowired
    public AdminAccountController(AccountService accountService, AccountRoleService accountRoleService) {
        this.accountService = accountService;
        this.accountRoleService = accountRoleService;
    }

    @GetMapping(path = "/list")
    public String getUserList(Model model) {
        model.addAttribute("atr_accountsList", accountService.getAll());

        return "account-list";
    }

    @GetMapping(path = "/toggleLock")
    public String toggleLock(@RequestParam(name = "accountId") Long accountId) {
        accountService.toggleLock(accountId);

        return "redirect:/admin/account/list";
    }

    @GetMapping(path = "/remove")
    public String remove(@RequestParam(name = "accountId") Long deletedId) {
        accountService.remove(deletedId);

        return "redirect:/admin/account/list";
    }

    @GetMapping(path = "/resetPassword")
    public String resetPasword(Model model, @RequestParam(name = "accountId") Long accountResetedPasswordId) {
        Optional<Account> optionalAccount = accountService.findById(accountResetedPasswordId);
        if (optionalAccount.isPresent()) {
            model.addAttribute("atr_account", optionalAccount.get());
            return "account-passwordReset";
        }

        return "redirect:/admin/account/list";
    }

    @PostMapping(path = "/resetPassword")
    public String resetPassword(AccountResetPasswordRequest request) {
        accountService.resetPassword(request);

        return "redirect:/admin/account/list";
    }

    @GetMapping(path = "/editRoles")
    public String editRoles(Model model, @RequestParam(name = "accountId") Long accountEditedRolesId) {
        Optional<Account> optionalAccount = accountService.findById(accountEditedRolesId);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            model.addAttribute("atr_roles", accountRoleService.getAll());
            model.addAttribute("atr_account", account);

            return "account-roles";
        }

        return "redirect:/admin/account/list";
    }

    @PostMapping(path = "/editRoles")
    public String editRoles(Long accountId, HttpServletRequest request) {
        accountService.editRoles(accountId, request);

        return "redirect:/admin/account/list";
    }
}
