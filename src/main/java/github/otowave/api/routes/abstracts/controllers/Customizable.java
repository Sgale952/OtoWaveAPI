package github.otowave.api.routes.abstracts.controllers;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

public interface Customizable {
    String IMAGE_DIR = "D:\\Archive\\";

    void profile(int itemID);

    void resetImage(int itemID);

    //void changeImage(int itemID, MultipartFile file);

    void changeName(int itemID, String newName);

    void changeTale(int itemID, String newTale);

    void delete(int itemID);

    default <T> T getItemEntity(int itemID, CrudRepository<T, Integer> repo) {
        Optional<T> entity = repo.findById(itemID);
        return entity.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item ["+itemID+"] Not Found"));
    }
}