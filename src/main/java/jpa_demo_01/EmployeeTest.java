package jpa_demo_01;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeTest {
    
    static final String DBNAME = "jpa_demo";
    
    public static void main(String[] args) {
        // overriding the existing properties in persistence.xml
        Map<String,String> persistenceMap = new HashMap<>();
        persistenceMap.put("jakarta.persistence.jdbc.url", 
                "jdbc:mariadb://localhost:3306/"+DBNAME);
        
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("EmployeeService", persistenceMap);
        EntityManager em = emf.createEntityManager();
        EmployeeService service = new EmployeeService(em);
        Employee emp = null;

//        em.getTransaction().begin();
//        //  create and persist an employee
//        emp = service.createEmployee(202, "Jane", "Doe");
//        em.getTransaction().commit();
//        System.out.println("Persisted " + emp);

        // 1. find a specific employee
        emp = service.findEmployee(10018);
        System.out.println("Found " + emp);

//         2. find all employees
//        List<Employee> lst_emps = service.findAllEmployees();
//        for (Employee e : lst_emps)
//            System.out.println("Found employee: " + e);

        // 3. update the employee
//        emp = service.raiseEmployeeSalary(158, 1000);
//        em.getTransaction().commit();
//        System.out.println("Updated " + emp);

//        // 4. remove an employee
//        service.removeEmployee(158);
//        em.getTransaction().commit();
//        System.out.println("Removed Employee 158");

        // close the EM and EMF when done
        em.close();
        emf.close();
    }
}
