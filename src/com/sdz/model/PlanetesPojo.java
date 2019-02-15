package com.sdz.model;

import java.util.HashSet;
import java.util.Set;

/*Classe Pojo pour les Planètes, Elle permet de créer une entité représentant comment se définit une planéte.
 * Avec un ID, un nom, une température, un diamètre, un système et un type de planète
 * On y joint les POJO des autres entités (Systeme et Type) afin de pouvoir y travailler dessus
*/
public class PlanetesPojo 
	{
		//Attributs d'une planète
		private int id = 0;
		private String nom = "";
		private int temperature = 0;
		private int diametre = 0;
		private int systeme = 0;
		private int type = 0;
		
		//Appel des POJO de Systeme et Type
		private Set<SystemePojo>listSysteme = new HashSet<SystemePojo>();
		private Set<TypePojo>listType = new HashSet<TypePojo>();
		
		//Constructeur de la classe
		public PlanetesPojo(int id, String nom, int temperature, int diametre, int systeme, int type)
			{
				this.id = id;
				this.nom = nom;
				this.temperature = temperature;
				this.diametre = diametre;
				this.systeme = systeme;
				this.type = type;
			}
		
		//Constructeur par défaut
		public PlanetesPojo()
			{
				
			}
		//GETTERS
		public long getId()
			{
				return id;
			}
			
		public String getNom()
			{
				return nom;
			}
		
		public int getTemperature()
			{
				return temperature;
			}
		
		public int getDiametre()
			{
				return diametre;
			}
		
		//SETTERS
		public void setId(int iPltId)
			{
				this.id = iPltId ;
			}
		
		public void setNom(String nom)
			{
				this.nom = nom;
			}
		
		public void setTemperature(int temperature)
			{
				this.temperature = temperature;
			}
		
		public void setDiametre(int diametre)
			{
				this.diametre = diametre;
			}
		
		//GETTER de JOINTURE
		
		public int getSystemePlt()
			{
				return systeme;
			}
		
		public int getTypePlt()
			{
				return type;
			}
		
		public void setSystemePlt(int systeme)
			{
				this.systeme = systeme;
			}
		
		public void setTypePlt(int type)
			{
				this.type = type;
			}
		
		public Set<SystemePojo>getListSysteme()
			{
				return listSysteme;
			}
		
		public Set<TypePojo>getListType()
			{
				return listType;
			}
		
		//SETTER de JOINTURE
		public void setListSysteme(int systeme)
			{
				this.systeme = systeme;
			}
		
		public void setListType(int type)
			{
				this.type = type;
			}
			
		//AJOUT d'un systeme à une planète
		public void addSysteme(SystemePojo systeme)
			{
				if(!this.listSysteme.contains(systeme))
					{
						this.listSysteme.add(systeme);
					}
			}
		
		//DELETE un systeme d'une planète
		public void removeSysteme(SystemePojo systeme)
			{
				this.listSysteme.remove(systeme);
			}
		
		//AJOUT D'un type de planète à une Planète
		public void addType(TypePojo type)
			{
				if(!this.listType.contains(type))
					{
						this.listType.remove(type);
					}
			}
		
		//DELETE un type de planète dans Planète
		public void removeType(TypePojo type)
			{
				this.listType.remove(type);
			}
		
	}
