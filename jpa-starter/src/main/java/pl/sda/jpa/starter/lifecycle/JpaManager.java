package pl.sda.jpa.starter.lifecycle;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class JpaManager {

    public static void main(String[] args) {

        EntityManagerFactory nyManager = Persistence.createEntityManagerFactory("pl.sda.jpa.starter.lifecycle");
        CourseEntityDao courseEntityDao = new CourseEntityDao(nyManager);
        courseEntityDao.list();
        String date = "2000-11-01";
        java.sql.Date javaSqlDate = java.sql.Date.valueOf(date);

        courseEntityDao.save(new CourseEntity("ksmx", "axsx", Date.valueOf(LocalDate.now()),Date.valueOf(LocalDate.of(2015,11,11))));


    }
}
