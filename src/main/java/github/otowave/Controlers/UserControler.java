package github.otowave.Controlers;

import github.otowave.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/user")
public class UserControler {
    @Autowired
    private UserRepo userRepo;
    @PostMapping("/registrahion")
    public ResponseEntity Registrahion(RequestBody user){
        try{
           return ResponseEntity.ok("200");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error 500");
        }
    }
}
