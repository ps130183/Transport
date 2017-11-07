package com.km.transport.event;

import android.text.TextUtils;

import com.km.transport.basic.BaseEntity;


/**
 * Created by kamangkeji on 17/7/4.
 */

public class ClubIntroduceEntity extends BaseEntity {

    private String introduceImgPath;
    private String introduceContent;
    private int progress;
    private boolean isCanDelete;

    public String getIntroduceImgPath() {
        return introduceImgPath;
    }

    public void setIntroduceImgPath(String introduceImgPath) {
        this.introduceImgPath = introduceImgPath;
    }

    public String getIntroduceContent() {
        return introduceContent;
    }

    public void setIntroduceContent(String introduceContent) {
        this.introduceContent = introduceContent;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public boolean isCanDelete() {
        return isCanDelete;
    }

    public void setCanDelete(boolean canDelete) {
        isCanDelete = canDelete;
    }

    @Override
    public boolean isEmpty() {
        if (TextUtils.isEmpty(introduceContent) || TextUtils.isEmpty(introduceImgPath)){
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "ClubIntroduceEntity{" +
                "introduceImgPath='" + introduceImgPath + '\'' +
                ", introduceContent='" + introduceContent + '\'' +
                ", progress=" + progress +
                ", isCanDelete=" + isCanDelete +
                '}';
    }
}
