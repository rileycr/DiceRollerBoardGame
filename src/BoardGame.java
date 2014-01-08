import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Class which creates a maze type boardgame with multiple types of squares and
 * 2 human players;
 * @author Cooper Riley, David Gayda
 * 
 */
public class BoardGame extends JFrame {
    
    private ArrayList<Square>    squares;
    private ArrayList<Player>    players;
    private Square                start, end;
    private JButton                roll;
    private JLabel                whoseTurn;
    private String                playerTurn;
    private JPanel                boardPanel, framePanel;
    
    /**
     * Constructor for the BoardGame
     * @param names
     *        The ArrayList of names of the players that will be playing
     */
    public BoardGame(ArrayList<String> names) {
        super("Let's play a game!");
        players = new ArrayList<Player>();
        for (int i = 0; i < names.size(); i++) {
            if (!names.get(i).equals("none"))
                players.add(new Player(names.get(i)));
        }
        
        // Creating the JFrame to hold the board game.
        this.setResizable(false);
        this.setBounds(0, 0, 680, 590);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Centering on the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        this.setLocation(screenWidth / 2 - this.getWidth() / 2,
                screenHeight / 2 - this.getHeight() / 2);
        
        // framePanel is what contains boardPanel (which holds the
        // squares) and it holds the roll button and the JLabel which
        // Tells whose turn it is.
        boardPanel = new JPanel();
        framePanel = new JPanel();
        framePanel.setLayout(new BorderLayout());
        boardPanel.setLayout(null);
        framePanel.add(boardPanel, BorderLayout.CENTER);
        boardPanel.setBackground(Color.BLUE);
        
        ButtonListener buttonListener = new ButtonListener();
        roll = new JButton("Roll");
        roll.addActionListener(buttonListener);
        
        playerTurn = players.get(0).getPlayerName();
        whoseTurn = new JLabel("It's " + playerTurn + "'s" + " Turn");
        
        start = new Square('t');
        end = new Square('e');
        
        start.add(new JLabel("       Start"), BorderLayout.NORTH);
        for (int i = 0; i < players.size(); i++) {
            start.addPlayer(players.get(i));
        }
        end.add(new JLabel("      Finish"), BorderLayout.NORTH);
        
        squares = new ArrayList<Square>();
        createArray();
        
        shuffle(squares);
        placeSquares(squares);
        JPanel south = new JPanel();
        framePanel.add(boardPanel, BorderLayout.CENTER);
        framePanel.add(south, BorderLayout.SOUTH);
        south.add(roll);
        south.add(whoseTurn);
        this.add(framePanel);
        this.setVisible(true);
    }
    
    /**
     * Method to place all of the squares in the correct layout
     * @param squares
     *        the ArrayList of squares to be placed
     */
    public void placeSquares(ArrayList<Square> squares) {
        boardPanel.add(start);
        start.setBounds(0, 0, 75, 75);
        for (int i = 0; i < 9; i++) {
            boardPanel.add(squares.get(i));
            squares.get(i).setBounds(75 * i, 0, 75, 75);
        }
        for (int i = 0; i < 3; i++) {
            boardPanel.add(squares.get(i + 8));
            squares.get(i + 8).setBounds(600, 75 * i, 75, 75);
        }
        for (int i = 0; i < 9; i++) {
            boardPanel.add(squares.get(i + 10));
            squares.get(i + 10).setBounds(600 - 75 * i, 150, 75, 75);
        }
        for (int i = 0; i < 3; i++) {
            boardPanel.add(squares.get(i + 18));
            squares.get(i + 18).setBounds(0, 150 + 75 * i, 75, 75);
        }
        for (int i = 0; i < 9; i++) {
            boardPanel.add(squares.get(i + 20));
            squares.get(i + 20).setBounds(75 * i, 300, 75, 75);
        }
        for (int i = 0; i < 3; i++) {
            boardPanel.add(squares.get(i + 28));
            squares.get(i + 28).setBounds(600, 300 + 75 * i, 75, 75);
        }
        for (int i = 0; i < 8; i++) {
            boardPanel.add(squares.get(i + 30));
            squares.get(i + 30).setBounds(600 - 75 * i, 450, 75, 75);
        }
        boardPanel.add(end);
        end.setBounds(0, 450, 75, 75);
        repaint();
    }
    
