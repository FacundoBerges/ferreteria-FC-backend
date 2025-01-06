package com.ferreteriafc.api.backend.persistence.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import lombok.*;

import com.ferreteriafc.api.backend.domain.utils.Constant;

@Entity
@Table(name = "brands")
@NoArgsConstructor @AllArgsConstructor @Getter @Setter
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true, nullable = false, length = Constant.BRANDS_NAME_MAX_LENGTH)
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public Brand(Long id, String name) {
        this.products = new ArrayList<>();
        this.id = id;
        this.name = name;
    }
}
