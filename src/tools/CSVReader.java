package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import Model.PersonModel;

public class CSVReader {

    public static ArrayList<PersonModel> getCSVFile(String csvFile, ArrayList<PersonModel> employeeOfFactory) {

    	String line = "";
        String cvsSplitBy = ",";
        int i = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                String[] person = line.split(cvsSplitBy);
                PersonModel personModel = new PersonModel(person[0], person[1], Double.parseDouble(person[2]), new GraphicalEntity(new Position(50+i,50),60,60),"file:Ressource/images/test2.png",4,3,32,32,8);
                personModel.setNbAnim(1);
                personModel.stopAnim();
				personModel.setNewPosition(new Position(50+i+260,260));
				employeeOfFactory.add(personModel);
                i += 100;
            } 
            return employeeOfFactory;

        } catch (IOException e) {
            e.printStackTrace();
        }
		return null;

    }
    
    public static boolean generateCsvFile(String CSVFile, ArrayList<PersonModel> employeeOfFactory)
    {
	     try
	     {
	        FileWriter writer = new FileWriter(CSVFile,false);
	 		for (PersonModel employee : employeeOfFactory) {
	 			writer.append(employee.getName());
	 	        writer.append(',');
	 	        writer.append(employee.getJob());
	 	        writer.append(',');
	 	        writer.append(""+employee.getSalary());
	 	        writer.append('\n');
			}
	          
	        writer.flush();
	        writer.close();
	        return true;
	
	     }
	     catch(IOException e)
	     {
	          e.printStackTrace();
	     }
			return false;
	
	    }
}