package com.example.tp4dan5_h071231006;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataGenerator {
    public static List<Book> bookList = new ArrayList<>();

    public static List<Book> generateDummyBooks() {
        if (!bookList.isEmpty()) return bookList;

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "I Decided to Live as Me",
                "Kim Suhyun",
                2020,
                "We can't be or need to be anything else but ourselves.",
                "book",
                "Young Adult",
                4.7f,
                " It’s a pretty good guide if you’re starting your journey as an adult or even if you’re already halfway there."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "11:11",
                "Fiersa Besari",
                2018,
                "Orang bilang, jodoh takkan ke mana. Aku rasa mereka keliru. Jodoh akan kemana-mana terlebih dahulu sebelum akhirnya menetap. Ketika waktunya telah tiba, ketika segala rasa sudah tidak bisa lagi dilawan, yang bisa kita lakukan hanyalah merangkul tanpa perlu banyak kompromi.",
                "book2",
                "Romance",
                4.1f,
                "11:11 merupakan proyek Album Buku yang berisi 11 cerita pendek dengan ruh dari 11 lagu di album lama Bung Fiersa. Beberapa lagunya bisa dibilang sudah banyak didengar oleh penggemarnya. "
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Konspirasi Alam Semesta",
                "Fiersa Besari",
                2015,
                "Seperti apakah warna cinta? Apakah merah muda mewakili rekahannya, ataukah kelabu mewakili pecahannya?",
                "book3",
                "Fiction",
                4.3f,
                "Chillingly prophetic and thought-provoking."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Negeri Di Ujung Tanduk",
                "Tere Liye",
                2013,
                "Di Negeri di Ujung Tanduk kehidupan semakin rusak, bukan karena orang jahat semakin banyak, tapi semakin banyak orang yang memilih tidak peduli lagi",
                "book4",
                "Fiction",
                4.3f,
                "Ketegangan, intrik, twist-nya masih tetap cetar menggelora."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Asih",
                "Risa Saraswati",
                2018,
                "Namanya Kasih.\n" +
                        "Kedua orangtuanya berharap dia akan tumbuh dewasa dengan hati yang kaya kasih sayang. Bisa saja awalnya begitu, sebelum dirinya menjadi sosok yang seolah tak punya hati.\n" +
                        "\n" +
                        "‘Kasih’ menjadi nama yang terlalu indah untuk si wajah kaku tanpa senyuman itu. Wajah yang lebih baik tak usah tersenyum, ketimbang bermalam-malam dihantui oleh bayangan mengerikan. Entah sejak kapan panggilan ‘Asih’ tersemat pada dirinya.\n" +
                        "\n" +
                        "Saat kali pertama bertemu, aku mengira hanya aku yang dia temui dengan cara seperti itu. Namun, nyatanya tidak. Cerita demi cerita dari mulut orangtua dan saudara - saudaraku bergulir. Ternyata, jauh sebelum aku lahir, dia sudah sering mencoba mendatangi banyak manusia.\n" +
                        "\n" +
                        "Asih, datanglah...\n" +
                        "Kali ini, gerbang dialog kubuka lebar untukmu. Tapi ingat, aku tak berharap lebih dari sekadar bicara denganmu. Aku tak ingin menjadi teman baikmu. Biarkan aku menjadi jembatan antara pikiran mereka yang mencibir dengan kisah sesungguhnya....",
                "book5",
                "Horror",
                3.9f,
                "Aku masih terheran-heran dengan karakter Asih yang kesannya lebay banget. Tapi mungkin memang zaman dulu masyarakatnya belum seterbuka dan setoleran sekarang ya, jadi tekanan yang perempuan muda rasakan itu lebih berat. Bisa memicu emosi buruk nih kalau memikirkan bagaimana perempuan dijadikan korban kebejatan lelaki. RAPE IS NOT OKAY. You need to speak up about it!"
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Laut Bercerita",
                "Leila S.Chudori",
                2017,
                "bertutur tentang kisah keluarga yang kehilangan, sekumpulan sahabat yang merasakan kekosongan di dada, sekelompok orang yang gemar menyiksa dan lancar berkhianat, sejumlah keluarga yang mencari kejelasan akan anaknya, dan tentang cinta yang tak akan luntur.",
                "book6",
                "History",
                4.6f,
                "sebagai orang yang sulit untuk memulai membaca buku-buku non-fiksi sejarah, buku ini sangat membantu anak-anak muda indonesia dalam mempelajari sejarah yang minim sensor.\n" +
                        "melalui buku ini, aku sadar kalau ternyata selama ini, buku-buku sejarah resmi yang diajarkan di sekolah-sekolah didistorsi sedemikian rupa untuk kepentingan rezim."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "The Lord of the Rings",
                "J.R.R. Tolkien",
                1954,
                "An epic fantasy adventure to destroy the One Ring.",
                "book9",
                "Fantasy",
                4.8f,
                "The definitive fantasy epic."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Harry Potter and the Philosopher's Stone",
                "J.K. Rowling",
                1997,
                "The first book in the Harry Potter series.",
                "book8",
                "Fantasy",
                4.7f,
                "Magical start to an incredible series."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "To Kill a Mockingbird",
                "Harper Lee",
                1960,
                "A powerful story of racial injustice and moral growth in the American South.",
                "book7",
                "Fiction",
                4.5f,
                "An important novel that remains relevant today."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Mariposa",
                "Luluk HF",
                2018,
                "Acha, siswi pintar dan keras kepala yang jatuh cinta pada Iqbal, cowok cuek di sekolahnya.",
                "book10",
                "Romance",
                4.2f,
                "Ceritanya ringan tapi menghibur, cocok buat remaja."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Laskar Pelangi",
                "Andrea Hirata",
                2005,
                "Kisah inspiratif tentang perjuangan 10 anak dari keluarga miskin yang bersekolah di Belitung.",
                "book11",
                "Romance",
                4.7f,
                "Menyentuh dan membuka mata tentang pentingnya pendidikan."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Tentang Kamu",
                "Tere Liye",
                2016,
                "Petualangan seorang pengacara muda dalam mengungkap masa lalu seorang wanita tua misterius.",
                "book12",
                "Mystery",
                4.6f,
                "Penuh teka-teki dan kejutan yang emosional."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Geez & Ann",
                "Rintik Sedu",
                2017,
                "Kisah romansa remaja modern yang penuh perasaan dan konflik emosional.",
                "book13",
                "Romance",
                4.1f,
                "Relatable dan penuh dialog emosional."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Jadi Ibu",
                "Revalina Naya",
                2023,
                "Capek kuliah, pengen nikah aja, terus jadi ibu!",
                "book14",
                "Fiction",
                3.2f,
                "Dari novel ini, aku belajar untuk lebih berhati-hati dalam berucap karena bisa aja apa yang kita ucapkan itu menjadi doa dan terkabulkan. Belajar untuk menerima pasangan kita, belajar untuk menerima takdir, belajar cepat beradaptasi, belajar untuk menjadi orang tua yang tidak mengekang atau terlalu mengatur ke anak-anak karena itu malah membawa dampak yang negatif ke anak-anak, serta masih banyak lagi pesan moral dari novel ini yang bisa kita terapkan dalam kehidupan sehari-hari."
        ));

        bookList.add(new Book(
                UUID.randomUUID().toString(),
                "Rumah untuk Alie",
                "Lenn Liu",
                2024,
                "Alie Ishala Samantha, 16 tahun, tak pernah mengira hidupnya akan sepelik ini.",
                "book15",
                "Young Adult",
                4.2f,
                "Sangat menyayat hati pas endingnya... and always penyesalan datangnya selalu di akhir"
        ));
        return bookList;
    }
}