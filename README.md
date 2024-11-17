# My Movies App Expert

[![CircleCI](https://dl.circleci.com/status-badge/img/gh/abizarhaikal/My_Movies_App_Expert/tree/master.svg?style=svg&circle-token=CCIPRJ_6iAgcCd6LwSn4Nk6npaYot_719bdd01878a06988bab58af99998e027c81a495)](https://dl.circleci.com/status-badge/redirect/gh/abizarhaikal/My_Movies_App_Expert/tree/master)


Aplikasi ini merupakan implementasi dari arsitektur **Clean Architecture** menggunakan modul `app`, `core`, dan `favorite`. Proyek ini dibangun dengan tujuan untuk mengelola data film menggunakan teknologi Android terkini.

## 📂 Struktur Proyek

- **`app/`**: Berisi kode utama untuk aplikasi, seperti activity, fragment, dan integrasi modul lainnya.
- **`core/`**: Berisi layer domain dan data, seperti repository, use case, dan entity.
- **`favorite/`**: Modul dinamis untuk fitur favorit, memungkinkan pengelolaan film yang ditandai sebagai favorit oleh pengguna.

## ⚙️ Teknologi yang Digunakan

- **Bahasa Pemrograman**: Kotlin 100%
- **Dependency Injection**: [Koin](https://insert-link)
- **Database Lokal**: Room Database dengan dukungan **encryption**.
- **Network**: Retrofit untuk API request.
- **Build Tools**: Gradle (build.gradle.kts)
- **ProGuard**: Obfuscation diterapkan untuk keamanan tambahan.

## 🚀 Pipeline CI/CD

Proyek ini menggunakan [CircleCI](https://circleci.com/) untuk Continuous Integration. Pipeline yang terkonfigurasi memastikan:
- Build dilakukan dengan image `Docker Large`.
- Notifikasi sukses/error pada setiap push ke branch `circleci-project-setup`.
- **Status Build**: **Berhasil ✅** (lihat badge di atas).

## 📑 Cara Menjalankan Proyek

1. Clone repository ini:
   ```bash
   git clone https://github.com/abizarhaikal/My_Movies_App_Expert.git
