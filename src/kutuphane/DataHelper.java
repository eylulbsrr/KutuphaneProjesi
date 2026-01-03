package kutuphane;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Veri saklama işlemlerini yöneten yardımcı sınıf.
 * Kitap ve üye bilgilerini metin dosyalarına (CSV formatında) kaydeder ve oradan okur.
 */
public class DataHelper {
    private static final String BOOKS_FILE = "books.csv";
    private static final String MEMBERS_FILE = "members.csv";
    private static final String SEPARATOR = ";"; 

    /**
     * Mevcut kitap listesini dosyaya kalıcı olarak yazar.
     * Format: ISBN;Başlık;Durum
     */
    public static void saveBooks(List<Book> books) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKS_FILE))) {
            for (Book b : books) {
                writer.write(b.getIsbn() + SEPARATOR + b.getTitle() + SEPARATOR + b.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    /**
     * Dosyadan kitapları okur ve bir liste haline getirir.
     * Program açıldığında eski verileri yüklemek için kullanılır.
     */
    public static List<Book> loadBooks() {
        List<Book> list = new ArrayList<>();
        File file = new File(BOOKS_FILE);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR); 
                if (parts.length >= 3) { 
                    Book b = new Book(parts[0], parts[1]);
                    b.setStatus(BookStatus.valueOf(parts[2]));
                    list.add(b);
                }
            }
        } catch (IOException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return list;
    }

    /**
     * Üye listesini dosyaya kaydeder.
     * Öğrenci ve Akademisyen ayrımını yaparak (S veya A harfiyle) kaydeder.
     */
    public static void saveMembers(List<Member> members) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBERS_FILE))) {
            for (Member m : members) {
                // Polimorfizm kontrolü: Öğrenci mi Akademisyen mi?
                String type = (m instanceof StudentMember) ? "S" : "A";
                String extra = (m instanceof StudentMember) ? ((StudentMember) m).getStudentNumber() : ((AcademicMember) m).getDepartment();
                
                writer.write(type + SEPARATOR + m.getMemberID() + SEPARATOR + m.getName() + SEPARATOR + extra);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }

    /**
     * Dosyadan üyeleri okur.
     * Satırın başındaki harfe (S/A) bakarak doğru sınıfı (StudentMember/AcademicMember) oluşturur.
     */
    public static List<Member> loadMembers() {
        List<Member> list = new ArrayList<>();
        File file = new File(MEMBERS_FILE);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(SEPARATOR);
                if (parts.length >= 4) {
                    String type = parts[0];
                    if (type.equals("S")) {
                        list.add(new StudentMember(parts[1], parts[2], parts[3]));
                    } else if (type.equals("A")) {
                        list.add(new AcademicMember(parts[1], parts[2], parts[3]));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Hata: " + e.getMessage());
        }
        return list;
    }
}