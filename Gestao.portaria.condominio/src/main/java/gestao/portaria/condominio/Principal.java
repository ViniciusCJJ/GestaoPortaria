package gestao.portaria.condominio;

import java.sql.SQLException;
import java.time.LocalDateTime;

public class Principal {

	public static void main(String[] args) throws SQLException {
		
		try {
			Porteiro p1 =new Porteiro("Zezinho","173-948-281-85");
			Visitante v1=new Visitante("Zé","504-349-228-70");
			Porteiro p2 =new Porteiro("ZePorteiro","123-456-789-00");
			Visitante v2=new Visitante("Zezaum","103-365-157-80");
			RegistroVisitante r1=new RegistroVisitante();
			RegistroVisitante r2=new RegistroVisitante("Zezinho","173-948-281-85","ZePorteiro","123-456-789-00",LocalDateTime.now());
	
			if(r1.addRegistro(v1, p1,LocalDateTime.now()) == true) {
				System.out.println("Registro adicionado com sucesso");
			}else {
				System.out.println("Não foi possivel adicionar!");
			}
			if(r1.addRegistro(v2, p2,LocalDateTime.now()) == true) {
				System.out.println("Registro adicionado com sucesso");
			}else {
				System.out.println("Não foi possivel adicionar!");
			}
			//registro sem banco
			System.out.println(r1.gerarRegistro());
			//Salvando no banco
			p1.salvar(p1);
			v1.salvar(v1);
			r1.salvar(r2);
			//Excluindo no banco
			p1.excluir(1);
			v1.excluir(1);
			r1.excluir(1);
			//Atualizando no banco
			p1.atualizar("zezaooo","183-129-334-67", 2);
			v1.atualizar("VisitanteZezim","183-124-567-59", 2);
			r2.atualizar("zezaooo","183-129-334-67","PorteiroZe","183-124-567-59","31/03/2022 17:28", 3);
			//Registro dos visitantes do banco
			System.out.println(v1.pegarVisitantesBanco());
			//Registro dos porteiros do banco
			System.out.println(p1.pegarPorteirosBanco());
			//Registro DasVisitasBanco
			System.out.println(r2.pegarRegistroBanco());
		} catch (ExcessaoNuloOuMenorQueZero e1) {
			System.out.println(e1.getMessage());
		}
	}

}
