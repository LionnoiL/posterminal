package ua.gaponov.posterminal.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DatePeriod {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
