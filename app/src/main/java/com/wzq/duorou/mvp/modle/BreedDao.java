package com.wzq.duorou.mvp.modle;

import com.wzq.duorou.beans.Breed;

import java.util.List;

/**
 * Created by weizhenqing on 2017/4/12.
 */

public interface BreedDao {

    String TABLE_NAME = "breed";
    String BREED_ID = "breed_id";
    String BREED_TITLE = "title";
    String BREED_IMAGE = "breed_image";
    String BREED_TIME = "time";
    String BREED_LIKE_COUNT = "breed_like_count";
    String BREED_SHARE_FROM = "breed_share_from";
    String BREED_LOCATION = "breed_location";
    String BREED_PATH = "breed_path";
    String BREED_REID = "breed_re_id";

    List<Breed> getBreedList();

    long saveBreedList(List<Breed> breeds);

    long delBreed(int breedId);
}
