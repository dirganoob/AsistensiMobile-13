package com.example.tugaspraktikum4.utils;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.example.tugaspraktikum4.R;
import com.example.tugaspraktikum4.models.Book;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BookRepository {
    private static final String TAG = "BookRepository";
    private static BookRepository instance;
    private final List<Book> books;
    private final List<String> allGenres;

    private BookRepository(Context context) {
        books = new ArrayList<>();
        allGenres = new ArrayList<>();
        createDummyBooks(context);
    }

    public static synchronized BookRepository getInstance(Context context) {
        if (instance == null) {
            instance = new BookRepository(context);
        }
        return instance;
    }

        private void createDummyBooks(Context context) {
            Uri dummyCoverUri = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.default_book_cover);
            Uri Negeri5MenaraCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_1);
            Uri LaskarPelangiCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_2);
            Uri BumiManusiaCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_3);
            Uri PerahuKertasCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_4);
            Uri FilosofiKopiCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_5);
            Uri AyatAyatCintaCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_6);
            Uri PulangCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_7);
            Uri RonggengDukuhParukCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_8);
            Uri TenggelamnyaKapalVanDerWijckCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_9);
            Uri DilanCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_10);
            Uri AzzamineCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_11);
            Uri fiveCmCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_12);
            Uri sangPemimpiCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_13);
            Uri padangBulanCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_14);
            Uri daunYangJatuhCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_15);
            Uri hujanCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_16);
            Uri DuaEmpatPerTujuhCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_20);
            Uri AsyaStoryCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_21);
            Uri MariposaCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_17);
            Uri MariposaDuaSatuCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_18);
            Uri MariposaDuaDuaCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_19);
            Uri GalaksiCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_22);
            Uri AntaresCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_23);
            Uri JessieCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_24);
            Uri TelukAlaskaCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_25);
            Uri DearNathanCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_26);
            Uri lautberceritaCover = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.drawable.buku_27);

            books.add(new Book("1", "Negeri 5 Menara", "Ahmad Fuadi", 2009,
                    "Alif had never set foot outside of West Sumatra. He never thought he would be graduating from Islamic boarding school, and yet he found himself among the rows of students.",
                    Negeri5MenaraCover, false, "Novel", 4.7f));

            books.add(new Book("2", "Laskar Pelangi", "Andrea Hirata", 2005,
                    "This book tells the inspiring story of 10 children from Belitung Island, Indonesia, who struggle to get an education despite poverty.",
                    LaskarPelangiCover, false, "Novel", 4.6f));

            books.add(new Book("3", "Bumi Manusia", "Pramoedya Ananta Toer", 1980,
                    "Set in the Dutch East Indies at the start of the 20th century, the novel tells the story of Minke, a Javanese minor royal who studies at a Hogere Burger School.",
                    BumiManusiaCover, false, "History", 4.8f));

            books.add(new Book("4", "Perahu Kertas", "Dee Lestari", 2009,
                    "A novel about the journey of Kugy and Keenan, two young people who pursue their dreams while finding love.",
                    PerahuKertasCover, false, "Romance", 4.5f));

            books.add(new Book("5", "Filosofi Kopi", "Dee Lestari", 2006,
                    "A collection of short stories that revolve around coffee and life philosophies.",
                    FilosofiKopiCover, false, "Story", 4.4f));

            books.add(new Book("6", "Ayat-Ayat Cinta", "Habiburrahman El Shirazy", 2004,
                    "A love story set in Egypt that revolves around Fahri, an Indonesian student studying at Al-Azhar University.",
                    AyatAyatCintaCover, false, "Romance", 4.3f));

            books.add(new Book("7", "Pulang", "Leila S. Chudori", 2012,
                    "A novel about Indonesian political exiles in Paris after the 1965 incident in Indonesia.",
                    PulangCover, false, "History", 4.6f));

            books.add(new Book("8", "Ronggeng Dukuh Paruk", "Ahmad Tohari", 1982,
                    "A trilogy that tells the story of a dancer named Srintil in a small village called Dukuh Paruk.",
                    RonggengDukuhParukCover, false, "Sastra", 4.5f));

            books.add(new Book("9", "Tenggelamnya Kapal Van Der Wijck", "Hamka", 1938,
                    "A tragic love story between Zainuddin and Hayati, set against the backdrop of the Minangkabau culture.",
                    TenggelamnyaKapalVanDerWijckCover, false, "Sastra", 4.7f));

            books.add(new Book("10", "Dilan: Dia Adalah Dilanku Tahun 1990", "Pidi Baiq", 2014,
                    "A teenage love story set in Bandung in the 1990s, focusing on the relationship between Milea and Dilan.",
                    DilanCover, false, "Romance", 4.2f));

            books.add(new Book("11", "Azzamine", "Sophie Aulia", 2022,
                    "A story about a rebellious girl named Jasmine who is arranged to marry a pious man, Azzam, and must choose between her current lover and a future she never expected.",
                    AzzamineCover, false, "Romance", 4.0f));

            books.add(new Book("12", "5 cm", "Donny Dhirgantoro", 2005,
                    "A story about five friends who challenge themselves to climb Mount Semeru, the highest peak in Java.",
                    fiveCmCover, false, "Adventure", 4.3f));

            books.add(new Book("13", "Sang Pemimpi", "Andrea Hirata", 2006,
                    "The sequel to Laskar Pelangi, continuing the story of the children from Belitung Island and their dreams.",
                    sangPemimpiCover, false, "Novel", 4.5f));

            books.add(new Book("14", "Padang Bulan", "Andrea Hirata", 2010,
                    "A novel about a young girl named Enong who works in a tin mine to support her family after her father dies.",
                    padangBulanCover, false, "Novel", 4.4f));

            books.add(new Book("15", "Daun Yang Jatuh Tak Pernah Membenci Angin", "Tere Liye", 2010,
                    "A story about a girl named Tania who grows up in poverty but is helped by a kind young man named Danar.",
                    daunYangJatuhCover, false, "Romance", 4.3f));

            books.add(new Book("16", "Hujan", "Tere Liye", 2012,
                    "A story about a girl named Lail who loses her parents in a devastating disaster and finds solace in a brilliant young man named Esok",
                    hujanCover, false, "Novel", 4.5f));

            books.add(new Book("27", "Laut Bercerita", "Leila S. Chudori", 2015,
                    "A story about Biru Laut, a student activist who disappears during Indonesia’s political turmoil, and the love he leaves behind, told through haunting memories and painful separations.",
                    lautberceritaCover, false, "Romance", 4.6f));

            books.add(new Book("17", "24/7 Cinta Sejati", "Sabrina Febrianti", 2020,
                    "A story about a girl who devotes her life to protecting her true love, facing endless trials and sacrifices to keep their bond alive 24/7.",
                    DuaEmpatPerTujuhCover, false, "Romance", 3.5f));

            books.add(new Book("18", "Asya Story", "Sabrina Febrianti", 2019,
                    "A story about a brave girl named Asya, who struggles with heartbreak, family pressure, and friendship to find her own version of true love.",
                    AsyaStoryCover, false, "Romance", 4.3f));

            books.add(new Book("19", "Mariposa", "Luluk HF", 2018,
                    "A story about Acha, a bright and stubborn girl who falls for the cold and genius Iqbal, showing that love can melt even the hardest hearts.",
                    MariposaCover, false, "Romance", 5.0f));

            books.add(new Book("20", "Mariposa 2 Part 1", "Luluk HF", 2021,
                    "The sequel to Mariposa, continuing the love story of Acha and Iqbal as they navigate new challenges and adventures.",
                    MariposaDuaSatuCover, false, "Romance", 5.0f));

            books.add(new Book("21", "Mariposa 2 Part 2", "Luluk HF", 2021,
                    "The final chapter of Acha and Iqbal’s journey, where they must decide between pursuing their dreams or fighting for their love against all odds.",
                    MariposaDuaDuaCover, false, "Romance", 5.0f));

            books.add(new Book("22", "Galaksi", "Poppi Pertiwi", 2018, "A story about Galaksi Aldebaran, a tough high school gang leader whose life changes when he falls for Kejora, a kind and bright girl who teaches him about true love.",
                    GalaksiCover, false, "Romance", 4.0f));

            books.add(new Book("23", "Antares", "Rweinda", 2019,
                    "A story about Antares Sebastian Aldevaro, the leader of a rebellious gang, who meets Zea, a new girl with a hidden past that challenges his understanding of loyalty and love.",
                    AntaresCover, false, "Romance", 3.8f));

            books.add(new Book("24", "Jessie", "Rweinda", 2019,
                    "A story about Jessie, a girl with a painful past who tries to rebuild her life, only to find herself falling for someone who might break her heart all over again.",
                    JessieCover, false, "Romance", 4.1f));

            books.add(new Book("25", "Teluk Alaska", "Eka Aryani", 2019, "A story about Ana and Alister, two lonely souls from different worlds, whose unexpected meeting at Teluk Alaska brings healing, hope, and first love.",
                    TelukAlaskaCover, false, "Romance", 4.2f));

            books.add(new Book("26", "Dear Nathan", "Erisca Febriani", 2016,
                    "A story about Ana and Alister, two lonely souls from different worlds, whose unexpected meeting at Teluk Alaska brings healing, hope, and first love.",
                    DearNathanCover, false, "Romance", 3.0f));

            for (Book book : books) {
                if (!allGenres.contains(book.getGenre())) {
                    allGenres.add(book.getGenre());
                }
            }
        }

    public List<Book> getAllBooks() {
        List<Book> sortedBooks = new ArrayList<>(books);
        sortedBooks.sort((b1, b2) -> b2.getDateAdded().compareTo(b1.getDateAdded()));
        return sortedBooks;
    }

    public List<Book> getBooksByGenre(String genre) {
        if (genre.equals("All")) {
            return getAllBooks();
        }
        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                filteredBooks.add(book);
            }
        }
        filteredBooks.sort((b1, b2) -> b2.getDateAdded().compareTo(b1.getDateAdded()));
        return filteredBooks;
    }

    public List<Book> getLikedBooks() {
        List<Book> likedBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.isLiked()) {
                likedBooks.add(book);
            }
        }
        Log.d(TAG, "getLikedBooks: Found " + likedBooks.size() + " liked books");
        likedBooks.sort((b1, b2) -> b2.getDateAdded().compareTo(b1.getDateAdded()));
        return likedBooks;
    }

    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    book.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public void addBook(Book book) {
        books.add(book);
        if (!allGenres.contains(book.getGenre())) {
            allGenres.add(book.getGenre());
        }
    }

    public void toggleLike(String bookId) {
        Log.d(TAG, "toggleLike: Toggling like for book ID: " + bookId);
        for (Book book : books) {
            if (book.getId().equals(bookId)) {
                boolean newStatus = !book.isLiked();
                book.setLiked(newStatus);
                Log.d(TAG, "toggleLike: Book " + book.getTitle() + " like status changed to: " + newStatus);
                break;
            }
        }
    }

    public Book getBookById(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                return book;
            }
        }
        return null;
    }

    public List<String> getAllGenres() {
        List<String> genres = new ArrayList<>(allGenres);
        genres.add(0, "All");
        return genres;
    }

    public void addGenre(String genre) {
        if (!allGenres.contains(genre)) {
            allGenres.add(genre);
            Log.d(TAG, "New genre added: " + genre);
        }
    }
}