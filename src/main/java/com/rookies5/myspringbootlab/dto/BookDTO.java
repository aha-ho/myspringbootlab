package com.rookies5.myspringbootlab.dto;

import com.rookies5.myspringbootlab.entity.Book;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

public class BookDTO {

    // 도서 등록 요청
    @Getter
    @NoArgsConstructor
    public static class BookCreateRequest {
        @NotBlank(message = "제목은 필수입니다.")
        private String title;
        @NotBlank(message = "저자는 필수입니다.")
        private String author;
        @NotBlank(message = "ISBN은 필수입니다.")
        private String isbn;
        @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
        private Integer price;
        private LocalDate publishDate;

        public Book toEntity() {
            return Book.builder()
                    .title(title).author(author).isbn(isbn)
                    .price(price).publishDate(publishDate).build();
        }
    }

    // 도서 수정 요청 (필요한 필드만 선택적으로 입력)
    @Getter
    @NoArgsConstructor
    public static class BookUpdateRequest {
        private String title;
        private String author;
        @PositiveOrZero(message = "가격은 0원 이상이어야 합니다.")
        private Integer price;
        private LocalDate publishDate;
    }

    // 도서 응답 (클라이언트에게 전달)
    @Getter
    @Builder
    @AllArgsConstructor
    public static class BookResponse {
        private Long id;
        private String title;
        private String author;
        private String isbn;
        private Integer price;
        private LocalDate publishDate;

        public static BookResponse from(Book book) {
            return BookResponse.builder()
                    .id(book.getId()).title(book.getTitle()).author(book.getAuthor())
                    .isbn(book.getIsbn()).price(book.getPrice()).publishDate(book.getPublishDate()).build();
        }
    }
}