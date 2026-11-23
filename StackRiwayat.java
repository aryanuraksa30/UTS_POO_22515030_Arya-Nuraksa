import java.util.ArrayList;

public class StackRiwayat {
    private final ArrayList<Peminjaman> data;
    private final int kapasitas;

    public StackRiwayat(int kapasitas) {
        if (kapasitas <= 0) {
            throw new IllegalArgumentException("Kapasitas stack harus lebih dari 0.");
        }
        this.kapasitas = kapasitas;
        this.data = new ArrayList<>();
    }

    /** taruh di puncak. Mengembalikan false jika stack penuh. */
    public boolean push(Peminjaman p) {
        if (isFull()) {
            return false; // kondisi batas: penuh
        }
        data.add(p);
        return true;
    }

    /** ambil & hapus dari puncak. Mengembalikan null jika kosong. */
    public Peminjaman pop() {
        if (isEmpty()) {
            return null; // kondisi batas: kosong
        }
        return data.remove(data.size() - 1);
    }

    /** Melihat puncak tanpa menghapus; null bila kosong. */
    public Peminjaman peek() {
        return isEmpty() ? null : data.get(data.size() - 1);
    }

    public boolean isEmpty() { return data.isEmpty(); }
    public boolean isFull() { return data.size() >= kapasitas; }
    public int size() { return data.size(); }
    public int getKapasitas() { return kapasitas; }

    /** Menampilkan isi stack dari puncak ke dasar. */
    public void tampilkan() {
        if (isEmpty()) {
            System.out.println("(Stack riwayat kosong)");
            return;
        }
        for (int i = data.size() - 1; i >= 0; i--) {
            System.out.print((i == data.size() - 1 ? "TOP -> " : "       "));
            data.get(i).tampilkanInfo();
        }
    }
}
