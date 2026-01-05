package kutuphane;

/**
 * Kullanıcılara E-posta yoluyla bildirim gönderilmesini sağlayan sınıf.
 * NotificationService arayüzünü uygular ve bildirim detaylarını işler.
 */
public class EmailNotification implements NotificationService {

    @Override
    public void sendNotification(String message) {
        System.out.println("[E-POSTA GÖNDERİLDİ]: " + message);
    }
}