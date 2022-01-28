import java.util.Arrays;	//importamos todas las clases necesarias
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {
	
	private static final String MSG_DESPEDIDA = "Fin del programa.\nGracias por utilizar la aplicación.";
	
	//mensajes por si ocurren errores
	private static final String MSG_ACCION_NO_VALIDA = "Accion no valida, elija una nueva acción";
	private static final String MSG_DNI_NO_VALIDO = "DNI introducido incorrecto";
	
	
	//creamos los mensajes del menú
	private static final String MSG_MENU = 
							"Realiza una nueva acción\n" +
							"1 Introduce un nuevo gasto\n" +
							"2 Introduce un nuevo ingreso\n" +
							"3 Mostrar gastos\n" +
							"4 Mostrar ingresos\n" +
							"5 Mostrar saldo\n" +
							"0 Salir\n" ;
	
	//creamos los mensajes para solicitar introducir al usuario la información necesaria
	private static final String MSG_SOLICITAR_NOMBRE = "Introduce el nombre de usuario: ";
	private static final String MSG_SOLICITAR_EDAD = "Introduce la edad del usuario: ";
	private static final String MSG_SOLICITAR_DNI = "Introduce el DNI del usuario: ";
	private static final String MSG_USUARIO_CREADO = "Usuario creado correctamente ";
	
	//gastos
	private static final String MSG_SOLICITAR_DESCRIPCION_GASTO = "Descripcion del gasto: ";
	private static final String MSG_SOLICITAR_NUEVO_GASTO = "Introduzca cantidad a gastar: ";
	
	//ingresos
	private static final String MSG_SOLICITAR_DESCRIPCION_INGRESO = "Introduce descripción: ";
	private static final String MSG_SOLICITAR_NUEVO_INGRESO = "Introduce la cantidad: ";
	
	//números a ingresar para iniciar
	private static final int NUEVO_GASTO = 1; 
	private static final int NUEVO_INGRESO = 2; 
	private static final int MOSTRAR_GASTOS = 3; 
	private static final int MOSTRAR_INGRESOS = 4; 
	private static final int MOSTRAR_SALDO = 5; 
	private static final int SALIR = 0;

	//saldo
	private static final String MSG_SALDO_RESTANTE = "Saldo restante: ";
	private static final String MSG_SALDO_ACTUAL = "El saldo actual de la cuenta es: ";

	private static Cuenta miCuenta; //variables
	
	public static void main(String[] args) {
		
		solicitarDatos(); 
		
		//lo iniciamos en false para que se repita el do...while
		boolean condicionSalir = true; 
		int opcion; 
		
		//utilizamos un do...while porque necesitamos que mínimo, se ejecute una vez
		do {
			mostraMenu();
			opcion = elegirOpcion();
			condicionSalir = ejecutarOpcion(opcion); 
		} while (condicionSalir);
	}

	//metodo main
	private static void solicitarDatos() {
		
		//creamos un usuario 
		Usuario miUsuario = new Usuario(); 
		
		boolean DNICorrecto = false; //condicion para salir del bucle do...while
		
			String nombre = solicitarString(MSG_SOLICITAR_NOMBRE); 
			//comprobamos que no dejen en blanco el nombre
			while(nombre.isEmpty()) {
				nombre = solicitarString(MSG_SOLICITAR_NOMBRE); 
			}
			
			int edad = solicitarEntero(MSG_SOLICITAR_EDAD);
			miUsuario.setNombre(nombre);
			miUsuario.setEdad(edad);
			
		//comprobamos que se haya introducido el DNI según la pac
		do {
			String DNI = solicitarString(MSG_SOLICITAR_DNI); 
			miUsuario.setDNI(DNI);
			
			if(miUsuario.setDNI(DNI)) {
				DNICorrecto = true; 
			}else {
				System.out.println(MSG_DNI_NO_VALIDO);
			}
			
		} while (!DNICorrecto );
		
		System.out.println(MSG_USUARIO_CREADO);
		
		//tras crear el usuario, creamos la cuenta y le pasamos los datos
		miCuenta = new Cuenta(miUsuario);
		 
	}

	private static void mostraMenu() {
		System.out.print(MSG_MENU);
	}
	
	private static int elegirOpcion() {
		return leerEntero(); 
	}

	private static boolean ejecutarOpcion(int opcion) {
		
		switch (opcion) {
		case NUEVO_GASTO:
			nuevoGasto(); 
			break;
		case NUEVO_INGRESO:
			nuevoIngreso();
			break;
		case MOSTRAR_GASTOS:
			mostrarGastos();
			break;
		case MOSTRAR_INGRESOS:
			mostrarIngresos();
			break;
		case MOSTRAR_SALDO:
			mostrarSaldo();
			break;
		case SALIR:
			System.out.println(MSG_DESPEDIDA);
			return false; 
			
		default:
			System.out.println(MSG_ACCION_NO_VALIDA);
			break;
		}
		return true; 
	}

	//metodo ejecutarOpcion
	private static void nuevoGasto() {
		
		String descripcion = solicitarString(MSG_SOLICITAR_DESCRIPCION_GASTO); 
		double cantidad = solicitarDouble(MSG_SOLICITAR_NUEVO_GASTO);
		
		//error por si no tenemos saldo suficiente
		try {
			double saldo = miCuenta.addGastos(descripcion,cantidad);
			System.out.println(MSG_SALDO_RESTANTE + saldo);
		} catch (GastoException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	private static void nuevoIngreso() {
		
		String descripcion = solicitarString(MSG_SOLICITAR_DESCRIPCION_INGRESO); 
		double cantidad = solicitarDouble(MSG_SOLICITAR_NUEVO_INGRESO);
		double saldo = miCuenta.addIngresos(descripcion,cantidad);
		
		System.out.println(MSG_SALDO_RESTANTE + saldo);
	}
	
	private static void mostrarIngresos() {
		
		//creamos una copia para trabajar encima de las copias
		List<Ingreso> listaIngresos = miCuenta.getIngresos(); 
		
		Stream<Ingreso> str = listaIngresos.stream(); 
		str.forEach(System.out::println);
	}

	private static void mostrarGastos() {
		
		List<Gasto> listaGastos = miCuenta.getGastos(); 
		
		for (int i = 0; i < listaGastos.size(); i++) {
			
			System.out.println(listaGastos.get(i));
		}
	}
	
	private static void mostrarSaldo() {
		System.out.println(MSG_SALDO_ACTUAL + miCuenta.getSaldo()+"€");
	}

	//creamos los metodos para solicitar los datos por consola
	private static String solicitarString(String mensaje) {
		System.out.println(mensaje);
		return leerString();
	}

	private static int solicitarEntero(String mensaje) {
		System.out.println(mensaje);
		return leerEntero();
	}
	
	private static double solicitarDouble(String mensaje) {
		System.out.println(mensaje);
		return leerDouble(); 
	}
	
	//ahora los metodos para leer por consola
	private static String leerString() {
		return new Scanner(System.in).nextLine(); 
	}
	
	private static int leerEntero() {

		try {
			return new Scanner(System.in).nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Introduzca un número entero: ");
			
			return leerEntero(); 
		}
	}
	
	private static double leerDouble() {

		return new Scanner(System.in).nextDouble();  
	}
}
