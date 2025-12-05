// Coach.java
class Coach {
    private int coachId;
    private String navn;
    private String email;
    private java.util.List<AktivtMedlem> spillere;
    private static int naesteCoachId = 1;

    public Coach(String navn, String email) {
        this.coachId = naesteCoachId++;
        this.navn = navn;
        this.email = email;
        this.spillere = new java.util.ArrayList<>();
    }

    public int getCoachId() { return coachId; }
    public String getNavn() { return navn; }
    public String getEmail() { return email; }
    public java.util.List<AktivtMedlem> getSpillere() { return spillere; }

    public void tilfoejSpiller(AktivtMedlem spiller) {
        if (!spillere.contains(spiller)) {
            spillere.add(spiller);
        }
    }

    public java.util.List<AktivtMedlem> hentTop5Spillere(Disciplin disciplin, Aldersgruppe gruppe) {
        return spillere.stream()
                .filter(s -> s.getAldersgruppe() == gruppe)
                .filter(s -> s.harTraeningsDataFor(disciplin))
                .sorted((a, b) -> Double.compare(
                        b.bedsteResultatFor(disciplin),
                        a.bedsteResultatFor(disciplin)
                ))
                .limit(5)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public String toString() {
        return "Coach: " + navn + " | Email: " + email + " | Spillere: " + spillere.size();
    }
}