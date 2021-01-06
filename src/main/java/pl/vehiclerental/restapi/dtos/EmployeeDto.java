package pl.vehiclerental.restapi.dtos;

public class EmployeeDto {

    private Long id;

    private double bonus;

    private Long bossId;

    private Long userId;

    private Long jobId;

    public EmployeeDto() {
    }

    public EmployeeDto(Long id, double bonus, Long bossId, Long userId, Long jobId) {
        this.id = id;
        this.bonus = bonus;
        this.bossId = bossId;
        this.userId = userId;
        this.jobId = jobId;
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

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }
}
