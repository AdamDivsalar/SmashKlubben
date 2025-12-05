// Medlem.java
abstract class Medlem implements Comparable<Medlem> {
    private static int naesteMedlemsId = 1;
    private int medlemsId;
    private String navn;
    private String email;
    private int alder;
    private Medlemstype medlemstype;

    public Medlem(String navn, String email, int alder) {
        this.medlemsId = naesteMedlemsId++;
        this.navn = navn;
        this.email = email;
        this.alder = alder;
    }

    public int getMedlemsId() { return medlemsId; }
    public String getNavn() { return navn; }
    public String getEmail() { return email; }
    public int getAlder() { return alder; }
    public Medlemstype getMedlemstype() { return medlemstype; }

    public void setAlder(int alder) { this.alder = alder; }
    public void setNavn(String navn) { this.navn = navn; }
    public void setEmail(String email) { this.email = email; }
    public void setMedlemstype(Medlemstype type) { this.medlemstype = type; }

    public static void setNaesteMedlemsId(int id) {
        naesteMedlemsId = id;
    }

    @Override
    public int compareTo(Medlem andet) {
        return this.navn.compareTo(andet.navn);
    }

    @Override
    public String toString() {
        return "ID: " + medlemsId + " | Navn: " + navn + " | Email: " + email +
                " | Alder: " + alder;
    }
}