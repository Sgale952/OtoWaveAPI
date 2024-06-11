package github.otowave.api.routes.users.services;

import github.otowave.api.routes.users.entities.UsersMetaEntity;
import github.otowave.api.routes.users.entities.UsersProfileEntity;
import github.otowave.api.routes.users.entities.UsersSecurityEntity;
import github.otowave.api.routes.users.repositories.UsersMetaRepo;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersSecurityRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class Accessor {
    @Autowired
    UsersProfileRepo usersProfileRepo;
    @Autowired
    UsersMetaRepo usersMetaRepo;
    @Autowired
    UsersSecurityRepo usersSecurityRepo;

    public Mono<Integer> login(String email, String password) {
        return usersSecurityRepo.findByEmailAndPassword(email, password)
                .map(UsersSecurityEntity::getItemID);
    }

    public Mono<Integer> register(String username, String email, String password) {
        return saveProfileEntity(username)
                .flatMap(userID -> saveSecurityEntity(userID, email, password).then(saveMetaEntity(userID)));
    }

    private Mono<Integer> saveProfileEntity(String username) {
        return usersProfileRepo.saveWithUsername(username)
                .map(UsersProfileEntity::getItemID);
    }

    private Mono<Integer> saveSecurityEntity(int userID, String email, String password) {
        return usersSecurityRepo.saveWithEmailAndPassword(userID, email, password, "AUTHOR")
                .map(UsersSecurityEntity::getItemID);
    }

    private Mono<Integer> saveMetaEntity(int userID) {
        return usersMetaRepo.save(new UsersMetaEntity(userID))
                .map(UsersMetaEntity::getItemID);
    }
}
