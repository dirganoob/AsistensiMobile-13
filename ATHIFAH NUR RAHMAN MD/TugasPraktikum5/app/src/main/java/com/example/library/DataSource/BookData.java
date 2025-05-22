package com.example.library.DataSource;

import com.example.library.Models.Book;
import com.example.library.R;

import java.util.ArrayList;

public class BookData {
    public static ArrayList<Book> books = generateDummyBooks();

    private static ArrayList<Book> generateDummyBooks() {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(R.drawable.iv_book01,
                "Laut Bercerita",
                "Leila S. Chudori",
                2017,
                "Sebuah kisah kehilangan, perlawanan, dan harapan.",
                "Drama",
                "Laut Bercerita mengisahkan tentang Biru Laut, seorang aktivis yang menghilang pada masa penculikan mahasiswa 1998. Novel ini menyentuh tema keluarga, perjuangan, dan kekuasaan.",
                379,
                false,
                4.5
                ));
        books.add(new Book(R.drawable.iv_book02,
                "Namaku Alam",
                "Leila S. Chudori",
                2023,
                "Cerita coming-of-age seorang remaja bernama Alam di tengah pergolakan keluarga dan politik.",
                "Drama",
                "Alam adalah anak remaja yang dibesarkan oleh ibu tunggalnya. Ketika ayahnya kembali dari pengasingan politik, ia dihadapkan pada pilihan-pilihan hidup yang sulit.",
                252,
                false,
                5
        ));

        books.add(new Book(R.drawable.iv_book03,
                "Bumi Manusia",
                "Pramoedya Ananta Toer",
                1980,
                "Kisah cinta dan perjuangan seorang pribumi dalam sistem kolonial Hindia Belanda.",
                "History",
                "Minke, seorang pemuda pribumi terpelajar, jatuh cinta pada Annelies dan berjuang melawan ketidakadilan kolonial.",
                535,
                false,
                5
        ));

        books.add(new Book(R.drawable.iv_book04,
                "Laskar Pelangi",
                "Andrea Hirata",
                2005,
                "Perjalanan inspiratif anak-anak Belitung dalam meraih pendidikan.",
                "Horror",
                "Ikal dan teman-temannya membentuk kelompok Laskar Pelangi di sekolah sederhana mereka dan membuktikan bahwa impian tak terbatasi keadaan.",
                529,
                false,
                5
        ));

        books.add(new Book(R.drawable.iv_book05,
                "Himpunan",
                "Citra Saras",
                2022,
                "Antologi kisah dan renungan tentang cinta, kehilangan, dan keberanian.",
                "Comedy",
                "Buku ini menyajikan kumpulan cerita pendek dan esai reflektif yang menggambarkan pengalaman manusia secara intim dan penuh makna.",
                190,
                false,
                4
        ));

        books.add(new Book(R.drawable.iv_book06,
                "Tentang Kamu",
                "Tere Liye",
                2016,
                "Sebuah kisah menyentuh tentang perjalanan hidup Sri Ningsih yang penuh teka-teki, cinta, dan pengorbanan.",
                "Drama",
                "Seorang pemuda dari firma hukum di London mengungkap kisah hidup seorang wanita luar biasa melalui dokumen warisan misterius.",
                524,
                false,
                5
        ));

        books.add(new Book(R.drawable.iv_book07,
                "Si Anak Kuat",
                "Tere Liye",
                2020,
                "Kisah seorang anak laki-laki yang tumbuh dengan kekuatan batin luar biasa untuk menghadapi kerasnya kehidupan.",
                "Sci-Fi",
                "Melalui perjuangan dan kerja keras, ia menunjukkan ketangguhan dalam menghadapi kemiskinan dan tekanan sosial.",
                380,
                false,
                5
        ));

        books.add(new Book(R.drawable.iv_book08,
                "Perahu Kertas",
                "Dee Lestari",
                2009,
                "Sebuah kisah cinta dua remaja dengan mimpi besar yang dipisahkan oleh waktu dan keadaan.",
                "Romance",
                "Kugy dan Keenan harus memilih antara cinta, keluarga, dan mimpi, dalam perjalanan emosional yang penuh inspirasi.",
                444,
                false,
                5
        ));

        books.add(new Book(R.drawable.iv_book09,
                "Funiculi Funicula: Before the Coffee Gets Cold",
                "Toshikazu Kawaguchi",
                2015,
                "Sebuah kafe di Tokyo menawarkan kesempatan untuk kembali ke masa lalu selama secangkir kopi belum habis.",
                "Slice of Life",
                "Empat kisah menyentuh tentang cinta, penyesalan, dan harapan, dalam nuansa fantasi yang lembut dan penuh makna.",
                272,
                false,
                5
        ));

        books.add(new Book(R.drawable.iv_book10,
                "The Things You Can See Only When You Slow Down",
                "Haemin Sunim",
                2017,
                "Panduan bijak untuk menemukan kedamaian batin di tengah kesibukan dunia modern.",
                "Slice of Life",
                "Melalui refleksi spiritual dan nasihat penuh empati, buku ini membantu pembaca memperlambat langkah dan memahami kehidupan dengan lebih dalam.",
                288,
                false,
                5
        ));

        books.add(new Book(R.drawable.iv_book11,
                "Hansel ande Gretel Book",
                "Brothers Grimm",
                2018,
                "Panduan bijak untuk menemukan kedamaian batin di tengah kesibukan dunia modern.",
                "Fantasy",
                "Melalui refleksi spiritual dan nasihat penuh empati, buku ini membantu pembaca memperlambat langkah dan memahami kehidupan dengan lebih dalam.",
                289,
                false,
                4
        ));

        return books;
    }
}
