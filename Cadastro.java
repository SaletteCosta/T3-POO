import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
public class Cadastro {
    private ArrayList<Equipe> equipes;
    public Cadastro() {
        equipes = new ArrayList<>();
    }
    public boolean addEquipe(Equipe equipe) {
        if (buscarNome(equipe.getCodinome()) != null) {
            return false;
        }
        equipes.add(equipe);
        Comparator<Equipe> comparador = Comparator.comparing(Equipe::getCodinome);
        Collections.sort(equipes, comparador);
        return true;
    }
    public String buscarNome(String codinome) {
        int i;
        for (i = 0; i < equipes.size(); i++) {
            Equipe eq = equipes.get(i);
            if (eq.getCodinome().equalsIgnoreCase(codinome)) {
                return codinome;
            }
        }
        return null;
    }
    public ArrayList<Equipe> clonarEquipes() {
        ArrayList<Equipe> cloneEquipes = new ArrayList<>(equipes);
        return cloneEquipes;
    }
}