
public class Gasto extends Dinero {
	
	//creamos constructor
	public Gasto(double gasto, String description) {
		this.setDinero(gasto);
		this.setDescription(description);
	}

	@Override
	public String toString() {
		return "Gasto: "+ super.getDescription() +  ", cantidad:" + this.getDinero() +"€";
	}
}
