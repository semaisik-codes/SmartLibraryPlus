package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate borrowDate;
    private LocalDate returnDate;

    // İlişkiler:
    // Bir ödünç işlemi BİR öğrenciye aittir.
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Bir ödünç işlemi BİR kitabı kapsar (Ödevde OneToOne istenmiş)
    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public Loan() {}

    public Loan(Student student, Book book, LocalDate borrowDate) {
        this.student = student;
        this.book = book;
        this.borrowDate = borrowDate;
    }

    // Getter Setter
    public Long getId() { return id; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
}