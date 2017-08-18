package vcode.dao;

import vcode.model.PictureSon;

import java.util.List;

/**
 * Created by liqiang on 2017/8/15.
 */
public interface PictureSDao {
    void save(PictureSon pictureSon);
    List<PictureSon> selectPictureSByRand();
    void deletePictureSon(String pKid);
}
