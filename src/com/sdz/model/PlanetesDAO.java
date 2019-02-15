package com.sdz.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Classe DAO de la table Planetes. Elle permet de réécrire les méthodes abstraites du "DAO" en fonction des fonctionnalités sur les Planètes(ajout, suppression, update, find)
public class PlanetesDAO extends DAO<PlanetesPojo>
	{
		//Constructeur de la classe, avec un appel à la connexion BDD
		public PlanetesDAO(Connection conn) 
			{
				super(conn);	
			}
		
		//Réécriture de la méthode create
		public PlanetesPojo create(PlanetesPojo obj)
			{
				try 
				  {	 
						//Vu que nous sommes sous postgres, nous allons chercher manuellement
						//la prochaine valeur de la séquence correspondant à l'id de notre table
						ResultSet result = this	.connect
			                                  .createStatement(
			                                  		ResultSet.TYPE_SCROLL_INSENSITIVE, 
			                                  		ResultSet.CONCUR_UPDATABLE
			                                  ).executeQuery(
			                                  		"SELECT NEXTVAL('planetes_id_seq') as id"
			                                  );
						//Si le résultat est bon on peut créer une nouvelle entrée Planèete dans la BDD
						if(result.first())
							{
								@SuppressWarnings("unused")
								long id = result.getLong("id");
							//Requête préparée
				  			PreparedStatement prepare = this	  .connect
				                                                  .prepareStatement(
				                                                  "INSERT INTO planetes (plt_id, plt_nom, plt_temperature, plt_diametre, plt_id_type, plt_id_systeme) VALUES(?,?,?,?,?,?)"
				                                                  );
								prepare.setLong(1, obj.getId());
								prepare.setString(2, obj.getNom());
								prepare.setLong(3, obj.getTemperature());
								prepare.setLong(4, obj.getDiametre());
								prepare.setLong(5, obj.getTypePlt());
								prepare.setLong(6, obj.getSystemePlt());
								
								prepare.executeUpdate();
								//obj = this.find(id);	
								
							}
				    } 
				  catch (SQLException e)
					  {
					     e.printStackTrace();
					  }
					  	return obj;
				}
	
		@Override
		//Réécriture de la méthode delete
		public void delete(PlanetesPojo obj) 
			{
				try 
				  {
			          this.connect.createStatement(  ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
			          			  .executeUpdate("DELETE FROM planetes WHERE plt_id = " + obj.getId());
					
				  } 
				 catch (SQLException e) 
				  {
			          e.printStackTrace();
				  }
			}
	
		@Override
		//Réécriture de la méthode update
		public PlanetesPojo update(PlanetesPojo obj) 
			{
				  try 
					  {
							
				          this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)
				                      .executeUpdate("UPDATE planetes SET plt_nom = '" + obj.getNom() + "'"+", plt_temperature = '" +obj.getTemperature() +""+
				              				         "'"+", plt_diametre = '" +obj.getDiametre() +"'"+
				              				         " WHERE plt_id = " + obj.getId());
				          obj = this.find(obj.getId());
					  } 
				  catch (SQLException e) 
					  {
				          e.printStackTrace();
					  }
				  return obj;
			}
		
		//Réécriture de la méthode find
		public PlanetesPojo find(int id) 
			{
				PlanetesPojo poj = new PlanetesPojo();
				try
					{
						ResultSet result = this.connect.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY)
													   .executeQuery("SELECT * FROM planetes WHERE plt_id = " + id);
						if(result.first())
							{
								poj = new PlanetesPojo(id, result.getString("plt_nom"), result.getInt("plt_temperature")
																					  , result.getInt("plt_diametre")
																					  , result.getInt("plt_id_type")
																					  , result.getInt("plt_id_systeme"));
								result = this.connect.createStatement().executeQuery(
										 "SELECT systeme_nom, plt_nom, plt_temperature, plt_diametre, type_nom\r\n" + 
										 "FROM planetes\r\n" + 
										 "INNER JOIN systeme\r\n" + 
										 "ON planetes.plt_id_systeme = systeme.systeme_id\r\n" + 
										 "INNER JOIN type\r\n" + 
										 "ON planetes.plt_id_type = type.type_id;");												
							}
					}
				catch (SQLException e) 
				    {
				      e.printStackTrace();
				    }
				return poj;
				
			}
		@Override
		//Méthode autogénérée de JAVA(Eclispe) permettant de travailler sur des Long 
		public PlanetesPojo find(long l) 
			{
				// TODO Auto-generated method stub
				return null;
			}
			
	}
		
