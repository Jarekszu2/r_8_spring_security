package jarek.security.service;

import jarek.security.model.Account;
import jarek.security.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
////        if (username.equals("admin")) {
////            return User.builder()
////                    .username("admin")
////                    .password("$2a$12$BhI.YYii8fi97aZu/hCipu6jLijUCQpXp0g/SHV0cFARVdl.iy0RW")
//////                    BCrypt - zaszyfrowany admin
////                    .roles("ADMIN")
////                    .build();
//        }

        Optional<Account> optionalAccount = accountRepository.findByUsername(username);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();

            return User.builder()
                    .username(account.getUsername())
                    .password(account.getPassword())
                    .roles("USER")
                    .build();
        }

        throw new UsernameNotFoundException("Username not found.");
    }
}
