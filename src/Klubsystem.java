// Klubsystem.java
class Klubsystem implements Persistable {
    private java.util.List<Medlem> medlemsliste;
    private java.util.List<Medlemsliste> medlemmerlister;
    private java.util.List<KontingentOversigt> kontingentOversigter;
    private java.util.List<Coach> coaches;
    private java.util.List<Turnering> turneringer;
    private static final String DATAFIL = "tennisklub_data.txt";

    public Klubsystem() {
        this.medlemsliste = new java.util.ArrayList<>();
        this.medlemmerlister = new java.util.ArrayList<>();
        this.kontingentOversigter = new java.util.ArrayList<>();
        this.coaches = new java.util.ArrayList<>();
        this.turneringer = new java.util.ArrayList<>();
    }

    public void tilfoejMedlem(Medlem medlem) {
        medlemsliste.add(medlem);

        double kontingent = beregnKontingent(medlem);
        KontingentOversigt ko = new KontingentOversigt(
                medlem.getMedlemsId(),
                kontingent,
                0.0,
                BetalingsStatus.RESTANCE
        );
        kontingentOversigter.add(ko);
    }

    public void fjernMedlem(int medlemsId) throws MedlemskabsFundException {
        Medlem medlem = findMedlem(medlemsId);
        medlemsliste.remove(medlem);
        kontingentOversigter.removeIf(ko -> ko.getMedlemsId() == medlemsId);
    }

    public Medlem findMedlem(int medlemsId) throws MedlemskabsFundException {
        return medlemsliste.stream()
                .filter(m -> m.getMedlemsId() == medlemsId)
                .findFirst()
                .orElseThrow(() -> new MedlemskabsFundException("Medlem med ID " + medlemsId + " ikke fundet"));
    }

    public java.util.List<Medlem> getAlleMedlemmer() {
        return new java.util.ArrayList<>(medlemsliste);
    }

    public double beregnKontingent(Medlem medlem) {
        if (medlem instanceof PassivtMedlem) {
            return 250.0;
        } else if (medlem instanceof AktivtMedlem) {
            AktivtMedlem aktivt = (AktivtMedlem) medlem;
            int alder = aktivt.getAlder();

            if (alder < 18) {
                return 800.0;
            } else if (alder >= 60) {
                return 1500.0 * 0.75;
            } else {
                return 1500.0;
            }
        }
        return 0.0;
    }

    public double beregnSamletForventetIndtaegt() {
        return medlemsliste.stream()
                .mapToDouble(this::beregnKontingent)
                .sum();
    }

    public java.util.List<KontingentOversigt> hentRestance() {
        return kontingentOversigter.stream()
                .filter(ko -> ko.getBetalingsStatus() == BetalingsStatus.RESTANCE)
                .collect(java.util.stream.Collectors.toList());
    }

    public void registrerBetaling(int medlemsId) throws MedlemskabsFundException {
        Medlem medlem = findMedlem(medlemsId);
        double kontingent = beregnKontingent(medlem);

        KontingentOversigt ko = kontingentOversigter.stream()
                .filter(k -> k.getMedlemsId() == medlemsId)
                .findFirst()
                .orElse(null);

        if (ko != null) {
            ko.setBetalt(kontingent);
            ko.setBetalingsStatus(BetalingsStatus.BETALT);
        }
    }

    public Coach findEllerOpretCoach(String navn) {
        return coaches.stream()
                .filter(c -> c.getNavn().equalsIgnoreCase(navn))
                .findFirst()
                .orElseGet(() -> {
                    Coach nyCoach = new Coach(navn, "coach" + (coaches.size() + 1) + "@smash.dk");
                    coaches.add(nyCoach);
                    return nyCoach;
                });
    }

    public java.util.List<AktivtMedlem> hentTop5Spillere(Disciplin disciplin, Aldersgruppe gruppe) {
        return medlemsliste.stream()
                .filter(m -> m instanceof AktivtMedlem)
                .map(m -> (AktivtMedlem) m)
                .filter(am -> am.getAldersgruppe() == gruppe)
                .filter(am -> am.harTraeningsDataFor(disciplin))
                .sorted((a, b) -> Double.compare(
                        b.bedsteResultatFor(disciplin),
                        a.bedsteResultatFor(disciplin)
                ))
                .limit(5)
                .collect(java.util.stream.Collectors.toList());
    }

    public void tilfoejTurnering(Turnering turnering) {
        turneringer.add(turnering);
    }

