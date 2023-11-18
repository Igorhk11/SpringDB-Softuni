package entities;

import javax.persistence.*;

@Entity
@Table(name = "medications")
public class Medication extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "visitation_id")
    private Visitation visitation;

    @Column(name = "name")
    private String name;
}
