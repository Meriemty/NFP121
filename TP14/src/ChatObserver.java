import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ChatObserver implements PropertyChangeListener {
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("nouveauMessage".equals(evt.getPropertyName())) {
            Message message = (Message) evt.getNewValue();
            System.out.println("Nouveau message : " + message);
        }
    }
}
