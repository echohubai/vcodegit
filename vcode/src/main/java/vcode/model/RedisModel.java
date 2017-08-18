package vcode.model;

/**
 * Created by hubai on 2017/8/16.
 */
public class RedisModel {
  private PictureModel pictureModel;
  private int failedCount;
  private String AppID;
  private String validate;

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public PictureModel getPictureModel() {
        return pictureModel;
    }

    public void setPictureModel(PictureModel pictureModel) {
        this.pictureModel = pictureModel;
    }

    public String getAppID() {
        return AppID;
    }

    public void setAppID(String appID) {
        AppID = appID;
    }

    public String getValidate() {
        return validate;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }
}
