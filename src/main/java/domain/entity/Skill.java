package domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skills", indexes = @Index(columnList = "name", unique = true))
public class Skill {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    public Skill() {}

    public Skill(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}