    public Turnering findTurnering(int turneringId) throws TurneringException {
        return turneringer.stream()
                .filter(t -> t.getTurneringId() == turneringId)
                .findFirst()
                .orElseThrow(() -> new TurneringException("Turnering med ID " + turneringId + " ikke fundet"));
    }

    @Override
    public void gemTilFil() {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(DATAFIL))) {
            writer.println("MEDLEMMER:" + medlemsliste.size());
            for (Medlem m : medlemsliste) {
                if (m instanceof PassivtMedlem) {
                    writer.println("PASSIV|" + m.getMedlemsId() + "|" + m.getNavn() +
                            "|" + m.getEmail() + "|" + m.getAlder());
                } else if (m instanceof AktivtMedlem) {
                    AktivtMedlem am = (AktivtMedlem) m;
                    writer.println("AKTIV|" + am.getMedlemsId() + "|" + am.getNavn() +
                            "|" + am.getEmail() + "|" + am.getAlder() + "|" +
                            am.getAldersgruppe() + "|" +
                            (am.getCoach() != null ? am.getCoach().getNavn() : "INGEN"));
                }
            }

            writer.println("COACHES:" + coaches.size());
            for (Coach c : coaches) {
                writer.println(c.getNavn() + "|" + c.getEmail());
            }

            writer.println("TURNERINGER:" + turneringer.size());
            for (Turnering t : turneringer) {
                writer.println(t.getTurneringId() + "|" + t.getNavn() + "|" + t.getStartDato());
            }

            System.out.println("Data gemt til " + DATAFIL);
        } catch (java.io.IOException e) {
            System.out.println("Fejl ved gemning: " + e.getMessage());
        }
    }

    @Override
    public void indlaesFraFil() {
        java.io.File file = new java.io.File(DATAFIL);
        if (!file.exists()) {
            System.out.println("Ingen tidligere data fundet. Starter med tom database.");
            return;
        }

        try (java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.FileReader(DATAFIL))) {
            String line;

            line = reader.readLine();
            if (line != null && line.startsWith("MEDLEMMER:")) {
                int antal = Integer.parseInt(line.split(":")[1]);
                for (int i = 0; i < antal; i++) {
                    line = reader.readLine();
                    if (line == null) break;
                    String[] dele = line.split("\\|");

                    if (dele[0].equals("PASSIV")) {
                        PassivtMedlem pm = new PassivtMedlem(dele[2], dele[3], Integer.parseInt(dele[4]));
                        Medlem.setNaesteMedlemsId(Integer.parseInt(dele[1]));
                        medlemsliste.add(pm);
                        Medlem.setNaesteMedlemsId(Integer.parseInt(dele[1]) + 1);
                    } else if (dele[0].equals("AKTIV")) {
                        Aldersgruppe gruppe = Aldersgruppe.valueOf(dele[5]);
                        Coach coach = dele[6].equals("INGEN") ? null : findEllerOpretCoach(dele[6]);
                        AktivtMedlem am = new AktivtMedlem(gruppe, Disciplin.SINGLE, coach,
                                dele[2], dele[3], Integer.parseInt(dele[4]));
                        Medlem.setNaesteMedlemsId(Integer.parseInt(dele[1]));
                        medlemsliste.add(am);
                        Medlem.setNaesteMedlemsId(Integer.parseInt(dele[1]) + 1);
                    }
                }
            }

            line = reader.readLine();
            if (line != null && line.startsWith("COACHES:")) {
                int antal = Integer.parseInt(line.split(":")[1]);
                for (int i = 0; i < antal; i++) {
                    line = reader.readLine();
                    if (line == null) break;
                    String[] dele = line.split("\\|");
                    if (coaches.stream().noneMatch(c -> c.getNavn().equals(dele[0]))) {
                        coaches.add(new Coach(dele[0], dele[1]));
                    }
                }
            }

            line = reader.readLine();
            if (line != null && line.startsWith("TURNERINGER:")) {
                int antal = Integer.parseInt(line.split(":")[1]);
                for (int i = 0; i < antal; i++) {
                    line = reader.readLine();
                    if (line == null) break;
                    String[] dele = line.split("\\|");
                    Turnering t = new Turnering(dele[1], dele[2]);
                    Turnering.setNaesteTurneringId(Integer.parseInt(dele[0]));
                    turneringer.add(t);
                    Turnering.setNaesteTurneringId(Integer.parseInt(dele[0]) + 1);
                }
            }

            System.out.println("Data indlæst fra " + DATAFIL);
        } catch (java.io.IOException e) {
            System.out.println("Fejl ved indlæsning: " + e.getMessage());
        }
    }
}