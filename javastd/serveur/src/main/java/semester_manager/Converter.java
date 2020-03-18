package semester_manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.semestre.Semestre;

public class 
Converter 
{
	private Gson gson;
	
	public
	Converter ()
	{
		this.gson = new GsonBuilder().create();
	}
	
	public Semestre
	stringToSemestre (String s)
	{
		return gson.fromJson(s, Semestre.class);
	}
}
