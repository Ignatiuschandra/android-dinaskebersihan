package blackskystudio.com.dinaskebersihan;

public class ItemSampah {
    String id_sampah;
    String nama;
    String alamat;
    String jumlahSampah;

    public ItemSampah(String id_sampah, String nama, String alamat, String jumlahSampah) {
        this.id_sampah = id_sampah;
        this.nama = nama;
        this.alamat = alamat;
        this.jumlahSampah = jumlahSampah;
    }

    public String getId_sampah() {
        return id_sampah;
    }

    public void setId_sampah(String id_sampah) {
        this.id_sampah = id_sampah;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getJumlahSampah() {
        return jumlahSampah;
    }

    public void setJumlahSampah(String jumlahSampah) {
        this.jumlahSampah = jumlahSampah;
    }
}
