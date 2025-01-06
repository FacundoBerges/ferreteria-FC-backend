package com.ferreteriafc.api.backend.persistence.entity;

import jakarta.persistence.*;

import lombok.*;

import com.ferreteriafc.api.backend.domain.utils.Constant;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false, length = Constant.CATEGORIES_NAME_MAX_LENGTH)
    private String name;

    @Column(name = "img_url")
    private String imageUrl;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products;

    public Category(Long id, String name, String imageUrl) {
        this.products = new ArrayList<>();
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

}
