package ua.gaponov.posterminal.entity.orders;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletedOrderItem {

    private LocalDateTime deletedTime;
    private String userName;
    private String productName;
    private double qty;
    private double summa;
}
