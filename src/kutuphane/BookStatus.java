package kutuphane;

/**
 * Kitabın kütüphanedeki güncel durumunu belirten liste (Enum).
 * Kitabın rafta mı, üyede mi yoksa kayıp mı olduğunu takip etmek için kullanılır.
 */
public enum BookStatus {
    
    /** Kitap kütüphanede mevcut, ödünç verilebilir. */
    AVAILABLE,      

    /** Kitap şu an bir üyede ödünçte. */
    LOANED,         

    /** Kitap kayıp olarak işaretlenmiş. */
    LOST,           

    /** Kitap hasarlı veya bakım sürecinde, ödünç verilemez. */
    MAINTENANCE     
}