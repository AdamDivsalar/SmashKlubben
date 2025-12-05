import java.util.Date;

public class TestDataGenerator {

    public static void fyldMedTestData(Klubsystem klub) {
        // Hvis der allerede ER medlemmer, gør ikke noget
        if (!klub.getAlleMedlemmer().isEmpty()) {
            return;
        }

        System.out.println("Ingen medlemmer fundet – opretter testdata...");

        // Coaches
        Coach coachJonas = klub.findEllerOpretCoach("Jonas");
        Coach coachSara  = klub.findEllerOpretCoach("Sara");

        // Aktivt medlem 1 (junior, med coach Jonas)
        AktivtMedlem m1 = new AktivtMedlem(
                Aldersgruppe.JUNIOR,
                Disciplin.SINGLE,
                coachJonas,
                "Lukas Hansen",
                "lukas@smash.dk",
                15
        );
        klub.tilfoejMedlem(m1);
        coachJonas.tilfoejSpiller(m1);

        m1.tilfoejTraeningsData(new TraeningsData(Disciplin.SINGLE, 76.0, new Date()));
        m1.tilfoejTraeningsData(new TraeningsData(Disciplin.DOUBLE, 85.0, new Date()));

        // Aktivt medlem 2 (junior, med coach Sara)
        AktivtMedlem m2 = new AktivtMedlem(
                Aldersgruppe.JUNIOR,
                Disciplin.SINGLE,
                coachSara,
                "Emma Sørensen",
                "emma@smash.dk",
                16
        );
        klub.tilfoejMedlem(m2);
        coachSara.tilfoejSpiller(m2);

        m2.tilfoejTraeningsData(new TraeningsData(Disciplin.SINGLE, 99.0, new Date()));

        // Aktivt medlem 3 (senior, med coach Jonas)
        AktivtMedlem m3 = new AktivtMedlem(
                Aldersgruppe.SENIOR,
                Disciplin.SINGLE,
                coachJonas,
                "Noah Nielsen",
                "noah@smash.dk",
                22
        );
        klub.tilfoejMedlem(m3);
        coachJonas.tilfoejSpiller(m3);

        m3.tilfoejTraeningsData(new TraeningsData(Disciplin.SINGLE, 64.0, new Date()));
        m3.tilfoejTraeningsData(new TraeningsData(Disciplin.DOUBLE, 95.0, new Date()));
        m3.tilfoejTraeningsData(new TraeningsData(Disciplin.MIXED_DOUBLE, 87.0, new Date()));

        // Aktivt medlem 4 (junior, med coach Sara)
        AktivtMedlem m4 = new AktivtMedlem(
                Aldersgruppe.JUNIOR,
                Disciplin.MIXED_DOUBLE,
                coachSara,
                "Ida Madsen",
                "ida@smash.dk",
                14
        );
        klub.tilfoejMedlem(m4);
        coachSara.tilfoejSpiller(m4);

        m4.tilfoejTraeningsData(new TraeningsData(Disciplin.MIXED_DOUBLE, 90.0, new Date()));

        // Passivt medlem
        PassivtMedlem p1 = new PassivtMedlem(
                "Sponsor Bageren",
                "bageren@sponsor.dk",
                45
        );
        klub.tilfoejMedlem(p1);

        // Simpel turnering
        Turnering sommer = new Turnering("Sommermesterskab", "2025-06-01");
        klub.tilfoejTurnering(sommer);

        // Kamp tilføjet til en spiller (valgfrit – mest for at bruge klassen)
        Kamp kamp1 = new Kamp(
                0,                     // parameter bruges ikke i din nuværende konstruktor
                new Date(),
                "W2-L1",
                sommer
        );
        m1.tilfoejKampResultat(kamp1);

        System.out.println("Testdata oprettet: "
                + klub.getAlleMedlemmer().size() + " medlemmer.");
    }
}