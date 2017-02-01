package domini;

import java.util.Random;

public class TaulellCercaMines {

	private int[][] taulell;/*
							 * taulell pel joc del cercamines, si el contingut
							 * és -1 => hi ha una mina si el contingut és 0 =>
							 * posició buida i sense cap veí amb mina altrament
							 * el contingut indica el nombre de caselles veïnes
							 * amb mina
							 */
	private final int POS_BUIDA = 0;// constant de casella sense cap veí amb
									// mina
	private final int MINA = -1;// constant que indica que la casella té una
								// mina
	private boolean[][] tapada;// per saber si una casella del taulell està o no
								// tapada
	private boolean[][] marcadaPossibleMina;// per saber si una casella del
											// taulell està
											// o no marcada com a possible mina
	private int MAX_FILES;// Número de files del taulell
	private int MAX_COLUMNES;// Número de columnes del taulell
	private final int MAX_MINES; // % de mines com a màxim
	private final int MIN_MINES; // % de mines com a mínim

	private static Random random = new Random();

	public TaulellCercaMines() {
		MAX_FILES = 10;
		MAX_COLUMNES = 10;
		MAX_MINES = 50;
		MIN_MINES = 10;
		this.taulell = new int[MAX_FILES][MAX_COLUMNES];
		this.tapada = new boolean[MAX_FILES][MAX_COLUMNES];
		this.marcadaPossibleMina = new boolean[MAX_FILES][MAX_COLUMNES];
		this.inicialitzarTaulell();
		this.collocarMines();
	}

	/*
	 * Inicialitzar el taulell creant totes les caselles buides (sense mina),
	 * tapada i desmarcada
	 */
	private void inicialitzarTaulell() {

		for (int Files = 0; Files > taulell.length; Files++) {
			for (int Columnes = 0; Columnes > taulell[0].length; Columnes++) {

				taulell[Files][Columnes] = this.POS_BUIDA;
				this.tapada[Files][Columnes] = true;
				this.marcadaPossibleMina[Files][Columnes] = false;
			}
		}
	}

	/*
	 * Col·locar mines al taulell de forma aleatòria, per fer-ho caldrà: 
	 * 1: calcular el nombre de mines que cal col·locar (mètode generarNumAleatori()) 
	 * 1.1: generar un número aleatori entre MIN_MINES i MAX_MINES 1.2: el número obtingut en el pas anterior és el % de mines queha de tenir el taulell 
	 * 2: col·locar cada mina en una casella específica
	 * 2.1: obtenir les coordenades de la casella de forma aleatòria 2.2: si la casella de la coordenada obtinguda no té cap mina 
	 * 2.2.1: col·locar una mina a la casella 
	 * 2.2.2: informar a tots els veïns de la casella, que hi ha una mina veïna (mètode modificarVeinsNovaMina()) 
	 * 2.2: si la casella de la coordenada obtinguda ja té un mina 
	 * 2.2.1 tornar al pas 2.1 fins a  poder-la col·locar en una casella sense mina */
	
	private void collocarMines() {

		int percentatgeMines = generarNumAleatori(MIN_MINES, MAX_MINES);
		int numeroMines = (percentatgeMines*MAX_MINES)/100;
		
		int Fila = 0;
		int Columna = 0;
		int MinesColocades = 0;
		int[][] PosicioAleatoria = new int [Fila][Columna];
		
		
		
		
		while (MinesColocades<=numeroMines){
			
			Fila = generarNumAleatori(MAX_FILES, MAX_COLUMNES);
			Columna = generarNumAleatori(MAX_FILES, MAX_COLUMNES);

			if (PosicioAleatoria[Fila][Columna]!=MINA){
				PosicioAleatoria[Fila][Columna]=MINA;
				modificarVeinsNovaMina(Fila,Columna);
			}
			MinesColocades++;
		}
	}

