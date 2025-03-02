public class Main {
    public static void main(String[] args) {
        Chat chat = new Chat();
        ChatObserver observer = new ChatObserver();
        chat.ajouterObservateur(observer);

        new ChatSwing(chat, "Myriam");
        new ChatSwing(chat, "Sarah");

        chat.ajouter(new MessageTexte("Meriem", "Bonjour tout le monde !"));
        chat.ajouter(new MessageTexte("Sarah", "Salut Myriam !"));
    }
}
