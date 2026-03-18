package com.rookies5.myspringbootlab.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author;

    @Column(unique = true)
    private String isbn;

    private Integer price;
    private LocalDate publishDate;

    public void updateFields(String title, String author, Integer price, LocalDate publishDate) {
        if (title != null) this.title = title;
        if (author != null) this.author = author;
        if (price != null) this.price = price;
        if (publishDate != null) this.publishDate = publishDate;
    }
}