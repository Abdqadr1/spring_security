package qadr.springsecurity.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor @Data @Entity @AllArgsConstructor
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private boolean enabled = false;
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<UserRole> roles = new ArrayList<>();

    public AppUser(String name, String email, String password, Collection<UserRole> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }
}
