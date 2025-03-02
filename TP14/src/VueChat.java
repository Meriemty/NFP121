import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class VueChat extends JTextArea implements PropertyChangeListener {

    public VueChat(Chat chat) {
        chat.ajouterObservateur(this);
        setEditable(false);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("nouveauMessage".equals(evt.getPropertyName())) {
            Message message = (Message) evt.getNewValue();
            append(message.toString() + "\n");
        }
    }
}

