package com.github.startzyp;

public class CDtime {
    private long useTime;
    private long CDTime;
    private String lorename;
    private String playername;

    public CDtime(long useTime, long CDTime, String lorename, String playername) {
        this.useTime = useTime;
        this.CDTime = CDTime;
        this.lorename = lorename;
        this.playername = playername;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public long getCDTime() {
        return CDTime;
    }

    public void setCDTime(long CDTime) {
        this.CDTime = CDTime;
    }

    public String getLorename() {
        return lorename;
    }

    public void setLorename(String lorename) {
        this.lorename = lorename;
    }

    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    @Override
    public int hashCode(){
        return playername.hashCode()+lorename.length();//简单原则
    }

    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof CDtime)){
            return false;
        }
        if (!((CDtime) obj).playername.equalsIgnoreCase(playername)){
            return false;
        }
        CDtime obj1 = (CDtime) obj;
        return obj1.lorename.equalsIgnoreCase(lorename);
    }
}
