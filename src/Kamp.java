// Kamp.java
class Kamp {
    private int kampId;
    private java.util.Date dato;
    private String kampResultat;
    private Turnering turnering;
    private static int naesteKampId = 1;

    public Kamp(int kampId, java.util.Date dato, String kampResultat, Turnering turnering) {
        this.kampId = naesteKampId++;
        this.dato = dato;
        this.kampResultat = kampResultat;
        this.turnering = turnering;
    }

    public int getKampId() { return kampId; }
    public java.util.Date getDato() { return dato; }
    public String getKampResultat() { return kampResultat; }
    public Turnering getTurnering() { return turnering; }

    @Override
    public String toString() {
        return "Kamp ID: " + kampId + " | Dato: " + dato + " | Resultat: " + kampResultat +
                " | Turnering: " + (turnering != null ? turnering.getNavn() : "N/A");
    }
}