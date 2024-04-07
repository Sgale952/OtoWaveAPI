package github.otowave.otoplaylists;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import github.otowave.otomusic.MusicApi;
import spark.Request;
import spark.Response;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaylistHandler {
    static JsonArray getMusicFilling(int playlistId, Connection conn) throws SQLException {
        JsonArray musicIds = new JsonArray();
        String sql = "SELECT music_id FROM fillingPlaylists WHERE playlist_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, playlistId);

        ResultSet rs = stmt.executeQuery();
        while(rs.next()) {
            musicIds.add(rs.getInt("music_id"));
        }

        return musicIds;
    }

    static int getCoverId(int playlistId, Connection conn) throws SQLException {
        String sql = "SELECT cover_id FROM playlists WHERE playlist_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setInt(1, playlistId);
        ResultSet rs = stmt.executeQuery();

        if(rs.next()) {
            return rs.getInt("cover_id");
        }

        throw new SQLException("Image not found");
    }

    //I don't know how, but it works
    static void deleteAllMusic(JsonArray musicIds, Request req, Response res) throws SQLException {
        List<Integer> musicIdList = new ArrayList<>();
        for(JsonElement element : musicIds) {
            int id = element.getAsJsonPrimitive().getAsInt();
            musicIdList.add(id);
        }

        for(int id : musicIdList) {
            req.attribute("musicId", id);
            MusicApi.delete(req, res);
        }
    }
}
