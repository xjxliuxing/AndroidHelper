package com.xjx.helper.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 云店模块---店铺活动
 */
public class StoreActivityBean implements Parcelable {

    /**
     * id : 4028da5d6ed9006d016ed93f7fd4000d
     * createTime : 2019-12-06 11:27:27
     * name : cehsasdasdasd
     * activityType : 2
     * activityNature : 2
     * agentId : ff808081647099c101648d5526980078
     * agentName : 集中培训
     * beginTime : 2019-12-07 10:10:00
     * endTime : 2019-12-18 18:18:00
     * showStatus : 1
     * lineType : 1
     * onlineTime : 2019-12-06 11:30:40
     * isLine : 1
     * href : <p>123123123</p>
     * img : http://foxgoing.oss-cn-beijing.aliyuncs.com/img/ftms/20191206112653403-356.png
     * province : 140000
     * city : 140300
     * county : 140311
     * address : 12312312
     * maximumEnrolment : 100
     * maxinumCustomer : 3
     * applyBeginTime : 2019-12-10 09:09:00
     * applyEndTime : 2019-12-11 13:13:00
     * checkStatus : 1
     * checkOpinion : 12312
     * contentType : 1
     * publisher : 1
     * activityIntro : 123123123
     * homepageFlag : 0
     * isOpen : 1
     * activityCode : activity_1999
     * offlineTime : 2019-09-18 15:54:37
     * centent : 11111
     */

    private String id;
    private String createTime;
    private String name;
    private int activityType;
    private int activityNature;
    private String agentId;
    private String agentName;
    private String beginTime;
    private String endTime;
    private int showStatus;
    private int lineType;
    private String onlineTime;
    private int isLine;
    private String href;
    private String img;
    private String province;
    private String city;
    private String county;
    private String address;
    private int maximumEnrolment;
    private int maxinumCustomer;
    private String applyBeginTime;
    private String applyEndTime;
    private int checkStatus;
    private String checkOpinion;
    private int contentType;
    private String publisher;
    private String activityIntro;
    private int homepageFlag;
    private int isOpen;
    private String activityCode;
    private String offlineTime;
    private String centent;

    protected StoreActivityBean(Parcel in) {
        id = in.readString();
        createTime = in.readString();
        name = in.readString();
        activityType = in.readInt();
        activityNature = in.readInt();
        agentId = in.readString();
        agentName = in.readString();
        beginTime = in.readString();
        endTime = in.readString();
        showStatus = in.readInt();
        lineType = in.readInt();
        onlineTime = in.readString();
        isLine = in.readInt();
        href = in.readString();
        img = in.readString();
        province = in.readString();
        city = in.readString();
        county = in.readString();
        address = in.readString();
        maximumEnrolment = in.readInt();
        maxinumCustomer = in.readInt();
        applyBeginTime = in.readString();
        applyEndTime = in.readString();
        checkStatus = in.readInt();
        checkOpinion = in.readString();
        contentType = in.readInt();
        publisher = in.readString();
        activityIntro = in.readString();
        homepageFlag = in.readInt();
        isOpen = in.readInt();
        activityCode = in.readString();
        offlineTime = in.readString();
        centent = in.readString();
    }

