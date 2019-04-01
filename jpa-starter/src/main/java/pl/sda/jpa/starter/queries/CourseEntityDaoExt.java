package pl.sda.jpa.starter.queries;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.sda.jpa.starter.queries.entities.CourseEntity;
import pl.sda.jpa.starter.queries.entities.EntitiesLoader;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CourseEntityDaoExt {
    private static Logger logger = LoggerFactory.getLogger(CourseEntityDaoExt.class);
    private EntityManagerFactory entityManagerFactory;

    public CourseEntityDaoExt() {
        entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.queries");
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void close() {
        entityManagerFactory.close();
    }

    public static void main(String[] args) {
        CourseEntityDaoExt dao = new CourseEntityDaoExt();
        try {
            EntitiesLoader.fillDataBase(dao.getEntityManagerFactory());

        } catch (Exception e) {
            logger.error("", e);
        } finally {
            dao.close();
        }
    }
    /**
     * Metoda wyszukuje wszystkie kursy znajdujące się w mieście podanym w argumencie 'city', posortowane malejąco po dacie rozpoczęcia.
     */
    public List<CourseEntity> findByCity(String city)
    {
        EntityManager entityManager = null;

        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<CourseEntity> typedQuery = entityManager.createQuery("SELECT c FROM CourseEntity c where c.place = :city_name ORDER BY c.startDate DESC ", CourseEntity.class)
                    .setParameter("city_name", city);
            List<CourseEntity> resultList = typedQuery.getResultList();
            entityManager.getTransaction().commit();
            return resultList;
        }finally {
            entityManager.close();
        }
    }








    /**
     * Metoda znajduje wszystkie kursy o nazwie zaczynającej się od frazy 'prefix', przycinając wyniki do ilości określonej parametrem: 'max'
     * i zaczynając od kursu z indeksem: 'from' (użyj: LIKE ‘%fraza%’)
     */
    public List<CourseEntity> findByName(String prefix, int from, int max) {
        EntityManager entityManager = null;

            try{
                entityManager = getEntityManagerFactory().createEntityManager();
                entityManager.getTransaction().begin();
                TypedQuery<CourseEntity> typedQuery = entityManager.createQuery("select c from CourseEntity c where c.name like concat(:prefix,'%')", CourseEntity.class)
                        .setParameter("prefix", prefix)
                        .setFirstResult(from)
                        .setMaxResults(max);
                List<CourseEntity> resultList = typedQuery.getResultList();
                entityManager.getTransaction().commit();
                return resultList;
            }finally {
                entityManager.close();
            }
        }


    /**
     * Metoda znajduje kursy które zaczynają się w podanym zakresie dat.
     */
    public List<CourseEntity> findByDateRange(Date from, Date to) {
        EntityManager entityManager = null;

        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<CourseEntity> typedQuery = entityManager.createQuery("select c from CourseEntity c where c.startDate Between: startDate and :endDate", CourseEntity.class)
                    .setParameter("startDate", from)
                    .setParameter("endDate", to);

            List<CourseEntity> resultList = typedQuery.getResultList();
            entityManager.getTransaction().commit();
            return resultList;
        }finally {
            entityManager.close();
        }
    }

    /**
     * Metoda znajduje kursy odbywające się w podanych miastach, posortowane po nazwie rosnąco.
     */
    public List<CourseEntity> findByCities(Set<String> cities) {

        EntityManager entityManager = null;

        try{
            entityManager = getEntityManagerFactory().createEntityManager();
            entityManager.getTransaction().begin();
            TypedQuery<CourseEntity> typedQuery = entityManager.createQuery("select c from CourseEntity c WHERE c.place IN :cities ORDER BY c.name ASC", CourseEntity.class)
                    .setParameter("cities", cities);


            List<CourseEntity> resultList = typedQuery.getResultList();
            entityManager.getTransaction().commit();
            return resultList;
        }finally {
            entityManager.close();
        }

    }



}