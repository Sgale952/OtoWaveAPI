package github.otowave.api.routes.common.services.items.factory.songlist;

import github.otowave.api.routes.common.entities.ProfileEntity;
import github.otowave.api.routes.common.services.items.factory.item.Item;
import github.otowave.api.routes.images.models.DefaultImageIDs;
import reactor.core.publisher.Mono;

public abstract class SonglistItem extends Item {
    public SonglistItem(DefaultImageIDs defaultImageID) {
        super(defaultImageID);
    }

    public abstract Mono<Integer> upload(int creatorID, String title, String tale, boolean access);

    public abstract ProfileEntity makeProfileEntity(int creatorID, String title);

    public abstract Mono<Void> addMusic(int musicID);

    public abstract Mono<Void> removeMusic(int musicID);

    public abstract Mono<Void> like(int userID);

    public abstract Mono<Void> discard(int userID);
}
