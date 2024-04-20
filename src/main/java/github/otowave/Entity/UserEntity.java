package github.otowave.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int user_id;
    int avatar_id;
    int header_id;
    String nickname;
    String email;
    String password;
    int access;

    public UserEntity() {
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAvatar_id() {
        return avatar_id;
    }

    public void setAvatar_id(int avatar_id) {
        this.avatar_id = avatar_id;
    }

    public int getHeader_id() {
        return header_id;
    }

    public void setHeader_id(int header_id) {
        this.header_id = header_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
}
