package vcode.dao;

import org.springframework.stereotype.Repository;
import vcode.model.PictureModel;

import java.util.List;

/**
 * Created by liqiang on 2017/7/25.
 */
@Repository
public interface PictureDao {

    List<PictureModel> selectPictureByRand();
    void save(PictureModel pictureModel);

}
