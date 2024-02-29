package ua.gaponov.posterminal.server.commands;

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
    String requestString;
}
