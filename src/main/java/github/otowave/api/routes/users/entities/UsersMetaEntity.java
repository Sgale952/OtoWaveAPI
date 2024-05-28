package github.otowave.api.routes.users.entities;

import github.otowave.api.routes.common.entities.MetaEntity;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "meta", schema = "users")
public class UsersMetaEntity extends MetaEntity {
    private int subscribed;

    public UsersMetaEntity() {
    }

    public int getSubscribed() {
        return subscribed;
    }
    public void setSubscribed(int subscribed) {
        this.subscribed = subscribed;
    }
}
