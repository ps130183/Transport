package com.km.transport.module.order.unfinished;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;

/**
 * Created by PengSong on 17/9/26.
 */

public interface PutStorageContract {
    interface View extends BaseView{
        void putStorageSuccess();
        void uploadSuccess(String resultUrl);
    }
    interface Presenter extends BasePresenter{
        void putStorageGoods(String id, String bournTunnage, String bournImage, String bournTime);
        void uploadPicture(String picturePath, String optionType);
    }
}
