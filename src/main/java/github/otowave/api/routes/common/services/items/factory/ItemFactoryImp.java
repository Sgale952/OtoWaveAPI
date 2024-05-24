package github.otowave.api.routes.common.services.items.factory;

import github.otowave.api.routes.albums.repositories.AlbumsMetaRepo;
import github.otowave.api.routes.albums.repositories.AlbumsRepo;
import github.otowave.api.routes.common.models.items.ItemModel;
import github.otowave.api.routes.common.models.items.ItemTypes;
import github.otowave.api.routes.common.services.items.products.*;
import github.otowave.api.routes.common.services.items.products.user.ItemUser;
import github.otowave.api.routes.common.services.items.products.user.ItemUserHeader;
import github.otowave.api.routes.music.repositories.MusicMetaRepo;
import github.otowave.api.routes.music.repositories.MusicRepo;
import github.otowave.api.routes.playlists.repositories.PlaylistsMetaRepo;
import github.otowave.api.routes.playlists.repositories.PlaylistsRepo;
import github.otowave.api.routes.users.repositories.UsersProfileRepo;
import github.otowave.api.routes.users.repositories.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ItemFactoryImp implements ItemFactory {
    @Autowired
    private MusicRepo musicRepo;
    @Autowired
    private MusicMetaRepo musicMetaRepo;
    @Autowired
    private PlaylistsRepo playlistsRepo;
    @Autowired
    private PlaylistsMetaRepo playlistsMetaRepo;
    @Autowired
    private AlbumsRepo albumsRepo;
    @Autowired
    private AlbumsMetaRepo albumsMetaRepo;
    @Autowired
    private UsersRepo usersRepo;
    @Autowired
    private UsersProfileRepo usersProfileRepo;

    @Override
    public Mono<Item> makeItem(ItemTypes itemType, int itemID) {
        ItemModel itemModel = new ItemModel(itemType, itemID);
        return makeItem(itemModel);
    }

    @Override
    public Mono<Item> makeItem(ItemModel itemModel) {
        ItemTypes itemType = itemModel.itemType();
        return switch (itemType) {
            case MUSIC -> Mono.just(new ItemMusic(itemModel, musicRepo, musicMetaRepo));
            case PLAYLIST -> Mono.just(new ItemPlaylist(itemModel, playlistsRepo, playlistsMetaRepo));
            case ALBUM -> Mono.just(new ItemAlbum(itemModel, albumsRepo, albumsMetaRepo));
            case USER -> Mono.just(new ItemUser(itemModel, usersRepo, usersProfileRepo));
            case USER_HEADER -> Mono.just(new ItemUserHeader(itemModel, usersRepo, usersProfileRepo));
        };
    }
}