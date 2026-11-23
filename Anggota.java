public class Anggota {
    /** Batas maksimal buku. */
    public static final int MAKS_PINJAM = 3;

    private String idAnggota;
    private String nama;
    private String telepon;
    private int jumlahPinjam;

    /** Konstruktor default. */
    public Anggota() {
        this("-", "Tanpa Nama", "-");
    }

    /** Konstruktor berparameter. */
    public Anggota(String idAnggota, String nama, String telepon) {
        this.setIdAnggota(idAnggota);
        this.setNama(nama);
        this.setTelepon(telepon);
        this.jumlahPinjam = 0;
    }

    public String getIdAnggota() { return idAnggota; }
    public String getNama() { return nama; }
    public String getTelepon() { return telepon; }
    public int getJumlahPinjam() { return jumlahPinjam; }

    public void setIdAnggota(String idAnggota) {
        if (idAnggota == null || idAnggota.trim().isEmpty()) {
            throw new IllegalArgumentException("ID anggota tidak boleh kosong.");
        }
        this.idAnggota = idAnggota.trim().toUpperCase();
    }

    public void setNama(String nama) {
        if (nama == null || nama.trim().isEmpty()) {
            throw new IllegalArgumentException("Nama anggota tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }

    public void setTelepon(String telepon) {
        if (telepon == null || telepon.trim().isEmpty()) {
            throw new IllegalArgumentException("Telepon tidak boleh kosong.");
        }
        this.telepon = telepon.trim();
    }

    /** aturan boleh pinjam selama belum maks. */
    public boolean bolehMeminjam() {
        return jumlahPinjam < MAKS_PINJAM;
    }

    /** Menambah hitungan pinjaman (ditolak jika sudah mencapai batas). */
    public void tambahPinjaman() {
        if (!bolehMeminjam()) {
            throw new IllegalStateException("Anggota " + nama + " sudah meminjam "
                    + MAKS_PINJAM + " buku (batas maksimal).");
        }
        jumlahPinjam++;
    }

    /** Mengurangi hitungan pinjaman saat pinjman dibatalkan. */
    public void kurangiPinjaman() {
        if (jumlahPinjam > 0) {
            jumlahPinjam--;
        }
    }

    public void tampilkanInfo() {
        System.out.printf("%-8s | %-20s | %-14s | pinjam: %d/%d%n",
                getIdAnggota(), getNama(), getTelepon(), getJumlahPinjam(), MAKS_PINJAM);
    }
}
