package com.example.linux.myadapterview;

public class Music {
    private int id;
    private int musicImg;
    private String musicName;
    private boolean mLike;

    public Music(int id, int musicImg, String musicName, boolean like) {
        this.mLike = like;
        this.id = id;
        this.musicImg = musicImg;
        this.musicName = musicName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMusicImg() {
        return musicImg;
    }

    public void setMusicImg(int musicImg) {
        this.musicImg = musicImg;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public boolean isLike() {
        return mLike;
    }

    public void setLike(boolean like) {
        mLike = like;
    }
}
