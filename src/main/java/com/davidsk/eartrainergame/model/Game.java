package com.davidsk.eartrainergame.model;


public class Game
{
    private int level;
    protected int score;
    protected int currentRound;
    protected Round[] rounds;
    protected int nOfRounds;


    //Empty Constructor
    public Game()
    {
        level = 1;
        rounds = new Round[11];
        score = 0;
        currentRound = 1;
        nOfRounds = 10;
    }

    //Constructor for different levels
    public Game(int level, int rounds)
    {
        this.level = level;
        score = 0;
        this.rounds = new Round[rounds+1];
        currentRound = 1;
        nOfRounds = rounds;
    }

    public boolean addRound(String key, String PlayerKey)
    {
        if (currentRound <= nOfRounds)
        {
            rounds[currentRound] = new Round(key, PlayerKey, 0);
            currentRound++;
            return true;
        }
        return false;
    }

    public void removeRound(int id)
    {
        rounds[id] = null;
    }



    /**Getters and Setters for manipulating variables**/

    public void setScore(int score)
    {
        this.score = score;
    }

    public int getScore()
    {
        return score;
    }

    public int getRound()
    {
        return currentRound;
    }

    public void addPoint()
    {
        score++;
    }

    public int getLevel()
    {
        return level;
    }

    public void removePoint()
    {
        score--;
    }

    public Round getRound(int id)
    {
        return rounds[id];
    }

    public Round[] getAllRounds()
    {
        return rounds;
    }


    //method for saving scores to db
    public Score save()
    {
        Score result = new Score();
        result.setLevel(level);
        result.setRounds(nOfRounds);
        result.setScore(score);
        return result;
    }

    /**Inner Round Class**/
    private class Round
    {
        private String key;
        private String playerKey;
        private int distance;

        public Round(String key, String playerKey, int distance)
        {
            this.key = key;
            this.playerKey = playerKey;
            this.distance = distance;
        }

        /**Extra getters and setters**/

        public String getKey()
        {
            return key;
        }

        public String getPlayerKey()
        {
            return playerKey;
        }

        public int getDistance()
        {
            return distance;
        }

        public void setKey(String key)
        {
            this.key = key;
        }

        public void setPlayerKey(String playerKey)
        {
            this.playerKey = playerKey;
        }

        public void setDistance(int distance)
        {
            this.distance = distance;
        }
    }
}


