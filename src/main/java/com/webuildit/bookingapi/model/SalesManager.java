package com.webuildit.bookingapi.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity(name = "sales_managers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SalesManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Convert(converter = StringListConverter.class)
    private List<String> languages;

    @Convert(converter = StringListConverter.class)
    private List<String> products;

    @Convert(converter = StringListConverter.class)
    private List<String> customerRatings;
}
