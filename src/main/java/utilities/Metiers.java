package utilities;

import metiers.EtudiantBusiness;
import metiers.OptionBusiness;

/**
 * Les classes métier fournies remettent leurs listes à zéro à chaque "new".
 * On les crée donc une seule fois ici, et les deux ressources les partagent.
 */
public class Metiers {
    public static final OptionBusiness OPTIONS = new OptionBusiness();
    public static final EtudiantBusiness ETUDIANTS = new EtudiantBusiness();
}
