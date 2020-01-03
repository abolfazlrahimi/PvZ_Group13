package commands.Menu;

public class ShopCollection implements Menu {
    Menu parentMenu = new MainMenu();

    @Override
    public void help() {
        System.out.println("help : showing commands" +
                "shoe hand : shoe cards that you select" +
                "shoe collection : show cards in your collection" +
                "select : go to shop menu" +
                "play : go to shop menu" +
                "exit : go to pvp"
        );
    }

    @Override
    public void exit(Menu menu) {
        menu=this.parentMenu;
    }
}