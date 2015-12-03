/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package employee.controller;

import de.grobmeier.jjson.JSONObject;
import de.grobmeier.jjson.JSONString;
import java.io.IOException;
import java.io.PrintWriter;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.UserTransaction;
import system.core.AppCore;
import system.core.Encoder;
import system.entity.AccountType;
import system.entity.Airport;
import system.entity.Employee;
import system.entity.Job;
import system.entity.Roles;
import system.entity.SystemAccount;

/**
 *
 * @author Loyd
 */
@WebServlet(name = "ModifyEmployeeFormCheck", urlPatterns = {"/modifyEmployeeFormCheck"})
public class ModifyEmployeeFormCheck extends HttpServlet
{
    @PersistenceUnit(unitName="AMSPU")
    private EntityManagerFactory emf;
    private boolean status = true;
    private String errorMessage = "";
    
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (!AppCore.initPage(request, response))
            return ;

        String id = request.getParameter("id");
        String accountType = request.getParameter("account_type");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phone_number");
        String address = request.getParameter("address");
        String job = request.getParameter("job");
        String hub = request.getParameter("hub");
        String salary = request.getParameter("salary");
        String localisation = request.getParameter("localisation");
        
        status = true;
        errorMessage = "";
        
        //EntityManagerFactory emf = Persistence.createEntityManagerFactory("AMSPU");
        EntityManager em = emf.createEntityManager();
        
        SystemAccount myAccount = (SystemAccount) request.getSession().getAttribute("_systemAccount");
        int jobId = myAccount.getEmployee().getJobId().getId();

        Job jobEntity = null;
        Airport hubEntity = null;
        Airport positionEntity = null;
        AccountType accountTypeEntity = null;
        Roles roleEntity = null;
        
        checkLogin(login, em, id);
        if (status) checkPassword(password);
        if (status) checkName(name);
        if (status) checkPhoneNumber(phoneNumber);
        if (status) checkAddress(address);
        if (status) checkJob(job);
        if (status) hubEntity = checkHub(hub, em);
        if (status) jobEntity = checkSalary(salary, em, job);
        if (status) positionEntity = checkLocalisation(localisation, em);       
        if (status) roleEntity = checkRole(accountType, em, jobId);
        if (status) accountTypeEntity = checkAccountType(em);


