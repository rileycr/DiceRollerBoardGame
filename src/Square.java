import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.Border;

// Maybe an arraylist to tell who is on the square.

/**
 * A square that is used in the BoardGame class
 * 
 * @author Cooper Riley, David Gayda
 */

public class Square extends JPanel {

	public static final char ROLL = 'r', SHUFFLE = 's', SAVE = 'v', BACK = 'b',
			START = 't', END = 'e', MOVE_FORWARD = 'f';
	private char status;
	private ArrayList<Player> players;
	private JPanel squarePanel;
	private int saveNumber;

	/**
	 * Creates a square with a given type and status
	 * 
	 * @param type
	 *            Label to be displayed if it is a special square, i.e. 'roll
	 *            again' or 'go back'
	 * @param status
	 *            Will change depending on wether or not a player is on the
	 *            square.
	 */

	public Square() {
		super();
		this.players = new ArrayList<Player>();
		squarePanel = new JPanel();
		squarePanel.setLayout(new FlowLayout());
		this.setLayout(new BorderLayout());
		this.add(squarePanel, BorderLayout.CENTER);
		squarePanel.setBackground(Color.YELLOW);
		setBackground(Color.WHITE);

		Border blackline = BorderFactory.createLineBorder(Color.BLACK, 1);
		setBorder(blackline);
	}

	/**
	 * Constructor if a square is given a specific status.
	 * 
	 * @param status
	 *            the status of the square.
	 */
	public Square(char status) {
		this();
		setStatus(status);
	}

	/**
	 * Sets the status of the square, checks to make sure the status is a legal
	 * status.
	 * 
	 * @param status
	 *            the satus to set the square to.
	 */
	public void setStatus(char status) {
		if (status != ROLL && status != SHUFFLE && status != SAVE
				&& status != BACK && status != START && status != END
				&& status != MOVE_FORWARD)
			throw new IllegalArgumentException("Illegal square status: "
					+ status);
		this.status = status;
	}

	/**
	 * Returns the status of the square.
	 * 
	 * @return status of the square.
	 */
	public char getStatus() {
		return status;
	}

	/**
	 * Sets the save number of only the save point squares, if a square is not a
	 * save square, the number stays 'null'.
	 * 
	 * @param saveNumber
	 *            the number save square it is.
	 */
	public void setSaveNumber(int saveNumber) {
		this.saveNumber = saveNumber;
	}

	/**
	 * Returns the save number of the square.
	 * 
	 * @return save number
	 */
	public int getSaveNumber() {
		return saveNumber;
	}

	/**
	 * Adds a player to the square and also to the panel that will display the
	 * players as on that square on the board.
	 * 
	 * @param player
	 *            player to be added
	 */
	public void addPlayer(Player player) {
		this.players.add(player);
		this.squarePanel.add(player);
	}

	/**
	 * Removes a player from the square and the panel.
	 * 
	 * @param player
	 *            player to be removed
	 */
	public void removePlayer(Player player) {
		this.players.remove(player);
		this.squarePanel.remove(player);
	}

	/**
	 * Returns the ArrayList of all players on that square;
	 * 
	 * @return List of players.
	 */
	public ArrayList<Player> getPlayers() {
		return players;
	}

	/**
	 * Determines if the square has the player on it.
	 * 
	 * @param player
	 *            the player being looked for
	 * @return true or false depending on if it has the player.
	 */
	public boolean hasPlayer(Player player) {
		return players.contains(player);
	}

	public void paintComptonent(Graphics g) {
		super.paintComponent(g);
	}

}