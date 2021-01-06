package pl.vehiclerental.restapi.models;

import javax.persistence.*;

@Entity
@Table(	name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double bonus;

    private Boolean isActive;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="boss_id")
    private Employee boss;

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="job_id")
    private Job job;

    public Employee() {
    }

    public Employee(double bonus, Employee boss) {
        this.bonus = bonus;
        this.boss = boss;
        this.setActive(true);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBonus() {
        return bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public Employee getBoss() {
        return boss;
    }

    public void setBoss(Employee boss) {
        this.boss = boss;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
