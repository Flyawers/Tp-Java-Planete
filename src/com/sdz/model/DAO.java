package com.sdz.model;

import java.sql.Connection;

//Classe abstraite Principale du DAO, cr�ant les m�thodes abstraites qui seront r��crites par les DAO de chaque classe de la Base de donn�es (ici par PlanetesDAO)
public abstract class DAO<T> 
{
	protected Connection connect = null;
	
	//Constructeur de la classe abstraite
	public DAO(Connection conn)
	{
		this.connect = conn;
	}
	
	  /**
	  * M�thode de cr�ation
	  * @param obj
	  * @return boolean 
	  */
	//M�thode abstraite pour cr�er une nouvelle entr�e dans la base de donn�es
	 public abstract T create(T obj);

	  /**
	  * M�thode pour effacer
	  * @param obj
	  *
	  */
	 //M�thode abstraite pour supprimer une entr�e dans la base de donn�es
	 public abstract void delete(T obj);

	  /**
	  * M�thode de mise � jour
	  * @param obj
	  * @return boolean
	  */
	//M�thode abstraite pour modifier une entr�e dans la base de donn�es
	 public abstract T update(T obj);

	  /**
	  * M�thode de recherche des informations
	  * @param l
	  * @return T
	  */
	//M�thode abstraite pour rechercher une entr�e dans la base de donn�es
	  public abstract T find(long l);
}


