package jarek.security.service;

import jarek.security.model.AccountRole;
import jarek.security.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountRoleService {

//    @Value("USER,ADMIN,KTOS_INNY") nadajemy role przy rejestracji
    @Value("USER")
    private String[] defaultRoles;

    private AccountRoleRepository accountRoleRepository;

    @Autowired
    public AccountRoleService(AccountRoleRepository accountRoleRepository) {
        this.accountRoleRepository = accountRoleRepository;
    }

    public Set<AccountRole> getDefaultRoles() {
        Set<AccountRole> accountRoles = new HashSet<>();

        for (String role : defaultRoles) {
            Optional<AccountRole> optionalAccountRole = accountRoleRepository.findByName(role);
            optionalAccountRole.ifPresent(accountRoles::add);
        }

        return accountRoles;
    }

    public List<AccountRole> getAll() {
        return accountRoleRepository.findAll();
    }
}
