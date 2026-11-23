public class Peminjaman {
    /** Denda per hari keterlambatan */
    public static final double DENDA_PER_HARI = 1000.0;

    private String idPinjam;
    private Anggota anggota;
    private Buku buku;
    private int lamaHari;
    private boolean dikembalikan;

    /** Konstruktor default. */
    public Peminjaman() {
        this.idPinjam = "-";
        this.lamaHari = 7;
        this.dikembalikan = false;
    }

    /** Konstruktor berparameter. */
    public Peminjaman(String idPinjam, Anggota anggota, Buku buku, int lamaHari) {
        this.setIdPinjam(idPinjam);
        this.setAnggota(anggota);
        this.setBuku(buku);
        this.setLamaHari(lamaHari);
        this.dikembalikan = false;
    }

    public String getIdPinjam() { return idPinjam; }
    public Anggota getAnggota() { return anggota; }
    public Buku getBuku() { return buku; }
    public int getLamaHari() { return lamaHari; }
    public boolean isDikembalikan() { return dikembalikan; }

    public void setIdPinjam(String idPinjam) {
        if (idPinjam == null || idPinjam.trim().isEmpty()) {
            throw new IllegalArgumentException("ID peminjaman tidak boleh kosong.");
        }
        this.idPinjam = idPinjam;
    }

    public void setAnggota(Anggota anggota) {
        if (anggota == null) {
            throw new IllegalArgumentException("Anggota tidak boleh null.");
        }
        this.anggota = anggota;
    }

    public void setBuku(Buku buku) {
        if (buku == null) {
            throw new IllegalArgumentException("Buku tidak boleh null.");
        }
        this.buku = buku;
    }

    /** lama pinjaman jika 1-30 hari */
    public void setLamaHari(int lamaHari) {
        if (lamaHari < 1 || lamaHari > 30) {
            throw new IllegalArgumentException("Lama pinjam harus 1 sampai 30 hari.");
        }
        this.lamaHari = lamaHari;
    }

    /** Menghitung denda berdasarkan hari terlambat. */
    public double hitungDenda(int hariTerlambat) {
        return hariTerlambat > 0 ? hariTerlambat * DENDA_PER_HARI : 0;
    }

    /** Menandai buku sudah dikembalikan dan mengembalikan stok. */
    public void tandaiKembali() {
        if (dikembalikan) {
            throw new IllegalStateException("Peminjaman " + idPinjam + " sudah dikembalikan.");
        }
        dikembalikan = true;
        buku.tambahStok();
        anggota.kurangiPinjaman();
    }

    public void tampilkanInfo() {
        System.out.printf("%-6s | %-18s | %-24s | %2d hari | %s%n",getIdPinjam(),
                getAnggota().getNama(), getBuku().getJudul(), getLamaHari(),
                isDikembalikan() ? "Kembali" : "Dipinjam");
    }
}
