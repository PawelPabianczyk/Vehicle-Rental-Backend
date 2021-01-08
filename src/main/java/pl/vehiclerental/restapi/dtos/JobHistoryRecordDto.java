package pl.vehiclerental.restapi.dtos;

import java.time.LocalDate;

public class JobHistoryRecordDto {

    private LocalDate startDate;
    private Long userId;

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
