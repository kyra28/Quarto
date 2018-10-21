public interface Partie1{

    /** initialise un plateau à partir d'un fichier texte
     * @param fileName  le nom du fichier à lire
     */
    public void setFromFile(String fileName);

    /** sauve la configuration de l'état courant (plateau et pièces restantes restantes) dans un fichier
     * @param fileName  le nom du fichier à sauvegarder
     * Le format doit être compatible avec celui utilisé pour la lecture
     */
    public void saveToFile(String fileName);

    /** indique si le coup <choose> est valide pour le joueur <player> sur le plateau courant
     * @param choose le choix de la pièce sous la forme "bgpr"
     * @param player le joueur qui joue (représenté par "noir" ou "blanc")
     */
    public boolean estchoixValide(String choose, String player);

    /** indique si le coup <move> est valide pour le joueur <player> sur le plateau courant
     * @param move le coup à jouer sous la forme "B2"
     * @param player le joueur qui joue (représenté par "noir" ou "blanc")
     */
    public boolean estmoveValide(String move, String player);

    /** calcule les coups possibles pour le joueur <player> sur le plateau courant
     * @param player le joueur qui joue (représenté par "noir" ou "blanc")
     */
    public String[] mouvementsPossibles(String player);

    /** calcule les choix possibles pour le joueur <player> sur les pièces restantes
     * @param player le joueur qui joue (représenté par "noir" ou "blanc")
     */
    public String[] choixPossibles(String player);

    /** modifie le plateau en jouant le coup move avec la pièce choose
     * @param move le coup à jouer sous la forme "B2"
     * @param choose le choix de la pièce sous la forme "bgpr"
     * @param player le joueur qui joue (représenté par "noir" ou "blanc")
     */
    public void play(String move, String choose, String player);

    /** vrai lorsque le plateau correspond à une fin de partie
     */

    public boolean finDePartie();

}