package github.otowave.api.routes.users.controlers;

import github.otowave.api.routes.users.repositories.UsersSecurityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersSecurityRepo usersSecurityRepo;

    @PostMapping("/registration")
    public ResponseEntity Registration(RequestBody user) {
        try{
           return ResponseEntity.ok("200");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error 500");
        }
    }
}