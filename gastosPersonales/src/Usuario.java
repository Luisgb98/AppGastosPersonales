import java.util.regex.Pattern; //importamos la clase pattern

public class Usuario {

	//creamos las variables
	private String nombre;
	private int edad;
	private String DNI;

	//creamos el constructor
	public Usuario() {
	}

	//metodos get y set
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEdad() {
		return this.edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public String getDNI() {
		return this.DNI;
	}

	//creamos un booleano para validar el DNI
	public boolean setDNI(String DNI) {

		boolean salir = false;
		// si se cumple, cambia el booleano a true
		if (validarDNI(DNI)) {
			this.DNI = DNI;
			salir = true;
		}

		return salir;
	}
	
	//función tostring para devolver el contenido que nos exige la pac
	@Override
	public String toString() {
		return " Usuario: " + getNombre() + ", edad: " + this.getEdad() + ", DNI: " + this.getDNI();
	}

	//validamos que el DNI este bien escrito
	private boolean validarDNI(String DNI) {
		final String patron = "^\\d{8}-?[A-Z]$";
		
		return Pattern.matches(patron, DNI);
	}
	
}
