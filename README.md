# SmartLibrary2 ORM Tabanlı Kütüphane Sistemi

Bu proje, Nesneye Yönelik Programlama (OOP) prensiplerine uygun olarak, veri tabanı işlemleri için Hibernate ORM teknolojisi kullanılarak geliştirilmiş bir konsol uygulamasıdır. Proje, klasik JDBC yöntemleri yerine Hibernate Framework kullanılarak geliştirilmiş olup, tüm SQL işlemleri nesneler üzerinden yönetilmektedir.

 --Projenin Amacı ve Özellikleri--
Bu sistem, bir üniversite kütüphanesinin kitap, öğrenci ve ödünç alma süreçlerini dijitalleştirmeyi amaçlar.

--Gerçekleştirilen İşlevler--
Kitap Yönetimi: Kitap ekleme ve durumlarını (AVAILABLE / BORROWED) takip etme.
Öğrenci Yönetimi: Öğrenci kaydı ve bölüm bilgileri.
Ödünç (Loan) Sistemi:
Stok kontrolü (Ödünçteki kitap tekrar verilemez).
Tarih takibi (Alış ve İade tarihleri).
İade İşlemi: Kitap iade alındığında durumunun otomatik güncellenmesi.
Raporlama: Tüm kitapları, öğrencileri ve aktif/pasif ödünç listesini görüntüleme.

--Kullanılan Teknolojiler--
Dil: Java (JDK 17+)
ORM Framework:Hibernate Core 6.4.1
Veritabanı: SQLite
Build Tool: Maven
IDE: IntelliJ IDEA
