import javax.swing.JLabel;

/**
 * This class extends JLabel and these objects are displayed by the squares on
 * the BoardGame JFrame
 * 
 * @author Cooper Riley, David Gayda
 * 
 */
public class Player extends JLabel {

	private String name;
	private int savePosition;

	/**
	 * Constructs a player object
	 * 
	 * @param name
	 *            the name to be displayed by the JLabel
	 */
	public Player(String name) {
		setPlayerName(name);
		setSavePosition(-1);
	}

	/**
	 * Sets the name of the player object
	 * 
	 * @param name
	 *            name to be set to the object
	 */
	public void setPlayerName(String name) {
		this.setText(name);
		this.name = name;
	}

	/**
	 * returns the name of the player object
	 * 
	 * @return name of the player object
	 */
	public String getPlayerName() {
		return this.name;
	}

	/**
	 * Sets the save position of the player, this happens when the player lands
	 * on a save square and the number remembers which square it was. Default as
	 * -1 for the start square
	 * 
	 * @param save
	 *            int save number for which square was landed on
	 */
	public void setSavePosition(int save) {
		savePosition = save;
	}

	/**
	 * Returns the save number of the save square the player landed on last
	 * 
	 * @return
	 */
	public int getSavePosition() {
		return savePosition;
	}

}
