package vcode.dao;

import org.apache.ibatis.annotations.Param;
import vcode.model.Picture;

import java.util.List;


/**
 * Created by liqiang on 2017/8/15.
 */
public interface PictureMDao {
    void save(Picture picture);
    List<Picture> selectPicture();
    List<Picture> selectPictureM(@Param("pKid") String pKid);
    Picture selectPictureById(@Param("pMid") Long pMid);
    void deletePicture(@Param("pMid") Long pMid);
}
