// Turnering.java
class Turnering {
    private int turneringId;
    private String navn;
    private String startDato;
    private java.util.List<AktivtMedlem> deltagere;
    private static int naesteTurneringId = 1;

    public Turnering(String navn, String startDato) {
        this.turneringId = naesteTurneringId++;
        this.navn = navn;
        this.startDato = startDato;
        this.deltagere = new java.util.ArrayList<>();
    }

    public int getTurneringId() { return turneringId; }
    public String getNavn() { return navn; }
    public String getStartDato() { return startDato; }
    public java.util.List<AktivtMedlem> getDeltagere() { return deltagere; }

    public static void setNaesteTurneringId(int id) {
        naesteTurneringId = id;
    }

    public void tilfoejDeltager(AktivtMedlem deltager) {
        if (!deltagere.contains(deltager)) {
            deltagere.add(deltager);
        }
    }

    @Override
    public String toString() {
        return "Turnering ID: " + turneringId + " | Navn: " + navn +
                " | Start: " + startDato + " | Deltagere: " + deltagere.size();
    }
}