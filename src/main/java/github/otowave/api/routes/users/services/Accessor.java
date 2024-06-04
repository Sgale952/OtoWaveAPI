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

    public Mono<Integer> register(String username, String email, String password) {
        return saveSecurityEntity(email, password)
                .flatMap(userID -> saveProfileEntity(userID, username).then(saveMetaEntity(userID)));
    }

    public Mono<Integer> login(String email, String password) {
        return usersSecurityRepo.findByEmailAndPassword(email, password).map(UsersSecurityEntity::getItemID);
    }

    private Mono<Integer> saveSecurityEntity(String email, String password) {
        return usersSecurityRepo.saveWithEmailAndPassword(email, password, "AUTHOR").map(UsersSecurityEntity::getItemID);
    }

    private Mono<Integer> saveProfileEntity(int userID, String username) {
        return usersProfileRepo.saveWithUsername(userID, username).map(UsersProfileEntity::getItemID);
    }

    private Mono<Integer> saveMetaEntity(int userID) {
        return usersMetaRepo.save(new UsersMetaEntity(userID)).map(UsersMetaEntity::getItemID);
    }
}
