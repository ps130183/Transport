package com.km.transport.module.personal;

import com.km.transport.dto.MessageDto;
import com.km.transport.utils.retrofit.PresenterWrapper;

import java.util.List;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by PengSong on 17/9/27.
 */

public class MessagePresenter extends PresenterWrapper<MessageContract.View> implements MessageContract.Presenter {

    public MessagePresenter(MessageContract.View mView) {
        super(mView);
    }

    @Override
    public void onCreateView() {

    }

    @Override
    public void getMessageList(final int pageNo) {
        mView.showLoading();
        mApiwrapper.getMessageList(pageNo)
                .subscribe(newSubscriber(new Consumer<List<MessageDto>>() {
                    @Override
                    public void accept(@NonNull List<MessageDto> messageDtos) throws Exception {
                        mView.showMessage(messageDtos,pageNo);
                    }
                }));
    }
}
