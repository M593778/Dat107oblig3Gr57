package no.hvl.dat107;

import jakarta.persistence.*;
import java.util.List;

public class ProsjektDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit");

    public Prosjekt finnProsjektMedId(int id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(Prosjekt.class, id);
        } finally {
            em.close();
        }
    }

    public void leggTilProsjekt(String navn, String beskrivelse) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Prosjekt prosjekt = new Prosjekt();
            em.persist(prosjekt);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public List<Prosjekt> hentAlleProsjekter() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM Prosjekt p", Prosjekt.class).getResultList();
        } finally {
            em.close();
        }
    }


    // Legger til en ansatt i et prosjekt med en rolle
    public void leggTilDeltagelse(int ansattId, int prosjektId, String rolle) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Ansatt ansatt = em.find(Ansatt.class, ansattId);
            Prosjekt prosjekt = em.find(Prosjekt.class, prosjektId);

            if (ansatt == null || prosjekt == null) {
                System.out.println("Ugyldig ansatt eller prosjekt.");
                return;
            }

            Prosjektdeltagelse deltagelse = new Prosjektdeltagelse();
            em.persist(deltagelse);

            tx.commit();
            System.out.println(ansatt.getFornavn() + " er lagt til i prosjektet " + prosjekt.getNavn() + " som " + rolle);
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Fører timer for en ansatt på et prosjekt
    public void føreTimer(int ansattId, int prosjektId, int timer) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Prosjektdeltagelse deltagelse = em.createQuery(
                    "SELECT p FROM Prosjektdeltagelse p WHERE p.ansatt.id = :ansattId AND p.prosjekt.id = :prosjektId",
                    Prosjektdeltagelse.class)
                .setParameter("ansattId", ansattId)
                .setParameter("prosjektId", prosjektId)
                .getSingleResult();

            if (deltagelse != null) {
                deltagelse.setTimer(deltagelse.getTimer() + timer);
                em.merge(deltagelse);
                System.out.println(timer + " timer ført for " + deltagelse.getAnsatt().getFornavn() + " på prosjekt " + deltagelse.getProsjekt().getNavn());
            } else {
                System.out.println("Deltagelsen ble ikke funnet.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
 // Viser prosjektinfo og deltagere
    public void visProsjektInfo(int prosjektId) {
        EntityManager em = emf.createEntityManager();

        try {
            Prosjekt prosjekt = em.find(Prosjekt.class, prosjektId);

            if (prosjekt == null) {
                System.out.println("Prosjektet ble ikke funnet.");
                return;
            }

            System.out.println("\nProsjekt: " + prosjekt.getNavn());
            System.out.println("Beskrivelse: " + prosjekt.getBeskrivelse());
            System.out.println("Deltagere:");

            List<Prosjektdeltagelse> deltagere = em.createQuery(
                    "SELECT p FROM Prosjektdeltagelse p WHERE p.prosjekt.id = :prosjektId",
                    Prosjektdeltagelse.class)
                .setParameter("prosjektId", prosjektId)
                .getResultList();

            for (Prosjektdeltagelse p : deltagere) {
                System.out.println("- " + p.getAnsatt().getFornavn() + " " + p.getAnsatt().getEtternavn() +
                        " (" + p.getRolle() + ") - " + p.getTimer() + " timer");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
