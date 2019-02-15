package com.sdz.vue;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import com.sdz.model.DAO;
import com.sdz.model.PlanetesDAO;
import com.sdz.model.PlanetesPojo;
import com.sdz.observer.SdzConnection;

/*Classe qui va afficher l'essentiel du programme. Elle permet l'intéraction avec différentes boutons et menus(Ajout, Supprimer etc...)
 * Un menu principal avec la possibilité de consulter, supprimer et ajouter une planète, de consulter les systèmes et les types
 * Un Tableau de consultation des différentes entités, planètes, systèmes, types
 * Cette classe se charge également des actions Menus, clic droit(Supprimer) + raccourcis clavier
 * Elle appelle aussi les deux frames d'ajout et de modification de planètes
*/
public class Fenetre extends JFrame
	{
		//Menu pour les différentes fonctionnalités
	
		//Mise en place de la barre de Menu en haut de l'écran
		private JMenuBar menuBar = new JMenuBar();
		
		//Mise en place des différentes noms de menus
		private JMenu planeteMenu = new JMenu("Planètes");
		private JMenu systemMenu = new JMenu("Systèmes");
		private JMenu typeMenu = new JMenu("Types");
		
		//Mise en place des fonctionnalités pour le menu Planète
		public JMenuItem voirPlanete = new JMenuItem("Voir");
		private JMenuItem ajouterPlanete = new JMenuItem("Ajouter");
		private JMenuItem updatePlanete = new JMenuItem("Modifier");
		
		//Mise en place des fonctionnalités pour les menus Systemes et Types
		private JMenuItem voirSysteme = new JMenuItem("Voir");
		private JMenuItem voirType = new JMenuItem("Voir");
		
		//Création d'un JPanel principal
		private JPanel tableau = new JPanel(new BorderLayout());	
		
		//Création d'un Jlabel pour le lancement du programme, souhaitant la Bienvenue et une petite aide
		private JLabel labou = new JLabel("<html><li><font color = green><b>Bonjour<b/></font> et Bienvenue dans le monde fantastique de la création de planètes</li>."
										+ "<li><font color = green> Cliquer sur le menu pour consulter les données Planètes(ou appuyer sur ctrl+W)</font></li></html>");

		//Mise en place d'un menu contextuel(click droit) avec sa fonctionnalité de suppression d'une entrée
		private JPopupMenu menuCont = new JPopupMenu();
		private JMenuItem delete = new JMenuItem("Supprimer");
		
		/*Mise sous variables des requêtes SQL Importantes pour l'affichage du tableau de consultation de la base de données
		 * Requêtes pour afficher toutes les planètes
		 * Requêtes pour afficher tous les systèmes
		 * Requêtes pour afficher tous les types
		 */
		String sqlPlanete = "SELECT plt_id, plt_nom, plt_temperature, plt_diametre, type_nom, systeme_nom\r\n" + 
							"FROM planetes\r\n" + 
							"INNER JOIN systeme\r\n" + 
							"ON planetes.plt_id_systeme = systeme.systeme_id\r\n" + 
							"INNER JOIN type\r\n" + 
							"ON planetes.plt_id_type = type.type_id;";
		String sqlSysteme = "SELECT * FROM systeme ORDER BY systeme_id";
		String sqlType = "SELECT * FROM type ORDER BY type_id";
		
		//Instanciation DAO
		DAO<PlanetesPojo> planeteDAO = new PlanetesDAO(SdzConnection.getInstance());
		
		//Constructeur de la classe
		public Fenetre()
			{
				//Donne une dimension à la fenetre principale du programme
				setSize(900, 600);
				//Donne un nom à la fenêtre principale du programme
				setTitle("Planètes");
				//Donne une position de lancement à la fenêtre principale du programme
				setLocationRelativeTo(null);
				//Donne la possibilité de fermer le programme grâce à la croix d'arrêt en haut de la Fenêtre
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				
				//Initialisation des composants du menu principal
				initMenuBar();
				//Donne une taille, une police , et une position au label qui souhaite la Bienvenue
				labou.setFont(new Font("Verdana", 5, 35));
				//Ajout du Label au Panel Principal 
				tableau.add(labou);
				//Donne une couleur de fond (Gris Foncé) au Panel principal
				tableau.setBackground(Color.DARK_GRAY);
				//Ajout du Panel au container principal
				getContentPane().add(tableau);
			}  
		
		//Mise en place des différents items et fonctionnalités du Menu principal dans une méthode
		private void initMenuBar()
			{
				//Ajout des menus Planètes, Sysèmes et Types au menu
				this.menuBar.add(planeteMenu);
				this.menuBar.add(systemMenu);
				this.menuBar.add(typeMenu);
				//Ajout du sous-menu Voir Planete + une touche raccourci pour l'afficher plus rapidement
				voirPlanete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK));
				this.planeteMenu.add(voirPlanete);
				//Ajout du sous-menu Ajouter Planete + une touche raccourci pour l'afficher plus rapidement
				ajouterPlanete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_DOWN_MASK));
				this.planeteMenu.add(ajouterPlanete);
				//Ajout du sous-menu Voir Systeme + une touche raccourci pour l'afficher plus rapidement
				voirSysteme.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
				this.systemMenu.add(voirSysteme);
				//Ajout du sous-menu Voir Type + une touche raccourci pour l'afficher plus rapidement
				voirType.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK));
				this.typeMenu.add(voirType);
				//Ajout du sous-menu Update Planete + une touche raccourci pour l'afficher plus rapidement
				updatePlanete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_DOWN_MASK));
				this.planeteMenu.add(updatePlanete);
				
				//Ajouter le menuBar dans la fenetre Principale
				this.setJMenuBar(menuBar);
				
				//Ajout de l'action pour consulter les différentes Planètes
				voirPlanete.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
							{
								//Appel de la méthode de création de tableau, avec la requete correspondante
								initRequest(sqlPlanete);
								//Echo pour tester le fonctionnement du menu
								System.out.println("Hey je suis le menu voir Planète");
							}
					});
				
				//Ajout de l'action pour consulter les différents Systèmes
				voirSysteme.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
							{
								//Appel de la méthode de création de tableau, avec la requete correspondante
								initRequest(sqlSysteme);
								//Echo pour tester le fonctionnement du menu
								System.out.println("Hey je suis le menu voir Système");
							}
					});
				
				//Ajout de l'action pour consulter les différents Types
				voirType.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
							{
								initRequest(sqlType);
								//Echo pour tester le fonctionnement du menu
								System.out.println("Hey je suis le menu voir Type de Planète");
							}
					});
				
				//Ajout de l'action pour ajouter une Planète à la base de données
				ajouterPlanete.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
							{
								//Appel de la fenetre Ajouter Planete
								FrameAjoutPlanete framou = new FrameAjoutPlanete();
								//Echo pour tester le fonctionnement du menu
								System.out.println("Hey je suis le menu ajout Planète");
							}
					});
				
				//Ajout de l'action pour modifier une Planète à la base de données
				updatePlanete.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
						{
						
							//Appel de la fenetre Modifier Planete
							FrameModifPlanete framModif = new FrameModifPlanete();
							//Echo pour tester le fonctionnement du menu
							System.out.println("Hey je suis le menu Modifier");
						}
					});
			}
		
		//Méthode de génération d'un tableau en fonction d'une requête SQL
		public void initRequest(String sql) 
			{	
				try 
					{
				      Statement state = SdzConnection.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				      //On exécute la requête
				      ResultSet result = state.executeQuery(sql);
				      //Temps d'exécution
		
				      //On récupère les meta afin de récupérer le nom des colonnes
				      ResultSetMetaData meta = result.getMetaData();
				      //On initialise un tableau d'Object pour les en-têtes du tableau
				      Object[] column = new Object[meta.getColumnCount()];
		
				      for(int i = 1 ; i <= meta.getColumnCount(); i++)
				        column[i-1] = meta.getColumnName(i);
		
				      //Petite manipulation pour obtenir le nombre de lignes
				      result.last();//on se place sur la dernière ligne du tableau
				      int rowCount = result.getRow();//on stock la methode pour recuperer les lignes dans une variable int	
				      Object[][] data = new Object[result.getRow()][meta.getColumnCount()]; //Puis on la charge pour initialiser le tableau des resultats
		
				      //On revient au départ
				      result.beforeFirst();
				      int j = 1;
		
				      //On remplit le tableau d'Object[][]
				      while(result.next())
					      {
					        for(int i = 1 ; i <= meta.getColumnCount(); i++)
					          data[j-1][i-1] = result.getObject(i);
									
					        j++;
					      }
				      //On ferme le tout                                     
				      result.close();
				      state.close();
		
				      //On enlève le contenu de notre conteneur
				      tableau.removeAll();
				      JTable tabou = new JTable(data, column);
				      //On y ajoute un JTable
				      tableau.add(new JScrollPane(tabou), BorderLayout.NORTH);
				      //On force la mise à jour de l'affichage
				      tableau.revalidate();	
				      
				      //Action sur JTable afin de pouvoir ouvrir un menu contextuel, en fonction de la ligne selectionnée
				      tabou.addMouseListener(new MouseAdapter()
					  		{ 
					  			public void mouseReleased(MouseEvent e)
							  		{
							  			System.out.println("Ligne n° "+ tabou.rowAtPoint(e.getPoint())+ " selectionnée.");
							  			if(e.isPopupTrigger())
								  			{
								  				menuCont.add(delete);
								  				menuCont.show(tableau, e.getX(), e.getY());
								  			}		
							  		}
					  		});
					      
				   
				      //Ajout de l'action supprimer une planète au menu contextuel
				      delete.addActionListener(new ActionListener()
					      {
							public void actionPerformed(ActionEvent arg0)
								{
									System.out.println("Menu Contextuel Delete marche");
									DAO<PlanetesPojo> planeteDAO = new PlanetesDAO(SdzConnection.getInstance());
									PlanetesPojo plt = new PlanetesPojo();
									int selected = tabou.getSelectedRow();
									plt.setId(selected);
									planeteDAO.delete(plt);
								}
					      });
					} 
				
				catch (SQLException e) 
					{
				       //Fenetre PopUp en cas d'erreurs	
				    	tableau.removeAll();
				    	tableau.add(new JScrollPane(new JTable()), BorderLayout.NORTH);
				    	tableau.revalidate();
				    	//Affichage de la fenetre d'erreur
				        JOptionPane.showMessageDialog(null, e.getMessage(), "SOS ERREUR ! ", JOptionPane.ERROR_MESSAGE);
				    }
			}
	}
