package roles;

public class Top extends Role{
	
	public Top(String name, int id) {
		super(name, id);
	}
	
	@Override
	public void loadChampions() {
		Champion[] c = Champion.values();

		addChampion(c[0]);
		addChampion(c[3]); //Master Yi
		addChampion(c[5]); //Ryze
		addChampion(c[9]); //Teemo
		addChampion(c[14]); //Tryndamere
		addChampion(c[15]); //Jax
		addChampion(c[18]); //Singed
		addChampion(c[22]); //Cho'Gath
		addChampion(c[26]); //Dr. Mundo
		addChampion(c[30]); //Gankplank
		addChampion(c[35]); //Malphite
		addChampion(c[37]); //Nasus
		addChampion(c[39]); //Udyr
		addChampion(c[42]); //Nidalee
		addChampion(c[43]); //Poppy
		addChampion(c[44]); //Pantheon
		addChampion(c[45]); //Gragas
		addChampion(c[46]); //Mordekaiser
		addChampion(c[48]); //Shen
		addChampion(c[49]); //Kennen
		addChampion(c[51]); //Garen
		addChampion(c[53]); //Olaf
		addChampion(c[55]); //Xin Zhao
		addChampion(c[56]); //Vladimir
		addChampion(c[57]); //Galio
		addChampion(c[61]); //Swain
		addChampion(c[64]); //Irelia
		addChampion(c[65]); //Trundle
		addChampion(c[68]); //Renekton
		addChampion(c[70]); //Maokai
		addChampion(c[71]); //Jarvan IV
		addChampion(c[73]); //Lee Sin
		addChampion(c[75]); //Rumble
		addChampion(c[78]); //Yorick
		addChampion(c[80]); //Wukong
		addChampion(c[83]); //Riven
		addChampion(c[86]); //Shyvana
		addChampion(c[88]); //Volibear
		addChampion(c[91]); //Sejuani
		addChampion(c[94]); //Fiora
		addChampion(c[96]); //Hecarim
		addChampion(c[98]); //Darius
		addChampion(c[100]); //Jayce
		addChampion(c[103]); //Rengar
		addChampion(c[105]); //Kha'Zix
		addChampion(c[106]); //Elise
		addChampion(c[107]); //Zed
		addChampion(c[109]); //Vi
		addChampion(c[112]); //Zac
		addChampion(c[114]); //Aatrox
		addChampion(c[120]); //Gnar
		addChampion(c[123]);
		
		sortAfterAlp();
	}
}
