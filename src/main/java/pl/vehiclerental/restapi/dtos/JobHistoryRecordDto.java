package pl.vehiclerental.restapi.dtos;

public class JobHistoryRecordDto {

    private String startDate;
    private Long userId;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
