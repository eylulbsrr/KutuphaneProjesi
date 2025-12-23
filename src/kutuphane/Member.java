package kutuphane;

/**
 * Kütüphane üyelerini temsil eden soyut (abstract) temel sınıf.
 * Bu sınıftan doğrudan nesne üretilmez, alt sınıflar (Öğrenci, Akademisyen) tarafından miras alınır.
 */
public abstract class Member implements Searchable {
    private String memberID;
    private String name;

    /**
     * Yeni bir üye oluşturur.
     */
    public Member(String memberID, String name) {
        this.memberID = memberID;
        this.name = name;
    }

    /**
     * Üyenin gecikme ücretini hesaplar.
     * Polimorfizm: Her alt sınıf bu hesabı kendi kurallarına göre yapar.
     */
    public abstract double calculateFee(); 

    /**
     * Üyenin alabileceği maksimum kitap sayısını döndürür.
     * Polimorfizm: Her üye tipi için limit farklıdır.
     */
    public abstract int getMaxBookLimit();

    /**
     * Üye isminde veya ID'sinde arama yapar.
     */
    @Override
    public boolean search(String keyword) {
        return name.toLowerCase().contains(keyword.toLowerCase()) || 
               memberID.contains(keyword);
    }

    /**
     * Üyenin ID numarasını verir.
     */
    public String getMemberID() {
        return memberID;
    }

    /**
     * Üyenin adını verir.
     */
    public String getName() {
        return name;
    }

    /**
     * Üye bilgilerini metin olarak döndürür.
     */
    @Override
    public String toString() {
        return "Üye [ID: " + memberID + ", İsim: " + name + "]";
    }
}