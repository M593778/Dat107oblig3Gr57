package no.hvl.dat107;

import jakarta.persistence.*;

public class ProsjektdeltagelseDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ansattPersistenceUnit");

    public void leggTilDeltager(Ansatt ansatt, Prosjekt prosjekt, String rolle) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Prosjektdeltagelse deltagelse = new Prosjektdeltagelse(ansatt, prosjekt, rolle);
            em.persist(deltagelse);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void leggTilTimer(int ansattId, int prosjektId, int timer) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Prosjektdeltagelse deltagelse = em.createQuery(
                "SELECT p FROM Prosjektdeltagelse p WHERE p.ansatt.id = :ansattId AND p.prosjekt.id = :prosjektId",
                Prosjektdeltagelse.class)
                .setParameter("ansattId", ansattId)
                .setParameter("prosjektId", prosjektId)
                .getSingleResult();

            if (deltagelse != null) {
                deltagelse.leggTilTimer(timer);
                em.merge(deltagelse);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}

