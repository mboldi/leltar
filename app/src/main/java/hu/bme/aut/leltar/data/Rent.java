package hu.bme.aut.leltar.data;

import java.util.ArrayList;
import java.util.List;

public class Rent {
    private int _id;
    private boolean out;
    private String outDate, propBackDate, actBackDate, givenBy, givenTo;
    private List<Device> devices;

    public Rent() {
        devices = new ArrayList<>();
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public boolean isOut() {
        return out;
    }

    public void setOut(boolean out) {
        this.out = out;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getPropBackDate() {
        return propBackDate;
    }

    public void setPropBackDate(String propBackDate) {
        this.propBackDate = propBackDate;
    }

    public String getActBackDate() {
        return actBackDate;
    }

    public void setActBackDate(String actBackDate) {
        this.actBackDate = actBackDate;
    }

    public String getGivenBy() {
        return givenBy;
    }

    public void setGivenBy(String givenBy) {
        this.givenBy = givenBy;
    }

    public String getGivenTo() {
        return givenTo;
    }

    public void setGivenTo(String givenTo) {
        this.givenTo = givenTo;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public void addDevice(Device device) {
        devices.add(device);
    }
}
