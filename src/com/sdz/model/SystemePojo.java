package com.sdz.model;

/*Classe Pojo pour les Syst�mes, Elle permet de cr�er une entit� repr�sentant comment se d�finit un syst�me.
 * Avec un ID, un nom
*/
public class SystemePojo 
{
	//Attributs d'un syst�me
	private static int id = 0;
	private String nom = "";
	
	@SuppressWarnings("static-access")
	//Constructeur de la classe
	public SystemePojo(int id, String nom)
	{
		this.id = id;
		this.nom = nom;
	}
	
	//Constructeur par d�faut
	public SystemePojo()
	{
	}
	
	//GETTERS
	public static int getIdSysteme()
	{
		return id;
	}
	
	public String getNomSysteme()
	{
		return nom;
	}
	
	//SETTERS
	public void setIdSysteme(int id)
	{
		this.id = id;
	}
	
	public void setNomSysteme(String nom)
	{
		this.nom = nom;
	}
}
