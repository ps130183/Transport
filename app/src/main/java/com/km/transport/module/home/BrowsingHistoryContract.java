package com.km.transport.module.home;

import com.km.transport.basic.BasePresenter;
import com.km.transport.basic.BaseView;
import com.km.transport.dto.BrowsingHistoryDto;
import com.km.transport.dto.GoodsOrderDetailDto;

import java.util.List;

/**
 * Created by PengSong on 17/9/21.
 */

public interface BrowsingHistoryContract {
    interface View extends BaseView{
        void showBrowsingHistroyList(List<BrowsingHistoryDto> browsingHistoryDtos,int pageNo);
        void showGoodsOrderInfo(GoodsOrderDetailDto goodsOrderDetailDto);
        void clearSuccess();
    }
    interface Presneter extends BasePresenter{
        void getBrowsingHistoryList(int pageNo);
        void hireGoods(String id);
        void clearBrowsingHistory();
    }
}
