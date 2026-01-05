# Üniversite Kütüphane Yönetim Sistemi

Bu proje, üniversite kütüphanelerinde yürütülen kitap ödünç/iade, üye takibi ve ceza hesaplama süreçlerini dijitalleştirmek amacıyla geliştirilmiş, Nesne Yönelimli Programlama (OOP) temelli bir otomasyon sistemidir.

## Proje Hakkında
Sistem, kütüphane görevlilerinin (Admin) kitap ve üye yönetimini yapmasını sağlarken, öğrenci ve akademisyen üyelerin farklı kurallara göre kütüphane hizmetlerinden yararlanmasını simüle eder. Veriler uçucu bellekte değil, CSV dosyalarında tutularak kalıcılık sağlanmıştır.

## Kullanılan Teknolojiler
* **Dil:** Java (JDK 17+)
* **Mimari:** OOP (Encapsulation, Inheritance, Polymorphism, Abstraction)
* **Veri Yönetimi:** Dosya İşlemleri (File Handling - CSV)
* **Test:** JUnit 5 (Birim Testleri)
* **Versiyon Kontrol:** Git & GitHub

## Temel Özellikler

### 1. Polimorfizm ile Üye Yönetimi
"Member" ana sınıfından türetilen "StudentMember" ve "AcademicMember" sınıfları sayesinde farklı üye tiplerine özel kurallar uygulanmıştır:
* **Öğrenci:** Maksimum 5 kitap limiti, günlük 0.5 TL gecikme cezası.
* **Akademisyen:** Maksimum 10 kitap limiti, günlük 2.0 TL gecikme cezası.

### 2. Kitap Durum Kontrolü
Kitapların durumu Enum yapısı ile kontrol edilir:
* AVAILABLE (Mevcut)
* LOANED (Ödünç Verilmiş)
* MAINTENANCE (Bakımda)
* LOST (Kayıp)

### 3. Otomatik Ceza Hesaplama
İade tarihi geçen kitaplar için sistem, gün farkını ve üye tipini baz alarak otomatik ceza hesaplaması yapar.

### 4. Veri Kalıcılığı (File I/O)
Program kapatıldığında veriler kaybolmaz. Tüm kitap ve üye kayıtları "books.csv" ve "members.csv" dosyalarına kaydedilir ve her açılışta bu dosyalardan okunur.

## Proje Dosya Yapısı

src/kutuphane
├── Main.java               # Ana menü ve kullanıcı arayüzü
├── LibraryManager.java     # Kitap ve üye işlemlerini yöneten sınıf
├── Member.java             # (Abstract) Ana üye sınıfı
├── StudentMember.java      # Öğrenci sınıfı
├── AcademicMember.java     # Akademisyen sınıfı
├── Book.java               # Kitap nesnesi ve özellikleri
├── Loan.java               # Ödünç alma ve tarih işlemleri
├── DataHelper.java         # CSV dosya okuma/yazma işlemleri
├── EmailNotification.java  # Bildirim servisi
└── LibraryTest.java        # JUnit test senaryoları

## Kurulum ve Çalıştırma

1. Projeyi bilgisayarınıza indirin.
2. IDE (Eclipse veya IntelliJ) ile açın.
3. Main.java dosyasını çalıştırın.
4. Giriş Bilgileri:
   - Kullanıcı Adı: admin
   - Şifre: 1234