package kutuphane;

/**
 * Kullanıcılara bildirim göndermek için kullanılan arayüz (Interface).
 * Bu yapı sayesinde sisteme SMS, E-posta veya Konsol bildirimi gibi farklı yöntemler eklenebilir.
 */
public interface NotificationService {
    
    /**
     * Belirtilen mesajı kullanıcıya iletir.
     */
    void sendNotification(String message);
}