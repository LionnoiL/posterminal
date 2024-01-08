package ua.gaponov.posterminal.entity.options;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author Andriy Gaponov
 */
@Getter
@Setter
@Builder
public class OptionsValue implements Serializable {
    private String optionsKey;
    private String optionsValue;
}
