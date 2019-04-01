package pl.sda.jdbc.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDate;


public class CoursesManager {
    private static Logger logger = LoggerFactory.getLogger(CoursesManager.class);
    private ConnectionFactory connectionFactory;

    public CoursesManager() {
        try {
            connectionFactory = new ConnectionFactory("/database_courses.properties");

        } catch (SQLException e) {
            logger.error("cannot crate factory");
        }


    }





    public void createCoursesTable() throws SQLException {

        try (Connection connection = connectionFactory.getConnection()) {
            logger.info("poleczenie sukces!");
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "create table if not EXISTS courses(\n" +
                            "id int primary key AUTO_INCREMENT,\n" +
                            "name VARCHAR(50),\n" +
                            "place VARCHAR(50),\n" +
                            "start_date DATE,\n" +
                            "end_date DATE\n" +
                            ");"
            );
            logger.info("Wykonano update! !");
        }

    }

    public void createCourse() throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO " +
                    "courses(name, place, start_date, end_date)" +
                    "values('JavaWWa14', 'Warszawa', '2018-10-10', '2019-05-05')");
        }


    }

    public void createCourse(String name, String place, Date startDate, Date endDate) throws SQLException {

        try (Connection connection = connectionFactory.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "courses(name, place, start_date, end_date)" +
                    "values(?, ?, ?, ?)");
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,place);
            preparedStatement.setDate(3,startDate);
            preparedStatement.setDate(4,endDate);
            preparedStatement.executeUpdate();
        }


    }


    public void createStudent(String name, int courseID, String description, String seat) throws SQLException {

        try (Connection connection = connectionFactory.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "students(name, course_id, seat, description)" +
                    "values(?, ?, ?, ?)");
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,courseID);
            preparedStatement.setString(3,description);
            preparedStatement.setString(4,seat);
            preparedStatement.executeUpdate();
        }
    }
    public void createAttendance(int course_id, int student_id, Date date) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " +
                    "attendance_list(course_id, student_id, date)" +
                    "values(?, ?, ?)");
            preparedStatement.setInt(1,course_id);
            preparedStatement.setInt(2,student_id);
            preparedStatement.setDate(3,date);
            preparedStatement.executeUpdate();
            logger.info("Udało si utworzyc obecność");
        }
    }


    public void createStudentsTable() throws SQLException {

        try (Connection connection = connectionFactory.getConnection()) {
            logger.info("poleczenie sukces!");
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "create table if not EXISTS students(\n" +
                            "id int PRIMARY key AUTO_INCREMENT,\n" +
                            "name VARCHAR(50),\n" +
                            "course_id INT,\n" +
                            "description VARCHAR(200),\n" +
                            "seat VARCHAR(10),\n" +
                            "FOREIGN KEY (course_id) REFERENCES courses(id)\n" +
                            "\n" +
                            ");"
            );
            logger.info("Stworzono tabele student jezeli nie istniała");
        }
    }

    public void createAttendanceListTable() throws SQLException {


        try (Connection connection = connectionFactory.getConnection()) {
            logger.info("poleczenie sukces!");
            Statement statement = connection.createStatement();
            statement.executeUpdate(
                    "create table if not EXISTS attendance_list(\n" +
                            "id int PRIMARY key AUTO_INCREMENT,\n" +
                            "course_id INT,\n" +
                            "student_id INT,\n" +
                            "date date,\n" +
                            "foreign key (course_id) REFERENCES courses(id),\n" +
                            "FOREIGN KEY (student_id) REFERENCES students(id)\n" +
                            ");"
            );
            logger.info("Stworzono tabele LISTA OBECNOSCI");
        }
    }



    public void printAllCourses() throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {
            Statement statement = connection.createStatement();
           ResultSet resultSet =
            statement.executeQuery("SELECT id, name, place, start_date, end_date FROM courses");
            while (resultSet.next()){
               int id = resultSet.getInt("id");
                String name  = resultSet.getString("name");
               String place = resultSet.getString("place");
                Date start_date = resultSet.getDate("start_date");
                Date end_date = resultSet.getDate("end_date");

                logger.info("id = " + id +
                        "name = " + name +
                        "place = " + place +
                        "startDate = " + start_date +
                        "endDate = " + end_date);


            }
        }
    }

    public void printStudentsOFCourse(int course_id) throws SQLException {
        try(Connection connection = connectionFactory.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT id, name FROM students WHERE course_id = ?");
            preparedStatement.setInt(1, course_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                logger.info("ID = " + id + " course id = " + course_id);
            }




        }


    }


    public void dropAllTables() {

    }



    public void dropCourse(int course_id){
        try (Connection connection = connectionFactory.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM COURSES WHERE ID = ?");

            preparedStatement.setInt(1,course_id );
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }




    public void studentUpdate(int student_id, String description, String seat) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE students SET description = ?,  seat = ? WHERE ID = ?");

            preparedStatement.setString(1, description);
            preparedStatement.setString(2,seat);
            preparedStatement.setInt(3, student_id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void courseNameUpdate(int course_id, String newName) throws SQLException {
        try (Connection connection = connectionFactory.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE courses SET name = ? WHERE ID = ?");

            preparedStatement.setString(1, newName);
            preparedStatement.setInt(2, course_id);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void deleteAttendanceOfStudent(int student_id, Date date) {
        try (Connection connection = connectionFactory.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM attendance_list where student_id = ? and date = ?");
            preparedStatement.setInt(1, student_id);
            preparedStatement.setDate(2, date);
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        try {
            CoursesManager coursesManager = new CoursesManager();
            coursesManager.printAllCourses();
            coursesManager.printStudentsOFCourse(3);






            //zadaniia:
            //coursesManager.studentUpdate(1,"po poprawie","1.1.1");
            //coursesManager.dropCourse(1);
            coursesManager.courseNameUpdate(1,"nowa nazwa");


        } catch (SQLException e) {
            e.printStackTrace();
        }


//            coursesManager.createCoursesTable();
//            coursesManager.createStudentsTable();
//            coursesManager.createAttendanceListTable();
//            coursesManager.createAttendance(1,1, Date.valueOf(LocalDate.of(2019,01,01)));
//            coursesManager.createAttendance(1,1, Date.valueOf(LocalDate.of(2019,01,02)));
//            coursesManager.createAttendance(1,2, Date.valueOf(LocalDate.of(2019,01,01)));
//            coursesManager.createAttendance(1,2, Date.valueOf(LocalDate.of(2019,01,02)));
//            coursesManager.createAttendance(3,3, Date.valueOf(LocalDate.of(2019,01,05)));
//            coursesManager.createAttendance(3,3, Date.valueOf(LocalDate.of(2019,01,06)));





    /*        coursesManager.createStudent(
                    "Tomasz",
                    1,
                    "C.3.4",
                    "Kocha Jave");

            coursesManager.createStudent(
                    "Michał",
                    1,
                    "B.2.1",
                    "Kocha c++");


            coursesManager.createStudent(
                    "Wojtek",
                    3,
                    "A.1.1",
                    "jezdzi rowerem");
    */




            //coursesManager.createCourse();
         //   LocalDateTime start = new LocalDateTime(2018,)
          //  coursesManager.createCourse("JavaWro1", "Wroclaw",
//               Date.valueOf(LocalDate.of(2018,01,01),
//               Date.valueOf(LocalDate.of(2019,10,10)));




    }


}
