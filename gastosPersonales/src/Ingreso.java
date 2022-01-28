
public class Ingreso extends Dinero {
	
	//creamos constructor
	public Ingreso(double ingreso, String description) {
		this.setDinero(ingreso);
		super.setDescription(description);
	}

		
	@Override
	public String toString() {
		return "Ingreso: "+ super.getDescription() +  ", cantidad:" + this.getDinero() +"€"; 
	}
		
}
