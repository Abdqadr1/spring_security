package qadr.springsecurity.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @AllArgsConstructor @Entity @NoArgsConstructor
public class UserRole {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;
}
