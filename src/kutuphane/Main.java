package kutuphane;

import java.util.Scanner;
import java.time.LocalDate; // <--- YENİ: Tarih kütüphanesi eklendi

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryManager library = new LibraryManager();

        System.out.println("==========================================");
        System.out.println("      ÜNİVERSİTE KÜTÜPHANE SİSTEMİ    ");
        System.out.println("==========================================");
        System.out.print("Kullanıcı Adı: ");
        String u = scanner.nextLine();
        System.out.print("Şifre: ");
        String p = scanner.nextLine();

        if (!u.equals("admin") || !p.equals("1234")) {
            System.out.println(" Hatalı Giriş! Sistem güvenlik nedeniyle kilitlendi.");
            return;
        }

        System.out.println(" GİRİŞ BAŞARILI. Veriler yükleniyor...");

        while (true) {
            System.out.println("\n----------------- ANA MENÜ -----------------");
            System.out.println("1 - Kitap Ara");
            System.out.println("2 - Kitap Ödünç Ver");
            System.out.println("3 - Kitap İade Al");
            System.out.println("4 - Yeni Kitap Ekle");
            System.out.println("5 - Kitap Sil"); 
            System.out.println("0 - Kaydet ve Çık");
            System.out.print("Seçiminiz: ");
            String secim = scanner.nextLine();

            switch (secim) {
                case "1":
                    System.out.print("Aranacak kelime: ");
                    library.searchLibrary(scanner.nextLine());
                    break;
                    
                // --- BURASI DEĞİŞTİ ---
                case "2": 
                    System.out.print("Üye ID: ");
                    String mId = scanner.nextLine();
                    System.out.print("Kitap ISBN: ");
                    String isbn = scanner.nextLine();
                    
                    // Kullanıcıdan tarihi string olarak alıyoruz
                    System.out.print("Veriliş Tarihi (Yıl-Ay-Gün örn: 2025-12-20): ");
                    String tarihGirdisi = scanner.nextLine();
                    
                    try {
                        // String'i LocalDate'e çeviriyoruz
                        LocalDate girilenTarih = LocalDate.parse(tarihGirdisi);
                        
                        // Metoda tarihi de gönderiyoruz
                        library.borrowBook(mId, isbn, girilenTarih); 
                    } catch (Exception e) {
                        System.out.println("HATA: Tarih formatı yanlış! (Örnek: 2025-12-20 olmalı)");
                    }
                    break;
                // ----------------------

                case "3": 
                    System.out.print("İade edilen Kitap ISBN: ");
                    library.returnBook(scanner.nextLine());
                    break;
                case "4": 
                    System.out.print("ISBN: ");
                    String newIsbn = scanner.nextLine();
                    System.out.print("Başlık: ");
                    String title = scanner.nextLine();
                    library.addBook(new Book(newIsbn, title));
                    System.out.println(" Kitap sisteme eklendi.");
                    break;
                case "5":
                    System.out.print("Silinecek Kitap ISBN: ");
                    library.removeBook(scanner.nextLine());
                    break;
                case "0": 
                    library.saveChanges();
                    System.out.println(" Güle güle! Veriler kaydedildi.");
                    scanner.close();
                    return;
                default:
                    System.out.println(" Geçersiz seçim.");
            }
        }
    }
}