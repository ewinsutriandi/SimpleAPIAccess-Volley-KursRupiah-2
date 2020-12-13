package simple.example.kursrupiah;

public class NilaiKurs {
    private String mataUang;
    private double nilaiKurs;

    public NilaiKurs(String mataUang, double nilaiKurs) {
        this.mataUang = mataUang;
        this.nilaiKurs = nilaiKurs;
    }

    public String getMataUang() {
        return mataUang;
    }

    public void setMataUang(String mataUang) {
        this.mataUang = mataUang;
    }

    public double getNilaiKurs() {
        return nilaiKurs;
    }

    public void setNilaiKurs(double nilaiKurs) {
        this.nilaiKurs = nilaiKurs;
    }
}
