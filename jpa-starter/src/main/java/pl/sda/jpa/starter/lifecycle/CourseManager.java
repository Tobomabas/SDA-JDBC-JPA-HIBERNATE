package pl.sda.jpa.starter.lifecycle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;

public class CourseManager {
    private static Logger logger = LoggerFactory.getLogger(CourseManager.class);
    public static void main(String[] args) {

        EntityManagerFactory entityManagerFactory = null;
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.lifecycle");
            CourseEntityDao dao = new CourseEntityDao(entityManagerFactory);
            dao.save(new CourseEntity("Javawwa14", "Warszawa", Date.valueOf("2019-01-01"), Date.valueOf("2019-05-01")));
            dao.save(new CourseEntity("Wro222", "Wrocław", Date.valueOf("2019-01-01"), Date.valueOf("2019-05-01")));
            dao.save(new CourseEntity("Poz322", "Poznań", Date.valueOf("2019-01-01"), Date.valueOf("2019-05-01")));
            dao.save(new CourseEntity("Javawwa15", "Warszawa", Date.valueOf("2019-01-01"), Date.valueOf("2019-05-01")));
            dao.save(new CourseEntity("Javawwa16", "Warszawa", Date.valueOf("2019-01-01"), Date.valueOf("2019-05-01")));
            dao.save(new CourseEntity("Javawwa17", "Warszawa", Date.valueOf("2019-01-01"), Date.valueOf("2019-05-01")));

            CourseEntity courseEntity = dao.findById(2);
            logger.info("Course entity before:{}", courseEntity);
            courseEntity.setName("1123456");
            logger.info("Course entity after:{}", courseEntity);
            dao.update(courseEntity);

            CourseEntity courseEntiyToDelete = dao.findById(3);
            dao.delete(courseEntiyToDelete);
        }
        finally {
            if(entityManagerFactory !=null){
            entityManagerFactory.close();
            }
        }
    }


}
