package gestao.portaria.condominio;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import gestao.portaria.condominio.persistencia.ConexaoBD;


public class RegistroVisitante {

	private Long id;
	private String nomeVisitante;
	private String cpfVisitante;
	private String nomePorteiro;
	private String cpfPorteiro;
	private LocalDateTime Data;
	private  List<RegistroVisitante> registro = new ArrayList<>();
	
	public RegistroVisitante() {
	}
	
	public RegistroVisitante(String nomeVisitante, String cpfVisitante, String nomePorteiro, String cpfPorteiro,LocalDateTime data) throws ExcessaoNuloOuMenorQueZero {
		setNomeVisitante(nomeVisitante);
		setCpfVisitante(cpfVisitante);
		setNomePorteiro(nomePorteiro);
		setCpfPorteiro(cpfPorteiro);
		setData(data);
	}
	
	public RegistroVisitante(Long id,String nomeVisitante, String cpfVisitante, String nomePorteiro, String cpfPorteiro,LocalDateTime data) throws ExcessaoNuloOuMenorQueZero {
		setId(id);
		setNomeVisitante(nomeVisitante);
		setCpfVisitante(cpfVisitante);
		setNomePorteiro(nomePorteiro);
		setCpfPorteiro(cpfPorteiro);
		setData(data);
	}

	public boolean addRegistro(Visitante visitante, Porteiro porteiro,LocalDateTime data) throws ExcessaoNuloOuMenorQueZero {
		 String nomeVisitante=visitante.getNome();
		 String cpfVisitante=visitante.getCpf();
		 String nomePorteiro=porteiro.getNome();
		 String cpfPorteiro=porteiro.getCpf();
		 RegistroVisitante r1=new RegistroVisitante(nomeVisitante,cpfVisitante,nomePorteiro,cpfPorteiro,data);
		 if(registro.add(r1)== true) {
			 return true;
		 }else {
			 return false;
		 }	 
	}
	
	public String getNomeVisitante() {
		return nomeVisitante;
	}

	public void setNomeVisitante(String nomeVisitante)  throws ExcessaoNuloOuMenorQueZero{
		this.nomeVisitante = Validador.ExcessaoEspecialString(nomeVisitante, "Nome do visitante");
	}

	public String getCpfVisitante() {
		return cpfVisitante;
	}

	public void setCpfVisitante(String cpfVisitante) throws ExcessaoNuloOuMenorQueZero {
		this.cpfVisitante = Validador.ExcessaoEspecialString(cpfVisitante, "Cpf Visitante");
	}

	public String getNomePorteiro() {
		return nomePorteiro;
	}

	public void setNomePorteiro(String nomePorteiro) throws ExcessaoNuloOuMenorQueZero{
		this.nomePorteiro = Validador.ExcessaoEspecialString(nomePorteiro, "Nome do porteiro");
	}

	public String getCpfPorteiro() {
		return cpfPorteiro;
	}

	public void setCpfPorteiro(String cpfPorteiro) throws ExcessaoNuloOuMenorQueZero {
		this.cpfPorteiro = Validador.ExcessaoEspecialString(cpfPorteiro, "Cpf do Porteiro");
	}

	public String getData() {
		return Data.toString();
	}

	public void setData(LocalDateTime data) {
		Data = data;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String gerarRegistro() {
		StringBuilder resultado = new StringBuilder();
		resultado.append("Lista de registro de visitas:");
		resultado.append("\n");
		for (RegistroVisitante reg : this.registro) {
			resultado.append(String.format("Nome Visitante: %3s  CPF Visitante: %3s Nome Porteiro: %3s CPF Porteiro :%3s Data: %3s", reg.getNomeVisitante(), reg.getCpfVisitante(), reg.getNomePorteiro(),reg.getCpfPorteiro(),reg.getData()));
			resultado.append("\n");
		}
		return resultado.toString();
	}
	
	public void salvar(RegistroVisitante registro) throws SQLException {
		Connection conexao = null;

		try {
			conexao = ConexaoBD.getConexao();

			PreparedStatement sql = conexao.prepareStatement("INSERT INTO registrovisitante(reg_nome_vis,reg_cpf_vis,reg_nome_por,reg_cpf_por,reg_data) VALUES(?,?,?,?,?)");
			sql.setString(1, registro.getNomeVisitante());
			sql.setString(2,registro.getCpfVisitante());
			sql.setString(3,registro.getNomePorteiro());
			sql.setString(4,registro.getCpfPorteiro());
			//Date data= Date.valueOf(registro.getData().toLocalDate());
			//Só consegui salvar a data e a hora no sql convertendo para string mesmo :/
			sql.setString(5,registro.getData());
			
			sql.execute();
		} finally {
			conexao.close();
		}
	}
	public void excluir(long id) throws SQLException {
		Connection conexao = null;

		try {
			conexao = ConexaoBD.getConexao();

			PreparedStatement sql = conexao.prepareStatement("DELETE FROM registrovisitante WHERE reg_id=?");
			sql.setLong(1, id);
			sql.execute();
		} finally {
			conexao.close();
		}
	}
	
	public void atualizar(String nomeVis, String cpfVis,String nomePor, String cpfPor,String data, long id) throws SQLException {
		Connection conexao = null;

		try {
			conexao = ConexaoBD.getConexao();

			PreparedStatement sql = conexao.prepareStatement("UPDATE registrovisitante SET reg_nome_vis=? , reg_cpf_vis=? , reg_nome_por=? , reg_cpf_por=? , reg_data=? WHERE reg_id=?");
			sql.setString(1, nomeVis);
			sql.setString(2, cpfVis);
			sql.setString(3, nomePor);
			sql.setString(4, cpfPor);
			sql.setString(5, data);
			sql.setLong(6, id);
			sql.execute();
		} finally {
			conexao.close();
		}
	}
	
	public  String pegarRegistroBanco() throws SQLException, ExcessaoNuloOuMenorQueZero {
		Connection conexao = null;

		try {
			conexao = ConexaoBD.getConexao();
			//Aparentemente oracle não suporta LIMIT 
			PreparedStatement sql = conexao.prepareStatement("SELECT reg_id , reg_nome_vis , reg_cpf_vis , reg_nome_por , reg_cpf_por FROM registrovisitante ORDER BY reg_id ");
			ResultSet resultado = sql.executeQuery();

			List<RegistroVisitante> retorno = new ArrayList<>();
			while (resultado.next()) {
				// @formatter:off
				retorno.add(new RegistroVisitante(
						resultado.getLong("reg_id"),
						resultado.getString("reg_nome_vis"),
						resultado.getString("reg_cpf_vis"),
						resultado.getString("reg_nome_por"),
						resultado.getString("reg_cpf_por"),
						LocalDateTime.now()
						));
				// @formatter:on
			}
			String ret ="\n";
			for(RegistroVisitante registro: retorno) {
				 ret += String.format("ID:%d  Nome Visitante:%3s  Cpf Visitante:%3s Nome Porteiro:%3s Cpf Porteiro:%3s Data:%3s",registro.getId(),registro.getNomeVisitante(),registro.getCpfVisitante(),registro.getNomePorteiro(),registro.getCpfPorteiro(),registro.getData())+"\n";
			}
			return ret;
		} finally {
			conexao.close();
		}
	}
}
