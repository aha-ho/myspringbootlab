package com.rookies5.myspringbootlab.repository;

import com.rookies5.myspringbootlab.entity.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        // 1. 기존 데이터를 모두 지워 중복(Duplicate Entry) 오류 방지
        bookRepository.deleteAll();

        // 2. 공통 기초 데이터 저장
        Book book1 = Book.builder()
                .title("스프링 부트 입문")
                .author("홍길동")
                .isbn("9788956746425")
                .price(30000)
                .publishDate(LocalDate.of(2025, 5, 7))
                .build();

        Book book2 = Book.builder()
                .title("JPA 프로그래밍")
                .author("박둘리")
                .isbn("9788956746432")
                .price(35000)
                .publishDate(LocalDate.of(2025, 4, 30))
                .build();

        bookRepository.save(book1);
        bookRepository.save(book2);

        System.out.println("=== 테스트 전 데이터 세팅 완료 ===");
    }

    @Test
    @DisplayName("도서 등록 테스트")
    void testCreateBook() {
        // Given
        Book newBook = Book.builder()
                .title("새로운 도서")
                .author("이순신")
                .isbn("111-222-333")
                .price(20000)
                .publishDate(LocalDate.now())
                .build();

        // When
        Book savedBook = bookRepository.save(newBook);

        // Then
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("새로운 도서");
    }

    @Test
    @DisplayName("ISBN으로 도서 조회 테스트")
    void testFindByIsbn() {
        // When (setUp에서 넣은 데이터 조회)
        Optional<Book> foundBook = bookRepository.findByIsbn("9788956746425");

        // Then
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("스프링 부트 입문");
        assertThat(foundBook.get().getAuthor()).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("저자명으로 도서 목록 조회 테스트")
    void testFindByAuthor() {
        // When
        List<Book> books = bookRepository.findByAuthor("박둘리");

        // Then
        assertThat(books).isNotEmpty();
        assertThat(books.size()).isEqualTo(1);
        assertThat(books.get(0).getTitle()).isEqualTo("JPA 프로그래밍");
    }

    @Test
    @DisplayName("도서 정보 수정 테스트")
    void testUpdateBook() {
        // Given
        Book book = bookRepository.findByIsbn("9788956746425").orElseThrow();
        String newTitle = "스프링 부트 정복";
        int newPrice = 40000;

        // When
        book.setTitle(newTitle);
        book.setPrice(newPrice);
        Book updatedBook = bookRepository.save(book);

        // Then
        assertThat(updatedBook.getTitle()).isEqualTo(newTitle);
        assertThat(updatedBook.getPrice()).isEqualTo(newPrice);
    }

    @Test
    @DisplayName("도서 삭제 테스트")
    void testDeleteBook() {
        // Given
        Book book = bookRepository.findByIsbn("9788956746432").orElseThrow();
        Long id = book.getId();

        // When
        bookRepository.deleteById(id);

        // Then
        Optional<Book> deletedBook = bookRepository.findById(id);
        assertThat(deletedBook).isEmpty();
    }
}