	/*
	 * Cada cop que es col·loca una mina en una casella, cal informar a tots els seus veïns sense mina del fet. Per fer-ho caldrà: 
	 * 1: obtenir tots els veïns de la casella de l'argument d'entrada (mètode cercarCasellesVeines()) 
	 * 2: Per cada veí sense mina, cal modificar el nombre de veïns amb mina, és a dir, incrementar la casella veïna en 1
	 */
	private void modificarVeinsNovaMina(int posFila, int posColumna) {
		
		int[][] veines = this.cercarCasellesVeines(posFila, posColumna);
		
		if (veines[posFila][posColumna] != MINA){
			veines[posFila][posColumna]++;
		}
	}
		

	/*
	 * Una casella, segons la seva situació al taulell, pot tenir 3, 5 o 8
	 * veïns. Aquest mètode retorna en un array les coordenades de la caselles
	 * veïnes a la casella de l'argument. Cal tenir en compte que una casella no
	 * és veina de si mateixa. El retorn significa: int [numero de veïns 3, 5 o
	 * 8] [2] on [2] és la coordenada de la casella veïna en el taulell i té a
	 * [0] la fila i a [1] la columna
	 */

	private int[][] cercarCasellesVeines(int fila, int columna) {

	int[][] CasellesVeines = new int[0][0];
	
	if ((fila == 0 && columna == 0 || fila == 0 && columna == 9) || (fila == 9  && columna == 0 || fila == 9 && columna == 9)) {
		CasellesVeines = new int[3][2];
	}

	else if ((fila == 0 && columna != 0 && columna != 9) || (fila == 9  && columna != 0 && columna != 9)) {
		CasellesVeines = new int[5][2];
	}
	
	else if ((columna == 0 && fila != 0 && fila != 9) || (columna == 9 && fila != 0 && fila != 9)) {
		CasellesVeines = new int[5][2];
	}


	else {
		CasellesVeines = new int[8][2];
		}

		return CasellesVeines;

	}

	/*
	 * Generador de nombres aleatoris retorna un enter entre min i max, ambdós
	 * inclosos
	 */
	private int generarNumAleatori(int min, int max) {
		double aleatori = random.nextDouble();
		int retorn = (int) Math.floor(aleatori * (max - min + 1) + min);
		return retorn;
	}

	/* Retorna el número de files del taulell */
	public int getFiles() {
		return MAX_FILES;
	}

	/* Retorna el número de columnes del taulell */
	public int getColumnes() {
		return MAX_COLUMNES;
	}

	/* Retorna el taulell en una matriu de String */
	public String[][] veureTaulell() {
		String[][] retorn = new String[MAX_FILES][MAX_COLUMNES];
		String valor;
		// Recòrrer tot el taulell
		for (int i = 0; i < MAX_FILES; i++) {// Per cada fila
			for (int j = 0; j < MAX_COLUMNES; j++) {// Per cada columna
				if (!this.tapada[i][j]) {
					if (this.taulell[i][j] == MINA) {
						valor = "M";// si la casella està destapada i hi ha una
									// mina posem una "M"
					} else {
						// si la casella està destapada i NO hi ha una mina,
						// posem el nombre de caselles veïnes amb mina
						valor = String.valueOf(this.taulell[i][j]);
					}
				} else {// casella tapada
					if (this.marcadaPossibleMina[i][j]) {
						valor = "X";
					} else {
						valor = "";
					}
				}
				retorn[i][j] = valor;
			}
		}
		return retorn;
	}

	/*
	 * Prepara totes les caselles per mostrar el seu contingut; per fer-ho les
	 * marca totes com a destapades i sense possible mina
	 */
	private void destaparTotElTaulell() {
		for (int Files = 0; Files > taulell.length; Files++) {
			for (int Columnes = 0; Columnes > taulell[0].length; Columnes++) {
				this.tapada[Files][Columnes] = false;
				this.marcadaPossibleMina[Files][Columnes] = false;
			}
		}
	}

