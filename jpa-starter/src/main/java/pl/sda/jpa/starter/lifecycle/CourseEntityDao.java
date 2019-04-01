package pl.sda.jpa.starter.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

public class CourseEntityDao {
    private static Logger logger = LoggerFactory.getLogger(CourseEntityDao.class);
    private  EntityManagerFactory entityManagerFactory;

    public CourseEntityDao(EntityManagerFactory entityManagerFactory){
        this.entityManagerFactory = entityManagerFactory;

    }

    public List<CourseEntity> changeShedule(Date startDate, Date endDate){





        return null;

    }


    public void save(CourseEntity courseEntity) {
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(courseEntity);
            logger.info("Udało się dodać kurs: {}", courseEntity);
            entityManager.getTransaction().commit();

        }
        finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    public void update(CourseEntity courseEntity) {

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            entityManager.merge(courseEntity);
            logger.info("Udało się zaktualizować !! kurs (merge): {}", courseEntity);
            entityManager.getTransaction().commit();

        }
        finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }


    }

    public void delete(CourseEntity courseEntity) {

        EntityManager entityManager = null;
        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            courseEntity = entityManager.merge(courseEntity);
            entityManager.remove(courseEntity);
            logger.info("Udało się usunąć kurs: {}", courseEntity);
            entityManager.getTransaction().commit();

        }
        finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }
    }

    public CourseEntity findById(int id) {


        EntityManager entityManager = null;
        CourseEntity result;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();
            result = entityManager.find(CourseEntity.class, id);
           // logger.info("Udało się usunąć kurs: {}", courseEntity);
            entityManager.getTransaction().commit();

            return result;
        }
        finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }

    }

    public List<CourseEntity> list() {


        EntityManager entityManager = null;
        List<CourseEntity> result;

        try {
            entityManager = entityManagerFactory.createEntityManager();
            entityManager.getTransaction().begin();

            logger.info("Szukam kursow");
            TypedQuery<CourseEntity> query = entityManager.createQuery("from CourseEntity", CourseEntity.class);
            result = query.getResultList();

            // logger.info("Udało się usunąć kurs: {}", courseEntity);


            entityManager.getTransaction().commit();

            return result;
        }
        finally {
            if(entityManager != null) {
                entityManager.close();
            }
        }




    }

}