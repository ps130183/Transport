package com.km.transport.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by PengSong on 17/9/18.
 */

@Entity(nameInDb = "city",createInDb = true)
public class City {

    @Id
    private long id;

    private long parentId;

    private String name;

    private int type;

    @Generated(hash = 894386435)
    public City(long id, long parentId, String name, int type) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.type = type;
    }

    @Generated(hash = 750791287)
    public City() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return this.parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
