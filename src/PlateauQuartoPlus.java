import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class PlateauQuartoPlus implements Partie1 {

	private final static int TAILLE = 4;
	private static final String[] colonneValide = {"A","B","C","D"};
	private static final String VIDE = "++++";
    /* *********** Paramètres de classe *********** */
    /** Le joueur que joue "1" */
    private static String joueur1;
    /** Le joueur que joue "2" */
    private static String joueur2;

    /* *********** Attributs  *********** */
    /** Le nombre de graines capturées par chaque joueur (indice 0 : Joueur 1, indice 1 : Joueur 2) */
    private int[] score;
    /** Garde en memoire le joueur qui vient de jouer */
    private String vientDeJouer;
    
    private String[][] plateau = new String[TAILLE][TAILLE];
	private ArrayList<String> pieces = new ArrayList<String>();
	private ArrayList<String> colonnes = new ArrayList<String>();
	
    public PlateauQuartoPlus(){
    	// Initialisation du plateau
    	for(int i=0; i<TAILLE;i++)
    	{
    		for(int j=0; j<TAILLE;j++){
    			plateau[i][j]=VIDE;
    		}
    	}
    	for(int i=0; i<TAILLE;i++)
    	{
    		colonnes.add(colonneValide[i]);
    	}
    	
    	// Création pièces
    	String [] couleur = {"b", "r"};
        String [] hauteur = {"p", "g"};
        String [] sommet = {"p", "t"};
        String [] forme = {"r", "c"};
        for (String c : couleur) {
            for (String h : hauteur) {
                for (String s : sommet) {
                    for (String f : forme) {
                        pieces.add(c + h + s + f);
                    }
                }
            }
        }
    }
    
	public static void main(String[] args) {
		PlateauQuartoPlus plateau = new PlateauQuartoPlus();
		//Test ligen
//		plateau.play("A1","bgpr", "noir");
//		plateau.play("A2","bppr", "blanc");
//		plateau.play("A3","bgtr", "noir");
//		plateau.play("A4","rptc", "blanc");
		
		//Test colonne
//		plateau.play("A1","bgpr", "noir");
//		plateau.play("B1","bppr", "blanc");
//		plateau.play("C1","bgtr", "noir");
//		plateau.play("D1","rptc", "blanc");
		
		//Test diago
//		plateau.play("A1","bgpr", "noir");
//		plateau.play("B2","bppr", "blanc");
//		plateau.play("C3","bgtr", "noir");
//		plateau.play("D4","rptc", "blanc");
		
		//TEst CArrée
		plateau.play("A1","bgpr", "noir");
		plateau.play("B2","bppr", "blanc");
		plateau.play("A2","bgtr", "noir");
		plateau.play("B1","rptr", "blanc");
		
		plateau.saveToFile("test.txt");
		//plateau.setFromFile("test.txt");
		//plateau.play("A4","rptc", "blanc");
		System.out.println("FIN execution");

	}

	@Override
	public void setFromFile(String fileName) {
        try {
        	BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            String [] lineSplit = new String [4];

            int i = 0;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("%")) {
                    line = line.substring(2, line.length() - 2);
                    lineSplit = line.split(" ");
                    for (int j=0; j<TAILLE; j++) {
                		 plateau[i][j]=lineSplit[j];
                		 pieces.remove(lineSplit[j]);
	                }
	            }
	            
            }
            br.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }

		
	}

	@Override
	public void saveToFile(String fileName) {
		try { 
            FileWriter lu = new FileWriter(fileName);
            BufferedWriter out = new BufferedWriter(lu);

            if (pieces.size()==16)
            	out.write("% Etat initial du plateau de jeu:");
            else
            	out.write("% Etat du plateau de jeu au coup : "+ Integer.toString(16-pieces.size()));
            out.newLine();
            out.write("%   A    B    C   D");
            out.newLine();
            for (int i = 0; i < TAILLE; i++) {  
            	out.write(i+1 + " ");
                for (int j = 0; j < TAILLE; j++) {
                    out.write(plateau[i][j]+" ");
                }
                out.write(i+1);
                out.newLine();
            }
            out.write("%   A    B    C   D");

            out.close(); 
         } catch (IOException e) {
        	 System.out.println(e.getMessage());
         }
		
	}

	@Override
	public boolean estchoixValide(String choose, String player) {
        return pieces.contains(choose);
	}

	@Override
	public boolean estmoveValide(String move, String player) {
		int[] pos = getPosition(move);
		if (pos==null)
			return false;
		if (plateau[pos[0]][pos[1]]==VIDE)
			return true;
		return false;
	}

	@Override
	public String[] mouvementsPossibles(String player) {
		int cpt = 0;
        String[] mouvPossibles = new String[TAILLE*TAILLE];
        for(int i=0; i<TAILLE;i++)
        {
            for(int j=0; j<TAILLE;j++){
                if(plateau[i][j]==VIDE){
                	
                    mouvPossibles[cpt] = colonnes.get(j)+i ;
                    cpt ++;
                }
            }
        }
        return mouvPossibles;
	}

	@Override
	public String[] choixPossibles(String player) {
		return (String[]) pieces.toArray();
	}

	@Override
	public void play(String move, String choose, String player) {
		if (estchoixValide(choose, player) && estmoveValide(move, player))
		{
			int pos[] = getPosition(move);
			plateau[pos[0]][pos[1]]=choose;
			pieces.remove(choose);
		}
		if (finDePartie())
		{
			System.out.println("FINI GAGNE");
		}
		System.out.println(toString());
		
	}

	@Override
	public boolean finDePartie() {		
		for(int i=0; i<TAILLE;i++)
    	{
			/* Ligne */
			//Si ligne remplie 
			if (plateau[i][0] != VIDE &&plateau[i][1] != VIDE &&plateau[i][2] != VIDE &&plateau[i][3] != VIDE){
				//Pour chaque carractéristique
				for(int j=0; j<TAILLE;j++)
    			{
					//Test ligne
					if(plateau[i][0].charAt(j)==plateau[i][1].charAt(j) && plateau[i][1].charAt(j)==plateau[i][2].charAt(j) && plateau[i][2].charAt(j)==plateau[i][3].charAt(j) ){
						return true;
					}
				}
			}
			/* Colonne */
			//Si colonne remplie 
			if (plateau[0][i] != VIDE &&plateau[1][i] != VIDE &&plateau[2][i] != VIDE &&plateau[3][i] != VIDE){
				//Pour chaque carractéristique
				for(int j=0; j<TAILLE;j++)
    			{
					//Test colonne
					if(plateau[0][i].charAt(j)==plateau[1][i].charAt(j) && plateau[1][i].charAt(j)==plateau[2][i].charAt(j) && plateau[2][i].charAt(j)==plateau[3][i].charAt(j) ){
						return true;
					}
				}
			}
		}

		/* Diagonal */
		// Si diagonale 1 remplie
		if (plateau[0][0] != VIDE && plateau[1][1] != VIDE &&plateau[2][2] != VIDE &&plateau[3][3] != VIDE){
			//Pour chaque carractéristique
			for(int j=0; j<TAILLE;j++)
			{
				//Test colonne
				if(plateau[0][0].charAt(j)==plateau[1][1].charAt(j) && plateau[1][1].charAt(j)==plateau[2][2].charAt(j) && plateau[2][2].charAt(j)==plateau[3][3].charAt(j) ){
					return true;
				}
			}
		}

		// Si diagonale 2 remplie
		if (plateau[0][3] != VIDE && plateau[1][2] != VIDE &&plateau[2][1] != VIDE &&plateau[3][0] != VIDE){
			//Pour chaque carractéristique
			for(int j=0; j<TAILLE;j++)
			{
				//Test colonne
				if(plateau[0][3].charAt(j)==plateau[1][2].charAt(j) && plateau[1][2].charAt(j)==plateau[2][1].charAt(j) && plateau[2][1].charAt(j)==plateau[3][0].charAt(j) ){
					return true;
				}
			}
		}

		/* Carré */
		for(int i=0; i<TAILLE-1;i++)
    	{
			//Carré remplie
			if (plateau[i][i] != VIDE && plateau[i][i+1] != VIDE && plateau[i+1][i] != VIDE &&plateau[i+1][i+1] != VIDE){
				//Pour chaque carractéristique
				for(int j=0; j<TAILLE;j++)
				{
					//Test colonne
					if(plateau[i][i].charAt(j)==plateau[i][i+1].charAt(j) && plateau[i][i+1].charAt(j)==plateau[i+1][i].charAt(j) && plateau[i+1][i].charAt(j)==plateau[i+1][i+1].charAt(j) ){
						return true;
					}
				}
			}
		}

		//Test plus de coup possible
		if(pieces.isEmpty()){
			return true;
		}

		return false;
	}
	
	private int[] getPosition(String move)
	{
		try{
			int[] pos= new int[2];
			pos[0]=Integer.parseInt(move.substring(1,2))-1;
			pos[1] = colonnes.indexOf(move.substring(0,1));
			return pos;
		
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for(int i=0 ; i<TAILLE ; i++){
			for(int j=0 ; j<TAILLE ; j++){
				sb.append(plateau[i][j]+" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	

}
