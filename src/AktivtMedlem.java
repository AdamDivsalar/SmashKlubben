// AktivtMedlem.java
class AktivtMedlem extends Medlem {
    private SpillerNiveau spillerNiveau;
    private Coach coach;
    private java.util.List<TraeningsData> traeningsData;
    private Aldersgruppe aldersgruppe;
    private java.util.List<Kamp> kampe;

    public AktivtMedlem(Aldersgruppe aldersgruppe, Disciplin disciplin, Coach coach,
                        String navn, String email, int alder) {
        super(navn, email, alder);
        this.aldersgruppe = aldersgruppe;
        this.coach = coach;
        this.traeningsData = new java.util.ArrayList<>();
        this.kampe = new java.util.ArrayList<>();
        this.spillerNiveau = coach != null ? SpillerNiveau.KONKURRENCE : SpillerNiveau.MOTIONIST;
        setMedlemstype(coach != null ? Medlemstype.AKTIV : Medlemstype.PASSIV);
    }

    public Coach getCoach() { return coach; }
    public Aldersgruppe getAldersgruppe() { return aldersgruppe; }
    public SpillerNiveau getSpillerNiveau() { return spillerNiveau; }

    public void setAldersgruppe(Aldersgruppe aldersgruppe) {
        this.aldersgruppe = aldersgruppe;
    }

    public void setCoach(Coach coach) { this.coach = coach; }

    public void tilfoejTraeningsData(TraeningsData data) {
        traeningsData.add(data);
    }

    public void tilfoejKampResultat(Kamp kamp) {
        kampe.add(kamp);
    }

    public boolean harTraeningsDataFor(Disciplin disciplin) {
        return traeningsData.stream()
                .anyMatch(td -> td.getDisciplin() == disciplin);
    }

    public double bedsteResultatFor(Disciplin disciplin) {
        return traeningsData.stream()
                .filter(td -> td.getDisciplin() == disciplin)
                .mapToDouble(TraeningsData::getBedsteResultat)
                .max()
                .orElse(0.0);
    }

    @Override
    public String toString() {
        return super.toString() + " | Type: AKTIV | Gruppe: " + aldersgruppe +
                " | Coach: " + (coach != null ? coach.getNavn() : "Ingen");
    }
}