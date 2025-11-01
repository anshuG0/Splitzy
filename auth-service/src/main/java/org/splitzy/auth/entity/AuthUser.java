package org.splitzy.auth.entity;

import jakarta.persistence.*;
import lombok.*;
import org.splitzy.common.entity.BaseEntity;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "auth_users", indexes = {
        @Index(name = "idx_email", columnList = "email", unique = true),
        @Index(name = "idx_username", columnList = "username", unique = true),
        @Index(name = "idx_last_login", columnList = "last_login")
})
@EqualsAndHashCode(callSuper = true)
public class AuthUser extends BaseEntity {

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    @Column(name = "is_email_verified", nullable = false)
    @Builder.Default
    private Boolean isEmailVerified = Boolean.FALSE;

    @Column(name = "is_phone_verified", nullable = false)
    @Builder.Default
    private Boolean isPhoneVerified = Boolean.FALSE;

    @Column(name = "failed_login_attempts", nullable = false)
    @Builder.Default
    private Integer failedLoginAttempts = 0;

    @Column(name = "account_locked_until")
    private LocalDateTime accountLockedUntil;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "password_changed_at")
    private LocalDateTime passwordChangedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @Builder.Default
    private UserRole role = UserRole.USER;

    public enum UserRole {
        USER, ADMIN
    }

    public boolean isAccountLocked(){
        return accountLockedUntil != null && accountLockedUntil.isAfter(LocalDateTime.now());
    }

    public void resetFailedloginAttempts(){
        this.failedLoginAttempts = 0;
        this.accountLockedUntil = null;
    }

    public void incrementFailedLoginAttempts(){
        this.failedLoginAttempts++;
        if(this.failedLoginAttempts >= 5){
            this.accountLockedUntil = LocalDateTime.now().plusMinutes(30);
        }
    }
}
