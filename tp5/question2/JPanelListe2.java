package question2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class JPanelListe2 extends JPanel implements ActionListener, ItemListener {

    private JPanel cmd = new JPanel();
    private JLabel afficheur = new JLabel();
    private JTextField saisie = new JTextField();

    private JPanel panelBoutons = new JPanel();
    private JButton boutonRechercher = new JButton("rechercher");
    private JButton boutonRetirer = new JButton("retirer");

    private CheckboxGroup mode = new CheckboxGroup();
    private Checkbox ordreCroissant = new Checkbox("croissant", mode, false);
    private Checkbox ordreDecroissant = new Checkbox("décroissant", mode, false);

    private JButton boutonOccurrences = new JButton("occurrence");

    private JButton boutonAnnuler = new JButton("annuler");

    private TextArea texte = new TextArea();

    private List<String> liste;
    private Map<String, Integer> occurrences;
    
    // List Memento Stack
    private Stack<ListMemento> undoStack = new Stack<>();

    public JPanelListe2(List<String> liste, Map<String, Integer> occurrences) {
        this.liste = liste;
        this.occurrences = occurrences;

        cmd.setLayout(new GridLayout(3, 1));

        cmd.add(afficheur);
        cmd.add(saisie);

        panelBoutons.setLayout(new FlowLayout(FlowLayout.LEFT));
        panelBoutons.add(boutonRechercher);
        panelBoutons.add(boutonRetirer);
        panelBoutons.add(new JLabel("tri du texte :"));
        panelBoutons.add(ordreCroissant);
        panelBoutons.add(ordreDecroissant);
        panelBoutons.add(boutonOccurrences);
        panelBoutons.add(boutonAnnuler);
        cmd.add(panelBoutons);


        if (liste != null && occurrences != null){
            afficheur.setText(liste.getClass().getName() + " et "+ occurrences.getClass().getName());
            texte.setText(liste.toString());
        } else
            texte.setText("la classe Chapitre2CoreJava semble incomplète");

        setLayout(new BorderLayout());

        add(cmd, "North");
        add(texte, "Center");

        boutonRechercher.addActionListener(this);
        boutonRetirer.addActionListener(this);
        boutonAnnuler.addActionListener(this);
        boutonOccurrences.addActionListener(this);
        
        ordreCroissant.addItemListener(this);
        ordreDecroissant.addItemListener(this);
        
        saisie.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent e) {
                String keyText = KeyEvent.getKeyText( e.getKeyCode() );
                if (keyText.equals("Enter"))
                    actionPerformed(new ActionEvent(saisie, ActionEvent.ACTION_PERFORMED, null));
            }
            public void keyReleased(KeyEvent e) { }
            public void keyTyped(KeyEvent e) { }
        });
        
        annulerBoutonState();
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            boolean res = false;
            if (ae.getSource() == boutonRechercher || ae.getSource() == saisie) {
                res = liste.contains(saisie.getText());
                Integer occur = occurrences.get(saisie.getText());
                afficheur.setText("résultat de la recherche de : "
                    + saisie.getText() + " -->  " + res);
            } else if (ae.getSource() == boutonRetirer) {
                saveState();
                res = retirerDeLaListeTousLesElementsCommencantPar(saisie.getText(), occurrences);
                afficheur.setText("résultat du retrait de tous les éléments " +
                    "commençant par -->  " + saisie.getText() + " : " + res);
            } else if (ae.getSource() == boutonOccurrences) {
                Integer occur = occurrences.get(saisie.getText());
                if (occur != null)
                    afficheur.setText(" -->  " + occur + " occurrence(s)");
                else
                    afficheur.setText(" -->  ??? ");
            } else if (ae.getSource() == boutonAnnuler)
                undo();
                
            texte.setText(liste.toString());

        } catch (Exception e) {
            afficheur.setText(e.toString());
        }
    }

    public void itemStateChanged(ItemEvent ie) {
        saveState();
        if (ie.getSource() == ordreCroissant)
            Collections.sort(liste);
        else if (ie.getSource() == ordreDecroissant)
            Collections.sort(liste, new DescendingComparator());
        
        texte.setText(liste.toString());
    }

    private boolean retirerDeLaListeTousLesElementsCommencantPar(String prefixe,
            Map<String, Integer> occurrences) {
        boolean resultat = false;
        Iterator<String> iter = liste.iterator();
        while (iter.hasNext()) {
            String element = iter.next();
            if (element.startsWith(prefixe)) {
                iter.remove();
                occurrences.put(element, occurrences.getOrDefault(element, 1) - 1);
                resultat = true;
            }
        }
        return resultat;
    }

    // DESCENDING COMPARATOR
    private class DescendingComparator implements Comparator<String> {
        public int compare(String s1, String s2) {
            return s2.compareTo(s1);
        }
    }
    
    //////////////////////////
    // MEMENTO METHODS
    //////////////////////////
    private void saveState() {
        undoStack.push(new ListMemento(liste));
        System.out.println("New save state added! " + undoStack.size() );
        updateUIData(occurrences);
    }
    
    private void undo() {
        if (!undoStack.isEmpty()) {
            ListMemento memento = undoStack.pop();
            liste = memento.getState();
            System.out.println("Undo! " + undoStack.size() );
            updateUIData(occurrences);
        }
    }
    
    private void updateUIData(Map<String, Integer> occurrences) {
        occurrences.clear();
        occurrences.putAll(Chapitre2CoreJava2.occurrencesDesMots(liste));
        annulerBoutonState();
    }
    
    // Activer le bouton s'il y a des Undos et le d�sactiver sinon
    private void annulerBoutonState() {
        if (undoStack.isEmpty())
            boutonAnnuler.setEnabled(false);
        else
            boutonAnnuler.setEnabled(true);
    }
}