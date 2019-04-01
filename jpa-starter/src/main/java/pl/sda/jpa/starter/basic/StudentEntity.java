package pl.sda.jpa.starter.basic;

import javax.persistence.*;

@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(name ="year_of_study")
    private int yarOfStudy;



    public StudentEntity(){};

    public StudentEntity(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYarOfStudy() {
        return yarOfStudy;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yarOfStudy=" + yarOfStudy +
                '}';
    }
}
