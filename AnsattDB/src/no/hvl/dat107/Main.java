package no.hvl.dat107;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static AnsattDAO ansattDAO = new AnsattDAO();
    private static AvdelingDAO avdelingDAO = new AvdelingDAO();
    private static ProsjektDAO prosjektDAO = new ProsjektDAO();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n MENY:");
            System.out.println("1. Søk etter ansatt (ID)");
            System.out.println("2. Søk etter ansatt (brukernavn)");
            System.out.println("3. List ut alle ansatte");
            System.out.println("4. Oppdater stilling/lønn");
            System.out.println("5. Legg til ny ansatt");
            System.out.println("6. Fjern en ansatt");
            System.out.println("7. Søk etter avdeling (ID)");
            System.out.println("8. List ut ansatte i avdeling");
            System.out.println("9. Endre ansatts avdeling");
            System.out.println("10. Legg til ny avdeling");
            System.out.println("11. Legg til nytt prosjekt");
            System.out.println("12. Registrere prosjektdeltagelse");
            System.out.println("13. Føre timer på prosjekt");
            System.out.println("14. Vis prosjektinfo");
            System.out.println("0. Avslutt");
            System.out.print("Velg et alternativ: ");
            
            int valg = scanner.nextInt();
            scanner.nextLine(); 

            switch (valg) {
                case 1 -> søkAnsattMedId();
                case 2 -> søkAnsattMedBrukernavn();
                case 3 -> listAlleAnsatte();
                case 4 -> oppdaterAnsatt();
                case 5 -> leggTilAnsatt();
                case 6 -> FjernAnsatt();
                case 7 -> søkAvdeling();
                case 8 -> listAnsatteIAvdeling();
                case 9 -> endreAnsattAvdeling();
                case 10 -> leggTilAvdeling();
                case 11 -> leggTilProsjekt();
                case 12 -> registrerProsjektDeltagelse();
                case 13 -> føreTimerPåProsjekt();
                case 14 -> visProsjektInfo();
                case 0 -> {
                    System.out.println("Avslutter programmet...");
                    System.exit(0);
                }
                default -> System.out.println("Ugyldig valg! Prøv igjen.");
            }
        }
    }

    private static void søkAnsattMedId() {
        System.out.print("Skriv inn ansatt-ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Ansatt ansatt = ansattDAO.finnAnsattMedId(id);
        System.out.println(ansatt != null ? ansatt : "Ingen ansatt funnet med ID " + id);
    }

    private static void søkAnsattMedBrukernavn() {
        System.out.print("Skriv inn brukernavn: ");
        String brukernavn = scanner.nextLine();
        Ansatt ansatt = ansattDAO.finnAnsattMedBrukernavn(brukernavn);
        System.out.println(ansatt != null ? ansatt : "Ingen ansatt funnet med brukernavn " + brukernavn);
    }

    private static void listAlleAnsatte() {
        List<Ansatt> ansatte = ansattDAO.hentAlleAnsatte();
        ansatte.forEach(System.out::println);
    }

    private static void oppdaterAnsatt() {
        System.out.print("Skriv inn ansatt-ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ny stilling: ");
        String stilling = scanner.nextLine();
        System.out.print("Ny lønn: ");
        int lønn = scanner.nextInt();
        scanner.nextLine();
        ansattDAO.oppdaterAnsatt(id, stilling, lønn);
        System.out.println("Oppdatert!");
    }

    private static void leggTilAnsatt() {
        System.out.print("Brukernavn: ");
        String brukernavn = scanner.nextLine();
        System.out.print("Fornavn: ");
        String fornavn = scanner.nextLine();
        System.out.print("Etternavn: ");
        String etternavn = scanner.nextLine();
        System.out.print("Ansettelsesdato (YYYY-MM-DD): ");
        String dato = scanner.nextLine();
        System.out.print("Stilling: ");
        String stilling = scanner.nextLine();
        System.out.print("Månedslønn: ");
        int lønn = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Avdeling-ID: ");
        int avdelingId = scanner.nextInt();
        scanner.nextLine();
        ansattDAO.leggTilAnsatt(brukernavn, fornavn, etternavn, dato, stilling, lønn, avdelingId);
        System.out.println("Ny ansatt lagt til!");
    }
    
    private static void FjernAnsatt() {
    System.out.print("Skriv inn ID for ansatt som skal fjernes: ");
    int ansattId = scanner.nextInt();
    ansattDAO.fjernAnsatt(ansattId);
}

    private static void søkAvdeling() {
        System.out.print("Skriv inn avdeling-ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Avdeling avdeling = avdelingDAO.finnAvdelingMedId(id);
        System.out.println(avdeling != null ? avdeling : "Ingen avdeling funnet med ID " + id);
    }

    private static void listAnsatteIAvdeling() {
        System.out.print("Skriv inn avdeling-ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        avdelingDAO.listAnsatteIAvdeling(id);
    }

    private static void endreAnsattAvdeling() {
        System.out.print("Ansatt-ID: ");
        int ansattId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Ny avdeling-ID: ");
        int avdelingId = scanner.nextInt();
        scanner.nextLine();
        ansattDAO.endreAvdeling(ansattId, avdelingId);
    }

    private static void leggTilAvdeling() {
        System.out.print("Navn på ny avdeling: ");
        String navn = scanner.nextLine();
        System.out.print("ID til sjefen for avdelingen: ");
        int sjefId = scanner.nextInt();
        scanner.nextLine();
        avdelingDAO.leggTilAvdeling(navn, sjefId);
    }

    private static void leggTilProsjekt() {
        System.out.print("Prosjektnavn: ");
        String navn = scanner.nextLine();
        System.out.print("Beskrivelse: ");
        String beskrivelse = scanner.nextLine();
        prosjektDAO.leggTilProsjekt(navn, beskrivelse);
    }

    private static void registrerProsjektDeltagelse() {
        System.out.print("Ansatt-ID: ");
        int ansattId = scanner.nextInt();
        System.out.print("Prosjekt-ID: ");
        int prosjektId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Rolle: ");
        String rolle = scanner.nextLine();
        prosjektDAO.leggTilDeltagelse(ansattId, prosjektId, rolle);
    }

    private static void føreTimerPåProsjekt() {
        System.out.print("Ansatt-ID: ");
        int ansattId = scanner.nextInt();
        System.out.print("Prosjekt-ID: ");
        int prosjektId = scanner.nextInt();
        System.out.print("Antall timer: ");
        int timer = scanner.nextInt();
        prosjektDAO.føreTimer(ansattId, prosjektId, timer);
    }

    private static void visProsjektInfo() {
        System.out.print("Prosjekt-ID: ");
        int prosjektId = scanner.nextInt();
        scanner.nextLine();
        prosjektDAO.visProsjektInfo(prosjektId);
    }
}

