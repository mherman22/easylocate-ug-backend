package com.easylocate.model;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;

    private String imageUrl;

    private String websiteUrl;

    private String location;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Column(length = 1000)
    private String about;

    private String contact;
}
