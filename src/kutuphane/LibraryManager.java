package kutuphane;

import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.List;      

/**
 * Kutuphane sisteminin ana yonetim sinifi.
 * Kitap ekleme, silme, odunc verme ve iade alma islemlerini yonetir.
 */
public class LibraryManager {
    private List<Book> books;
    private List<Member> members;
    private List<Loan> activeLoans; 
    private NotificationService notificationService; 

    /**
     * Kurucu metod. Verileri dosyadan yukler.
     * Dosya okunamazsa otomatik olarak acil durum uyelerini (Eylul, Burcu, Emir, Hoca) ekler.
     */
    public LibraryManager() {
        this.books = DataHelper.loadBooks();
        this.members = DataHelper.loadMembers();
        this.activeLoans = new ArrayList<>();
        
        // Eger dosyadan okunamazsa diye kontrol yapiyoruz
        boolean uyeVarMi = false;
        for (Member m : members) {
            if (m.getMemberID().equals("240312007")) {
                uyeVarMi = true;
                break;
            }
        }
        
        // Dosya bos veya hataliysa tum uyeleri manuel ekliyoruz.
        if (!uyeVarMi) {
            // Eylul
            this.members.add(new StudentMember("240312007", "Eylül Başer", "240312007"));
            
            // Burcu
            this.members.add(new StudentMember("101", "Burcu Başer", "101"));
            
            // Emir
            this.members.add(new StudentMember("102", "Emir Başer", "102"));
            
            // Fatmatul Hoca (Akademisyen)
            this.members.add(new AcademicMember("201", "Prof. Dr. Fatmatül Tarhan", "Bilgisayar Müh."));
            
            System.out.println(" SİSTEM NOTU: Üye dosyadan okunamadı. 'Eylül, Burcu, Emir ve Fatmatül Hoca' sisteme otomatik eklendi.");
        }


        

        this.notificationService = new EmailNotification();
    }

    /**
     * Sisteme yeni kitap ekler.
     * @param book Eklenecek kitap nesnesi
     */
    public void addBook(Book book) {
        books.add(book);
    }
    
    /**
     * ISBN numarasina gore kitap siler. öduncteyse silmez.
     * @param isbn Silinecek kitabin ISBN numarasi
     */
    public void removeBook(String isbn) {
        Iterator<Book> iterator = books.iterator();
        boolean silindi = false;
        
        while (iterator.hasNext()) {
            Book b = iterator.next();
            if (b.getIsbn().equals(isbn)) {
                if(b.getStatus() == BookStatus.LOANED) {
                    System.out.println("HATA: Bu kitap şu an ödünçte, silemezsiniz!");
                    return;
                }
                iterator.remove();
                silindi = true;
                System.out.println("Kitap sistemden silindi: " + b.getTitle());
                break;
            }
        }
        
        if (!silindi) {
            System.out.println("Kitap bulunamadı.");
        }
    }

    /**
     * Sisteme yeni uye ekler.
     * @param member Eklenecek uye
     */
    public void addMember(Member member) {
        members.add(member);
    }

    /**
     * ISBN ile kitap arar.
     * @param isbn Aranacak ISBN
     * @return Kitap nesnesi
     */
    public Book findBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                return b;
            }
        }
        return null;
    }

    /**
     * ID ile uye arar.
     * @param id Aranacak uye ID'si
     * @return Uye nesnesi
     */
    public Member findMember(String id) {
        for (Member m : members) {
            if (m.getMemberID().equals(id)) {
                return m;
            }
        }
        return null;
    }

    /**
     * Kitap odunc verme islemini yapar.
     * @param memberID Uyeyi temsil eden ID
     * @param isbn Kitabin ISBN numarasi
     */
    public void borrowBook(String memberID, String isbn) {
        Book book = findBook(isbn);
        Member member = findMember(memberID);

        if (book != null && member != null) {
            if (book.getStatus() == BookStatus.AVAILABLE) {
                Loan loan = new Loan(book, member);
                activeLoans.add(loan); 
                
                book.setStatus(BookStatus.LOANED);
                
                notificationService.sendNotification("Sayın " + member.getName() + ", '" + book.getTitle() + "' kitabını aldınız.");
                
                System.out.println(" Veriliş Tarihi: " + loan.getIssuedDate());
                System.out.println(" Son Teslim: " + loan.getDueDate());
            } else {
                System.out.println(" Bu kitap şu an başkasında!");
            }
        } else {
            System.out.println(" Hata: Kitap veya Üye bulunamadı.");
        }
    }

    /**
     * Kitap iade alir ve ceza varsa hesaplar.
     * @param isbn Iade edilen kitabin ISBN numarasi
     */
    public void returnBook(String isbn) {
        Loan foundLoan = null;
        for (Loan loan : activeLoans) {
            if (loan.getBook().getIsbn().equals(isbn)) {
                foundLoan = loan;
                break;
            }
        }

        if (foundLoan != null) {
            Book book = foundLoan.getBook();
            Member member = foundLoan.getMember();
            
            System.out.println("İade Alınıyor: " + book.getTitle());
            
            if (foundLoan.isOverdue()) {
                long gun = foundLoan.getOverdueDays();
                double ucret = member.calculateFee() * gun;
                
                System.out.println(" KİTAP GECİKMİŞ! (" + gun + " gün)");
                System.out.println(" Toplam Ceza: " + ucret + " TL");
            } else {
                System.out.println(" Teşekkürler, zamanında teslim ettiniz.");
            }

            book.setStatus(BookStatus.AVAILABLE);
            activeLoans.remove(foundLoan); 
            
            notificationService.sendNotification("İade onaylandı: " + book.getTitle());

        } else {
            System.out.println(" Bu kitap şu an sistemde ödünçte görünmüyor.");
        }
    }

    /**
     * Kitap ismine gore arama yapar.
     * @param keyword Aranacak kelime
     */
    public void searchLibrary(String keyword) {
        System.out.println("\n Arama Sonuçları (" + keyword + "):");
        boolean found = false;
        for (Book b : books) {
            if (b.search(keyword)) {
                System.out.println(" - " + b); 
                found = true;
            }
        }
        if (!found) System.out.println("Kayıt bulunamadı.");
    }

    /**
     * Degisiklikleri dosyaya kaydeder.
     */
    public void saveChanges() {
        DataHelper.saveBooks(books);
        DataHelper.saveMembers(members);
        System.out.println(" Veriler kaydedildi.");
    }

    /**
     * Kitap listesini döndürür.
     * @return List<Book>
     */
    public List<Book> getBooks() {
        return this.books;
    }
}