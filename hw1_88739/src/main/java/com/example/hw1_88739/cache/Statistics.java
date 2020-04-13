package com.example.hw1_88739.cache;

public class Statistics {
    int hit;
    int miss;

    public Statistics(int hit, int miss) {
        this.hit = hit;
        this.miss = miss;
    }

    public int getHit() {
        return hit;
    }

    public void setHit() {
        this.hit+=1;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss() {
        this.miss +=1;
    }

    @Override
    public String toString() {
        return "Cache statistics{" +
                "hit=" + hit +
                ", miss=" + miss +
                '}';
    }
}
