package com.thebaileybrew.retailinventory.QueryPull;

public class Game {
    private String gameName; //
    private String gamePublisher; //
    private String gameReleaseDate; //
    private String gameGenre; //
    private String gameSystem; //

    public Game(String gameName, String gamePublisher, String gameReleaseDate, String gameGenre, String gameSystem) {
        this.gameName = gameName;
        this.gamePublisher = gamePublisher;
        this.gameReleaseDate = gameReleaseDate;
        this.gameGenre = gameGenre;
        this.gameSystem = gameSystem;
    }

    public String getGameName() {
        return gameName;
    }

    public String getGamePublisher() {
        return gamePublisher;
    }

    public String getGameReleaseDate() {
        return gameReleaseDate;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public String getGameSystem() {
        return gameSystem;
    }
}
