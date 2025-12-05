import java.util.Scanner;
import java.util.List;

public class KlubMenu {

    private final Klubsystem klub;
    private final Scanner scanner;

    public KlubMenu(Klubsystem klub) {
        this.klub = klub;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean koerer = true;

        while (koerer) {
            visMenu();
            System.out.print("Vælg punkt: ");
            String valg = scanner.nextLine().trim();

            switch (valg) {
                case "1":
                    opretPassivtMedlem();
                    break;
                case "2":
                    opretAktivtMedlem();
                    break;
                case "3":
                    visAlleMedlemmer();
                    break;
                case "4":
                    registrerBetaling();
                    break;
                case "5":
                    visRestance();
                    break;
                case "6":
                    visSamletIndtaegt();
                    break;
                case "7":
                    gemData();
                    break;
                case "0":
                    afslut();
                    koerer = false;
                    break;
                default:
                    System.out.println("Ugyldigt valg – prøv igen.");
            }

            System.out.println();
        }
    }

    private void visMenu() {
        System.out.println("====================================");
        System.out.println("   Tennisklubben Smash - Menu");
        System.out.println("====================================");
        System.out.println("1) Opret passivt medlem");
        System.out.println("2) Opret aktivt medlem");
        System.out.println("3) Vis alle medlemmer");
        System.out.println("4) Registrer betaling for medlem");
        System.out.println("5) Vis medlemmer i restance");
        System.out.println("6) Vis samlet forventet kontingent-indtægt");
        System.out.println("7) Gem data nu");
        System.out.println("0) Afslut");
        System.out.println("====================================");
    }

    private void opretPassivtMedlem() {
        System.out.println("== Opret passivt medlem ==");
        System.out.print("Navn: ");
        String navn = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Alder: ");
        int alder = laesInt();

        Medlem medlem = new PassivtMedlem(navn, email, alder);
        klub.tilfoejMedlem(medlem);
        System.out.println("Passivt medlem oprettet med ID: " + medlem.getMedlemsId());
    }

    private void opretAktivtMedlem() {
        System.out.println("== Opret aktivt medlem ==");
        System.out.print("Navn: ");
        String navn = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Alder: ");
        int alder = laesInt();

        Aldersgruppe gruppe = (alder < 18) ? Aldersgruppe.JUNIOR : Aldersgruppe.SENIOR;

        System.out.print("Navn på coach (tom for ingen): ");
        String coachNavn = scanner.nextLine().trim();
        Coach coach = null;
        if (!coachNavn.isEmpty()) {
            coach = klub.findEllerOpretCoach(coachNavn);
        }

        // Standard-disciplin (kan udvides senere, hvis du vil vælge)
        Disciplin disciplin = Disciplin.SINGLE;

        AktivtMedlem aktiv = new AktivtMedlem(gruppe, disciplin, coach, navn, email, alder);
        klub.tilfoejMedlem(aktiv);

        if (coach != null) {
            coach.tilfoejSpiller(aktiv);
        }

        System.out.println("Aktivt medlem oprettet med ID: " + aktiv.getMedlemsId()
                + " (" + gruppe + ")");
    }

    private void visAlleMedlemmer() {
        System.out.println("== Alle medlemmer ==");
        List<Medlem> medlemmer = klub.getAlleMedlemmer();
        if (medlemmer.isEmpty()) {
            System.out.println("Ingen medlemmer registreret.");
            return;
        }

        for (Medlem m : medlemmer) {
            System.out.println(m);
        }
    }

    private void registrerBetaling() {
        System.out.println("== Registrer betaling ==");
        System.out.print("Indtast medlems-ID: ");
        int id = laesInt();

        try {
            klub.registrerBetaling(id);
            System.out.println("Betaling registreret for medlem ID: " + id);
        } catch (MedlemskabsFundException e) {
            System.out.println("Fejl: " + e.getMessage());
        }
    }

    private void visRestance() {
        System.out.println("== Medlemmer i restance ==");
        List<KontingentOversigt> restancer = klub.hentRestance();

        if (restancer.isEmpty()) {
            System.out.println("Ingen medlemmer i restance.");
            return;
        }

        for (KontingentOversigt ko : restancer) {
            System.out.println(ko);
        }
    }

    private void visSamletIndtaegt() {
        double sum = klub.beregnSamletForventetIndtaegt();
        System.out.println("Samlet forventet kontingent-indtægt: " + sum + " kr");
    }

    private void gemData() {
        System.out.println("Gemmer data...");
        klub.gemTilFil();
        System.out.println("Data gemt.");
    }

    private void afslut() {
        System.out.println("Gemmer data og afslutter...");
        klub.gemTilFil();
        scanner.close();
        System.out.println("Farvel.");
    }

    private int laesInt() {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Skriv et helt tal: ");
            }
        }
    }
}