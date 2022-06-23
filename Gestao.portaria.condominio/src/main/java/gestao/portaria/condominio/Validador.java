package gestao.portaria.condominio;

public class Validador {
	
	public static String ExcessaoEspecialString(String valor, String nomeCampo) throws ExcessaoNuloOuMenorQueZero {
		String valorTrim = valor.trim();
		if (null == valor || valorTrim.isEmpty()) {
			throw new ExcessaoNuloOuMenorQueZero("O campo: "+nomeCampo+" não pode ser vazio nem nulo");
		}
		return valorTrim;
	}
	
	public static Double ExcessaoEspecialDouble(Double valor, String nomeCampo) throws ExcessaoNuloOuMenorQueZero {
		if  (valor <= 0) {
			throw new ExcessaoNuloOuMenorQueZero("O campo: "+nomeCampo+" não pode ser menor ou igual zero");
		}
		return valor;
	}
	
	public static int ExcessaoEspecialInt(int valor, String nomeCampo) throws ExcessaoNuloOuMenorQueZero {
		if  (valor <= 0) {
			throw new ExcessaoNuloOuMenorQueZero("O campo: "+nomeCampo+" não pode ser menor ou igual zero");
		}
		return valor;
	}

}