        if (status)
        {
            float salaryFloat = Float.parseFloat(salary);
            // System account commit & Employee Commit
            SystemAccount account = null;
            try
            {
                UserTransaction transaction;
                Employee employee;
                
                transaction = (UserTransaction)new InitialContext().lookup("java:comp/UserTransaction");
                transaction.begin();
                
                account = (SystemAccount) em.createNamedQuery("SystemAccount.findById").setParameter("id", Integer.parseInt(id)).getSingleResult();
                account.setAccountType(accountTypeEntity);
                account.setRole(roleEntity);
                account.setLogin(login);
                account.setPassword(Encoder.encode("SHA-512", login.toLowerCase() + account.getSalt() + password));
                employee = account.getEmployee();
                
                employee.setName(name);
                employee.setPhone(phoneNumber);
                employee.setAddress(address);
                employee.setJobId(jobEntity);
                employee.setSalary(salaryFloat);
                employee.setHub(hubEntity);
                employee.setPosition(positionEntity);
                account.setEmployee(employee);
   
                em.persist(account);
                em.flush();
                transaction.commit();
            }
            catch (Exception ex)
            {
                errorMessage = "Problem encountered : please retry later";
                status = false;
            }
        }
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        jsonWriting(out, status);
    }
    
    private void checkLogin(String login, EntityManager em, String id)
    {
        if (login == null || login.isEmpty())
        {
            errorMessage = "Login field must be filled";
            status &= false;
        }
        else if (login.length() > 49)
        {
            errorMessage = "Login field too long";
            status &= false;
        }
        SystemAccount account = null;
        try
        {        
             account = (SystemAccount) em.createNamedQuery("SystemAccount.findByLogin").setParameter("login", login).getSingleResult();
        }
        catch (Exception ex) {}
        if (account != null && account.getId() != Integer.parseInt(id))
        {
            errorMessage = "Login already exists, please specify another login";
            status &= false;
        }
        status &= true;
    }

    private void checkPassword(String password)
    {
        if (password == null || password.isEmpty())
        {
            errorMessage = "Password field must be filled";
            status &= false;
        }
        else if (password.length() > 254)
        {
            errorMessage = "Password field too long";
            status &= false;
        }
        status &= true;
    }
    
    private void checkName(String name)
    {
        if (name == null || name.isEmpty())
        {
            errorMessage = "Name field must be filled";
            status &= false;
        }
        else if (name.length() > 254)
        {
            errorMessage = "Name field too long";
            status &= false;
        }
        status &= true;
    }
    
    private void checkPhoneNumber(String phoneNumber)
    {
        if (phoneNumber == null || phoneNumber.isEmpty())
        {
            errorMessage = "Phone number field must be filled";
            status &= false;
        }
        else if (phoneNumber.length() > 20)
        {
            errorMessage = "Phone number field too long";
            status &= false;
        }
        status &= true;
    }
    
    private void checkAddress(String address)
    {
        if (address == null || address.isEmpty())
        {
            errorMessage = "Address field must be filled";
            status &= false;
        }
        else if (address.length() > 254)
        {
            errorMessage = "Address field too long";
            status &= false;
        }
        status &= true;
    }
    
    private void checkJob(String job)
    {
        if (job == null || job.isEmpty())
        {
            errorMessage = "Job field must be filled";
            status &= false;
        }
        status &= true;
    }
    
    private Airport checkHub(String hub, EntityManager em)
    {
        Airport hubEntity = null;
        if (hub == null || hub.isEmpty())
        {
            errorMessage = "Hub field must be filled";
            status &= false;
        }
        else
        {
            try
            {
                hubEntity = (Airport) em.createNamedQuery("Airport.findByIcao").setParameter("icao", hub).getSingleResult();            
            }
            catch (Exception ex) {}
            if (hubEntity == null)
            {
                errorMessage = "Problem encountered : please retry later";
                status &= false;
            }
            status &= true;
        }
        return (hubEntity);
    }
    
    private Job checkSalary(String salary, EntityManager em, String jobId)
    {
        Job jobEntity = null;
        if (salary == null || salary.isEmpty())
        {
            errorMessage = "Salary field must be filled";
            status &= false;
        }
        else if (isNumeric(salary) == false)
        {
            errorMessage = "Salary must be numeric";
            status &= false;
        }
        else
        {
            float salaryFloat = Float.parseFloat(salary);
            try
            {
                jobEntity = (Job) em.createNamedQuery("Job.findById").setParameter("id", Integer.parseInt(jobId)).getSingleResult();            
            }
            catch (Exception ex) {}
            if (jobEntity == null)
            {
                errorMessage = "Problem encountered : please retry later";
                status &= false;
            }
            else if (jobEntity.getMinSalary() > salaryFloat || salaryFloat > jobEntity.getMaxSalary())
            {
                errorMessage = "Salary for " + jobEntity.getJobName() + " is between " + jobEntity.getMinSalary() + " and " + jobEntity.getMaxSalary();
                status &= false;
            }
            status &= true;
        }
        return (jobEntity);
    }
    
    private Airport checkLocalisation(String localisation, EntityManager em)
    {
        Airport positionEntity = null;
        if (localisation == null || localisation.isEmpty())
        {
            errorMessage = "Localisation field must be filled";
            status &= false;
        }
        else
        {
            try
            {
                positionEntity = (Airport) em.createNamedQuery("Airport.findByIcao").setParameter("icao", localisation).getSingleResult();            
            }
            catch (Exception ex) {}
            if (positionEntity == null)
            {
                errorMessage = "Problem encountered : please retry later";
                status &= false;
            }
            status &= true;
        }
        return (positionEntity);
    }
    
    private Roles checkRole(String accountType, EntityManager em, int jobId)
    {
        // ACCOUNTTYPE HERE = ROLE !!
        int accountTypeInt;
        // Datas for commit
        if ((jobId == 1 && (accountType == null || accountType.isEmpty())) || jobId == 0)
            accountTypeInt = 2;
        else
            accountTypeInt = Integer.parseInt(accountType);
        Roles roleEntity = null;
        try
        {
            roleEntity = (Roles) em.createNamedQuery("Roles.findById").setParameter("id", accountTypeInt).getSingleResult();
        }
        catch (Exception ex) {}
        if (roleEntity == null)
        {
            errorMessage = "Problem encountered : please retry later";
            status &= false;
        }
        status &= true;
        return (roleEntity);
    }
    
    private AccountType checkAccountType(EntityManager em)
    {
        AccountType accountTypeEntity = null;
        try
        {
            accountTypeEntity = (AccountType) em.createNamedQuery("AccountType.findById").setParameter("id", 1).getSingleResult();
        }
        catch (Exception ex) {}
        if (accountTypeEntity == null)
        {
            errorMessage = "Problem encountered : please retry later";
            status &= false;
        }
        return (accountTypeEntity);
    }
    
    private boolean isNumeric(String str)
    {
        float f;
        try
        {
            f = Float.parseFloat(str);
            return (true);
        }
        catch (Exception ex) {}
        return (false);
    }
    
    private void jsonWriting(PrintWriter out, boolean status)
    {
        JSONObject json = new JSONObject();
        if (status == false)
        {
            json.put("result", new JSONString("0"));
            json.put("error", new JSONString(errorMessage));
        }
        else
        {
            json.put("result", new JSONString("1"));
        }
        try
        {
            out.print(json.toJSON());
        }
        finally
        {            
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
