package com.musinsa.UrlShortener.model;

import com.musinsa.UrlShortener.util.Md5Hash;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 512, nullable = false)
    private String url;

    @Column(length = 64, nullable = false, unique = true)
    private String hash;

    @Setter
    @Column(length = 8, unique = true)
    private String shortKey;

    @Setter
    @ColumnDefault("0")
    private long count = 0L;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public ShortUrl(@NotBlank String url) {
        setUrl(url);
    }

    public void setUrl(@NotBlank String url) {
        this.url  = url.trim();
        this.hash = Md5Hash.md5Hex(this.url);
    }

}
