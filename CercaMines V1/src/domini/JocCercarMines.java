package domini;

public class JocCercarMines {

	private TaulellCercaMines taulell;
	private boolean fiJoc;

	public JocCercarMines() { 
		
		this.taulell = new TaulellCercaMines();
		this.fiJoc = true;
		
	}

	/* El jugador vol destapar la casella de coordenades x i y, 
	 * 1: comprovar que les coordenades pertanyen al taulell del joc (mètode privat validarCoordenades())
	 * 		Si no hi pertanyen cal informar-ne en el retorn del mètode.
	 * 2: destapar la casella de les coordenades entrades en l'argument (mètode de l'atribut taulell)
	 * 3: si la casella destapada té una mina (mètode de l'atribut taulell) cal informar del fet i finalitzar el joc
	 * 4: si la casella destapada no té cap mina, 
	 * 	  comprovar si totes les caselles sense mina estan destapadas (mètode privat estaTotElTaulellDestapat()),
	 *    si és així cal informar del fet i finaltzar el joc*/
	
	public String destaparCasella(int x, int y) {
		
		String Derrota = "Has perdut!!";
		String Victoria = "Has guanyat!!";
		String Error = "Combinació invalida";
		
		if (validarCoordenades(x,y) != null){
			return Error;
		}
			taulell.destapar(x, y);
			if (taulell.hiHaMina(x, y)){
				return  Derrota;
			}
			
			else if (taulell.estaTotElTaulellDestapat()){
				return Victoria;
			}
			
		return null;
	}
	
	/* El jugador vol marcar la casella de coordenades x i y com a possible mina
	 * 1: comprovar que les coordenades pertanyen al taulell del joc  (mètode privat validarCoordenades())
	 * 		Si no hi pertanyen cal informar-ne en el retorn del mètode.
	 * 2: marcar com a possible mina la casella de les coordenades entrades en l'argument  (mètode de l'atribut taulell)*/
	public String marcarMina(int x, int y) {
		 
		String noPertany = "Aquesta coordenada no pertany al taulell del Joc";
		String Pertany = "X";
		if (validarCoordenades(x,y) == null){
			return Pertany;
		}
		else return noPertany;
	}

	/* El jugador vol desmarcar la casella com a possible mina de coordenades x i y 
	 * 1: comprovar que les coordenades pertanyen al taulell del joc (mètode privat validarCoordenades())
	 * 		Si no hi pertanyen cal informar-ne en el retorn del mètode.
	 * 2: desmarcar com a possible mina la casella de les coordenades entrades en l'argument (mètode de l'atribut taulell)*/	
	public String desmarcarMina(int x, int y) {
		
		String noPertany = "Aquesta coordenada no pertany al taulell del Joc";
		String Pertany = "?";
		if (validarCoordenades(x,y) == null){
			return Pertany;
		}
		else return noPertany;
	}

	//retorna fiJoc
	public boolean esFiJoc() {
		return this.fiJoc = true;
	}

	//retorna tot el taulell en una matriu de String (mètode de l'atribut taulell)
	public String [][] veureTaulell() {
		return taulell.veureTaulell();
	}

	/*Si els arguments x o y no pertanyen al taulell del joc retorna
	 * un String informant de l'error, altrament retorna nul */
	private String validarCoordenades(int x, int y) {

		String noPertany = "Aquesta coordenada no pertany al taulell del Joc";
		
		if (x<0 & y <0){
			return noPertany;
		}
		else if (x>taulell.getFiles() || y>taulell.getColumnes()){
			return noPertany;
		}
		else return null;
	}

	/* Comprova si tot el taulell està destapat (mètode de l'atribut taulell), 
	 * en cas afirmatiu retorna que el jugador ha guanyat,
	 * i marca el final del joc, altrament retorna nul.*/
	private String estaTotElTaulellDestapat() {
		String victoria = "Has Guanyat!!";
		if (taulell.estaTotElTaulellDestapat()){
		return victoria;
	}
		else return null;
		
	}
		
}
	