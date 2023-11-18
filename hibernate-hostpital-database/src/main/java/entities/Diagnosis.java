package entities;

import javax.persistence.*;

@Entity
@Table(name = "diagnoses")
public class Diagnosis extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "visitation_id")
    private Visitation visitation;

    @Column
    private String name;

    @Column
    private String comments;

    public Diagnosis() {
    }

    public Visitation getVisitation() {
        return visitation;
    }

    public void setVisitation(Visitation visitation) {
        this.visitation = visitation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
