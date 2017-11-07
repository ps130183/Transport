package com.km.transport.module.personal;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.MessageDto;

import java.util.List;

/**
 * Created by PengSong on 17/9/27.
 */

public interface MessageContract {
    interface View extends BaseView{
        void showMessage(List<MessageDto> messageDtos ,int pageNo);
    }
    interface Presenter extends BasePresenter{
        void getMessageList(int pageNo);
    }
}
