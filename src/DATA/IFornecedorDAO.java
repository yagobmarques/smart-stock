package DATA;

import java.util.ArrayList;

import BUSINESS.Fornecedor;

public interface IFornecedorDAO {

	int inserir(Fornecedor fornecedor);

	int remover(int id);

	int alterar();

	Fornecedor procuraPeloId(int id);

	ArrayList<Fornecedor> procuraTodos();

}