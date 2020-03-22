package org.wirvsvirushackathon.stayathome.model;

/**
 * The player ranking is a descriptive title indicating the player's game experience, e.g. for
 * the high scores listing.
 */
public enum PlayerRanking {

    LEVEL1("Couchtourist", 0),
    LEVEL2("Fortgeschrittener Couchtourist", 3000),
    LEVEL3("Probelieger", 6000),
    LEVEL4("Kampf Probelieger", 12000),
    LEVEL5("Sesselpupser", 24000),
    LEVEL6("Professioneller Sesselpupser", 48000),
    LEVEL7("Wannabe B.e.t.t.-man", 96000),
    LEVEL8("B.e.t.t.-man", 192000),
    LEVEL9("Couchpotato", 384000),
    LEVEL10("Senior Couchpotato", 768000),
    LEVEL11("Master of Nichtstuing", 1536000),
    LEVEL12("Der mit der Couch tanzt", 3072000),
    LEVEL13("Held in Ausbildung", 6144000),
    LEVEL14("Videokonferenz Model", 12288000),
    LEVEL15("Stubenhocker", 24576000),
    LEVEL16("Perfekter Sidekick - Superheld", 49152000),
    LEVEL17("Superheld", 98304000);

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
