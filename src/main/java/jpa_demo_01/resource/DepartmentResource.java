package jpa_demo_01.resource;

import jpa_demo_01.dao.DepartmentDAO;
import jpa_demo_01.entity.Department;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    private final DepartmentDAO departmentDAO = new DepartmentDAO();

    /**
     * GET /api/departments
     * Returns all departments (dept_no + dept_name) as JSON.
     */
    @GET
    public List<Department> getAllDepartments() {
        // Return the entities directly – Jersey + Jackson will turn this into JSON
        return departmentDAO.findAll();
    }
}
