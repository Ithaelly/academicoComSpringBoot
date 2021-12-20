package equipeOrbitais.academicoComSpringBoot.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(schema = "comum", name = "pessoa") //nome da tabela no bd
public class Pessoa {
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="pessoa_seq") //os dados dessa chave serão criados pelo banco de dado
	@SequenceGenerator(name = "pessoa_seq", schema = "comum", sequenceName = "pessoa_seq", allocationSize = 1)//initialValue = 1
	@Column(name="id") //nome da coluna no bd
	private int id;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="cpf")
	private String cpf;
	
	//1 pessoa - N alunos
	@OneToMany(fetch = FetchType.EAGER , mappedBy = "pessoa")
	private List<Aluno> listarAlunos = new ArrayList<>();

	//CRIANDO CONSTRUTORES
	public Pessoa() {
	}
	
	public Pessoa(String cpf, String nome, int id) {
		this.cpf = cpf;
		this.nome = nome;
		this.id = id;
	}
	
	public Pessoa(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }
	

	//CRIANDO GETS E SETS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
    
   public List<Aluno> getListarAlunos() {
		return listarAlunos;
	}

	public void setListarAlunos(List<Aluno> listarAlunos) {
		this.listarAlunos = listarAlunos;
	}

@Override
    public String toString() {
        return "Nome: " + nome + "\nCPF: " + cpf + "\n";
    }
}
