package kutuphane;

import java.time.LocalDate; // <--- YENİ
import java.util.ArrayList; 
import java.util.Iterator; 
import java.util.List;      

public class LibraryManager {
    private List<Book> books;
    private List<Member> members;
    private List<Loan> activeLoans; 
    private NotificationService notificationService; 

    public LibraryManager() {
        this.books = DataHelper.loadBooks();
        this.members = DataHelper.loadMembers();
        this.activeLoans = new ArrayList<>();
        
        boolean uyeVarMi = false;
        for (Member m : members) {
            if (m.getMemberID().equals("240312007")) {
                uyeVarMi = true;
                break;
            }
        }
        
        if (!uyeVarMi) {
            this.members.add(new StudentMember("240312007", "Eylül Başer", "240312007"));
            this.members.add(new StudentMember("101", "Burcu Başer", "101"));
            this.members.add(new StudentMember("102", "Emir Başer", "102"));
            this.members.add(new AcademicMember("201", "Prof. Dr. Fatmatül Tarhan", "Bilgisayar Müh."));
            System.out.println(" SİSTEM NOTU: Üye dosyadan okunamadı. Otomatik eklendi.");
        }

        this.notificationService = new EmailNotification();
    }

    public void addBook(Book book) { books.add(book); }
    
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
        if (!silindi) System.out.println("Kitap bulunamadı.");
    }

    public void addMember(Member member) { members.add(member); }

    public Book findBook(String isbn) {
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) return b;
        }
        return null;
    }

    public Member findMember(String id) {
        for (Member m : members) {
            if (m.getMemberID().equals(id)) return m;
        }
        return null;
    }

    // --- BURASI GÜNCELLENDİ: ARTIK TARİH ALIYOR ---
    public void borrowBook(String memberID, String isbn, LocalDate islemTarihi) {
        Book book = findBook(isbn);
        Member member = findMember(memberID);

        if (book != null && member != null) {
            if (book.getStatus() == BookStatus.AVAILABLE) {
                
                // Loan nesnesine gelen tarihi veriyoruz
                Loan loan = new Loan(book, member, islemTarihi); 
                
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
    // ---------------------------------------------

    // Bu metodu overload ediyoruz ki eski kodlar patlamasın (Opsiyonel ama güvenli)
    public void borrowBook(String memberID, String isbn) {
        borrowBook(memberID, isbn, LocalDate.now());
    }

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
            
            // --- CEZA KONTROLÜ ZATEN VARDI, ÇALIŞACAK ---
            if (foundLoan.isOverdue()) {
                long gun = foundLoan.getOverdueDays();
                double ucret = member.calculateFee() * gun;
                
                System.out.println(" KİTAP GECİKMİŞ! (" + gun + " gün)");
                System.out.println(" Toplam Ceza: " + ucret + " TL");
            } else {
                System.out.println(" Teşekkürler, zamanında teslim ettiniz.");
            }
            // --------------------------------------------

            book.setStatus(BookStatus.AVAILABLE);
            activeLoans.remove(foundLoan); 
            
            notificationService.sendNotification("İade onaylandı: " + book.getTitle());

        } else {
            System.out.println(" Bu kitap şu an sistemde ödünçte görünmüyor.");
        }
    }

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

    public void saveChanges() {
        DataHelper.saveBooks(books);
        DataHelper.saveMembers(members);
        System.out.println(" Veriler kaydedildi.");
    }

    public List<Book> getBooks() { return this.books; }
}