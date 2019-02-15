import com.sdz.vue.Fenetre;


//Classe Principale servant à lancer l'application. Elle permet aussi de rendre visible la fenêtre principale de l'application
public class Main 
	{
		public static void main(String[] args) 
			{
				//DAO<PlanetesPojo> planetesDAO = new PlanetesDAO(SdzConnection.getInstance());
				//PlanetesPojo plt = new PlanetesPojo();
			
				Fenetre fen = new Fenetre();
				fen.setVisible(true);
				
				
				//FrameAjoutPlanete framou = new FrameAjoutPlanete();
				/*
				plt.setId(7);
				plt.setNom("Huméhius-Sigima");
				plt.setTemperature(-40);
				plt.setDiametre(15005);
				plt.setType(1);
				plt.setSysteme(5);
			
				plt = planetesDAO.create(plt);
				*/
				
				/*
				plt.setId(10);
				planetesDAO.delete(plt);
				*/
				
				/*
				plt.setId(6);
				plt.setNom("Ugenis-772");
				planetesDAO.update(plt);
				*/
				//plt.setId(1);
				//System.out.println("L'id : "+plt.getId()+ " de la planète " + plt.getNom()+ " du systeme " +plt.getSysteme());
			}
	
	}
