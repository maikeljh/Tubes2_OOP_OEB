# Tubes2_OOP_OEB


## Daftar Isi
* [Deskripsi Singkat Program](#deskripsi-singkat-program)
* [Requirements](#requirements)
* [Cara Menjalankan Program](#cara-menjalankan-program)
* [Screenshot](#screenshot)
* [Dibuat Oleh](#dibuat-oleh)
## Deskripsi Singkat Program
Aplikasi ini merupakan suatu program yang dapat digunakan oleh kasir untuk mendata segala keperluan toko BNMO. Program ini dapat digunakan untuk mendata pembelian pelanggan yang dilayani agar pelanggan mendapat keterangan barang yang dibeli beserta harga yang harus dibayar. Untuk hal tersebut, program ini dapat mencetak bill pembelian yang dilakukan pelanggan untuk membantu transaksi pembayaran pelanggan sekaligus pendataan oleh kasir untuk dimasukkan pada laporan penjualan barang - barang pada toko. Selain itu, kasir juga dapat mendata setiap pelanggan yang ingin mendaftar menjadi anggota member atau VIP untuk mendapatkan keuntungan lebih dari pelanggan biasa saat berbelanja di toko BNMO. Program ini dibuat dalam bahasa java dengan extensi JavaFX serta penggunaan maven.

## Requirements
- Java versi 17 atau lebih
- Maven versi 3.8 atau lebih
- JavaFX-SDK versi 17 atau lebih (Unduh pada: https://download2.gluonhq.com/openjfx/17.0.7/openjfx-17.0.7_linux-x64_bin-sdk.zip)
- XServer (untuk menampilkan aplikasi)
- Windows Subsystem Linux (WSL) untuk menjalankan program

## Cara Menjalankan Program
Untuk menjalankan jar program ini, lakukan:
1. Jalankan XServer
2. Masuk ke directory letak jar pada WSL terminal anda.
3. Jalankan perintah `export DISPLAY=:0`
4. Bila belum ada jdk versi 17 pada terminal, jalankan perintah `sudo apt-get update`. Lalu jalankan `sudo apt install openjdk-17-jdk`. 
5. Jalankan `java --module-path %PATH_TO_FX% --add-modules ALL-MODULE-PATH -jar ApplicationBNMOStore.jar`

Perhatikan: 
- Pada %PATH_TO_FX% dapat digantikan dengan direktori langsung lokasi JavaFX-SDK yang telah diunggah.
- Untuk membuat %PATH_TO_FX%, masukkan jalur direktori ke JavaFX-SDK ke sebuah variabel tersebut. Kemudian masukkan %PATH_TO_FX% kedalam Path variabel.

Untuk menjalankan program pada project ini, lakukan:
1. Masuk pada directory projek menggunakan intellij
2. Pastikan SDK menggunakan Java (jdk) versi 17 pada File >> Project Structure dari Intellij.
3. Jalankan program pada terminal di Intellij menggunakan `mvn clean javafx:run`

## Screenshot
![Screenshot_3146](https://user-images.githubusercontent.com/87570374/236706608-be03c181-f6c8-4a4c-8abd-623014ed16fa.png)

![Screenshot_3147](https://user-images.githubusercontent.com/87570374/236706613-48b00784-8c54-430f-b7d3-4b9c7fcf9c24.png)

![Screenshot_3148](https://user-images.githubusercontent.com/87570374/236706614-106181e8-131f-400d-ba25-e936a4469cf8.png)

![Screenshot_3149](https://user-images.githubusercontent.com/87570374/236706615-24d12608-fd4f-44df-8501-4587d10b98cb.png)

![Screenshot_3150](https://user-images.githubusercontent.com/87570374/236706617-e13e5d5d-1e36-4c21-b26a-5a25b993bdac.png)

![Screenshot_3151](https://user-images.githubusercontent.com/87570374/236706619-4e15a16f-95d5-4954-8e44-d2f90a7cd414.png)

![Screenshot_3152](https://user-images.githubusercontent.com/87570374/236706622-eaf3ec44-f9aa-4912-8447-530b438ea5d9.png)

![Screenshot_3153](https://user-images.githubusercontent.com/87570374/236706624-00f69513-922b-463a-b650-a94c45855297.png)

![Screenshot_3154](https://user-images.githubusercontent.com/87570374/236706629-79e3209b-26d1-4be6-95dc-f156ffd71e55.png)


## Dibuat oleh
* Nama: Arleen Chrysantha Gunardi
* NIM: 13521059
* Prodi/Jurusan: STEI/Teknik Informatika
* Profile Github: arleenchr
##
* Nama: Michael Jonathan Halim
* NIM: 13521124
* Prodi/Jurusan: STEI/Teknik Informatika
* Profile Github: maikeljh
##
* Nama: Marcel Ryan Antony
* NIM: 13521127
* Prodi/Jurusan: STEI/Teknik Informatika
* Profile Github: marcelryan
##
* Nama: Raynard Tanadi
* NIM: 13521143
* Prodi/Jurusan: STEI/Teknik Informatika
* Profile Github: Raylouiss
##
* Nama: Kenneth Dave Bahana
* NIM: 13521145
* Prodi/Jurusan: STEI/Teknik Informatika
* Profile Github: kenndave
