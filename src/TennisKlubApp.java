public class TennisKlubApp {

    public static void main(String[] args) {
        Klubsystem klub = new Klubsystem();

        // Prøv at indlæse eksisterende data fra fil
        klub.indlaesFraFil();

        // Fyld kun testdata på, hvis der ikke er noget i forvejen
        TestDataGenerator.fyldMedTestData(klub);

        // Start menu / brugerflade i konsollen
        KlubMenu menu = new KlubMenu(klub);
        menu.start();
    }
}