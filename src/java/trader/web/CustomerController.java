/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trader.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trader.BrokerException;
import trader.BrokerModel;
import trader.BrokerModelImpl;
import trader.Customer;

/**
 *
 * @author
 */
@WebServlet(name = "CustomerController", urlPatterns = {"/CustomerController","/AllCustomers"})
public class CustomerController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = "";
        String name = "";
        String address = "";
        String submit = "";
        
        // Recuperamos la UNICA (Singleton) instancia de BrokerModelImpl
        BrokerModel model = BrokerModelImpl.getInstance();
        
        //Usamos el objeto HttpServletRequest xa obtener la ruta q se usa para invocar al servlet
        String path = request.getServletPath();
        
        // Recuperamoslos datos de la solicitud "customer"
        Customer cust = (Customer) request.getAttribute("customer");
        Customer current;
        // Si estamos en la version controlador del Servlet 
        if  (path.equals("/CustomerController")) {
            // Si el boton pulsado es el de obtener los datos de un cliente
            if (request.getAttribute("submit").equals("Get Customer")){
                //comprobamos que los datos del compo id no es null ni "cadena vacia"
                if (cust.getId() != null && !cust.getId().equals("")) {
                    //como genera una excepción hay que tray-cachearlo
                    try {
                        //necesitamos un objeto Customer donde recoger el elemento buscado por ID en la lista de Customers
                        current = model.getCustomer(cust.getId());
                        // Nos traemos todos los datos del Customer encontrado a las variables locales (de instancia)
                        id = current.getId();
                        name =current.getName();
                        address = current.getAddr();
                    }catch (BrokerException ex) {
                        Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                // Pero si el botón pulsado del formulario es Actualizar un cliente concreto
            }else if  (request.getAttribute("submit").equals("Update Customer")) {
                //comprobamos que el ID no es nulo ni "cadena vacía"
                if (cust.getId() != null && !cust.getId().equals("")) {
                    try {
                        model.updateCustomer(cust);
                    } catch (BrokerException ex) {
                        Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }  
            }else if (request.getAttribute("submit").equals("Add Customer")) {
                   try {
                        model.addCustomer(cust);
                    } catch (BrokerException ex) {
                        Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }else if (request.getAttribute("submit").equals("Delete Customer")) {
                   try {
                        model.deleteCustomer(cust);
                    } catch (BrokerException ex) {
                        Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
                    }
           }
            RequestDispatcher dispatcher = request.getRequestDispatcher("CustomerDetails");
            dispatcher.forward(request, response);
        }
        
        if  (path.equals("/AllCustomers")) {
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
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
     * Handles the HTTP <code>POST</code> method.
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
    }// </editor-fold
}
