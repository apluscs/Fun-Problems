public class hashtags {
	
	public static void main(String[] args){
		design1();
		System.out.println();
		design2();
		design3();
	}
	
	public static void design3(){
		for(int i = 0; i < 4; i++){
			String indent = "";	String tags = "";	String extra = "";
			for(int j = 0; j < i; j++){
				indent += " ";
				tags += "#";
			}
			tags += "#";
			for(int j = 1; j <= 8 - 2 * (i + 1); j++){
				extra += " ";
			}
			String result = indent + tags + extra;
			System.out.print(result);
			for(int j = result.length() - 1; j >= 0; j--){
				System.out.print(result.charAt(j));
			}
			System.out.println();
		}
		for(int i = 0; i < 4; i++){
			String indent = "";	String tags = "";	String extra = "";
			for(int j = 1; j < 4-i; j++){
				indent += " ";
				tags += "#";
			}
			tags += "#";
			for(int j = 0; j < 2 * i; j++){
				extra += " ";
			}
			String result = indent + tags + extra;
			System.out.print(result);
			for(int j = result.length() - 1; j >= 0; j--){
				System.out.print(result.charAt(j));
			}
			System.out.println();
		}
	}

	public static void design2(){

		for(int i = 1; i <= 4; i++){
			String spaces = "";	String tags = "";
			for(int j = 0; j < i; j++){
				tags += "#";
			}
			for(int j = i; j < 4; j++){
				spaces += " ";
			}
			String result = spaces + tags;
			System.out.print(result);
			for(int j = result.length() - 1; j >= 0; j--){
				System.out.print(result.charAt(j));
			}
			System.out.println();
		}
		design1();
	}

	
	public static void design1(){
		for(int i = 0; i < 4; i++){
			String spaces = "";	String tags = "";
			for(int j = 0; j < i; j++){
				spaces += " ";
			}
			for(int j = i; j < 4; j++){
				tags += "#";
			}
			String result = spaces + tags;
			System.out.print(result);
			for(int j = result.length() - 1; j >= 0; j--){
				System.out.print(result.charAt(j));
			}
			System.out.println();
		}
	}
}
