package com.rookies5.myspringbootlab.service;

import com.rookies5.myspringbootlab.dto.BookDTO.*;
import com.rookies5.myspringbootlab.entity.Book;
import com.rookies5.myspringbootlab.exception.BusinessException;
import com.rookies5.myspringbootlab.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(BookResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public BookResponse createBook(BookCreateRequest request) {
        bookRepository.findByIsbn(request.getIsbn()).ifPresent(b -> {
            throw new BusinessException("이미 존재하는 ISBN입니다.", HttpStatus.CONFLICT);
        });
        return BookResponse.from(bookRepository.save(request.toEntity()));
    }

    @Transactional
    public BookResponse updateBook(Long id, BookUpdateRequest request) {
        Book existBook = bookRepository.findById(id)
                .orElseThrow(() -> new BusinessException("해당 도서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND));

        // DTO의 필드가 null이 아닌 경우에만 업데이트 (캡슐화된 메서드 호출)
        existBook.updateFields(
                request.getTitle(),
                request.getAuthor(),
                request.getPrice(),
                request.getPublishDate()
        );

        return BookResponse.from(existBook);
    }

    @Transactional
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BusinessException("삭제할 도서가 없습니다.", HttpStatus.NOT_FOUND);
        }
        bookRepository.deleteById(id);
    }
}