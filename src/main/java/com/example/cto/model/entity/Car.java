package com.example.cto.model.entity;

import com.example.cto.model.entity.base_entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;


/**
 * Сущность автомобиля, создаются при миграции для теста
 * */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cars")
public class Car extends BaseEntity {

    @Column(name = "name")
    private String name;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.MERGE})
    private User user;

}
