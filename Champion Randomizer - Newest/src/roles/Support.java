package roles;

public class Support extends Role{

	public Support(String name, int id) {
		super(name, id);
	}
	
	@Override
	public void loadChampions() {
		Champion[] c = Champion.values();
		
		addChampion(c[0]);
		addChampion(c[1], new Spell[]{Spell.flash, Spell.exhaust}, new Item[]{Item.healthpot, Item.spellthief, Item.ward_green, Item.trinket_green}, new Item[]{Item.sightstone, Item.sorceresshoes, Item.frostqueen, Item.morellonicon, Item.rabadonsdeathcap, Item.mikaels}, 2); //Annie
		addChampion(c[4]); //Alistar
		addChampion(c[8]); //Soraka
		addChampion(c[12]); //Nunu
		addChampion(c[16]); //Morgana
		addChampion(c[17]); //Zilean
		addChampion(c[29]); //Janna
		addChampion(c[32]); //Taric
		addChampion(c[34]); //Blitzcrank
		addChampion(c[60]); //Sona
		addChampion(c[62]); //Lux
		addChampion(c[69]); //Karma
		addChampion(c[79]); //Leona
		addChampion(c[93]); //Nautilus
		addChampion(c[95]); //Lulu
		addChampion(c[101]); //Zyra
		addChampion(c[108]); //Nami
		addChampion(c[110]); //Thresh
		addChampion(c[119]); //Braum
		addChampion(Champion.bard);
		
		sortAfterAlp();
	}
}
