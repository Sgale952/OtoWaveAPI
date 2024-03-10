package github.otowave.api;

import github.otowave.otomusic.MusicApi;
import github.otowave.otoplaylist.PlaylistApi;

import static spark.Spark.*;

public class Main {
    //TODO добавить методы регистрации пользователя
    public static void main(String[] args) {

        get("/daily", (request, response) ->
                MusicApi.dailyRandom());
        get("/search", ((request, response) ->
                MusicApi.search(request.queryParams("phrase"))));
        get("/navigator", ((request, response) ->
                MusicApi.sortByGenre(request.queryParams("genre"))));

        path("/navigator", () -> {
            get("/recent", ((request, response) ->
                    MusicApi.recentUploaded(0)));
            get("/top", ((request, response) ->
                    MusicApi.mostListensPerMonth(0)));
        });

        path("/:musicId", () -> {
            patch("/update-likes", ((request, response) ->
                    MusicApi.updateLikes()));
            patch("/update-listens", ((request, response) ->
                    MusicApi.updateListens()));
        });

        path("/:userId", () -> {
            get("/history", ((request, response) ->
                    ""));
            get("/liked", ((request, response) ->
                    ""));
            get("/subscribed", ((request, response) ->
                    ""));
            patch("/ban", ((request, response) ->
                    ""));
            patch("/change-header", ((request, response) ->
                    ""));
            patch("/change-name", ((request, response) ->
                    ""));
            post("/subscribe-user", ((request, response) ->
                    ""));
            delete("/discard-user", ((request, response) ->
                    ""));
            post("new-song", ((request, response) ->
                    MusicApi.add()));
            post("/like-song", ((request, response) ->
                    MusicApi.like()));
            delete("/discard-song", ((request, response) ->
                    MusicApi.discard()));
            post("/new-playlist", ((request, response) ->
                    PlaylistApi.add()));
            post("/add-playlist", ((request, response) ->
                    PlaylistApi.addSong()));
            post("like-playlist", ((request, response) ->
                    PlaylistApi.like()));

            path(":musicId", () -> {
                patch("/update-song", ((request, response) ->
                        MusicApi.update()));
                delete("/delete-song", ((request, response) ->
                        MusicApi.delete()));
            });

            path(":playlistId", () -> {
                patch("/update-playlist", ((request, response) ->
                        PlaylistApi.update()));
                delete("/delete-playlist", ((request, response) ->
                        PlaylistApi.delete()));
            });
        });
    }
}
