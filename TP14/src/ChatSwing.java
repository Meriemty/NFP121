import javax.swing.*;
import java.awt.*;

public class ChatSwing extends JFrame {
    public ChatSwing(Chat chat, String pseudo) {
        setTitle("Chat : " + pseudo);
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        VueChat vueChat = new VueChat(chat);
        ControleurChat controleurChat = new ControleurChat(chat, pseudo);

        add(new JScrollPane(vueChat), BorderLayout.CENTER);
        add(controleurChat, BorderLayout.SOUTH);

        setVisible(true);
    }
}
