package equipeOrbitais.academicoComSpringBoot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import equipeOrbitais.academicoComSpringBoot.models.Pessoa;

/*jpaRepository ele tem vários métodos como: salvar, deletar, find by id. Então é só criar uma instância dessa 
interface PessoaRepository e utilizar esse métodos no nosso modelo Pessoa, facilitando a persistência no bd, sem precisar criar DAO*/
public interface PessoaRepository extends JpaRepository<Pessoa, Long>{  //JpaRepository<nomeDaEntidade, tipoDoID>

}
