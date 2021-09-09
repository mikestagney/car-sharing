package carsharing;

import java.util.List;

public interface CarDAO {

    public void addCompany(String name);
    public List<CarCompany> getAllCompanies();

}
