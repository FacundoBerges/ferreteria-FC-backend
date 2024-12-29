package com.ferreteriafc.api.backend.persistence.entity;

import com.ferreteriafc.api.backend.domain.utils.Constant;
import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "categories")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
@ToString
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false, length = Constant.CATEGORIES_NAME_MAX_LENGTH)
    private String name;

    @Column(name = "img_url")
    private String imageUrl;

}
