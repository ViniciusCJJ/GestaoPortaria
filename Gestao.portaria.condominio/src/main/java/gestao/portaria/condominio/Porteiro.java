package gestao.portaria.condominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gestao.portaria.condominio.persistencia.*;

public class Porteiro {

private Long id;
private String nome;
private String cpf;

public Porteiro(String nome, String cpf) throws ExcessaoNuloOuMenorQueZero {
	setNome(nome);
	setCpf(cpf);
}

public Porteiro(Long id,String nome, String cpf) throws ExcessaoNuloOuMenorQueZero {
	setId(id);
	setNome(nome);
	setCpf(cpf);
}
public String getNome() {
	return nome;
}
public void setNome(String nome)  throws ExcessaoNuloOuMenorQueZero{
	this.nome = Validador.ExcessaoEspecialString(nome, "Nome do porteiro");
}
public String getCpf() {
	return cpf;
}
public void setCpf(String cpf)  throws ExcessaoNuloOuMenorQueZero{
	this.cpf = Validador.ExcessaoEspecialString(cpf, "Cpf do porteiro");
}

public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}

public void salvar(Porteiro porteiro) throws SQLException {
	Connection conexao = null;

	try {
		conexao = ConexaoBD.getConexao();

		PreparedStatement sql = conexao.prepareStatement("INSERT INTO porteiros(por_nome, por_cpf) VALUES(?, ?)");
		sql.setString(1, porteiro.getNome());
		sql.setString(2, porteiro.getCpf());
		sql.execute();
	} finally {
		conexao.close();
	}
}

public void excluir(long id) throws SQLException {
	Connection conexao = null;

	try {
		conexao = ConexaoBD.getConexao();

		PreparedStatement sql = conexao.prepareStatement("DELETE FROM porteiros WHERE por_id=?");
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

		PreparedStatement sql = conexao.prepareStatement("UPDATE porteiros SET por_nome=?, por_cpf=? WHERE por_id=?");
		sql.setString(1, nome);
		sql.setString(2, cpf);
		sql.setLong(3, id);
		sql.execute();
	} finally {
		conexao.close();
	}
}

public  String pegarPorteirosBanco() throws SQLException, ExcessaoNuloOuMenorQueZero {
	Connection conexao = null;

	try {
		conexao = ConexaoBD.getConexao();
		//Aparentemente oracle não suporta LIMIT 
		PreparedStatement sql = conexao.prepareStatement("SELECT por_id, por_nome, por_cpf FROM porteiros ORDER BY por_id ");
		ResultSet resultado = sql.executeQuery();

		List<Porteiro> retorno = new ArrayList<>();
		while (resultado.next()) {
			// @formatter:off
			retorno.add(new Porteiro(
					resultado.getLong("por_id"),
					resultado.getString("por_nome"),
					resultado.getString("por_cpf")
					));
			// @formatter:on
		}
		String ret ="\n";
		for(Porteiro porteiros: retorno) {
			 ret += String.format("ID:%d  Nome:%3s  Cpf:%3s",porteiros.getId(),porteiros.getNome(),porteiros.getCpf())+"\n";
		}
		return ret;
	} finally {
		conexao.close();
	}
}
}
