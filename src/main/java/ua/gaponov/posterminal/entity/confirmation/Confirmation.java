package ua.gaponov.posterminal.entity.confirmation;

import lombok.Getter;
import lombok.Setter;
import ua.gaponov.posterminal.entity.DocumentTypes;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
public class Confirmation {

    private DocumentTypes documentType;
    private String documentGuid;
}
