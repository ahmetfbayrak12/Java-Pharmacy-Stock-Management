import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ahmet Faruk Bayrak
 * This is Main class as you see.
 * This operates general medicine finding purposes.
 */
public class Main {

	/**
	 * This method operates best solution for finding medicines
	 * @param args first is prescription.txt second is medicaments.txt
	 */
	public static void main(String[] args) {
		
		String[] medicaments = Helper.readFile(args[1]);
		String[] prescription = Helper.readFile(args[0]);
		
		ArrayList<String> results_without_price_control = new ArrayList<String>();		
		results_without_price_control = createPriceless(prescription, medicaments);
		
		// new arraylist for price controlled medicines as a string collection
		ArrayList<String> results_with_price_control = new ArrayList<String>();
		
		// new arraylist for control the medicine names in price controlled list
		ArrayList<String> control_values = new ArrayList<String>();
		
		// this returns non controlled price list to controlled price list
		results_with_price_control = findCheapest(results_without_price_control, control_values);
		
		// prints the results
		printInvoice(results_with_price_control);
	}	
	
	/**
	 * This method searches prescription and medicaments.
	 * Then finds if their limitations okey.
	 * Then set these medicines an array list without price control.
	 * @param prescription prescription line list
	 * @param medicaments medicaments line list
	 * @return results_witout_price control is a medicine list as a String collection.
	 */
	private static ArrayList<String> createPriceless(String[] prescription, String[] medicaments){
		ArrayList<String> results_without_price_control = new ArrayList<String>();
		
		for (int j = 1; j < prescription.length; j++) {
			for (int i = 0; i < medicaments.length; i++) {
				Date medicine_validity_date = Helper.dateParse(medicaments[i].split("\t")[2]);
				Date medicine_expiry_date = Helper.dateParse(medicaments[i].split("\t")[3]);
				Date prescription_date = Helper.dateParse(prescription[0].split("\t")[2]);
				
				if (medicaments[i].split("\t")[1].equals(prescription[0].split("\t")[1])) { // sgk uygun mu
					if (prescription_date.after(medicine_validity_date) && prescription_date.before(medicine_expiry_date)) { // tarih uygun mu
						if (prescription[j].split("\t")[0].equals(medicaments[i].split("\t")[0])) { // isim uyuyo mu
							String result = prescription[j].split("\t")[0] + " " + prescription[j].split("\t")[1] + " " + medicaments[i].split("\t")[4];
							results_without_price_control.add(result);
						}
					}
				}
			} 
		}
		return results_without_price_control;
	}
	
	/**
	 * This method finds cheapest medicine that correct attributes. 
	 * To work with this method we need a array list that we have already created before.
	 * @param results_without_price_control this parameter is medicine array that we created before
	 * @param control_values it keeps some control values for the searching medicines.
	 * @return a array list that contains correct medicines correct price.
	 */
	private static ArrayList<String> findCheapest(ArrayList<String> results_without_price_control, ArrayList<String> control_values) {
		
		ArrayList<String> results_with_price_control = new ArrayList<String>();
		
		for (int i = 0; i < results_without_price_control.size(); i++) {
			if (results_with_price_control.isEmpty()) {
				results_with_price_control.add(results_without_price_control.get(i));
				control_values.add(results_without_price_control.get(i).split(" ")[0]);
			}
			else {
				for (int j = 0; j < results_with_price_control.size(); j++) {
					if (results_without_price_control.get(i).split(" ")[0].equals(results_with_price_control.get(j).split(" ")[0])) { // isimleri ayni mi
						if (Double.parseDouble(results_without_price_control.get(i).split(" ")[2]) < Double.parseDouble(results_with_price_control.get(j).split(" ")[2])) {
									results_with_price_control.remove(j);
									results_with_price_control.add(results_without_price_control.get(i));
						}
					}
					else if (!control_values.contains(results_without_price_control.get(i).split(" ")[0])){
						results_with_price_control.add(results_without_price_control.get(i));
						control_values.add(results_without_price_control.get(i).split(" ")[0]);
					}
				} 
			}
		}
		return results_with_price_control;
	}
	
	/**
	 * This method is a printer.
	 * printer operates given array list.
	 * Then creates simple invoice format.
	 * After that prints datas correct format.
	 * @param results_with_price_control its final invoice medicines 
	 */
	private static void printInvoice(ArrayList<String> results_with_price_control){
		double total = 0;
		 NumberFormat formatter = new DecimalFormat("#0.00"); 
		for (int i = 0; i < results_with_price_control.size(); i++) {
			
			double a = Integer.parseInt(results_with_price_control.get(i).split(" ")[1]) *
					  Double.parseDouble(results_with_price_control.get(i).split(" ")[2]);
			
			total += a;
			System.out.println ( results_with_price_control.get(i).split(" ")[0] + "\t" + 
					formatter.format(Double.parseDouble(results_with_price_control.get(i).split(" ")[2])) + "\t" + 
							   results_with_price_control.get(i).split(" ")[1] + "\t" + formatter.format(a));
		}
		System.out.println("Total: " + formatter.format(total));
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
