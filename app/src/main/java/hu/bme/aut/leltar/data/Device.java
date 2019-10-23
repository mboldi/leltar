package hu.bme.aut.leltar.data;

public class Device {
    private String maker, type, basicName, details;
    private int value;
    private boolean expanded = false;

    public Device() {
    }

    public Device(String maker, String type, String basicName, int value) {
        this.maker = maker;
        this.type = type;
        this.basicName = basicName;
        this.value = value;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBasicName() {
        return basicName;
    }

    public void setBasicName(String basicName) {
        this.basicName = basicName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
