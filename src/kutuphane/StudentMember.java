package kutuphane;

/**
 * Öğrenci üyeleri temsil eden sınıf.
 * Member sınıfından türetilmiştir.
 */
public class StudentMember extends Member {
    private String studentNumber;

    /**
     * Yeni bir öğrenci üye oluşturur.
     * @param memberID Üye kimlik numarası
     * @param name Üye adı
     * @param studentNumber Öğrenci okul numarası
     */
    public StudentMember(String memberID, String name, String studentNumber) {
        super(memberID, name); 
        this.studentNumber = studentNumber;
    }

    /**
     * Öğrenciler için günlük gecikme cezasını hesaplar.
     * Öğrenci tarifesi: 0.5 birim.
     */
    @Override
    public double calculateFee() {
        return 0.5;
    }

    /**
     * Öğrencilerin alabileceği maksimum kitap sayısını döndürür.
     * Limit: 5 Kitap.
     */
    @Override
    public int getMaxBookLimit() {
        return 5;
    }

    /**
     * Öğrenciler için kitap ödünç alma süresi.
     * Süre: 15 Gün.
     */
    public int getLoanPeriod() {
        return 15;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    @Override
    public String toString() {
        return super.toString() + " [Öğrenci No: " + studentNumber + "]";
    }
}