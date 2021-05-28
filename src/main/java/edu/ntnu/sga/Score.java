package edu.ntnu.sga;

public class Score<T extends SGA> implements Comparable<Score<T>>{
    private int hardScore;
    private int softScore;

    public Score(int hardScore, int softScore) {
        this.hardScore = hardScore;
        this.softScore = softScore;
    }

    public Score(){
        hardScore = 0;
        softScore = 0;
    }

    public Score(Score<T> score){
        this.hardScore = score.getHardScore();
        this.softScore = score.getSoftScore();
    }

    public int getHardScore() {
        return hardScore;
    }

    public int getSoftScore() {
        return softScore;
    }

    public void addSoftScore(int score){
        this.softScore += score;
    }

    public void addHardScore(int score){
        this.hardScore += score;
    }

    @Override
    public int compareTo(Score<T> o) {
        int hard = hardScore - o.getHardScore();
        if (hard == 0){
            return softScore - o.getSoftScore();
        }
        return hard;
    }
}
