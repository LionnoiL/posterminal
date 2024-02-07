package ua.gaponov.posterminal.entity;

public enum Language {
    UA("ua"),
    EN("en");
    private String shortName;

    public String getShortName() {
        return shortName;
    }

    Language(String shortName) {
        this.shortName = shortName;
    }
}
