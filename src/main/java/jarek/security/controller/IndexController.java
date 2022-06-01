package jarek.security.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/")
@Data
public class IndexController {

    private String imie;
    private String nick;

    @Autowired
    public IndexController(String imie, String nick) {
        this.imie = imie;
        this.nick = nick;
    }

    @GetMapping(path = "/")
    public String index(Model model) {
        IndexController indexController = new IndexController(imie, nick);
        model.addAttribute("toSee", indexController.getImie());
        model.addAttribute("test2", indexController.getNick());

        return "index_J.html";
    }


    @GetMapping("/strona2")
    public String indexZLogowaniem(Model model) {
        IndexController indexController = new IndexController(imie, nick);
        model.addAttribute("toSee", indexController.getImie());
        model.addAttribute("test2", indexController.getNick());
        model.addAttribute("test3", "nr3");


        return "index_J.html";
    }

    @GetMapping(path = "/login")
    public String loginForm() {

        return "login-form";
    }
}
