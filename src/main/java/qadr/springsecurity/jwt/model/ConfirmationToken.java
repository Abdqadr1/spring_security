package qadr.springsecurity.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class ConfirmationToken {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true, nullable = false)
    private String token;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    public ConfirmationToken(String token, LocalDateTime createdAt, LocalDateTime expiresAt, AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }
}
