package github.otowave.api.routes.users.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users", schema = "users")
public class UsersEntity {
    @Id
    private int userID;
    private String email;
    private String passwrd;
    private boolean blocked;
    private String accessRole;

    public UsersEntity() {

    }

    public int getUserID() {
        return userID;
    }
    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswrd() {
        return passwrd;
    }
    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public boolean isBlocked() {
        return blocked;
    }
    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getAccessRole() {
        return accessRole;
    }
    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }

    @Override
    public String toString() {
        return "userID="+getUserID()+
                " passwrd="+getPasswrd()+
                " email="+getEmail()+
                " accessRole="+getAccessRole();
    }
}
