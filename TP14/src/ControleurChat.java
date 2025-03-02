import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ControleurChat extends JPanel {
    private JTextField saisie;
    private Chat chat;
    private String pseudo;

    public ControleurChat(Chat chat, String pseudo) {
        this.chat = chat;
        this.pseudo = pseudo;

        saisie = new JTextField(20);
        JButton envoyer = new JButton("OK");

        envoyer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chat.ajouter(new MessageTexte(pseudo, saisie.getText()));
                saisie.setText("");
            }
        });

        add(saisie);
        add(envoyer);
    }
}
