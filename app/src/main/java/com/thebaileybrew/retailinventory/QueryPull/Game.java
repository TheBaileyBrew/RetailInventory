package com.thebaileybrew.retailinventory.QueryPull;

public class Game {
    public String gameName; //
    public String gamePublisher; //
    public String gameReleaseDate; //
    public String gameGenre; //
    public String gameAverageRating; //

    public Game(String gameName) {
        this.gameName = gameName;
        //this.gamePublisher = gamePublisher;
        //this.gameReleaseDate = gameReleaseDate;
        //this.gameGenre = gameGenre;
        //this.gameAverageRating = gameAverageRating;
    }

    public String getGameName() {
        return gameName;
    }

    /*public String getGamePublisher() {
        return gamePublisher;
    }

    public String getGameReleaseDate() {
        return gameReleaseDate;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public String getGameAverageRating() {
        return gameAverageRating;
    }*/
}
