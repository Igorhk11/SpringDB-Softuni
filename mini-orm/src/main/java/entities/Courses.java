package entities;

import orm.annotation.Column;
import orm.annotation.Entity;
import orm.annotation.Id;

@Entity(name = "courses")
public class Courses {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "length_in_weeks")
    private int weeksLength;

    public Courses() {}

    public Courses(String name, int weeksLength) {
        this.name = name;
        this.weeksLength = weeksLength;
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weeksLength=" + weeksLength +
                '}';
    }
}