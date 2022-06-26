package jarek.security.service;

import jarek.security.model.Account;
import jarek.security.model.AccountRole;
import jarek.security.model.dto.AccountResetPasswordRequest;
import jarek.security.repository.AccountRepository;
import jarek.security.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private PasswordEncoder passwordEncoder;
    private AccountRoleService accountRoleService;
    private AccountRoleRepository accountRoleRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, PasswordEncoder passwordEncoder, AccountRoleService accountRoleService, AccountRoleRepository accountRoleRepository) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountRoleService = accountRoleService;
        this.accountRoleRepository = accountRoleRepository;
    }

    public boolean register(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            return false;
        }

        // szyfrowanie hasła
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        account.setAccountRoles(accountRoleService.getDefaultRoles());

        // zapis do bazy
        accountRepository.save(account);

        return true;
    }

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public void toggleLock(Long accountId) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getOne(accountId);
            account.setLocked(!account.isLocked());

            accountRepository.save(account);
        }
    }

    public void remove(Long deletedId) {
        if (accountRepository.existsById(deletedId)) {
            Account account = accountRepository.getReferenceById(deletedId);
            if (!account.isAdmin()) {
                accountRepository.delete(account);
            }
        }
//        accountRepository.deleteById(deletedId);
    }

    public Optional<Account> findById(Long accountResetedPasswordId) {
        return accountRepository.findById(accountResetedPasswordId);
    }

    public void resetPassword(AccountResetPasswordRequest request) {
        if (accountRepository.existsById(request.getAccountId())) {
            Account account = accountRepository.getReferenceById(request.getAccountId());

            account.setPassword(passwordEncoder.encode(request.getResetpassword()));

            accountRepository.save(account);
        }
    }

    public void editRoles(Long accountId, HttpServletRequest request) {
        if (accountRepository.existsById(accountId)) {
            Account account = accountRepository.getReferenceById(accountId);

            Map<String, String[]> formParameters = request.getParameterMap();
            Set<AccountRole> newColletionOfRoles = new HashSet<>();

            for (String roleName : formParameters.keySet()) {
                String[] values = formParameters.get(roleName);

                if (values[0].equals("on")) {
//                    accountRoleRepository.findByName(roleName).ifPresent(newColletionOfRoles::add);
                    Optional<AccountRole> optionalAccountRole = accountRoleRepository.findByName(roleName);
                    if (optionalAccountRole.isPresent()) {
                        newColletionOfRoles.add(optionalAccountRole.get());
                    }
                }
            }

            account.setAccountRoles(newColletionOfRoles);

            accountRepository.save(account);
        }
    }
}
