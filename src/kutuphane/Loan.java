package kutuphane;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Ödünç alma (Loan) işlemini temsil eden sınıf.
 * Hangi üyenin hangi kitabı ne zaman aldığını ve teslim tarihini tutar.
 */
public class Loan {
    
    private Book book;
    private Member member;
    private LocalDate issuedDate; // Veriliş tarihi (Artık dışarıdan geliyor)
    private LocalDate dueDate;    // Son teslim tarihi (Otomatik hesaplanıyor)

    /**
     * Kurucu metod (Constructor).
     * Artık tarihi parametre olarak alıyor.
     * * @param book Ödünç alınan kitap
     * @param member Kitabı alan üye
     * @param issuedDate Kitabın verildiği tarih (Kullanıcı tarafından girilen)
     */
    public Loan(Book book, Member member, LocalDate issuedDate) {
        this.book = book;
        this.member = member;
        this.issuedDate = issuedDate; // Kullanıcının girdiği tarihi buraya atıyoruz
        
        // Akademisyense 30 gün, değilse 15 gün süre ver
        int gunLimiti = (member instanceof AcademicMember) ? 30 : 15;
        
        // Teslim tarihi = Verilen Tarih + Gün Limiti
        this.dueDate = this.issuedDate.plusDays(gunLimiti);
    }

    /**
     * Kitabın süresinin geçip geçmediğini kontrol eder.
     * @return Süre dolmuşsa true, dolmamışsa false döner.
     */
    public boolean isOverdue() {
        // Son teslim tarihi bugünden önceyse süre dolmuştur
        return LocalDate.now().isAfter(dueDate);
    }

    /**
     * Gecikme süresini gün cinsinden hesaplar.
     * @return Geciken gün sayısı.
     */
    public long getOverdueDays() {
        if (isOverdue()) {
            // Son teslim tarihi ile BUGÜN arasındaki farkı hesaplar
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