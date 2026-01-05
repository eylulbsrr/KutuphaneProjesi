package kutuphane;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Kütüphane iş kurallarını ve sistem limitlerini doğrulayan birim test sınıfı.
 */
public class LibraryTest {

    /**
     * KURAL: Bakımda (MAINTENANCE) olan kitap ödünç verilemez.
     */
    @Test
    public void testBakimdakiKitapEngeli() {
        LibraryManager library = new LibraryManager();
        Book b = new Book("K100", "Sefiller");
        b.setStatus(BookStatus.MAINTENANCE);
        library.addBook(b);
        
        library.borrowBook("240312007", "K100");
        
        // Kitap ödünç verilmemeli, hala MAINTENANCE kalmalı
        assertEquals(BookStatus.MAINTENANCE, library.findBook("K100").getStatus());
    }

    /**
     * KURAL: Öğrenci üyeler için belirlenen kitap limitini doğrular.
     */
    @Test
    public void testOgrenciKitapLimiti() {
        // Öğrenci (Eylül) oluşturuluyor
        StudentMember ogrenci = new StudentMember("240312007", "Eylül Başer", "240312007");
        
        // StudentMember sınıfındaki limit 5 olmalı
        assertEquals(5, ogrenci.getMaxBookLimit());
    }

    /**
     * KURAL: Akademisyen üyeler için belirlenen kitap limitini doğrular.
     */
    @Test
    public void testAkademisyenKitapLimiti() {
        // Akademisyen (Fatmatül Hoca) oluşturuluyor
        AcademicMember hoca = new AcademicMember("201", "Fatmatül Tarhan", "Bilgisayar Müh.");
        
        // AcademicMember sınıfındaki limit 10 olmalı
        assertEquals(10, hoca.getMaxBookLimit());
    }

    /**
     * KURAL: İade edilen kitabın durumu tekrar erişilebilir (AVAILABLE) olmalıdır.
     */
    @Test
    public void testIadeMantigi() {
        LibraryManager library = new LibraryManager();
        library.addBook(new Book("K101", "Nutuk"));
        library.borrowBook("240312007", "K101");
        
        library.returnBook("K101");
        
        assertEquals(BookStatus.AVAILABLE, library.findBook("K101").getStatus());
    }

    /**
     * KURAL: Kayıp (LOST) olarak işaretlenen bir kitap sistemde ödünç verilemez.
     */
    @Test
    public void testKayipKitapKontrolu() {
        LibraryManager library = new LibraryManager();
        Book b = new Book("K102", "Simyaci");
        b.setStatus(BookStatus.LOST);
        library.addBook(b);
        
        library.borrowBook("240312007", "K102");
        
        // Kitap ödünç verilmemeli, hala LOST kalmalı
        assertEquals(BookStatus.LOST, library.findBook("K102").getStatus());
    }
}