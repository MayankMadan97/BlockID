package com.security.identity.entities;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.security.identity.enums.IdentityProviderNameEnum;
import com.security.identity.enums.IdentityProviderTypeEnum;
import com.security.identity.enums.RecordStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "identity")
public class FederatedIdentityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "provider_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private IdentityProviderTypeEnum providerType;

    @Column(name = "provider_name", nullable = false)
    @Enumerated(EnumType.STRING)
    private IdentityProviderNameEnum providerName;

    @Column(name = "provider_user_id", nullable = false, length = 50)
    private String providerUserId;

    @Column(columnDefinition = "jsonb")
    private String attributes;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "access_token_expiry", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp accessTokenExpiry;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "refresh_token_expiry", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Timestamp refreshTokenExpiry;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RecordStatusEnum status;

    @Column(name = "created_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreationTimestamp
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @UpdateTimestamp
    private Timestamp updatedAt;
}
