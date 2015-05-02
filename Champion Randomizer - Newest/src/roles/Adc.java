package roles;

public class Adc extends Role{

	public Adc(String name, int id) {
		super(name, id);
	}
	
	@Override
	public void loadChampions() {
		Champion[] c = Champion.values();
		
		addChampion(c[0]);
		addChampion(c[7]); //Sivir
		addChampion(c[10]); //Tristana
		addChampion(c[13]); //Ashe
		addChampion(c[20]); //Twitch
		addChampion(c[31]); //Corki
		addChampion(c[47]); //Ezreal
		addChampion(c[54]); //Kog'Maw
		addChampion(c[58]); //Urgot
		addChampion(c[59]); //Miss Fortune
		addChampion(c[67]); //Caitlyn
		addChampion(c[76], new Spell[]{Spell.heal, Spell.flash}, new Item[]{Item.healthpot, Item.doransblade}, new Item[]{Item.botrk, Item.berserkers, Item.phantomdancer, Item.lastwhisper, Item.infinityedge, Item.banshees}, 1); //Vayne
		addChampion(c[85]); //Graves
		addChampion(c[97]); //Varus
		addChampion(c[99]); //Draven
		addChampion(c[111]); //Quinn
		addChampion(c[115]); //lucian
		addChampion(c[116]); //Jinx
		
		sortAfterAlp();
	}
}
