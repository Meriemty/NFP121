
import javax.swing.*;

public class VueChatFenetre extends JFrame {
    public VueChatFenetre(Chat chat) {
        setTitle("Chat");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        VueChat vueChat = new VueChat(chat);
        add(new JScrollPane(vueChat));

        setVisible(true);
    }
}
