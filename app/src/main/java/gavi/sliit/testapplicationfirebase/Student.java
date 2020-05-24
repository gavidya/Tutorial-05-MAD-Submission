package gavi.sliit.testapplicationfirebase;

public class Student {

    private String ID;
    private String name;
    private String address;
    private Integer phone;

    public Student() {
    }

    public Student(String ID, String name, String address, Integer phone) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
}
