package github.otowave.api.routes.users.models;

import github.otowave.api.routes.actions.Models.UserActionsModel;
import github.otowave.api.routes.common.models.FaceModel;
import github.otowave.api.routes.common.models.ProfileModel;

import java.time.LocalDateTime;

public class UserProfileModel extends ProfileModel {
    private final int headerID;
    private final int subscribed;
    private final UserActionsModel userActionsModel;

    public UserProfileModel(FaceModel userFace, String tale, int likes, LocalDateTime created, int headerID, int subscribed, UserActionsModel userActionsModel) {
        super(userFace, tale, likes, created);
        this.headerID = headerID;
        this.subscribed = subscribed;
        this.userActionsModel = userActionsModel;
    }

    public int getHeaderID() {
        return headerID;
    }

    public int getSubscribed() {
        return subscribed;
    }

    public UserActionsModel getUserActionsModel() {
        return userActionsModel;
    }
}
