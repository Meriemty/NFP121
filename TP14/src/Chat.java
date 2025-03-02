import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Chat implements Iterable<Message> {
    
    private List<Message> messages;
    private PropertyChangeSupport support;

    public Chat() {
        this.messages = new ArrayList<>();
        this.support = new PropertyChangeSupport(this);
    }

    public void ajouter(Message m) {
        this.messages.add(m);
        support.firePropertyChange("nouveauMessage", null, m);
    }

    public void ajouterObservateur(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void supprimerObservateur(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    @Override
    public Iterator<Message> iterator() {
        return messages.iterator();
    }
}
