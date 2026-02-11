package service;

import dao.EmployeDao;
import entities.Employe;
import java.util.List;

public class EmployeService {
    private final EmployeDao dao = new EmployeDao();

    public Employe getEmploye(Employe e) throws Exception {
        return dao.findById(e.getId());
    }

    public List<Employe> listEmployes() throws Exception {
        return dao.findAll();
    }

    public Employe createEmploye(Employe e) throws Exception {
        dao.insert(e);
        return e;
    }

    public boolean updateEmploye(Employe e) throws Exception {
        return dao.update(e);
    }

    public boolean deleteEmploye(Employe e) throws Exception {
        return dao.delete(e.getId());
    }
}