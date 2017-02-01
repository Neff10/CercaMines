package domini;

import java.util.Random;

public class TaulellCercaMines {

	private int[][] taulell;/*
							 * taulell pel joc del cercamines, si el contingut
							 * �s -1 => hi ha una mina si el contingut �s 0 =>
							 * posici� buida i sense cap ve� amb mina altrament
							 * el contingut indica el nombre de caselles ve�nes
							 * amb mina
							 */
	private final int POS_BUIDA = 0;// constant de casella sense cap ve� amb
									// mina
	private final int MINA = -1;// constant que indica que la casella t� una
								// mina
	private boolean[][] tapada;// per saber si una casella del taulell est� o no
								// tapada
	private boolean[][] marcadaPossibleMina;// per saber si una casella del
											// taulell est�
											// o no marcada com a possible mina
	private int MAX_FILES;// N�mero de files del taulell
	private int MAX_COLUMNES;// N�mero de columnes del taulell
	private final int MAX_MINES; // % de mines com a m�xim
	private final int MIN_MINES; // % de mines com a m�nim

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
	 * Col�locar mines al taulell de forma aleat�ria, per fer-ho caldr�: 
	 * 1: calcular el nombre de mines que cal col�locar (m�tode generarNumAleatori()) 
	 * 1.1: generar un n�mero aleatori entre MIN_MINES i MAX_MINES 1.2: el n�mero obtingut en el pas anterior �s el % de mines queha de tenir el taulell 
	 * 2: col�locar cada mina en una casella espec�fica
	 * 2.1: obtenir les coordenades de la casella de forma aleat�ria 2.2: si la casella de la coordenada obtinguda no t� cap mina 
	 * 2.2.1: col�locar una mina a la casella 
	 * 2.2.2: informar a tots els ve�ns de la casella, que hi ha una mina ve�na (m�tode modificarVeinsNovaMina()) 
	 * 2.2: si la casella de la coordenada obtinguda ja t� un mina 
	 * 2.2.1 tornar al pas 2.1 fins a  poder-la col�locar en una casella sense mina */
	
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
	 * Cada cop que es col�loca una mina en una casella, cal informar a tots els seus ve�ns sense mina del fet. Per fer-ho caldr�: 
	 * 1: obtenir tots els ve�ns de la casella de l'argument d'entrada (m�tode cercarCasellesVeines()) 
	 * 2: Per cada ve� sense mina, cal modificar el nombre de ve�ns amb mina, �s a dir, incrementar la casella ve�na en 1
	 */
	private void modificarVeinsNovaMina(int posFila, int posColumna) {
		
		int[][] veines = this.cercarCasellesVeines(posFila, posColumna);
		
		if (veines[posFila][posColumna] != MINA){
			veines[posFila][posColumna]++;
		}
	}
		

	/*
	 * Una casella, segons la seva situaci� al taulell, pot tenir 3, 5 o 8
	 * ve�ns. Aquest m�tode retorna en un array les coordenades de la caselles
	 * ve�nes a la casella de l'argument. Cal tenir en compte que una casella no
	 * �s veina de si mateixa. El retorn significa: int [numero de ve�ns 3, 5 o
	 * 8] [2] on [2] �s la coordenada de la casella ve�na en el taulell i t� a
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
	 * Generador de nombres aleatoris retorna un enter entre min i max, ambd�s
	 * inclosos
	 */
	private int generarNumAleatori(int min, int max) {
		double aleatori = random.nextDouble();
		int retorn = (int) Math.floor(aleatori * (max - min + 1) + min);
		return retorn;
	}

	/* Retorna el n�mero de files del taulell */
	public int getFiles() {
		return MAX_FILES;
	}

	/* Retorna el n�mero de columnes del taulell */
	public int getColumnes() {
		return MAX_COLUMNES;
	}

	/* Retorna el taulell en una matriu de String */
	public String[][] veureTaulell() {
		String[][] retorn = new String[MAX_FILES][MAX_COLUMNES];
		String valor;
		// Rec�rrer tot el taulell
		for (int i = 0; i < MAX_FILES; i++) {// Per cada fila
			for (int j = 0; j < MAX_COLUMNES; j++) {// Per cada columna
				if (!this.tapada[i][j]) {
					if (this.taulell[i][j] == MINA) {
						valor = "M";// si la casella est� destapada i hi ha una
									// mina posem una "M"
					} else {
						// si la casella est� destapada i NO hi ha una mina,
						// posem el nombre de caselles ve�nes amb mina
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
	 * El jugador vol marcar una posici� com a possible mina Si la casella est�
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
	 * El jugador vol desmarcar una posici� com a possible mina Si la casella
	 * est� destapada no cal fer res de res Altrament cal desmarcar la casella
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
	 * El jugador vol destapar una casella, hi ha v�ries situacions: A: Hi ha
	 * una mina a la casella que vol destapar, aleshores es destapar� tot el
	 * taulell, situaci� de fi de joc. B: La casella ja est� destapada, no cal
	 * fer res de res C: La casella est� o no marcada com a possible mina i est�
	 * tapada, caldr�: C1: destapar la casella C2: Si estava marcada com a
	 * possible mina, desmarcar-la C3: destapar de forma recursiva, totes les
	 * caselles ve�nes tapades sense mina (m�tode destaparVeinsBuits()).
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
	 * ve�nes de forma recursiva (m�tode que es crida a ell mateix). Es podr�
	 * destapar una casella ve�na si es compleixen totes les seg�ents
	 * condicions: 1: no estar destapada 2: no estar marcada com a possible mina
	 * (tant si hi ha mina com si no) 3: no tenir una mina En el sup�sit que
	 * finalment la casella �s destapada, si no t� cap mina veina caldr� cridar
	 * el mateix m�tode, essent l'argument d'entrada la casella destapada (aix�
	 * �s la recursivitat)
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