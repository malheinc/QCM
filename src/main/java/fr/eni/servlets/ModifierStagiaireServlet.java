package fr.eni.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.bean.Promotion;
import fr.eni.bean.Utilisateur;
import fr.eni.services.PromotionService;
import fr.eni.services.UtilisateurService;

/**
 * Servlet implementation class ModifierStagiaireServlet
 */
@WebServlet("/ModifierStagiaireServlet")
public class ModifierStagiaireServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifierStagiaireServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!request.isUserInRole("2")) {
			response.sendRedirect("/");
		}
		int id = Integer.parseInt(request.getParameter("id"));
		Utilisateur stagiaire = null;
		List<Promotion> listPromo = null;
		try {
			listPromo = PromotionService.importerListe();
			stagiaire = UtilisateurService.rechercheParId(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute("stagiaire", stagiaire);
		request.setAttribute("promotions", listPromo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("modifStagiaire.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (!request.isUserInRole("2")) {
			response.sendRedirect("/");
		}
		UtilisateurService service = new UtilisateurService();		
		Utilisateur stagiaire = new Utilisateur();
		stagiaire.setId_user(Integer.parseInt(request.getParameter("id")));
		stagiaire.setNom(request.getParameter("nom"));
		stagiaire.setPrenom(request.getParameter("prenom"));
		stagiaire.setEmail(request.getParameter("email"));
		stagiaire.setId_promo(Integer.parseInt(request.getParameter("promo")));
		stagiaire.setId_statut(1);
		stagiaire.setEst_archive(false);
		stagiaire.setLogin(stagiaire.getPrenom().substring(0, 1).toLowerCase()+stagiaire.getNom().toLowerCase());
		stagiaire.setPassword(stagiaire.getPrenom().substring(0, 3).toLowerCase());
		
		try {
			service.update(stagiaire);
			response.sendRedirect("stagiaires");
		} catch (Exception e) {
			doGet(request, response);
		}
	}
}