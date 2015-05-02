package roles;

public class Mid extends Role{
	
	public Mid(String name, int id) {
		super(name, id);
	}
	
	@Override
	public void loadChampions() {
		Champion[] c = Champion.values();

		addChampion(c[0]);
		addChampion(c[1], new Spell[]{Spell.ignite, Spell.flash}, new Item[]{Item.healthpot, Item.doransring, Item.trinket_green}, new Item[]{Item.morellonicon, Item.sorceresshoes, Item.hourglass, Item.rabadonsdeathcap, Item.voidstaff, Item.rylais}, 2);//Annie
		addChampion(c[2], new Spell[]{Spell.ignite, Spell.flash}, new Item[]{Item.healthpot, Item.doransring, Item.trinket_green}, new Item[]{Item.sorceresshoes ,Item.nashoorstooth, Item.rabadonsdeathcap, Item.lichbane, Item.hourglass, Item.voidstaff}, 2);//Kayle
		addChampion(c[5], new Spell[]{Spell.ignite, Spell.flash}, new Item[]{Item.healthpot, Item.manapotion, Item.sapphirecrystal, Item.trinket_green}, new Item[]{Item.rodofages, Item.archangelsstaff, Item.sorceresshoes, Item.iceborn, Item.hourglass, Item.manamune}, 2);//Ryze
		addChampion(c[16], new Spell[]{Spell.ignite, Spell.flash}, new Item[]{Item.healthpot, Item.bootsofspeed}, new Item[]{Item.hourglass, Item.rodofages, Item.sorceresshoes, Item.unholygrale, Item.rabadonsdeathcap, Item.voidstaff}, 3);//Morgana
		addChampion(c[17]); //Zilean //TODO Build missing!
		addChampion(c[21], new Spell[]{Spell.exhaust, Spell.flash}, new Item[]{Item.crysallineflask, Item.healthpot, Item.ward_green, Item.trinket_green}, new Item[]{Item.rodofages, Item.sorceresshoes, Item.archangelsstaff, Item.rabadonsdeathcap, Item.hourglass, Item.abyssalscepter}, 0); //karthus
		addChampion(c[25], new Spell[]{Spell.ignite, Spell.flash}, new Item[]{Item.healthpot, Item.doransring, Item.trinket_green}, new Item[]{Item.rodofages, Item.sorceresshoes, Item.unholygrale, Item.rabadonsdeathcap, Item.voidstaff, Item.liandry}, 2); //Aniva
		addChampion(c[27], new Spell[]{Spell.ignite, Spell.flash}, new Item[]{Item.healthpot, Item.crysallineflask, Item.trinket_green}, new Item[]{Item.rodofages, Item.sorceresshoes, Item.hourglass, Item.archangelsstaff, Item.rabadonsdeathcap, Item.voidstaff}, 1); //Kassadin
		addChampion(c[28], new Spell[]{Spell.ignite, Spell.flash}, new Item[]{Item.healthpot, Item.doransring}, new Item[]{Item.sorceresshoes, Item.rabadonsdeathcap, Item.voidstaff, Item.unholygrale, Item.lichbane, Item.hourglass}, 2); //Twisted Fate
		addChampion(c[33], new Spell[]{Spell.ignite, Spell.flash}, new Item[]{Item.healthpot, Item.doransring}, new Item[]{Item.unholygrale, Item.sorceresshoes, Item.morellonicon, Item.rabadonsdeathcap, Item.hourglass, Item.voidstaff, Item.abyssalscepter}, 2); //Veigar
		addChampion(c[36]); //Katarina
		addChampion(c[40]); //Heimerdinger
		addChampion(c[42]); //Nidalee
		addChampion(c[46]); //Mordekaiser
		addChampion(c[47]); //Ezreal
		addChampion(c[49]); //Kennen
		addChampion(c[50]); //Akali
		addChampion(c[52]); //Malzahar
		addChampion(c[57]); //Galio
		addChampion(c[61]); //Swain
		addChampion(c[62]); //Lux
		addChampion(c[63]); //LeBlanc
		addChampion(c[66]); //Cassiopeia
		addChampion(c[74]); //Brand
		addChampion(c[77]); //Orianna
		addChampion(c[82]); //Talon
		addChampion(c[84]); //Xerath
		addChampion(c[87]); //Fizz
		addChampion(c[89]); //Ahri
		addChampion(c[90]); //Viktor
		addChampion(c[92]); //Ziggs
		addChampion(c[101]); //Zyra
		addChampion(c[102]); //Diana
		addChampion(c[104]); //Syndra
		addChampion(c[107]); //Zed
		addChampion(c[113]); //Lissandra
		addChampion(c[117]); //Yasuo
		addChampion(c[118]); //Vel'Koz
		addChampion(c[121]); //Azir
		
		sortAfterAlp();
	}
}
