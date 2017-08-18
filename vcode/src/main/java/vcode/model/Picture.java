package vcode.model;

/**
 * Created by liqiang on 2017/7/24.
 */
public class Picture {
    private Long pMid;
    private String fullPicture;
    private String pictureWidth;
    private String pictureHeight;
    private String pNum;
    private String pKid;


    public Long getPMid() {
        return pMid;
    }

    public void setPMid(Long pMid) {
        this.pMid = pMid;
    }

    public String getFullPicture() {
        return fullPicture;
    }

    public void setFullPicture(String fullPicture) {
        this.fullPicture = fullPicture;
    }

    public String getPictureWidth() {
        return pictureWidth;
    }

    public void setPictureWidth(String pictureWidth) {
        this.pictureWidth = pictureWidth;
    }

    public String getPictureHeight() {
        return pictureHeight;
    }

    public void setPictureHeight(String pictureHeight) {
        this.pictureHeight = pictureHeight;
    }

    public String getPNum() {
        return pNum;
    }

    public void setPNum(String pNum) {
        this.pNum = pNum;
    }

    public String getPKid() {
        return pKid;
    }

    public void setPKid(String pKid) {
        this.pKid = pKid;
    }



}
