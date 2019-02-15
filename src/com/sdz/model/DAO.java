package com.sdz.model;

import java.sql.Connection;

//Classe abstraite Principale du DAO, créant les méthodes abstraites qui seront réécrites par les DAO de chaque classe de la Base de données (ici par PlanetesDAO)
public abstract class DAO<T> 
{
	protected Connection connect = null;
	
	//Constructeur de la classe abstraite
	public DAO(Connection conn)
	{
		this.connect = conn;
	}
	
	  /**
	  * Méthode de création
	  * @param obj
	  * @return boolean 
	  */
	//Méthode abstraite pour créer une nouvelle entrée dans la base de données
	 public abstract T create(T obj);

	  /**
	  * Méthode pour effacer
	  * @param obj
	  *
	  */
	 //Méthode abstraite pour supprimer une entrée dans la base de données
	 public abstract void delete(T obj);

	  /**
	  * Méthode de mise à jour
	  * @param obj
	  * @return boolean
	  */
	//Méthode abstraite pour modifier une entrée dans la base de données
	 public abstract T update(T obj);

	  /**
	  * Méthode de recherche des informations
	  * @param l
	  * @return T
	  */
	//Méthode abstraite pour rechercher une entrée dans la base de données
	  public abstract T find(long l);
}


