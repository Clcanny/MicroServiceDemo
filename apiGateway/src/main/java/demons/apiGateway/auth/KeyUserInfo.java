package demons.apiGateway.auth;

/**
 * Created by demons on 2017/6/27.
 */

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "KeyUserInfo")
public class KeyUserInfo {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLE")
    @MapKeyJoinColumn(name = "role_id")
    @Column(name = "ROLE")
    private List<String> roles;

    public KeyUserInfo() {
        roles = new ArrayList<String>() {
            @Override
            public String toString() {
                String str = new String();
                for (int i = 0; i < size(); i++) {
                    if (i != 0) {
                        str += ", ";
                    }
                    str += "ROLE_" + get(i);
                }
                return str;
            }
        };
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // 采取邮箱验证的方式
    // 受限于username是UserDetails以及UserDetailsService的重要组成部分
    // username也是email
    public String getUsername() {
        return email;
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

    public List<String> getRoles() {
        return roles;
    }

    public void addRoles(String role) {
        roles.add(role);
    }
}
