package github.otowave.api;

import github.otowave.otoimages.ImagesApi;
import github.otowave.otomusic.MusicApi;
import github.otowave.otoplaylists.PlaylistApi;
import github.otowave.otousers.UsersApi;

import static github.otowave.api.UploadHelper.multipartConfig;
import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        ipAddress("172.24.80.146");
        port(4567);
        multipartConfig();

        post("/register", UsersApi::upload);
        get("/login", UsersApi::login);
        post("/recovery", (UsersApi::recovery));

        get("/navigator", MusicApi::genreSort);
        get("/daily", MusicApi::dailyRandom);
        get("/search", (MusicApi::search));

        path("/navigator", () -> {
            get("/genres", (MusicApi::genres));
            get("/recent", (MusicApi::topPerMonth));
            get("/top", (MusicApi::topPerMonth));
        });

        path("/:userId", () -> {
            get("/activity", (UsersApi::activity));
            post("/subscribe-user", (UsersApi::subscribe));
            delete("/discard-user", (UsersApi::discard));

            patch("/ban", (UsersApi::ban));
            delete("/delete", (UsersApi::delete));
            patch("/change-name", (UsersApi::changeName));
            patch("/change-header", ((request, response) -> ""));

            post("/new-image", (ImagesApi::upload));
            post("/set-image", (ImagesApi::replace));

            post("/new-playlist", (PlaylistApi::upload));
            post("/like-playlist", (PlaylistApi::updateLikes));

            post("/new-music", (MusicApi::upload));
            post("/like-music", (MusicApi::like));
            delete("/discard-music", (MusicApi::discard));

            path("/:musicId", () -> {
                patch("/update-music", (MusicApi::update));
                delete("/delete-music", (MusicApi::delete));
            });
        });

        path("/:musicId", () -> {
            get("/data-music", (MusicApi::allData));
            //Try merging methods (updateInteraction)
            patch("/update-likes", (MusicApi::updateLikes));
            patch("/update-listens", (MusicApi::updateListens));
        });

        path("/:playlistId", () -> {
            get("/data-playlist", (PlaylistApi::allData));
            post("/fill-playlist", (PlaylistApi::addMusic));
            post("/empty-playlist", (PlaylistApi::deleteMusic));
            patch("/update-playlist", (PlaylistApi::update));
            delete("/delete-playlist", (PlaylistApi::delete));
        });
    }
}