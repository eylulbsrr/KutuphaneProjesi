package kutuphane;

/**
 * Akademisyen üyeleri temsil eden sınıf.
 * Member sınıfından türetilmiştir.
 */
public class AcademicMember extends Member {
    private String department; // Bölüm (Örn: Bilgisayar Müh.)

    /**
     * Yeni bir akademisyen üye oluşturur.
     */
    public AcademicMember(String memberID, String name, String department) {
        // Üst sınıfın (Member) yapıcısını çağırıyoruz
        super(memberID, name); 
        this.department = department;
    }

    /**
     * Akademisyenler için günlük gecikme cezası.
     * Akademisyen tarifesi: 2.0 TL
     */
    @Override
    public double calculateFee() {
        return 2.0; 
    }

    /**
     * Akademisyenlerin aynı anda alabileceği kitap sayısı.
     * Limit: 10 Kitap.
     */
    @Override
    public int getMaxBookLimit() {
        return 10; 
    }

    /**
     * Akademisyenlerin kitabı elinde tutma süresi (Gün).
     * Limit: 30 Gün.
     */
    public int getLoanLimit() {
        return 30;
    }
    
    /**
     * Akademisyenin bölüm bilgisini verir.
     */
    public String getDepartment() {
        return department;
    }
}