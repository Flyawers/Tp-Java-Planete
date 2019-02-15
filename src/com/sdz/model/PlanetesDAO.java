package com.sdz.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//Classe DAO de la table Planetes. Elle permet de r��crire les m�thodes abstraites du "DAO" en fonction des fonctionnalit�s sur les Plan�tes(ajout, suppression, update, find)
public class PlanetesDAO extends DAO<PlanetesPojo>
	{
		//Constructeur de la classe, avec un appel � la connexion BDD
		public PlanetesDAO(Connection conn) 
			{
				super(conn);	
			}
		
		//R��criture de la m�thode create
		public PlanetesPojo create(PlanetesPojo obj)
			{
				try 
				  {	 
						//Vu que nous sommes sous postgres, nous allons chercher manuellement
						//la prochaine valeur de la s�quence correspondant � l'id de notre table
						ResultSet result = this	.connect
			                                  .createStatement(
			                                  		ResultSet.TYPE_SCROLL_INSENSITIVE, 
			                                  		ResultSet.CONCUR_UPDATABLE
			                                  ).executeQuery(
			                                  		"SELECT NEXTVAL('planetes_id_seq') as id"
			                                  );
						//Si le r�sultat est bon on peut cr�er une nouvelle entr�e Plan�ete dans la BDD
						if(result.first())
							{
								@SuppressWarnings("unused")
								long id = result.getLong("id");
							//Requ�te pr�par�e
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
		//R��criture de la m�thode delete
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
		//R��criture de la m�thode update
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
		
		//R��criture de la m�thode find
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
		//M�thode autog�n�r�e de JAVA(Eclispe) permettant de travailler sur des Long 
		public PlanetesPojo find(long l) 
			{
				// TODO Auto-generated method stub
				return null;
			}
			
	}
		