    /**
     * Rolls the die to determine how many spaces the player will go and then
     * determines if that value is still within the board and calls movePlayer()
     * to move the player.
     */
    public void move() {
        int roll = (int) (6 * Math.random()) + 1;
        JOptionPane.showMessageDialog(null, playerTurn + " rolled a "
                + roll, "Roll!", JOptionPane.INFORMATION_MESSAGE);
        
        for (int i = 0; i < players.size(); i++) {
            if (playerTurn.equals(players.get(i).getPlayerName())) {
                int location = findPlayer(players.get(i));
                movePlayer(players.get(i), roll, location);
                repaint();
                return;
            }
        }
    }
    
    /**
     * Determines which square the player is starting from and calls
     * checkStatus() to decide what happens to the player once it lands on that
     * square
     * @param player
     *        the player to move
     * @param roll
     *        amount to be moved
     * @param location
     *        starting location (before moving)
     */
    private void movePlayer(Player player, int roll, int location) {
        if ((location + roll >= squares.size())) {
            end.addPlayer(player);
            repaint();
            diplayWinner();
        }
        if (location == -1) {
            start.removePlayer(player);
            roll++;
            checkStatus(player, start, location, roll);
        } else {
            squares.get(location).removePlayer(player);
            checkStatus(player, squares.get(location), location, roll);
        }
        repaint();
    }
    
    /**
     * Determines the status of the square to be moved to and what to do with
     * the player once they land there.
     * @param player
     *        the player being moved
     * @param square
     *        the square that the player is coming from
     * @param location
     *        location of that square
     * @param roll
     *        amount rolled and to be moved
     */
    private void checkStatus(Player player, Square square, int location,
            int roll) {
        char status = squares.get(location + roll).getStatus();
        if (status == 's') {
            JOptionPane.showMessageDialog(null,
                    "Every day we're shuffling!", "Shuffle!",
                    JOptionPane.INFORMATION_MESSAGE);
            squares.get(location + roll).addPlayer(player);
            shuffle(squares);
            placeSquares(squares);
            nextPlayer(player);
        } else if (status == 'f') {
            int newRoll = roll + 4;
            JOptionPane.showMessageDialog(null,
                    "You get your second wind! Move forward 4 spaces!",
                    "Forward!", JOptionPane.INFORMATION_MESSAGE);
            squares.get(location + newRoll).addPlayer(player);
            nextPlayer(player);
        } else if (status == 'v') {
            JOptionPane.showMessageDialog(null, playerTurn
                    + ", You hit a save point!", "Safe!",
                    JOptionPane.INFORMATION_MESSAGE);
            player.setSavePosition(squares.get(location + roll)
                    .getSaveNumber());
            squares.get(location + roll).addPlayer(player);
            nextPlayer(player);
        } else if (status == 'b') {
            JOptionPane.showMessageDialog(null,
                    playerTurn + "! Go Back!!", "Retreat!",
                    JOptionPane.INFORMATION_MESSAGE);
            if (player.getSavePosition() == -1) {
                start.addPlayer(player);
            } else {
                for (int i = 0; i < squares.size(); i++) {
                    if (squares.get(i).getSaveNumber() == player
                            .getSavePosition()) {
                        squares.get(i).addPlayer(player);
                        nextPlayer(player);
                        return;
                    }
                }
            }
            nextPlayer(player);
            
        } else if (status == 't') {
            squares.get(location + roll).addPlayer(player);
            nextPlayer(player);
        } else if (status == 'e') {
            nextPlayer(player);
        } else if (status == 'r') {
            JOptionPane.showMessageDialog(null, "Roll again " + playerTurn
                    + "!", "Roll Again!", JOptionPane.INFORMATION_MESSAGE);
            playerTurn = player.getPlayerName();
            squares.get(location + roll).addPlayer(player);
            whoseTurn.setText("It's " + playerTurn + "'s Turn");
        } else {
            squares.get(location + roll).addPlayer(player);
            nextPlayer(player);
        }
        repaint();
    }
    
