package roles;

public class Jungle extends Role{

	public Jungle(String name, int id) {
		super(name, id);
	}
	
	@Override
	public void loadChampions() {
		Champion[] c = Champion.values();
		
		addChampion(c[0]);
		addChampion(c[3]); //Master Yi
		addChampion(c[4]); //Alistar
		addChampion(c[11]); //Warwick
		addChampion(c[12]); //Nunu
		addChampion(c[14]); //Tryndamere
		addChampion(c[15]); //Jax
		addChampion(c[19]); //Evelynn
		addChampion(c[22]); //Cho'Gath
		addChampion(c[23]); //Amumu
		addChampion(c[24]); //Rammus
		addChampion(c[26]); //Dr. Mundo
		addChampion(c[30]); //Gankplank
		addChampion(c[35]); //Malphite
		addChampion(c[37]); //Nasus
		addChampion(c[38]); //Fiddlesticks
		addChampion(c[39]); //Udyr
		addChampion(c[41]); //Shaco
		addChampion(c[44]); //Pantheon
		addChampion(c[48]); //Shen
		addChampion(c[53]); //Olaf
		addChampion(c[55]); //Xin Zhao
		addChampion(c[65]); //Trundle
		addChampion(c[70]); //Maokai
		addChampion(c[71]); //Jarvan IV
		addChampion(c[72]); //Nocturne
		addChampion(c[73]); //Lee Sin
		addChampion(c[80]); //Wukong
		addChampion(c[81]); //Skarner
		addChampion(c[86]); //Shyvana
		addChampion(c[88]); //Volibear
		addChampion(c[91]); //Sejuani
		addChampion(c[93]); //Nautilus
		addChampion(c[96]); //Hecarim
		addChampion(c[102]); //Diana
		addChampion(c[103]); //Rengar
		addChampion(c[105]); //Kha'Zix
		addChampion(c[106]); //Elise
		addChampion(c[109]); //Vi
		addChampion(c[112]); //Zac
		addChampion(c[123]); //Rek'sai
		
		sortAfterAlp();
	}
}
