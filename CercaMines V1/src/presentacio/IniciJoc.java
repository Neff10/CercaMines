package presentacio;

import domini.JocCercarMines;
import jconsole.JConsole;

public class IniciJoc {

	private JocCercarMines joc;
	private JConsole console;

	public static void main(String[] args) {
		new IniciJoc();
	}

	/* 1: crear una consola de 100 per 30
	 * 2: crear un controlador del joc 
	 * 3: mostrar el taulell tot tapat, és a dir, amb ? (mètode veureTaulell())
	 * 4: Mentre el jugador no entri -1 i el joc no finalitzi, fer:
	 * 	4.1: llegir entrada de la consola
	 *  4.2 Si l'entrada és diferent de -1
	 * 	  4.2.1: validar i executar l'entrada
	 * 	  4.2.2: mostrar resultat de l'acció
	 * 	4.3: mostrar l'estat del taulell després de l'acció
	 * 5. Informar que el joc ha finalitzat.*/
	
	private IniciJoc() {
		
		String Entrada = null;
		String Sortida = "El Joc ha finalitzat";
		int mina=-1;
		
		console = new JConsole (100, 30); 
		this.joc = new JocCercarMines();
		
		veureTaulell();
		while (Entrada.equals(mina) == false && joc.esFiJoc() == false){
			Entrada = console.readString();
			if (Entrada.equals(mina) == false){
				validarExecutarEntrada(Entrada);
				console.print(Entrada);
			}
			veureTaulell();
		}
		
		console.println(Sortida);
	}


	/* Validar l'entrada que ha fet el jugador:
	 * 1: Validar que l'entrada té 3 valors separts per coma
	 * 	Si no és correcte retornar l'error
	 * 2: convertir la fila i la columna de text a enter (mètode isNumeric)
	 * 	Si no és correcte retornar l'error
	 * 3: validar el tipus d'acció, ha de ser d, m o e
	 *  Si no és correcte retornar l'error
	 * 4: transformar coordenades de 1..10 a 0..9 
	 * 5: Fer que el joc realitzi l'acció i retornar el resultat de l'execució.*/
	private String validarExecutarEntrada(String entrada) {

		String Error = "ERROR: cal entrar la fila, la columna i l'acció separat per comes.";
		String ErrorFila = "Error fila incorrecte: cal entrar un enter entre 1 i 10";
		String ErrorColumna = "Error columna incorrecte: cal entrar un enter entre 1 i 10";
		String ErrorAccio = "ERROR: L'acció ha de ser d=destapar casella, m=marcar possible mina i e=desmarcar possible mina";

		String [] arrayEntrada = entrada.split(",");
		if (arrayEntrada.length==3){
			if (isNumeric(arrayEntrada[0].toString())&&isNumeric(arrayEntrada[1].toString())){
				int valor1 = Integer.parseInt(arrayEntrada[0]);
				int valor2 = Integer.parseInt(arrayEntrada[1]);
				
				if (valor1<1 || valor1>10){
					return ErrorFila;
				}
				if (valor2<1 || valor2>10){
					return ErrorColumna;
				}
				else {
					String s = arrayEntrada[2].toString();
					switch (s){
					case "d":
						joc.destaparCasella(valor1-1, valor2-1);
						joc.veureTaulell();
						return "Destapant la casella "+valor1+", "+valor2+"\n Estat nou taulell:";
					case "m":
						joc.destaparCasella(valor1-1, valor2-1);
						joc.veureTaulell();
						return "Marcant la casella "+valor1+", "+valor2+"\n Estat nou taulell:";
					case "e":
						joc.destaparCasella(valor1-1, valor2-1);
						joc.veureTaulell();
						return "Desmarcant la casella "+valor1+", "+valor2+"\n Estat nou taulell:";
						default: return ErrorAccio;
					}
				}	
			}
			
			else return "Has d'introduir dos numeros";
	}
		else return Error;
	}
	
	//Retornar cert si la cadena és un enter i fals altrament
    private boolean isNumeric(String cadena){
    	try {
    		Integer.parseInt(cadena);
    		return true;
    	} catch (NumberFormatException nfe){
    		return false;
    	}
    }
    
    //Mostrar el taulell a la consola de sortida.
    private void veureTaulell() {
    	String [][] taulell = joc.veureTaulell();
    	for (int fila = 0; fila < taulell.length; fila++){
    		for (int columna = 0 ; columna < taulell[0].length; columna++){
    			if (taulell [fila][columna].equals("")) {
    				console.print ("?\t");    				
    			} else {
    				console.print (taulell [fila][columna] + "\t");
    			}
    		}
    		console.println();
    	}
    	console.println("Les files i les columnes del 1 al 10. destapar d, marcar m, desmercar e.");
    	console.println("Entra: fila, columna, d=destapar o m=marcar o e=desmarcar (exemple: 3,4,d o bé 3,4,m) o -1 sortir:");
    }
}