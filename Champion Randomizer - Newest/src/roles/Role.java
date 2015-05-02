package roles;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Role {
	
	protected final String roleName;
	protected ArrayList<Champion> champions;
	protected final int ROLEID;
	
	
	public Role(String name, int id) {
		roleName = name;
		ROLEID = id;
		champions = new ArrayList<Champion>();
	}
	
	public void loadChampions() {
		Champion[] fullList = Champion.values();
		champions = new ArrayList<Champion>();
		
		for(int i = 0; i<fullList.length;i++) {
			Champion c = fullList[i];
			addChampion(c);
		}
		sortAfterAlp();
		setNewId();
	}
	
	private void setNewId() {
		for(int i = 0; i<champions.size(); i++) {
			champions.get(i).setID(i);
		}
	}

	private static boolean error = false;
	
	public void sortAfterAlp() {
		try{
			//champions.sort(champions.get(0));
		} catch(NoSuchMethodError e) {
			if(!error) {
				JOptionPane.showMessageDialog(null, "Please update your java version! Some features might not works as expected!");
				error = true;
			}
		}
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public ArrayList<Champion> getChampions() {
		return champions;
	}
	
	protected void addChampion(Champion c) {
		champions.add(c);
		BuildFactory f = new BuildFactory(this);
		c.addBuild(f, ROLEID);
	}
	
	protected void addChampion(Champion c, Item[] items, int i) {
		addChampion(c, new Spell[]{Spell.ignite, Spell.flash}, new Item[]{Item.standard, Item.standard, Item.standard}, items, i);
	}
	
	protected void addChampion(Champion c, Spell[] spells, Item[] startingItems, Item[] items, int i) {
		champions.add(c);
		BuildFactory f = new BuildFactory(spells, startingItems, items, c, this, i);
		c.addBuild(f, ROLEID);
	}

}
