import javax.swing.*;

public class ControleurChatFenetre extends JFrame {
    public ControleurChatFenetre(Chat chat, String pseudo) {
        setTitle("Envoyer un message");
        setSize(400, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ControleurChat controleur = new ControleurChat(chat, pseudo);
        add(controleur);

        setVisible(true);
    }
}
