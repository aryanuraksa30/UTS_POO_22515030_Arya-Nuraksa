import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static final Perpustakaan LIB = new Perpustakaan("Perpustakaan Mini", 5);

    /** Membaca teks tidak kosong; keluar dengan rapi jika input habis. */
    private static String bacaTeks(String label) {
        while (true) {
            System.out.print(label + ": ");
            if (!SC.hasNextLine()) { System.out.println("\n(Input habis, keluar)"); System.exit(0); }
            String s = SC.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("[ERROR] Input tidak boleh kosong.");
        }
    }
    /** mengulang bila input bukan angka. */
    private static int bacaInt(String label) {
        while (true) {
            String s = bacaTeks(label);
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Masukkan angka bulat yang valid.");
            }
        }
    }

    private static void tampilMenu() {
        System.out.println("\n===== PERPUSTAKAAN MINI =====");
        System.out.println("1. Tambah buku");
        System.out.println("2. Tampilkan semua buku");
        System.out.println("3. Tambah anggota");
        System.out.println("4. Tampilkan semua anggota");
        System.out.println("5. Pinjam buku (transaksi)");
        System.out.println("6. Cari buku (kode / judul)");
        System.out.println("7. Riwayat peminjaman (Stack)");
        System.out.println("8. ringkasan perpustakaan");
        System.out.println("9. Tampilkan daftar peminjaman");
        System.out.println("0. Keluar");
    }

    /** menu 7*/
    private static void menuStack() {
        StackRiwayat st = LIB.getRiwayat();
        System.out.println("--- Riwayat Peminjaman (Stack) ---");
        st.tampilkan();
        System.out.println("Terisi: " + st.size() + "/" + st.getKapasitas()
                + (st.isFull() ? " (PENUH)" : st.isEmpty() ? " (KOSONG)" : ""));
        if (st.isEmpty()) return;
        System.out.println("1. Hapus (batalkan peminjaman terakhir)");
        System.out.println("2. Kembali ke menu");
        int pilih = bacaInt("Pilih");
        if (pilih == 1) {
            Peminjaman x = LIB.batalkanPeminjamanTerakhir();
            System.out.println("[OK] Dibatalkan: " + x.getIdPinjam() + " - " + x.getBuku().getJudul());
            st.tampilkan();
        } else if (pilih != 2) {
            System.out.println("[ERROR] Pilihan tidak valid.");
        }
    }
    /** menu 6*/
    private static void cari() {
        System.out.println("1. Cari berdasarkan kode  2. Cari berdasarkan judul");
        int p = bacaInt("Pilih");
        if (p == 1) {
            Buku b = LIB.cariBukuByKode(bacaTeks("Kode buku"));
            if (b == null) System.out.println("[INFO] Buku tidak ditemukan.");
            else b.tampilkanInfo();
        } else if (p == 2) {
            ArrayList<Buku> hasil = LIB.cariBukuByJudul(bacaTeks("Kata kunci judul"));
            if (hasil.isEmpty()) System.out.println("[INFO] Buku tidak ditemukan.");
            for (Buku b : hasil) b.tampilkanInfo();
        } else {
            System.out.println("[ERROR] Pilihan tidak valid.");
        }
    }

    public static void main(String[] args) {
        boolean jalan = true;
        while (jalan) {
            tampilMenu();
            int pilih = bacaInt("Pilih menu");
            try {
                switch (pilih) {
                    case 1:
                        String kode = bacaTeks("Kode");
                        String judul = bacaTeks("Judul");
                        String penulis = bacaTeks("Penulis");
                        int stok = bacaInt("Stok");
                        Buku bk = new Buku();
                        bk.setKode(kode);
                        bk.setJudul(judul);
                        bk.setPenulis(penulis);
                        bk.setStok(stok);
                        LIB.tambahBuku(bk);
                        System.out.println("[OK] Buku ditambahkan.");
                        break;
                    case 2: LIB.tampilkanBuku(); break;
                    case 3:
                        String id = bacaTeks("ID anggota");
                        String nama = bacaTeks("Nama");
                        String telp = bacaTeks("Telepon");
                        Anggota ang = new Anggota();
                        ang.setIdAnggota(id);
                        ang.setNama(nama);
                        ang.setTelepon(telp);
                        LIB.tambahAnggota(ang);
                        System.out.println("[OK] Anggota ditambahkan.");
                        break;
                    case 4: LIB.tampilkanAnggota(); break;
                    case 5:
                        String ia = bacaTeks("ID anggota");
                        String kb = bacaTeks("Kode buku");
                        int hari = bacaInt("Lama pinjam (hari)");
                        Peminjaman pj = LIB.pinjamBuku(ia, kb, hari);
                        System.out.println("[OK] Peminjaman " + pj.getIdPinjam() + " berhasil.");
                        break;
                    case 6: cari(); break;
                    case 7: menuStack(); break;
                    case 8: LIB.tampilkanRingkasan(); break;
                    case 9: LIB.tampilkanPeminjaman(); break;
                    case 0: jalan = false; System.out.println("Terima kasih."); break;
                    default: System.out.println("[ERROR] Menu tidak tersedia.");
                }
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
    }
}
