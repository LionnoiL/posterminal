package ua.gaponov.posterminal.entity.confirmation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ua.gaponov.posterminal.entity.moneymove.MoneyMoveService;
import ua.gaponov.posterminal.entity.orders.OrderService;

import java.sql.SQLException;

/**
 * @author Andriy Gaponov
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConfirmationService {

    public static void save(Confirmation confirmation) throws SQLException {
        switch (confirmation.getDocumentType()) {
            case PAY -> MoneyMoveService.confirmUploadDocument(confirmation.getDocumentGuid());
            default -> OrderService.confirmUploadDocument(confirmation.getDocumentGuid());
        }
    }
}
