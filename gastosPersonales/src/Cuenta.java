import java.util.ArrayList;//importamos la clase arraylist
import java.util.List;	   //y list

public class Cuenta {

	//creamos las variables
	private double saldo;
	private Usuario usuario;
	private List<Gasto> gastos;
	private List<Ingreso> ingresos;
	
	//creamos los constructores
	public Cuenta(Usuario usuario) {
		this.usuario = usuario; 
		this.saldo = 0.0;
		
		//inicializamos
		this.ingresos = new ArrayList<>();
		this.gastos = new ArrayList<>();
	}
	
	//utilizamos el get y set según la pac
	public double getSaldo() {
		return this.saldo;
	}
	
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public double addIngresos(String descripcion, double cantidad) {
		//añadimos un ingreso
		ingresos.add(new Ingreso(cantidad, descripcion)); 
		//a este saldo le añadimos una cantidad de ingreso
		this.saldo += cantidad; 
		
		//devolvemos el saldo
		return this.getSaldo();
	}

	public double addGastos(String descripcion, double cantidad) throws GastoException {
		
		//saltaría el gasto exception si no encontramos saldo suficiente
		boolean condicionGastoException = this.getSaldo()<cantidad;
		
		//comprobamos que haya saldo, si no es así, no hará nada
		if(condicionGastoException) {
			throw new GastoException(); 
		}
		
		//añadimos el gasto
		gastos.add(new Gasto(cantidad, descripcion));
		
		//le restamos la cantidad de gasto al saldo
		this.saldo -= cantidad; 
		return this.getSaldo(); 
	}

	public List<Ingreso> getIngresos() {
		return ingresos;
	}
	
	public List<Gasto> getGastos() {
		return gastos;
	}
	
	@Override
	public String toString() {
		//devolvemos el usuario y el saldo
		return "Usuario: " + this.getUsuario() + ", saldo: " + this.getSaldo(); 
	}
}
