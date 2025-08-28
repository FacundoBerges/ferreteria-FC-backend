package com.ferreteriafc.api.backend.persistence.entity;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Entity
@Table(name = "products")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false, length = Constant.PRODUCTS_CODE_MAX_LENGTH)
    private String code;

    @Column(nullable = false, length = Constant.PRODUCTS_DESCRIPTION_MAX_LENGTH)
    private String description;

    @Column(name = "img_url")
    private String imgUrl;

    @ManyToOne
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id")
    private Category category;

}
