package pl.sda.jpa.starter.queries;

public class StudentsStats {

    private int age;
    private long studentsNumber;

    @Override
    public String toString() {
        return "StudentsStats{" +
                "age=" + age +
                ", studentsNumber=" + studentsNumber +
                '}';
    }

    public StudentsStats(int age, long studentsNumber) {
        this.age = age;
        this.studentsNumber = studentsNumber;
    }


    public int getAge() {
        return age;
    }

    public long getStudentsNumber() {
        return studentsNumber;
    }
}
