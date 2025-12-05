// Medlemsliste.java
class Medlemsliste {
    private String klubNavn;
    private java.util.List<Medlem> medlemmer;

    public Medlemsliste(String klubNavn) {
        this.klubNavn = klubNavn;
        this.medlemmer = new java.util.ArrayList<>();
    }

    public void tilfoejMedlem(Medlem medlem) {
        medlemmer.add(medlem);
    }

    public void fjernMedlem(Medlem medlem) {
        medlemmer.remove(medlem);
    }

    public java.util.List<Medlem> getMedlemmer() {
        return new java.util.ArrayList<>(medlemmer);
    }

    public int antalMedlemmer() {
        return medlemmer.size();
    }
}