    public static final Creator<StoreActivityBean> CREATOR = new Creator<StoreActivityBean>() {
        @Override
        public StoreActivityBean createFromParcel(Parcel in) {
            return new StoreActivityBean(in);
        }

        @Override
        public StoreActivityBean[] newArray(int size) {
            return new StoreActivityBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public int getActivityNature() {
        return activityNature;
    }

    public void setActivityNature(int activityNature) {
        this.activityNature = activityNature;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getShowStatus() {
        return showStatus;
    }

    public void setShowStatus(int showStatus) {
        this.showStatus = showStatus;
    }

    public int getLineType() {
        return lineType;
    }

    public void setLineType(int lineType) {
        this.lineType = lineType;
    }

    public String getOnlineTime() {
        return onlineTime;
    }

    public void setOnlineTime(String onlineTime) {
        this.onlineTime = onlineTime;
    }

    public int getIsLine() {
        return isLine;
    }

    public void setIsLine(int isLine) {
        this.isLine = isLine;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaximumEnrolment() {
        return maximumEnrolment;
    }

    public void setMaximumEnrolment(int maximumEnrolment) {
        this.maximumEnrolment = maximumEnrolment;
    }

    public int getMaxinumCustomer() {
        return maxinumCustomer;
    }

    public void setMaxinumCustomer(int maxinumCustomer) {
        this.maxinumCustomer = maxinumCustomer;
    }

    public String getApplyBeginTime() {
        return applyBeginTime;
    }

    public void setApplyBeginTime(String applyBeginTime) {
        this.applyBeginTime = applyBeginTime;
    }

    public String getApplyEndTime() {
        return applyEndTime;
    }

    public void setApplyEndTime(String applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    public int getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(int checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getCheckOpinion() {
        return checkOpinion;
    }

    public void setCheckOpinion(String checkOpinion) {
        this.checkOpinion = checkOpinion;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getActivityIntro() {
        return activityIntro;
    }

    public void setActivityIntro(String activityIntro) {
        this.activityIntro = activityIntro;
    }

    public int getHomepageFlag() {
        return homepageFlag;
    }

    public void setHomepageFlag(int homepageFlag) {
        this.homepageFlag = homepageFlag;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public String getOfflineTime() {
        return offlineTime;
    }

    public void setOfflineTime(String offlineTime) {
        this.offlineTime = offlineTime;
    }

    public String getCentent() {
        return centent;
    }

    public void setCentent(String centent) {
        this.centent = centent;
    }

    @Override
    public String toString() {
        return "StoreActivityBean{" +
                "id='" + id + '\'' +
                ", createTime='" + createTime + '\'' +
                ", name='" + name + '\'' +
                ", activityType=" + activityType +
                ", activityNature=" + activityNature +
                ", agentId='" + agentId + '\'' +
                ", agentName='" + agentName + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", showStatus=" + showStatus +
                ", lineType=" + lineType +
                ", onlineTime='" + onlineTime + '\'' +
                ", isLine=" + isLine +
                ", href='" + href + '\'' +
                ", img='" + img + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", county='" + county + '\'' +
                ", address='" + address + '\'' +
                ", maximumEnrolment=" + maximumEnrolment +
                ", maxinumCustomer=" + maxinumCustomer +
                ", applyBeginTime='" + applyBeginTime + '\'' +
                ", applyEndTime='" + applyEndTime + '\'' +
                ", checkStatus=" + checkStatus +
                ", checkOpinion='" + checkOpinion + '\'' +
                ", contentType=" + contentType +
                ", publisher='" + publisher + '\'' +
                ", activityIntro='" + activityIntro + '\'' +
                ", homepageFlag=" + homepageFlag +
                ", isOpen=" + isOpen +
                ", activityCode='" + activityCode + '\'' +
                ", offlineTime='" + offlineTime + '\'' +
                ", centent='" + centent + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(createTime);
        dest.writeString(name);
        dest.writeInt(activityType);
        dest.writeInt(activityNature);
        dest.writeString(agentId);
        dest.writeString(agentName);
        dest.writeString(beginTime);
        dest.writeString(endTime);
        dest.writeInt(showStatus);
        dest.writeInt(lineType);
        dest.writeString(onlineTime);
        dest.writeInt(isLine);
        dest.writeString(href);
        dest.writeString(img);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(county);
        dest.writeString(address);
        dest.writeInt(maximumEnrolment);
        dest.writeInt(maxinumCustomer);
        dest.writeString(applyBeginTime);
        dest.writeString(applyEndTime);
        dest.writeInt(checkStatus);
        dest.writeString(checkOpinion);
        dest.writeInt(contentType);
        dest.writeString(publisher);
        dest.writeString(activityIntro);
        dest.writeInt(homepageFlag);
        dest.writeInt(isOpen);
        dest.writeString(activityCode);
        dest.writeString(offlineTime);
        dest.writeString(centent);
    }
}
