Selamat datang di repositori LAB-WEB-04-2024! Repositori ini adalah tempat pengumpulan tugas praktikum untuk mata kuliah Praktikum Pemrograman Web 2024. Berikut adalah panduan singkat untuk mengumpulkan tugas di repositori ini.

Alur pengumpulan tugas ke repositori ini:
Fork repositori ini

Clone repositori hasil fork anda

git clone https://github.com/YOUR_USERNAME/LAB-WEB-04-2024.git
Setelah anda clone, masuk ke folder hasil clone tersebut lalu buat branch dengan nama NIM anda

cd LAB-WEB-04-2024
git branch NIM_ANDA
git checkout NIM_ANDA
Setelah anda pindah ke branch yang telah anda buat, buat sebuah folder dengan nama NIM anda dan masuk ke folder tersebut.

mkdir NIM_ANDA
cd NIM_ANDA
Didalam folder tersebut, buat sebuah folder dengan nama Praktikum-n, n = praktikum keberapa

mkdir "Praktikum-n"
cd "Praktikum-n"

CATATAN: n DI SINI ADALAH NOMOR PRAKTIKUM KE BERAPA
CONTOH: Praktikum-1
Semua file untuk tugas praktikum ke-n, disimpan kedalam folder Praktikum-n

Setiap kali melakukan perubahan, lakukan proses commit dengan pesan yang deskriptif

git add . #perintah ini memilih seluruh file sekaligus
git status untuk mengecek apakah file sudah ter add atau tidak.
Jika file yang ingin di add sudah berwarna hijau lanjut ke commit.
Jika file yang ingin di add berwarna merah lakukan add terlebih dahulu

git commit -m "pesan mengenai penambahan atau perubahan apa yang anda lakukan"
Setelah asistensi dan tugas anda disetujui, push seluruh file jawaban yang telah anda buat

# pastikan proses commit telah selesai terhadap setiap file
git push origin NIM_ANDA
Masuk ke akun GitHub anda, dan buka repo yang telah anda fork dan clone. Lihat perubahan yang terjadi pada repo tersebut dan pastikan bahwa tugas yang telah anda push sesuai dan berada pada repo tersebut.

Pilih menu Pull request dan lakukan pull request pada tugas praktikum anda.

Tips Tambahan
Pastikan untuk memberi nama yang deskriptif pada pesan commit tugas.
Gunakan pesan commit yang jelas agar mudah dimengerti olehmu suatu saat nanti.
Terima kasih sudah mengerjakan tugas ygy!
