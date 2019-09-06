package ranglista;

public class Igrac {

    String ime;
    int brojPoena;

    public Igrac() {
    }

    public Igrac(String i, int b) {
        ime = i;
        brojPoena = b;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getBrojPoena() {
        return brojPoena;
    }

    public void setBrojPoena(int brojPoena) {
        this.brojPoena = brojPoena;
    }

}
