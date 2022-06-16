package jarek.security.service;

import jarek.security.model.AccountRole;
import jarek.security.repository.AccountRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AccountRoleService {

    @Value("USER,ADMIN,KTOS_INNY")
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
}
