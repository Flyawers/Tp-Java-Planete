package com.sdz.vue;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.sdz.model.DAO;
import com.sdz.model.PlanetesDAO;
import com.sdz.model.PlanetesPojo;
import com.sdz.observer.SdzConnection;

/*Fenetre dédiée à la modification d'une planète dans la base de données
 * Elle est appelée dans Fenetre lorsque l'on choisi de modifier une planète
 */
public class FrameModifPlanete extends JFrame
	{
		/*Création des différents composants pour modifier une planètes
		 * Des Labels qui vont accueillir les informations
		 * Des JTextFiels pour rentrer les données
		 * Une comboBox dont le contenu sera généré par la Base de données
		 * des Variables d'appels (id, type, systemPlt)
		 */
		private JLabel idPlanete, nomPlanete, labelSysteme, labelType, labelTemperature,labelTemperature2, labelDiametre, labelDiametre2;
		private JRadioButton telluriqueRadio, gazeuseRadio;
		private JComboBox<String> systemeCombo;
		private JTextField idPlt, nomPlt, tempPlt, diamPlt;
		private int id = 0;
		private int type = 0;
		private int systemePlt = 0;
		
		//Appel de la classe connexion afin de pouvoir se connecter, mais aussi mettre à jour sa base de données pour l'ajout, la suppression ou la modification
		SdzConnection conn = new SdzConnection();
		//Création d'une instance DAO afin d'utiliser les composants PlanetesDAO
		DAO<PlanetesPojo> planeteDAO = new PlanetesDAO(SdzConnection.getInstance());
		
		//Constructeur de la classe
		public FrameModifPlanete()
			{
				this.setTitle("Modifier une Planète");
				this.setSize(800, 270);
				this.setLocationRelativeTo(null);
				this.setResizable(false);
				this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				this.setUndecorated(false);
				this.initComponent();
				
				//Ajout d'une action sur les JRadioButton Tellurique et Gazeuse
				telluriqueRadio.addActionListener(new StateListener());
			    gazeuseRadio.addActionListener(new StateListener());
			    
			    //Ajout d'une action sur la JComboBox
			    systemeCombo.addActionListener(new ItemAction());
			    
			    this.setVisible(true);
			}
			
		//Mise en méthode de l'initialisation des différents composants de notre page modifier
		private void initComponent()
			{
				//Id de la planète
				JPanel panId = new JPanel();
				panId.setBackground(Color.WHITE);
				panId.setPreferredSize(new Dimension(200, 60));
				idPlt = new JTextField();
				idPlt.setPreferredSize(new Dimension(100, 25));
				panId.setBorder(BorderFactory.createTitledBorder("Id de la Planète"));
				idPlanete = new JLabel("Saisir l'ID :");
				panId.add(idPlanete);
				panId.add(idPlt);
				
				//le nom de la planète
				JPanel panNom = new JPanel();
				panNom.setBackground(Color.WHITE);
				panNom.setPreferredSize(new Dimension(220, 60));
				nomPlt = new JTextField();
				nomPlt.setPreferredSize(new Dimension(100, 25));
				panNom.setBorder(BorderFactory.createTitledBorder("Nom de la Planète"));
				nomPlanete = new JLabel("Saisir un nom :");
				panNom.add(nomPlanete);
				panNom.add(nomPlt);
				//
				setNomPlt(nomPlt);
				
				//Le Système de la planète
				JPanel panSysteme = new JPanel();
				panSysteme.setBackground(Color.WHITE);
				panSysteme.setPreferredSize(new Dimension(250, 60));
				panSysteme.setBorder(BorderFactory.createTitledBorder("Système de la Planète"));
				systemeCombo = new JComboBox<String>();
				//Methode qui remplit la ComboBox des noms systeme de la BDD
				rempliBox();
				//
				setSystemeCombo(systemeCombo);
		    
				labelSysteme = new JLabel("Système : ");
				panSysteme.add(labelSysteme);
				panSysteme.add(systemeCombo);
				
				//Type de la planète
				JPanel panType = new JPanel();
				panType.setBackground(Color.WHITE);
				panType.setBorder(BorderFactory.createTitledBorder("Type de Planète"));
				panType.setPreferredSize(new Dimension(220, 60));
				telluriqueRadio = new JRadioButton("Tellurique");
				gazeuseRadio = new JRadioButton("Gazeuse");
				ButtonGroup bg = new ButtonGroup();
				bg.add(telluriqueRadio);
				bg.add(gazeuseRadio);
				panType.add(telluriqueRadio);
				panType.add(gazeuseRadio);
				//
				setTelluriqueRadio(telluriqueRadio);
				setGazeuseRadio(gazeuseRadio);
				
				//Température de la Planète
				JPanel panTemp = new JPanel();
				panTemp.setBackground(Color.WHITE);
				panTemp.setPreferredSize(new Dimension(260, 60));
				panTemp.setBorder(BorderFactory.createTitledBorder("Température de la Planète"));
				labelTemperature = new JLabel("Température :");
				labelTemperature2 = new JLabel(" °C");
				tempPlt = new JTextField();
				tempPlt.setPreferredSize(new Dimension(110, 25));
				panTemp.add(labelTemperature);
				panTemp.add(tempPlt);
				panTemp.add(labelTemperature2);
				//
				setTempPlt(tempPlt);
				
				//Diamétre de la Planète
				JPanel panDiam = new JPanel();
				panDiam.setBackground(Color.WHITE);
				panDiam.setPreferredSize(new Dimension(220, 60));
				panDiam.setBorder(BorderFactory.createTitledBorder("Diamétre de la Planète"));
				labelDiametre = new JLabel("Diamétre");
				labelDiametre2 = new JLabel(" km");
				diamPlt = new JTextField();
				diamPlt.setPreferredSize(new Dimension(90, 25));
				panDiam.add(labelDiametre);
				panDiam.add(diamPlt);
				panDiam.add(labelDiametre2);
				//
				setDiamPlt(diamPlt);
				
				//Ajout des différents composants au container JFrame
				JPanel content = new JPanel();
				content.setBackground(Color.WHITE);
				content.add(panId);
				content.add(panNom);
				content.add(panSysteme);
				content.add(panDiam);
				content.add(panTemp);
				content.add(panType);
				
				//Ajout de bouton dans un Panel 
				JPanel control = new JPanel();
				control.setBackground(Color.DARK_GRAY);
				JButton modifBouton = new JButton("Modifier");
				JButton annulerBouton = new JButton("Annuler");
				
				//Ajout des boutons au Panel control
				control.add(modifBouton);
				control.add(annulerBouton);
				
				//Ajout du contenu au JFrame
				this.getContentPane().add(content, BorderLayout.CENTER);
				this.getContentPane().add(control, BorderLayout.SOUTH);
				
				//Action des boutons Annuler et Ajout
				annulerBouton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent arg0)
						{
							setVisible(false);
							System.out.println("Bouton Annuler Planète, marche");
						}
					});
				
				modifBouton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent arg0)
							{
								
								System.out.println("Bouton Ajouter une Planète, marche");
								
								try
									{
									
										int iTempPlt = Integer.parseInt(tempPlt.getText());
										int iDiamPlt = Integer.parseInt(diamPlt.getText());
										int iPltId = Integer.parseInt(idPlt.getText().trim());
									
										PlanetesPojo plt = new PlanetesPojo();
										System.out.println(plt.getNom());
										plt.setId(iPltId);
										plt.setNom(nomPlt.getText());
										plt.setTemperature(iTempPlt);
										plt.setDiametre(iDiamPlt);
										plt.setSystemePlt(systemePlt);
										
						
										//Structure de contrôle pour renvoyer l'id selon le type
										if (telluriqueRadio.isSelected())
											{
												setTypePlt(1);
											}
											
										if (gazeuseRadio.isSelected())
											{
												setTypePlt(2);
											}
										
										plt.setTypePlt(type);
										plt = planeteDAO.update(plt);
										System.out.println("Planète : id "+id+ " nom : " +nomPlt.getText()+ " a été mise à jour dans la base de données");
									}
								catch (NumberFormatException e)
									{
										JOptionPane.showMessageDialog(modifBouton, e.getMessage(), "SOS ERREUR ! ", JOptionPane.ERROR_MESSAGE);
									}	
							}
					});	
			}
		
		//Méthode pour récuperer les données de la table systeme(systeme_nom) et les initialiser dans la ComboBox
		public void rempliBox()
			{
				String sqlReq = "SELECT systeme_nom FROM systeme ORDER BY systeme_id";
				
				try
					{
						PreparedStatement prepare = SdzConnection.getInstance().prepareStatement(sqlReq);
						ResultSet res = prepare.executeQuery();
						
						while(res.next())
						{
							String nomSysteme = res.getString("systeme_nom").toString();
							systemeCombo.addItem(nomSysteme);
						}
						res.close();
						prepare.close();
					}
				catch (SQLException e)
					{
						e.printStackTrace();
					}	
			}
		
		//GETTER SETTER
		public JTextField getId()
			{
				return idPlt;
			}
		public void setId(JTextField idPlt)
			{
				this.idPlt = idPlt;
			}
		public JTextField getNomPlt()
			{
				return nomPlt;
			}
		
		public void setNomPlt(JTextField nomPlt)
			{
				this.nomPlt = nomPlt;
			}
		
		public JTextField getTempPlt()
			{
				return tempPlt;
			}
		
		public void setTempPlt(JTextField tempPlt)
			{
				this.tempPlt = tempPlt;
			}
		
		public JTextField getDiamPlt()
			{
				return diamPlt;
			}
		
		public void setDiamPlt(JTextField diamPlt)
			{
				this.diamPlt = diamPlt;
			}
			
		public JRadioButton getTelluriqueRadio()
			{
				return telluriqueRadio;
			}
		
		public void setTelluriqueRadio(JRadioButton telluriqueRadio)
			{
				this.telluriqueRadio = telluriqueRadio;
			}
		
		public JRadioButton getGazeuseRadio()
			{
				return gazeuseRadio;
			}
		
		public void setGazeuseRadio(JRadioButton gazeuseRadio)
			{
				this.gazeuseRadio = gazeuseRadio;
			}
		
		public JComboBox<String> getSystemeCombo()
			{
				return systemeCombo;
			}
		
		public void setSystemeCombo(JComboBox<String> systemeCombo)
			{
				this.systemeCombo = systemeCombo;
			}
		
		public int getIdPlt()
			{
				return id;
			}
		
		public void setIdPlt(int id)
			{
				this.id = id;
			}
		
		public int getTypepPlt()
			{
				return type;
			}
		
		public void setTypePlt(int type)
			{
				this.type = type;
			}
		
		public int getSystemePlt()
			{
				return systemePlt;
			}
		
		public void setSystemePlt(int systemePlt)
			{
				this.systemePlt = systemePlt;
			}
		
		public JLabel getLabelType() 
			{
				return labelType;
			}
	
		public void setLabelType(JLabel labelType) 
			{
				this.labelType = labelType;
			}
	
		//Classe Action sur les JRadioButton
		class StateListener implements ActionListener
			{
			    public void actionPerformed(ActionEvent e)
				    {
				      System.out.println("source : " + telluriqueRadio.getText() + " - état : " + telluriqueRadio.isSelected());
				      System.out.println("source : " + gazeuseRadio.getText() + " - état : " + gazeuseRadio.isSelected()); 
				    }
			  }
		
		//Classe Action sur JComBoBox, pour savoir sur quel id on se trouve
		  class ItemAction implements ActionListener
			  {
				    public void actionPerformed(ActionEvent e) 
					    {
					      System.out.println("ActionListener : action sur " + systemeCombo.getSelectedItem()); 
					      
					      if(systemeCombo.getSelectedItem().equals("Alpha Centauris"))
						      {
						    	  systemePlt = 1;
						      }
						      
					      if(systemeCombo.getSelectedItem().equals("HD 98800"))
						      {
						    	  systemePlt = 2;
						      }
						      
					      if(systemeCombo.getSelectedItem().equals("Kepler 758"))
						      {
						    	  systemePlt = 3;
						      }
					      if(systemeCombo.getSelectedItem().equals("Xi Ursae"))
						      {
						    	  systemePlt = 4;
						      }
					      if(systemeCombo.getSelectedItem().equals("2M1207"))
						      {
						    	  systemePlt = 5;
						      }
					      if(systemeCombo.getSelectedItem().equals("Keppler-11"))
						      {
						    	  systemePlt = 6;
						      }
					      if(systemeCombo.getSelectedItem().equals("TRAPPIST-1"))
						      {
						    	  systemePlt = 7;
						      }
					      if(systemeCombo.getSelectedItem().equals("WASP-47"))
						      {
						    	  systemePlt = 8;
						      }
					      if(systemeCombo.getSelectedItem().equals("Copernic-85"))
						      {
						    	  systemePlt = 9;
						      }
					      if(systemeCombo.getSelectedItem().equals("Helvetios-51"))
						      {
						    	  systemePlt = 10;
						      }
					      if(systemeCombo.getSelectedItem().equals("Titawin"))
						      {
						    	  systemePlt = 11;
						      }
					      if(systemeCombo.getSelectedItem().equals("Liche-B1257"))
						      {
						    	  systemePlt = 12;
						      }
					      if(systemeCombo.getSelectedItem().equals("16 Cygni"))
						      {
						    	  systemePlt = 13;
						      }
					      if(systemeCombo.getSelectedItem().equals("Brutalis-B854"))																				
						      {
						    	  systemePlt = 14;
						      }
					      if(systemeCombo.getSelectedItem().equals("Scorpi-18"))
						      {
						    	  systemePlt = 15;
						      }
					
					      System.out.println("L'id du système selectionné est : " +systemePlt);
					    }               
			  }
	}
	
	