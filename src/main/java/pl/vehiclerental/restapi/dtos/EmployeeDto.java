package pl.vehiclerental.restapi.dtos;

public class EmployeeDto {

    private Long id;

    private double bonus;

    private Long bossId;

    private Long userId;

    private String jobTitle;

    private Boolean isActive;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, double bonus, Long bossId, Long userId, String jobTitle) {
        this.id = id;
        this.bonus = bonus;
        this.bossId = bossId;
        this.userId = userId;
        this.jobTitle = jobTitle;
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

    public Long getBossId() {
        return bossId;
    }

    public void setBossId(Long bossId) {
        this.bossId = bossId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
