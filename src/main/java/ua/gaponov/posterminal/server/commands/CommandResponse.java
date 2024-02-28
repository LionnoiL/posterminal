package ua.gaponov.posterminal.server.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class CommandResponse {

    private TerminalCommand command;
    private String result;
    private String message;
}
