package ua.gaponov.posterminal.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Ініціалізація логування через SLF4J + log4j.properties
 */
public class LoggingConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);
    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;

        log.info("Logging initialized via SLF4J and log4j.properties");
    }
}
