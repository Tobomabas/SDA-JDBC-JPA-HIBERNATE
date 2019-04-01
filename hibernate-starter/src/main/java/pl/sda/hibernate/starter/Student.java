package pl.sda.hibernate.starter;

public class Student {
    private Integer id;
    private String name;
    private int courseId;
    private String description;
    private String seat;

    public Student(String name, int courseId, String description, String seat) {
        this.name = name;
        this.courseId = courseId;
        this.description = description;
        this.seat = seat;
    }
    public Student (){};

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getDescription() {
        return description;
    }

    public String getSeat() {
        return seat;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courseId=" + courseId +
                ", description='" + description + '\'' +
                ", seat='" + seat + '\'' +
                '}';
    }
}
