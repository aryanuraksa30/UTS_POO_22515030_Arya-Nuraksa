public class Buku {

    /*Atribut Private*/
    private String kode;
    private String judul;
    private String penulis;
    private int stok;

    /** Konstruktor default. */
    public Buku() {
        this("-", "Tanpa Judul", "Anonim", 0);
    }

    /** memakai setter */
    public Buku(String kode, String judul, String penulis, int stok) {
        this.setKode(kode);
        this.setJudul(judul);
        this.setPenulis(penulis);
        this.setStok(stok);
    }

    public String getKode() { return kode; }
    public String getJudul() { return judul; }
    public String getPenulis() { return penulis; }
    public int getStok() { return stok; }

    /**tidak boleh kosong */
    public void setKode(String kode) {
        if (kode == null || kode.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode buku tidak boleh kosong.");
        }
        this.kode = kode.trim().toUpperCase();
    }

    public void setJudul(String judul) {
        if (judul == null || judul.trim().isEmpty()) {
            throw new IllegalArgumentException("Judul buku tidak boleh kosong.");
        }
        this.judul = judul.trim();
    }

    public void setPenulis(String penulis) {
        if (penulis == null || penulis.trim().isEmpty()) {
            throw new IllegalArgumentException("Penulis tidak boleh kosong.");
        }
        this.penulis = penulis.trim();
    }

    /**stok tidak boleh negatif. */
    public void setStok(int stok) {
        if (stok < 0) {
            throw new IllegalArgumentException("Stok buku tidak boleh negatif.");
        }
        this.stok = stok;
    }

    /** cek apakah buku masih ada */
    public boolean isTersedia() {
        return stok > 0;
    }

    /** stok -1 saatpinjam buku (ditolak saat habis) . */
    public void kurangiStok() {
        if (!isTersedia()) {
            throw new IllegalStateException("Stok buku '" + judul + "' habis.");
        }
        stok--;
    }

    /** stok +1 saat peminjaman dibatalkan). */
    public void tambahStok() {
        stok++;
    }


    public void tampilkanInfo() {
        System.out.printf("%-8s | %-28s | %-18s | stok: %d%n",
                getKode(), getJudul(), getPenulis(), getStok());
    }
}
