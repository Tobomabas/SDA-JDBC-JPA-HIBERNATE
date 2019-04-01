package pl.sda.jpa.starter.inheritance;

import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "coaches")
//@DiscriminatorValue("coach")
public class Coach extends Member {
    private int salaryPerHour;
    private int yearsOfExpierence;
    @Version
    private int version;


    public void setYearsOfExpierence(int yearsOfExpierence) {
        this.yearsOfExpierence = yearsOfExpierence;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    protected Coach() { }

    public Coach(String name, int salaryPerHour, int yearsOfExpierence) {
        super(name);
        this.salaryPerHour = salaryPerHour;
        this.yearsOfExpierence = yearsOfExpierence;
    }

    public int getSalaryPerHour() {
        return salaryPerHour;
    }

    public void setSalaryPerHour(int salaryPerHour) {
        this.salaryPerHour = salaryPerHour;
    }

    public int getYearsOfExpierence() {
        return yearsOfExpierence;
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + getId() +
                ", name=" + getName() +
                ", salaryPerHour=" + salaryPerHour +
                ", yearsOfExpierence=" + yearsOfExpierence +
                '}';
    }
}