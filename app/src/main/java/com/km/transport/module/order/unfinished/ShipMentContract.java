package com.km.transport.module.order.unfinished;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;

/**
 * Created by PengSong on 17/9/26.
 */

public interface ShipMentContract {
    interface View extends BaseView{
        void shipMentSuccess();
        void uploadSuccess(String resultUrl);
    }
    interface Presenter extends BasePresenter{
        void shipMentGoods(String id,String outTunnage,String sourceImage,String sourceTime);
        void uploadPicture(String picturePath,String optionType);
    }
}
