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

/*Classe qui va afficher l'essentiel du programme. Elle permet l'int�raction avec diff�rentes boutons et menus(Ajout, Supprimer etc...)
 * Un menu principal avec la possibilit� de consulter, supprimer et ajouter une plan�te, de consulter les syst�mes et les types
 * Un Tableau de consultation des diff�rentes entit�s, plan�tes, syst�mes, types
 * Cette classe se charge �galement des actions Menus, clic droit(Supprimer) + raccourcis clavier
 * Elle appelle aussi les deux frames d'ajout et de modification de plan�tes
*/
public class Fenetre extends JFrame
	{
		//Menu pour les diff�rentes fonctionnalit�s
	
		//Mise en place de la barre de Menu en haut de l'�cran
		private JMenuBar menuBar = new JMenuBar();
		
		//Mise en place des diff�rentes noms de menus
		private JMenu planeteMenu = new JMenu("Plan�tes");
		private JMenu systemMenu = new JMenu("Syst�mes");
		private JMenu typeMenu = new JMenu("Types");
		
		//Mise en place des fonctionnalit�s pour le menu Plan�te
		public JMenuItem voirPlanete = new JMenuItem("Voir");
		private JMenuItem ajouterPlanete = new JMenuItem("Ajouter");
		private JMenuItem updatePlanete = new JMenuItem("Modifier");
		
		//Mise en place des fonctionnalit�s pour les menus Systemes et Types
		private JMenuItem voirSysteme = new JMenuItem("Voir");
		private JMenuItem voirType = new JMenuItem("Voir");
		
		//Cr�ation d'un JPanel principal
		private JPanel tableau = new JPanel(new BorderLayout());	
		
		//Cr�ation d'un Jlabel pour le lancement du programme, souhaitant la Bienvenue et une petite aide
		private JLabel labou = new JLabel("<html><li><font color = green><b>Bonjour<b/></font> et Bienvenue dans le monde fantastique de la cr�ation de plan�tes</li>."
										+ "<li><font color = green> Cliquer sur le menu pour consulter les donn�es Plan�tes(ou appuyer sur ctrl+W)</font></li></html>");

		//Mise en place d'un menu contextuel(click droit) avec sa fonctionnalit� de suppression d'une entr�e
		private JPopupMenu menuCont = new JPopupMenu();
		private JMenuItem delete = new JMenuItem("Supprimer");
		
		/*Mise sous variables des requ�tes SQL Importantes pour l'affichage du tableau de consultation de la base de donn�es
		 * Requ�tes pour afficher toutes les plan�tes
		 * Requ�tes pour afficher tous les syst�mes
		 * Requ�tes pour afficher tous les types
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
				//Donne une dimension � la fenetre principale du programme
				setSize(900, 600);
				//Donne un nom � la fen�tre principale du programme
				setTitle("Plan�tes");
				//Donne une position de lancement � la fen�tre principale du programme
				setLocationRelativeTo(null);
				//Donne la possibilit� de fermer le programme gr�ce � la croix d'arr�t en haut de la Fen�tre
				setDefaultCloseOperation(EXIT_ON_CLOSE);
				
				//Initialisation des composants du menu principal
				initMenuBar();
				//Donne une taille, une police , et une position au label qui souhaite la Bienvenue
				labou.setFont(new Font("Verdana", 5, 35));
				//Ajout du Label au Panel Principal 
				tableau.add(labou);
				//Donne une couleur de fond (Gris Fonc�) au Panel principal
				tableau.setBackground(Color.DARK_GRAY);
				//Ajout du Panel au container principal
				getContentPane().add(tableau);
			}  
		
		//Mise en place des diff�rents items et fonctionnalit�s du Menu principal dans une m�thode
		private void initMenuBar()
			{
				//Ajout des menus Plan�tes, Sys�mes et Types au menu
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
				
				//Ajout de l'action pour consulter les diff�rentes Plan�tes
				voirPlanete.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
							{
								//Appel de la m�thode de cr�ation de tableau, avec la requete correspondante
								initRequest(sqlPlanete);
								//Echo pour tester le fonctionnement du menu
								System.out.println("Hey je suis le menu voir Plan�te");
							}
					});
				
				//Ajout de l'action pour consulter les diff�rents Syst�mes
				voirSysteme.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
							{
								//Appel de la m�thode de cr�ation de tableau, avec la requete correspondante
								initRequest(sqlSysteme);
								//Echo pour tester le fonctionnement du menu
								System.out.println("Hey je suis le menu voir Syst�me");
							}
					});
				
				//Ajout de l'action pour consulter les diff�rents Types
				voirType.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
							{
								initRequest(sqlType);
								//Echo pour tester le fonctionnement du menu
								System.out.println("Hey je suis le menu voir Type de Plan�te");
							}
					});
				
				//Ajout de l'action pour ajouter une Plan�te � la base de donn�es
				ajouterPlanete.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent event)
							{
								//Appel de la fenetre Ajouter Planete
								FrameAjoutPlanete framou = new FrameAjoutPlanete();
								//Echo pour tester le fonctionnement du menu
								System.out.println("Hey je suis le menu ajout Plan�te");
							}
					});
				
				//Ajout de l'action pour modifier une Plan�te � la base de donn�es
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
		
		//M�thode de g�n�ration d'un tableau en fonction d'une requ�te SQL
		public void initRequest(String sql) 
			{	
				try 
					{
				      Statement state = SdzConnection.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				      //On ex�cute la requ�te
				      ResultSet result = state.executeQuery(sql);
				      //Temps d'ex�cution
		
				      //On r�cup�re les meta afin de r�cup�rer le nom des colonnes
				      ResultSetMetaData meta = result.getMetaData();
				      //On initialise un tableau d'Object pour les en-t�tes du tableau
				      Object[] column = new Object[meta.getColumnCount()];
		
				      for(int i = 1 ; i <= meta.getColumnCount(); i++)
				        column[i-1] = meta.getColumnName(i);
		
				      //Petite manipulation pour obtenir le nombre de lignes
				      result.last();//on se place sur la derni�re ligne du tableau
				      int rowCount = result.getRow();//on stock la methode pour recuperer les lignes dans une variable int	
				      Object[][] data = new Object[result.getRow()][meta.getColumnCount()]; //Puis on la charge pour initialiser le tableau des resultats
		
				      //On revient au d�part
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
		
				      //On enl�ve le contenu de notre conteneur
				      tableau.removeAll();
				      JTable tabou = new JTable(data, column);
				      //On y ajoute un JTable
				      tableau.add(new JScrollPane(tabou), BorderLayout.NORTH);
				      //On force la mise � jour de l'affichage
				      tableau.revalidate();	
				      
				      //Action sur JTable afin de pouvoir ouvrir un menu contextuel, en fonction de la ligne selectionn�e
				      tabou.addMouseListener(new MouseAdapter()
					  		{ 
					  			public void mouseReleased(MouseEvent e)
							  		{
							  			System.out.println("Ligne n� "+ tabou.rowAtPoint(e.getPoint())+ " selectionn�e.");
							  			if(e.isPopupTrigger())
								  			{
								  				menuCont.add(delete);
								  				menuCont.show(tableau, e.getX(), e.getY());
								  			}		
							  		}
					  		});
					      
				   
				      //Ajout de l'action supprimer une plan�te au menu contextuel
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
