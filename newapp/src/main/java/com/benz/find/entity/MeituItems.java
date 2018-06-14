package com.benz.find.entity;

import java.util.List;

/**
 * Created by Jiaxu on 2018-06-11
 */

public class MeituItems {
    private String cardCover;
    private MeituItemsCoverEntity cover;
    private  List<String> images;
    private String tags;
    private int index;
    private List<MeituItemsStyleCardEntity> styleCard;
    private String id;

    public String getCardCover() {
        return cardCover;
    }

    public void setCardCover(String cardCover) {
        this.cardCover = cardCover;
    }

    public MeituItemsCoverEntity getCover() {
        return cover;
    }

    public void setCover(MeituItemsCoverEntity cover) {
        this.cover = cover;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<MeituItemsStyleCardEntity> getStyleCard() {
        return styleCard;
    }

    public void setStyleCard(List<MeituItemsStyleCardEntity> styleCard) {
        this.styleCard = styleCard;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
