package kutuphane;

/**
 * "Arama Yapılabilir" özelliğini kazandıran arayüz (Interface).
 * Kitap ve Üye gibi sınıflar bu arayüzü kullanarak kendi arama mantıklarını oluştururlar.
 */
public interface Searchable {
    
    /**
     * Verilen anahtar kelimeye göre arama yapar.
     * Eşleşme bulunursa true, bulunamazsa false değeri döndürür.
     */
    boolean search(String keyword);
}