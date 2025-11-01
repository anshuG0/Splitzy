package org.splitzy.auth.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.splitzy.auth.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long>, JpaSpecificationExecutor<AuthUser>
{
    Optional<AuthUser> findByEmailAndIsActiveTrue(String email);
    Optional<AuthUser> findByUsernameAndIsActiveTrue(String username);
    @Query("SELECT u FROM AuthUser u WHERE (u.email = :emailOrUsername OR u.username = :emailOrUsername) AND u.isActive = true")
    Optional<AuthUser> findActiveUserByEmailOrUsername(@Param("emailOrUsername") String EmailOrUsername);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    @Modifying
    @Query("UPDATE AuthUser u SET u.lastLogin = :lastLogin WHERE u.id = :userId")
    void updateLastLogin(@Param("userId") Long userId, @Param("lastLogin") LocalDateTime lastLogin);

    @Modifying
    @Query("UPDATE AuthUser u SET u.failedLoginAttempts = :attempts, u.accountLockedUntil = :lockedUntil WHERE u.id = :userId")
    void updateFailedLoginAttempts(@Param("userId") Long userId, @Param("attempts") Integer attempts, @Param("lockedUntil") LocalDateTime lockedUntil);

    @Modifying
    @Query("UPDATE AuthUser u SET u.failedLoginAttempts = 0, u.accountLockedUntil = null WHERE u.id = :userId")
    void resetFailedLoginAttempts(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE AuthUser u SET u.isEmailVerified = :verified WHERE u.id = :userId")
    void updateEmailVerificationStatus(@Param("userId") Long userId, @Param("verified") Boolean verified);

    @Modifying
    @Query("UPDATE AuthUser u SET u.isEmailVerified = :verified WHERE u.id = :userId")
    void updatePhoneVerificationStatus(@Param("userId") Long userId, @Param("verified") Boolean verified);

    @Modifying
    @Query("SELECT u FROM AuthUser u WHERE u.accountLockedUntil IS NOT NULL AND u.accountLockedUntil <= :now")
    List<AuthUser> findUsersWithExpiredLocks(@Param("now") LocalDateTime now);
}
