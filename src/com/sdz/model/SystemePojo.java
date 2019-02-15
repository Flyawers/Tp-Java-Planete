package com.sdz.model;

/*Classe Pojo pour les Systèmes, Elle permet de créer une entité représentant comment se définit un système.
 * Avec un ID, un nom
*/
public class SystemePojo 
{
	//Attributs d'un système
	private static int id = 0;
	private String nom = "";
	
	@SuppressWarnings("static-access")
	//Constructeur de la classe
	public SystemePojo(int id, String nom)
	{
		this.id = id;
		this.nom = nom;
	}
	
	//Constructeur par défaut
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
