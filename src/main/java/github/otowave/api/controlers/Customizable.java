package github.otowave.api.controlers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface Customizable {
    default <T> T getEntity(int sourceID, CrudRepository<T, Integer> repo) {
        Optional<T> entity = repo.findById(sourceID);
        return entity.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Object ["+sourceID+"] Not Found"));
    }

    void profile(int sourceID);

    void changeImage(int sourceID, int prevImageID);

    void changeName(int sourceID, String newName);

    void changeTale(int sourceID, String newTale);

    void delete(int sourceID);
}