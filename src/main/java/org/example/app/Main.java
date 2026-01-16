package org.example.app;

import org.example.dao.BookDao;
import org.example.dao.LoanDao;
import org.example.dao.StudentDao;
import org.example.entity.Book;
import org.example.entity.Loan;
import org.example.entity.Student;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {
    static BookDao bookDao = new BookDao();
    static StudentDao studentDao = new StudentDao();
    static LoanDao loanDao = new LoanDao();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- SMART LIBRARY PLUS (ORM) ---");
            System.out.println("1. Kitap Ekle");
            System.out.println("2. Kitapları Listele");
            System.out.println("3. Öğrenci Ekle");
            System.out.println("4. Öğrencileri Listele");
            System.out.println("5. Kitap Ödünç Ver");
            System.out.println("6. Ödünç Listesini Görüntüle");
            System.out.println("7. Kitap İade Al");
            System.out.println("0. Çıkış");
            System.out.print("Seçim: ");

            int secim = Integer.parseInt(scanner.nextLine());

            switch (secim) {
                case 1 -> kitapEkle();
                case 2 -> kitaplariListele();
                case 3 -> ogrenciEkle();
                case 4 -> ogrencileriListele();
                case 5 -> oduncVer();
                case 6 -> oduncListele();
                case 7 -> iadeAl();
                case 0 -> System.exit(0);
            }
        }
    }

    private static void kitapEkle() {
        System.out.print("Kitap Adı: ");
        String title = scanner.nextLine();
        System.out.print("Yazar: ");
        String author = scanner.nextLine();
        System.out.print("Yıl: ");
        int year = Integer.parseInt(scanner.nextLine());

        Book book = new Book(title, author, year);
        bookDao.save(book);
        System.out.println("Kitap eklendi!");
    }

    private static void kitaplariListele() {
        bookDao.getAll().forEach(b ->
                System.out.println(b.getId() + " - " + b.getTitle() + " (" + b.getStatus() + ")")
        );
    }

    private static void ogrenciEkle() {
        System.out.print("Adı: ");
        String name = scanner.nextLine();
        System.out.print("Bölüm: ");
        String dept = scanner.nextLine();

        Student s = new Student(name, dept);
        studentDao.save(s);
        System.out.println("Öğrenci eklendi!");
    }

    private static void ogrencileriListele() {
        studentDao.getAll().forEach(s ->
                System.out.println(s.getId() + " - " + s.getName() + " (" + s.getDepartment() + ")")
        );
    }

    private static void oduncVer() {
        System.out.print("Öğrenci ID: ");
        Long sId = Long.parseLong(scanner.nextLine());
        System.out.print("Kitap ID: ");
        Long bId = Long.parseLong(scanner.nextLine());

        Book book = bookDao.getById(bId);
        Student student = studentDao.getById(sId);

        if (book != null && student != null) {
            if (book.getStatus() == Book.Status.AVAILABLE) {
                // Kitap müsaitse ödünç ver
                Loan loan = new Loan(student, book, LocalDate.now());
                loanDao.save(loan);

                // Kitabın durumunu güncelle
                book.setStatus(Book.Status.BORROWED);
                bookDao.update(book);

                System.out.println("Kitap ödünç verildi.");
            } else {
                System.out.println("HATA: Kitap zaten ödünçte!");
            }
        } else {
            System.out.println("HATA: Öğrenci veya Kitap bulunamadı.");
        }
    }

    private static void oduncListele() {
        loanDao.getAll().forEach(l -> {
            String returnInfo = l.getReturnDate() == null ? "Teslim Edilmedi" : l.getReturnDate().toString();
            System.out.println(l.getId() + " - " + l.getBook().getTitle() +
                    " -> " + l.getStudent().getName() +
                    " | Alış: " + l.getBorrowDate() + " | İade: " + returnInfo);
        });
    }

    private static void iadeAl() {
        System.out.print("İade edilecek Kitap ID: ");
        Long bId = Long.parseLong(scanner.nextLine());

        // Bu kitabın aktif loan kaydını bul
        Loan loan = loanDao.getActiveLoanByBookId(bId);

        if (loan != null) {
            // İade tarihini bugüne eşitle
            loan.setReturnDate(LocalDate.now());
            loanDao.update(loan);

            // Kitabı tekrar AVAILABLE yap
            Book book = loan.getBook();
            book.setStatus(Book.Status.AVAILABLE);
            bookDao.update(book);

            System.out.println("Kitap iade alındı.");
        } else {
            System.out.println("Bu kitap şu an ödünçte değil veya ID hatalı.");
        }
    }
}