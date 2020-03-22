package org.wirvsvirushackathon.stayathome.model;

/**
 * The player ranking is a descriptive title indicating the player's game experience, e.g. for
 * the high scores listing.
 */
public enum PlayerRanking {

    LEVEL1("(Fortgeschrittener) Couchtourist", 0),
    LEVEL2("(10 Kampf) Probelieger", 600),
    LEVEL3("(Professioneller) Sesselpupser", 3000),
    LEVEL4("Wannabe B.e.t.t.-man", 1200),
    LEVEL5("Senior Couchpotato", 36000),
    LEVEL6("Master of Nichtstuing", 108000),
    LEVEL7("Der mit der Couch tanzt", 864000),
    LEVEL8("Held in Ausbildung", 1728000),
    LEVEL9("Videokonferenz Model", 3456000),
    LEVEL10("Stubenhocker", 6912000),
    LEVEL11("Perfekter Sidekick - Superheld", 13824000),
    LEVEL12("Superheld", 27648000);

    private String name;

    private long score;

    PlayerRanking(final String name, final long score) {
        this.name = name;
        this.score = score;
    }

    /**
     * The human-readable name of the ranking.
     */
    public String getName() {
        return name;
    }

    /**
     * The score which the user must reach to be granted the player ranking.
     */
    public long getScore() {
        return score;
    }
}
