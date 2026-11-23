import java.util.ArrayList;

/**private class*/
public class Perpustakaan {
    private final String nama;
    private final ArrayList<Buku> daftarBuku = new ArrayList<>();
    private final ArrayList<Anggota> daftarAnggota = new ArrayList<>();
    private final ArrayList<Peminjaman> daftarPeminjaman = new ArrayList<>();
    private final StackRiwayat riwayat;
    private int counterPinjam = 0;

    public Perpustakaan(String nama, int kapasitasRiwayat) {
        this.nama = nama;
        this.riwayat = new StackRiwayat(kapasitasRiwayat);
    }

    public String getNama() { return nama; }
    public StackRiwayat getRiwayat() { return riwayat; }
    public ArrayList<Buku> getDaftarBuku() { return daftarBuku; }
    public ArrayList<Anggota> getDaftarAnggota() { return daftarAnggota; }

    /** Menambah buku; kode harus unik. */
    public void tambahBuku(Buku b) {
        if (cariBukuByKode(b.getKode()) != null) {
            throw new IllegalArgumentException("Kode buku " + b.getKode() + " sudah terdaftar.");
        }
        daftarBuku.add(b);
    }

    /** Menambah anggota; ID harus unik. */
    public void tambahAnggota(Anggota a) {
        if (cariAnggotaById(a.getIdAnggota()) != null) {
            throw new IllegalArgumentException("ID anggota " + a.getIdAnggota() + " sudah terdaftar.");
        }
        daftarAnggota.add(a);
    }

    public Buku cariBukuByKode(String kode) {
        for (Buku b : daftarBuku) {
            if (b.getKode().equalsIgnoreCase(kode.trim())) return b;
        }
        return null;
    }

    /** Mencari buku berdasarkan sebagian judul (tidak peka huruf besar/kecil). */
    public ArrayList<Buku> cariBukuByJudul(String kata) {
        ArrayList<Buku> hasil = new ArrayList<>();
        for (Buku b : daftarBuku) {
            if (b.getJudul().toLowerCase().contains(kata.trim().toLowerCase())) hasil.add(b);
        }
        return hasil;
    }

    public Anggota cariAnggotaById(String id) {
        for (Anggota a : daftarAnggota) {
            if (a.getIdAnggota().equalsIgnoreCase(id.trim())) return a;
        }
        return null;
    }

    /** semua aturan dicek DULU, baru data diubah */
    public Peminjaman pinjamBuku(String idAnggota, String kodeBuku, int lamaHari) {
        Anggota a = cariAnggotaById(idAnggota);
        if (a == null) throw new IllegalArgumentException("Anggota " + idAnggota + " tidak ditemukan.");
        Buku b = cariBukuByKode(kodeBuku);
        if (b == null) throw new IllegalArgumentException("Buku " + kodeBuku + " tidak ditemukan.");
        if (!b.isTersedia()) throw new IllegalStateException("Stok buku '" + b.getJudul() + "' habis.");
        if (!a.bolehMeminjam()) {
            throw new IllegalStateException("Anggota " + a.getNama() + " sudah meminjam "
                    + Anggota.MAKS_PINJAM + " buku (batas maksimal).");
        }
        if (riwayat.isFull()) {
            throw new IllegalStateException("Riwayat penuh (" + riwayat.getKapasitas()
                    + "). Batalkan peminjaman terakhir terlebih dahulu.");
        }
        Peminjaman p = new Peminjaman("P" + String.format("%03d", counterPinjam + 1), a, b, lamaHari);
        counterPinjam++;
        b.kurangiStok();
        a.tambahPinjaman();
        daftarPeminjaman.add(p);
        riwayat.push(p);
        return p;
    }

    /** Membatalkan peminjaman terakhir (pop stack) dan memulihkan stok/hitungan. */
    public Peminjaman batalkanPeminjamanTerakhir() {
        Peminjaman p = riwayat.pop();
        if (p == null) {
            throw new IllegalStateException("Riwayat kosong, tidak ada yang dibatalkan.");
        }
        p.tandaiKembali();
        daftarPeminjaman.remove(p);
        return p;
    }

    /** Menampilkan ringkasan sederhana. */
    public void tampilkanRingkasan() {
        int totalStok = 0;
        for (Buku b : daftarBuku) totalStok += b.getStok();
        int aktif = 0;
        for (Peminjaman p : daftarPeminjaman) if (!p.isDikembalikan()) aktif++;
        System.out.println("=== Ringkasan Perpustakaan" + getNama() + "  ===");
        System.out.println("Judul buku        : " + getDaftarBuku().size());
        System.out.println("Total stok tersedia: " + totalStok);
        System.out.println("Jumlah anggota    : " + getDaftarAnggota().size());
        System.out.println("Peminjaman aktif  : " + aktif);
        System.out.println("Isi riwayat stack : " + riwayat.size() + "/" + riwayat.getKapasitas());
    }

    public void tampilkanBuku() {
        if (daftarBuku.isEmpty()) { System.out.println("(Belum ada buku)"); return; }
        for (Buku b : daftarBuku) b.tampilkanInfo();
    }

    public void tampilkanAnggota() {
        if (daftarAnggota.isEmpty()) { System.out.println("(Belum ada anggota)"); return; }
        for (Anggota a : daftarAnggota) a.tampilkanInfo();
    }

    public void tampilkanPeminjaman() {
        if (daftarPeminjaman.isEmpty()) { System.out.println("(Belum ada peminjaman)"); return; }
        for (Peminjaman p : daftarPeminjaman) p.tampilkanInfo();
    }
}
