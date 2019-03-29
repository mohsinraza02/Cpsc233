package codes;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.text.Text;

public class Player extends Entities{
	
	//GUI
	private int speed;
	private int currentRoomX = 0;
	private int currentRoomY = 0;
	private int timeLft = 0;
	private String facing = "east";
	
	//LOGIC
	private int[] stats = {0, 0, 0, 0};
	private ArrayList<Collectable> inventory = new ArrayList<Collectable>();
	private double health;// default health
	private double attack;// default attack damage
	private int attackCap, defenseCap, healthCap, skillP;

	public Player(double health, double attack, int maxHealth, int skillP) {
		super(50, 650, 80, 100, "Player", "player4.png");
		// TODO Auto-generated constructor stub
		this.speed = 4;
		
		this.health = health;
		this.attack = attack;
		this.healthCap = maxHealth;
		this.skillP = skillP;
	}
	
	// LOGIC CODES START HERE
	
	public int getHealthCap() {
		return healthCap;
	}
	
	public void setHealthCap(int healthCap) {
		this.healthCap = healthCap;
	}
	
	public int getStat(int index) {
		return this.stats[index];
	}
	
	public double getHealth() {
		return health;
	}
	
	public void setHealth(double d) {
		this.health = d;
	}

	public double getAttack() {
		return attack;
	}

	public void setAttack(double attack) {
		this.attack = attack;
	}
	
	public void setSkillP(int s) {
		this.skillP = s;
	}

	public int getSkillP() {
		return this.skillP;
	}
	
	/**
	 * TODO: Implement reroll feature.
	 * Generate 4 random stats for each category: Strength, Defense, Attack, Luck,
	 * Speed The sum of the stats that is possible is 25 because it would be too
	 * unbalanced to get perfect rolls. The sum of the stats will always be 25 as
	 * well, so when you start the game it will always be perfectly balanced. Added
	 * a reroll method (credit to Josh)
	 */
	public int[] generateStats() {

		Random randStat = new Random();
		int maxStat = 25;
		Scanner kb = new Scanner(System.in);

		boolean reroll;
		do {
			reroll = false;
			while (maxStat != 0) {
				for (int i = 0; i < 4; i++) {
					int randomStat = randStat.nextInt(10);
					randomStat += 1;
					stats[i] = randomStat;
					maxStat -= randomStat;
				}
				if (maxStat != 0) {
					maxStat = 25;
				}
			}
			System.out.println("Attack:" + stats[0]);
			System.out.println("Defense:" + stats[1]);
			System.out.println("Speed:" + stats[2]);
			System.out.println("Luck:" + stats[3]);
//			System.out.println("Press 'r' to reroll your stats, if you are satisfied press any key to continue");
//			String input = kb.nextLine();
//			if (input.equals("r")) {
//
//				reroll = true;
//				maxStat = 25;
//			}

		} while (maxStat != 0 && reroll == true);

		return stats;

	}
	
	/**
	 * Print statistics of the player
	 */
	public Text[] updateStatsText(Text[] t) {
		
		t[0].setText("Attack: 	" + stats[0]);
		t[1].setText("Defense: 	" + stats[1]);
		t[2].setText("Speed: 	" + stats[2]);
		t[3].setText("Luck: 	" + stats[3]);

		return t;
	}
	
	/**
	 * Add and item to the player's inventory.
	 * 
	 * @param item
	 */
	public void addCollectableToInventory(Collectable cb) {
		System.out.println("Added to inventory: " + cb.getName());
		inventory.add(cb);
	}
	
	public void interactWithItem(Instant instant) {
		System.out.println("you used/picked up " + instant.getName());
		System.out.println("You gained " +  instant.getDesc()  + "\n");
		stats[instant.getType()] += instant.getValue();
	}
	
	/**
	 * Attack an enemy that is passed in.
	 * @param enemy
	 * 
	 */
	public void attack(Enemy enemy) {
		// enemy.setHealth(getHealth() - );
		Scanner skip = new Scanner(System.in);		
		System.out.println("You strike a shot at a question.");
		skip.nextLine();
		enemy.setHealth(enemy.getHealth() - this.attack);
		System.out.println("You did " + this.attack + " damage to the " + enemy.getName());
		System.out.println("The " + enemy.getName() + " has " + enemy.getHealth() + " health left.");
	}
	
	/**
	 * Lower the test's defense.
	 * @param enemy
	 */
	public void deepBreath(Enemy enemy) {
		Scanner skip = new Scanner(System.in);
		int defenseMod = (int) (this.getAttack() * 0.20);
		//System.out.println(defenseMod);
		enemy.setDefense(enemy.getDefense() - defenseMod);
		if (defenseMod == 0) {
			System.out.println("You try to take a deep breath, but the test just sits there with almost the same intimidation factor as before.");
			System.out.println("The test looks very slightly weaker.");
			enemy.setDefense(enemy.getDefense() - 1);
		} else {
			System.out.println("You take a quick breather. The test's intimidation is lowered.");
			System.out.println("The test's defense was lowered a bit due to your resolve.");
		}
	}
	
