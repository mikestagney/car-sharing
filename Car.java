package carsharing;

public class Car {

    private int  id;
    private String name;
    private int companyId;

    Car(int carId, String carName, int companyId) {
        id = carId;
        name = carName;
        this.companyId = companyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }
    public String toString() {
        return name;
    }
}
