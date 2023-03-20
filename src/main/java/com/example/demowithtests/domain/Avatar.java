package com.example.demowithtests.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "avatars")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
@ToString
@Builder
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String imgUrl;
    private LocalDateTime creationDate = LocalDateTime.now();
    private Boolean isExpired = Boolean.FALSE;

    public Avatar(String imgUrl) {
        this.imgUrl = imgUrl;
        this.creationDate = LocalDateTime.now();
        this.isExpired = Boolean.FALSE;
    }
}
