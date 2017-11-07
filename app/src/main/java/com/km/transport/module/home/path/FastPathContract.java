package com.km.transport.module.home.path;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.HomeFastPathDto;

import java.util.List;

/**
 * Created by PengSong on 17/9/20.
 */

public interface FastPathContract {
    interface View extends BaseView{
        void showFastPathList(List<HomeFastPathDto> fastPathDtos);
        void deleteSuccess(HomeFastPathDto homeFastPathDto);
    }
    interface Presneter extends BasePresenter{
        void getFastPathList(int pageNo);
        void deleteFastPath(HomeFastPathDto homeFastPathDto);
    }
}