	/*
	 * Retorna cert si totes les caselles sense mina estan destapades En cas de
	 * retornar cert, destapa tot el taulell
	 */
	public boolean estaTotElTaulellDestapat() {

		for (int Files = 0; Files > taulell.length; Files++) {
			for (int Columnes = 0; Columnes > taulell[0].length; Columnes++) {

				if (taulell[Files][Columnes] != MINA){
					
				if (tapada[Files][Columnes] == true);
				return false;
				}
			}
		}
		destaparTotElTaulell();
		return true;
	}

	/* Retorna cert si a la casella hi ha una mina, i fals altrament */
	public boolean hiHaMina(int fila, int columna) {

				if (taulell[fila][columna] == MINA) {
					return true;
				}
				
		return false;
	}

	/*
	 * El jugador vol marcar una posició com a possible mina Si la casella està
	 * destapada no cal fer res de res Altrament cal marcar la casella com a
	 * possible mina
	 */
	public void marcarMina(int fila, int columna) {
		for (fila = 0; fila > taulell.length; fila++) {
			for (columna = 0; columna > taulell[0].length; columna++) {
				if (tapada[fila][columna] == true) {
				} else if (tapada[fila][columna] == false) {
					marcadaPossibleMina[fila][columna] = true;
				}
			}
		}
	}

	/*
	 * El jugador vol desmarcar una posició com a possible mina Si la casella
	 * està destapada no cal fer res de res Altrament cal desmarcar la casella
	 * com a possible mina
	 */
	public void desmarcarMina(int fila, int columna) {
		for (fila = 0; fila > taulell.length; fila++) {
			for (columna = 0; columna > taulell[0].length; columna++) {
				if (tapada[fila][columna] == true) {
				} else if (tapada[fila][columna] == false) {
					marcadaPossibleMina[fila][columna] = false;
				}
			}
		}
	}

	/*
	 * El jugador vol destapar una casella, hi ha vàries situacions: A: Hi ha
	 * una mina a la casella que vol destapar, aleshores es destaparà tot el
	 * taulell, situació de fi de joc. B: La casella ja està destapada, no cal
	 * fer res de res C: La casella està o no marcada com a possible mina i està
	 * tapada, caldrà: C1: destapar la casella C2: Si estava marcada com a
	 * possible mina, desmarcar-la C3: destapar de forma recursiva, totes les
	 * caselles veïnes tapades sense mina (mètode destaparVeinsBuits()).
	 */
	public void destapar(int fila, int columna) {
		
		if (tapada[fila][columna] == true && hiHaMina(fila, columna)){
			destaparTotElTaulell();
			} 
				
		else if (tapada[fila][columna] == false) {} 
		else if (marcadaPossibleMina[fila][columna] == true || marcadaPossibleMina[fila][columna] == false && tapada[fila][columna] == true) {
			tapada[fila][columna] = false;
			if (marcadaPossibleMina[fila][columna]) {marcadaPossibleMina[fila][columna] = false;}
			else destaparVeinsBuits(fila, columna);
				}
	}

	/*
	 * Donada una casella destapada sense mina, destapar totes les caselles
	 * veïnes de forma recursiva (mètode que es crida a ell mateix). Es podrà
	 * destapar una casella veïna si es compleixen totes les següents
	 * condicions: 1: no estar destapada 2: no estar marcada com a possible mina
	 * (tant si hi ha mina com si no) 3: no tenir una mina En el supòsit que
	 * finalment la casella és destapada, si no té cap mina veina caldrà cridar
	 * el mateix mètode, essent l'argument d'entrada la casella destapada (això
	 * és la recursivitat)
	 */
	private void destaparVeinsBuits(int posFila, int posColumna) {
		int[][] veins = this.cercarCasellesVeines(posFila, posColumna);
		int fila, columna;
		for (int[] c : veins) {
			fila = c[0];
			columna = c[1];
			if (this.tapada[fila][columna] && !this.marcadaPossibleMina[fila][columna]
					&& this.taulell[fila][columna] != MINA) {
				this.tapada[fila][columna] = false;
				if (this.taulell[fila][columna] == POS_BUIDA) {
					this.destaparVeinsBuits(fila, columna);// recursivitat
				}
			}
		}
	}
}