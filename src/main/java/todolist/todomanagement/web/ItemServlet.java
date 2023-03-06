package todolist.todomanagement.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import todolist.todomanagement.dao.ItemDAO;
import todolist.todomanagement.model.Item;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/")
public class ItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ItemDAO itemDAO;
       
    public ItemServlet() {
      this.itemDAO = new ItemDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) {
        case "/new":
            showNewForm(request, response);
            break;
        case "/insert":
            try {
				insertItem(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            break;
        case "/delete":
        	try {
				deleteItem(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            break;
        case "/edit":
            showEditForm(request, response);
            break;
        case "/update":
        	try {
				updateItem(request, response);
			} catch (ServletException | IOException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            break;
        default:
            listItem(request, response);
            break;
		}

	}
	
	private void listItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Item> listItem = itemDAO.selectAllItem();
		request.setAttribute("listItem", listItem);
		RequestDispatcher dispatcher = request.getRequestDispatcher("item-list.jsp");
		dispatcher.forward(request, response);
		
	}

	
	private void updateItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		int id = Integer.parseInt(request.getParameter("id"));
		String description = request.getParameter("description");
		
		Item newItem = new Item(id, description);
		itemDAO.updateItem(newItem);
		response.sendRedirect("list");
		
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
		dispatcher.forward(request, response);
		
	}
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Item existingItem = itemDAO.selectItem(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("item-form.jsp");
		request.setAttribute("item", existingItem);
		dispatcher.forward(request, response);
		
	}
	
	private void insertItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String discription = request.getParameter("description");
		Item newItem = new Item(discription);
		itemDAO.insertItem(newItem);
		response.sendRedirect("list");
		
	}
	
	private void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int id = Integer.parseInt(request.getParameter("id"));
		itemDAO.deleteItem(id);
		response.sendRedirect("list");
		
	}

	
}
