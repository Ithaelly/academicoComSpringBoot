package equipeOrbitais.academicoComSpringBoot.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*Os @ são anotação das classes, tabelas, colunas e atributos que queremos armazenadas no banco de dados, 
 * indicando que os objetos criados são entidades e devem ser persistidos.*/

@Entity //define que essa classe é uma entidade a ser mapeada pela JPA
@Table(schema = "graduacao", name = "aluno") //nome da tabela no bd
public class Aluno {
	@Id
	@GeneratedValue (strategy = GenerationType.SEQUENCE, generator="aluno_seq") //os dados dessa chave serão criados pelo banco de dado
	@SequenceGenerator(name = "aluno_seq", schema = "graduacao", sequenceName = "aluno_seq", allocationSize = 1)
	@Column(name="id") //nome da coluna no bd
	private int id;
	
	@Column(name="matricula") 
	private String matricula;	
	
	@Column(name="ano_entrada")
	private int anoEntrada;	
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinColumn(name = "id_pessoa")
	private Pessoa pessoa;
	
	//CRIANDO CONSTRUTORES
	public Aluno() {
	}
	
	public Aluno(String matricula, int anoEntrada, Pessoa pessoa) {
		this.matricula = matricula;
		this.anoEntrada = anoEntrada;
		this.pessoa = pessoa;
	}

	//CRIANDO GETS E SETS
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public int getAnoEntrada() {
		return anoEntrada;
	}
	
	public void setAnoEntrada(int anoEntrada) {
		this.anoEntrada = anoEntrada;
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	@Override
    public String toString() {
        return "Matrícula: " + matricula + "\nAno de entrada: " + anoEntrada + "\nId pessoa: " + pessoa.getId()  + "\nNome: " + pessoa.getNome() + "\nCPF:  " + pessoa.getCpf()+ "\n";
    }
}