    private void nextPlayer(Player player) {
        playerTurn = players.get(
                (players.indexOf(player) + 1) % players.size())
                .getPlayerName();
        whoseTurn.setText("It's " + playerTurn + "'s Turn");
    }
    
    /**
     * Searches the ArrayList of squares to find which square has the specified
     * player
     * @param player
     *        the player being searched for
     * @return the index of the square in the ArrayList that has the player on
     *         it. Returns -1 for start and -2 for end
     */
    private int findPlayer(Player player) {
        if (start.getPlayers().contains(player))
            return -1;
        for (int i = 0; i < squares.size(); i++) {
            if (squares.get(i).getPlayers().contains(player))
                return i;
        }
        return -2;
    }
    
    /**
     * Takes in the ArrayList of squares to be shuffled and sets the ArrayList
     * to the new order
     * @param squares
     *        the ArrayList to be shuffled
     */
    private void shuffle(ArrayList<Square> squares) {
        
        ArrayList<Square> squares2 = new ArrayList<Square>();
        int i = 0;
        while (i < squares.size()) {
            squares2.add(squares.remove((int) (Math.random() * squares
                    .size())));
        }
        this.squares = squares2;
    }
    
    /**
     * Creates the array of squares that will be placed on the game board. The
     * types of squares are chosen incrementally and in that order initally in
     * the ArrayList
     */
    public void createArray() {
        for (int i = 0; i < 38; i++) {
            if (i < 3) {
                squares.add(new Square('r'));
                squares.get(i).add(new JLabel("   Roll Again"),
                        BorderLayout.NORTH);
            } else if (i < 5) {
                squares.add(new Square('s'));
                squares.get(i).add(new JLabel("      Shuffle"),
                        BorderLayout.NORTH);
            } else if (i < 10) {
                squares.add(new Square('b'));
                squares.get(i).add(new JLabel("     Go Back"),
                        BorderLayout.NORTH);
            } else if (i < 16) {
                squares.add(new Square('v'));
                squares.get(i).add(new JLabel("Save Point " + (i - 9)),
                        BorderLayout.NORTH);
                squares.get(i).setSaveNumber(i - 9);
            } else if (i < 20) {
                squares.add(new Square('f'));
                squares.get(i).add(new JLabel(" Forward 4"),
                        BorderLayout.NORTH);
            } else {
                squares.add(new Square());
                squares.get(i).add(new JLabel(""), BorderLayout.NORTH);
            }
        }
    }
    
    /**
     * This is called when a player lands on the end square, it displays the
     * winner and asks if the players would like a rematch.
     */
    private void diplayWinner() {
        int answer = JOptionPane.showConfirmDialog(null, "New Game?",
                playerTurn + " Wins!!!", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.NO_OPTION) {
            System.exit(0);
        } else {
            ArrayList<String> names = new ArrayList<String>();
            for (int i = 0; i < 4; i++) {
                names.add(JOptionPane.showInputDialog("Enter Player "
                        + (i + 1) + "'s Name, 'none', or 'computer'"));
            }
            BoardGame newGame = new BoardGame(names);
        }
        
    }
    
    /**
     * Listiner that listenes for the roll button to be pressed, it calles the
     * move() method.
     * @author Cooper Riley, David Gayda
     * 
     */
    public class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == roll) {
                move();
            }
        }
    }
    
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<String>();
        for (int i = 0; i < 4; i++) {
            names.add(JOptionPane.showInputDialog("Enter Player "
                    + (i + 1) + "'s Name, 'none', or 'computer'"));
        }
        BoardGame game = new BoardGame(names);
    }
}
