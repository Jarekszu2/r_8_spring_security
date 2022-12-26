package jarek.security.controller;

import jarek.security.service.AccountService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

@Controller
@RequestMapping(path = "/")
@Data
@AllArgsConstructor
public class IndexController {

    private String imi;
    private String nick;

    @Autowired
    public IndexController(String imi, String nick) {
        this.imi = imi;
        this.nick = nick;
    }

//    @GetMapping(path = "/")
//    public String index(Model model) {
//        IndexController indexController = new IndexController(imie, nick);
//        model.addAttribute("toSee", indexController.getImie());
//        model.addAttribute("test2", indexController.getNick());

//        return "index_J.html";
//    }

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
//    public String index(){
//        return "index";
//    }
    public String index(Model model, Principal authentication){
        if (authentication != null) { // jeżeli nie jesteśmy zalogowani authentication = null
            IndexController indexController = new IndexController(imi, nick);
            String cosTam = indexController.getImi();
            model.addAttribute("atr_user", authentication.getName()); // pokazanie zalogowanego użytkownika (Principal - zalogowany użytkownik - result to Object, ale praktycznie UserName)
            model.addAttribute("atr_imi", cosTam); // pokazanie zalogowanego użytkownika (Principal - zalogowany użytkownik - result to Object, ale praktycznie UserName)
//            model.addAttribute("atr_img", accountService.findPhotoByUsername(authentication.getName()));
        }
        return "index";
    }

    @GetMapping("/strona2")
    public String indexZLogowaniem(Model model) {
        IndexController indexController = new IndexController(imi, nick);
        String cos = indexController.nick;
//        model.addAttribute("toSee", indexController.getImie());
        model.addAttribute("test2", indexController.getNick());
        model.addAttribute("test3", cos);


        return "index_J.html";
    }

    @GetMapping(path = "/login")
    public String loginForm() {

        return "login-form";
    }

    @GetMapping("/profilePhoto")
    @PreAuthorize(value = "isAuthenticated()")
    public String profilePhoto(){
        return "photo-form";
    }

//    @PostMapping("/profilePhoto")
//    @PreAuthorize(value = "isAuthenticated()")
//    public String uploadPhoto(Principal principal, @RequestParam("photo") MultipartFile photo){
//        accountService.savePhotoFor(principal.getName(), photo);
//
//        return "redirect:/";
//    }
}
