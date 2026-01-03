package kutuphane;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit; 

/**
 * Ödünç alma (Loan) işlemini temsil eden sınıf.
 * Hangi üyenin hangi kitabı ne zaman aldığını ve teslim tarihini tutar.
 */
public class Loan {
    
    // SUNUM İÇİN AYAR: Burası 'true' ise kitap 30 gün önce alınmış gibi çalışır (Ceza çıkar).
    // Normal kullanım için 'false' yapınız.
    private boolean testModu = true; 

    private Book book;
    private Member member;
    private LocalDate issuedDate; // Veriliş tarihi
    private LocalDate dueDate;    // Son teslim tarihi

    /**
     * Kurucu metod (Constructor).
     * Kitap ve üye bilgisini alır, veriliş ve teslim tarihlerini hesaplar.
     * @param book Ödünç alınan kitap
     * @param member Kitabı alan üye
     */
    public Loan(Book book, Member member) {
        this.book = book;
        this.member = member;
        
        // Test modu açıksa tarihi geriye al, kapalıysa bugünün tarihini at
        if (testModu) {
            this.issuedDate = LocalDate.now().minusDays(30); 
        } else {
            this.issuedDate = LocalDate.now();
        }
        
        // Akademisyense 30 gün, değilse 15 gün süre ver
        int gunLimiti = (member instanceof AcademicMember) ? 30 : 15;
        
        // Teslim tarihi, veriliş tarihinin üzerine eklenerek bulunur
        this.dueDate = this.issuedDate.plusDays(gunLimiti);
    }

    /**
     * Kitabın süresinin geçip geçmediğini kontrol eder.
     * @return Süre dolmuşsa true, dolmamışsa false döner.
     */
    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
    }

    /**
     * Gecikme süresini gün cinsinden hesaplar.
     * @return Geciken gün sayısı.
     */
    public long getOverdueDays() {
        if (isOverdue()) {
            return ChronoUnit.DAYS.between(dueDate, LocalDate.now());
        }
        return 0;
    }

    /**
     * İşleme ait kitabı döndürür.
     * @return Kitap nesnesi
     */
    public Book getBook() { return book; }

    /**
     * İşlemi yapan üyeyi döndürür.
     * @return Üye nesnesi
     */
    public Member getMember() { return member; }

    /**
     * Veriliş tarihini döndürür.
     * @return Veriliş tarihi
     */
    public LocalDate getIssuedDate() { return issuedDate; }

    /**
     * Son teslim tarihini döndürür.
     * @return Teslim tarihi
     */
    public LocalDate getDueDate() { return dueDate; }
}