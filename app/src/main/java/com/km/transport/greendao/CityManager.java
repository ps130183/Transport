package com.km.transport.greendao;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * Created by PengSong on 17/9/18.
 */

public class CityManager {
    private static DaoSession mDaoSession;

    private static List<City> provinces;
    private CityManager(){
        mDaoSession = GreenDbManager.getInstances().getDaoSession();
    }
    private static CityManager mInstance = null;
    public static CityManager getInstance(){
        if (mInstance == null){
            synchronized (CityManager.class){
                if (mInstance == null){
                    mInstance = new CityManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取省级 城市
     * @return
     */
    public List<City> getProvinces() {
        if (provinces == null){
            provinces = mDaoSession.getCityDao().queryBuilder().where(CityDao.Properties.Type.eq(2)).list();
        }
        return provinces;
    }

    /**
     * 获取市级 城市
     * @param id
     * @return
     */
    public List<City> getCitys(long id){
        List<City> cityList = mDaoSession.getCityDao().queryBuilder().where(CityDao.Properties.Type.eq(3),CityDao.Properties.ParentId.eq(id)).list();
        return cityList;
    }

    /**
     * 获取 区县 上一级 市级 城市
     * @param parentId
     * @return
     */
    public List<City> getPreCitys(long parentId){
        City preCity = mDaoSession.getCityDao().queryBuilder().where(CityDao.Properties.Type.eq(3),CityDao.Properties.Id.eq(parentId)).unique();
        List<City> cityList = mDaoSession.getCityDao().queryBuilder().where(CityDao.Properties.Type.eq(3),CityDao.Properties.ParentId.eq(preCity.getParentId())).list();
        return cityList;
    }

    /**
     * 获取区/县 城市
     * @param id
     * @return
     */
    public List<City> getAreas(long id){
        return mDaoSession.getCityDao().queryBuilder().where(CityDao.Properties.Type.eq(4),CityDao.Properties.ParentId.eq(id)).list();
    }
}
