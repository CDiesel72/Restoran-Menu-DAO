package CDiesel72;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public abstract class EntityDAO<T> {
    private final EntityManager em;

    public EntityDAO(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEm() {
        return em;
    }

    public void add(T t) {
        try {
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("ERROR: Ошибка записи в базу данных");
            em.getTransaction().rollback();
        }
    }

    public void getSelect(Query query, String st) {
        List<T> ts = (List<T>) query.getResultList();
        toScreen("Меню:", ts);
    }

    public void toScreen(String st, List<T> ts) {
        System.out.println("======");
        System.out.println(st);
        System.out.println("------");
        ts.forEach(t -> System.out.println(t));
        System.out.println("======");
    }
}
