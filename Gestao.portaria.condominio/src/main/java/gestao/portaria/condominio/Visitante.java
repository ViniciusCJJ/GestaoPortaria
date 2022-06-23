package gestao.portaria.condominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import gestao.portaria.condominio.persistencia.ConexaoBD;

public class Visitante {
	
private Long id;
private String nome;
private String cpf;


public Visitante(String nome, String cpf) throws ExcessaoNuloOuMenorQueZero {
	setNome(nome);
	setCpf(cpf);
}

public Visitante(Long id,String nome, String cpf) throws ExcessaoNuloOuMenorQueZero {
	setId(id);
	setNome(nome);
	setCpf(cpf);
}
public String getNome() {
	return nome;
}
public void setNome(String nome)  throws ExcessaoNuloOuMenorQueZero {
	this.nome = Validador.ExcessaoEspecialString(nome, "Nome do visitante");
}
public String getCpf() {
	return cpf;
}
public void setCpf(String cpf)  throws ExcessaoNuloOuMenorQueZero {
	this.cpf = Validador.ExcessaoEspecialString(cpf, "Cpf do visitante");
}

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
	
public void salvar(Visitante visitante) throws SQLException {
	Connection conexao = null;

	try {
		conexao = ConexaoBD.getConexao();

		PreparedStatement sql = conexao.prepareStatement("INSERT INTO visitantes(vis_nome, vis_cpf) VALUES(?, ?)");
		sql.setString(1, visitante.getNome());
		sql.setString(2, visitante.getCpf());
		sql.execute();
	} finally {
		conexao.close();
	}
}

public void excluir(long id) throws SQLException {
	Connection conexao = null;

	try {
		conexao = ConexaoBD.getConexao();

		PreparedStatement sql = conexao.prepareStatement("DELETE FROM visitantes WHERE vis_id=?");
		sql.setLong(1, id);
		sql.execute();
	} finally {
		conexao.close();
	}
}

public void atualizar(String nome, String cpf, long id) throws SQLException {
	Connection conexao = null;

	try {
		conexao = ConexaoBD.getConexao();

		PreparedStatement sql = conexao.prepareStatement("UPDATE visitantes SET vis_nome=?, vis_cpf=? WHERE vis_id=?");
		sql.setString(1, nome);
		sql.setString(2, cpf);
		sql.setLong(3, id);
		sql.execute();
	} finally {
		conexao.close();
	}
}

public  String pegarVisitantesBanco() throws SQLException, ExcessaoNuloOuMenorQueZero {
	Connection conexao = null;

	try {
		conexao = ConexaoBD.getConexao();
		//Aparentemente oracle não suporta LIMIT 
		PreparedStatement sql = conexao.prepareStatement("SELECT vis_id, vis_nome, vis_cpf FROM visitantes ORDER BY vis_id ");
		ResultSet resultado = sql.executeQuery();

		List<Visitante> retorno = new ArrayList<>();
		while (resultado.next()) {
			// @formatter:off
			retorno.add(new Visitante(
					resultado.getLong("vis_id"),
					resultado.getString("vis_nome"),
					resultado.getString("vis_cpf")
					));
			// @formatter:on
		}
		String ret ="\n";
		for(Visitante visitantes: retorno) {
			 ret += String.format("ID:%d  Nome:%3s  Cpf:%3s",visitantes.getId(),visitantes.getNome(),visitantes.getCpf())+"\n";
		}
		return ret;
	} finally {
		conexao.close();
	}
}
}