	/**
	 * Heal yourself for a small amount.
	 */
	public void stretch() {
		int healMod = (int) ((this.getAttack() * 0.25) + (this.getHealthCap() * 0.15));
		if ((this.getHealth() + healMod) > this.getHealthCap()) {
			this.setHealth(this.getHealthCap());
		} else {
			this.setHealth(this.getHealth() + healMod);
		}
		System.out.println("You stretch a little, telling yourself it'll be okay. Hopefully.");
		System.out.println("You heal yourself for " + healMod + " health.");
	}
	
	public void cheat(Enemy enemy) {
		int attackMod = (int)(this.getAttack() * 2.5);
		enemy.setHealth(enemy.getHealth() - attackMod);

		System.out.println("Your minds telling you 'no', but your body is telling you 'yea'");
		System.out.println("BOOM! Critical Strike!");
		System.out.println("You dealt " + attackMod + " damage!");
	}
	
	public void cry(Enemy enemy) {
		int damageMod = (int)(this.getAttack() * 0.20);
		enemy.setAttack(enemy.getAttack() - damageMod);
		if (damageMod == 0) {
			System.out.println("You cry...alot, but the test just sits there with almost the same intimidation factor as before.");
			System.out.println("It wasn't very effective");
			enemy.setAttack(enemy.getAttack() - 1);
		}
		else{
			System.out.println("You cry...alot, everyone around starts looking at you, but luckily\nthere is a kind soul who slides his scantron to the\n side of his desk for you to look at");
			System.out.println("The tests damage was lowered due to tears");

		}
	}
	
	public boolean skill(Enemy enemy) {
		Scanner kb = new Scanner(System.in);
		boolean pick = false; 
		boolean valid = false;
		do {
			System.out.println("What skill would you like to use?\n(a) Take a Breather(Lower Enemy Defense)\n(b) Stretch (Heal)\n(c) Cheat (Guaranteed Critical Strike)\n(d) Cry (Lower Enemy Attack)\n(x) Back");
			String input = kb.nextLine();
			if (input.toLowerCase().equals("a")) {
				deepBreath(enemy);
				pick = true;
				valid = true;
			} else if(input.toLowerCase().equals("b")) {
				stretch();
				pick = true;
				valid = true;

			}else if (input.toLowerCase().equals("c")){
				cheat(enemy);
				pick = true;
				valid = true;

			}else if (input.toLowerCase().equals("d")){
				cry(enemy);
				pick = true;
				valid = true;

			}else if (input.toLowerCase().equals("x")) {
				valid = true; 
			}

			} while(valid == false);
			return pick;
		}

	/**
	 * Update the attack with adding the item bonuses
	 */
	public void updateAttack() {
		// Adds damage depending on the stats that you get in the beginning
		// int newAttack = attack + (stats[0]);
		this.attack += (stats[0]);
	}

	// LOGIC CODES END
	// GUI CODES START HERE
	
	// Getters for the room's coordinates
	public int getCurrentRoomX() {
		return currentRoomX;
	}
	
	public int getCurrentRoomY() {
		return currentRoomY;
	}
	
	/**
	 * Moves the to the left
	 * TODO: Implement walking animation
	 */
	public void moveLeft() {
		// check if the user is hitting the left edge
		if (getTranslateX() < 0) {
			// check if user is in the very left room. If they're not, move to the left room.
			if (currentRoomX != 0) {
				setTranslateX(750);
				currentRoomX--;
				setTranslateX(getTranslateX() - speed);
			}
		} else {
			setTranslateX(getTranslateX() - speed);
		}
		if (facing != "west") {
			super.flipSprite("left");
			facing = "west";
		}
	}
	
	/**
	 * Moves the player to the right
	 */
	public void moveRight() {
		if (getTranslateX() > 745) {
			if (currentRoomX != 2) {
				setTranslateX(-39);
				currentRoomX++;
				setTranslateX(getTranslateX() + speed);
			}
		} else {
			setTranslateX(getTranslateX() + speed);
		}
		
		// if statement so the change only happens once
		if (facing != "east") {
			super.flipSprite("right");
			facing = "east";
		}
	}
	
	/**
	 * Moves the player up
	 */
	public void moveUp() {
		if (getTranslateY() < 0) {
			if (currentRoomY != 2) {
				setTranslateY(800);
				currentRoomY++;
				setTranslateY(getTranslateY() - speed);
			}
		} else {
			setTranslateY(getTranslateY() - speed);
		}
	}
	
	/**
	 * Moves the player down
	 */
	public void moveDown() {
		if (getTranslateY() > 700) {
			if (currentRoomY != 0) {
				setTranslateY(-79);
				currentRoomY--;
				setTranslateY(getTranslateY() + speed);
			}
		} else {
			setTranslateY(getTranslateY() + speed);			
		}
	}

	@Override
	public String getName() {
		return "Player";
	}

	@Override
	public String getDesc() {
		return "This is you!";
	}

}