package com.sdz.model;

/*Classe Pojo pour les Types de Planète, Elle permet de créer une entité représentant comment se définit un type de planète.
 * Avec un ID, un nom
*/
public class TypePojo 
{
	//Attributs d'un type de planète
	private static int id = 0;
	private String nom = "";
	
	@SuppressWarnings("static-access")
	//Constructeur de la classe
	public TypePojo(int id, String nom)
	{
		this.id = id;
		this.nom = nom;
	}
	
	//COnstructeur par défaut
	public TypePojo()
	{
	}
	
	//GETTERS
	public static int getIdType()
	{
		return id;
	}
	
	public String getNomType()
	{
		return nom;
	}
	
	//SETTERS
	public void setIdType(int id)
	{
		TypePojo.id = id;
	}
	
	public void setNomType(String nom)
	{
		this.nom = nom;
	}
}
