package pl.vehiclerental.restapi.dtos;

import java.time.LocalDateTime;

public class JobHistoryRecordDto {

    private LocalDateTime startDate;
    private Long userId;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
