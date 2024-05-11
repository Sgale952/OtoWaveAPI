package github.otowave.api.controlers.users;

import github.otowave.api.entities.users.UsersEntity;
import github.otowave.api.repositories.users.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersRepo usersRepo;

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