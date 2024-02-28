package ua.gaponov.posterminal.server.commands;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TerminalCommandRequest {

    TerminalCommand command;
    @JsonProperty("request_string")
    String requestString;
}
