package com.rookies5.myspringbootlab.Runner;

import com.rookies5.myspringbootlab.entity.Book;
import com.rookies5.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        log.info(">>>> 초기 데이터 등록 시작...");

        if (bookRepository.count() == 0) {
            Book book1 = createBook("스프링 부트 입문", "홍길동", "9788956746425", 30000, LocalDate.of(2025, 5, 7));
            Book book2 = createBook("JPA 프로그래밍", "박둘리", "9788956746432", 35000, LocalDate.of(2025, 4, 30));

            bookRepository.saveAll(List.of(book1, book2));
            log.info(">>>> {}권의 도서 정보가 등록되었습니다.", bookRepository.count());
        } else {
            log.info(">>>> 이미 데이터가 존재하여 초기화를 건너뜁니다.");
        }
    }

    private Book createBook(String title, String author, String isbn, Integer price, LocalDate publishDate) {
        return Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .price(price)
                .publishDate(publishDate)
                .build();
    }
}