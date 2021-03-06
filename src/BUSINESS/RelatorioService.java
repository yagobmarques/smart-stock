package BUSINESS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import ENTITY.Encomenda;
import ENTITY.ProdutoFinal;
import ENTITY.ProdutoFinalReal;
import UTIL.BusinessRuleException;
import ENTITY.MateriaPrima;

public final class RelatorioService implements IRelatorioService {
	protected IEncomendaService encomendaService;
	protected IEstoqueService estoqueService;
	protected IProdutoFinalService produtoFinalService;
	protected IMateriaPrimaService materiaPrimaService;
	private static IRelatorioService instance;

	private RelatorioService() {
		this.encomendaService = EncomendaService.getInstance();
		this.estoqueService = EstoqueService.getInstance();
		this.produtoFinalService = ProdutoFinalService.getInstance();
		this.materiaPrimaService = MateriaPrimaService.getInstance();
	}

	public static IRelatorioService getInstance() {
		if (instance == null) {
			instance = new RelatorioService();
		}
		return instance;
	}

	@Override
	public HashMap<Integer, Integer> listarReposicaoProduto(Date dataInicio, Date dataFim) throws BusinessRuleException {
		// Função que retorna a lista de produtos necessário para atender as encomendas
		// a partir de um intervalo de tempo
		if (dataInicio.after(dataFim)){
			throw new BusinessRuleException("Data final é menor que a data inicial");
		}
		HashMap<Integer, Integer> qntProduto = new HashMap<Integer, Integer>();
		HashMap<Integer, Integer> qntProdutoFaltante = new HashMap<Integer, Integer>();
		for (Encomenda e : encomendaService.procuraTodos()) {
			if ((e.getDataEntrega().after(dataInicio) && e.getDataEntrega().before(dataFim)) || e.getDataEntrega().equals(dataInicio) || e.getDataEntrega().equals(dataFim)) {
				for (int id : e.getListaProdutos().keySet()) {
					if (!qntProduto.containsKey(id)) {
						qntProduto.put(id, e.getListaProdutos().get(id));
					} else {
						int aux = qntProduto.get(id) + e.getListaProdutos().get(id);
						qntProduto.replace(id, aux); // incrementando a lista de produtos necessários
					}
				}
			}
		}
		for (int id : qntProduto.keySet()) {
			int qntMinima = this.produtoFinalService.procuraPeloId(id).getQntMinima();
			int disponibilidade = estoqueService.verificaDisponibilidadeProduto(id, qntProduto.get(id) + qntMinima);
			if (disponibilidade < 0) {
				qntProdutoFaltante.put(id, -1 * disponibilidade);
			}
		}
		return qntProdutoFaltante;
	}
	
	@Override
	public HashMap <Integer, Float> listarReposicaoMateriaPrima(Date dataInicio, Date dataFim) throws BusinessRuleException{
		HashMap <Integer, Integer> listaDeProdutosFaltantes = this.listarReposicaoProduto(dataInicio, dataFim);
		HashMap <Integer, Float> listaDeMateriaPrimaFaltanteTotal = new HashMap <Integer, Float> ();
		HashMap <Integer, Float> listaDeMateriaPrimaFaltante = new HashMap <Integer, Float> ();
		
		//MateriaPrima materiaPrimaASerReposta = new MateriaPrima ();
		ProdutoFinal produtoFinalASerReposto = new ProdutoFinal ();
		HashMap<Integer, Float> receita = new HashMap <Integer, Float> (); 
		float valor_residual, valor_de_reposição, valor_final, diferença_de_estoque = 0;
		int quantidade_de_produtos = 0;
		
		if(!listaDeProdutosFaltantes.isEmpty()) {
			for(int ide_prod : listaDeProdutosFaltantes.keySet()) {
				
				produtoFinalASerReposto = produtoFinalService.procuraPeloId(ide_prod);
				receita = produtoFinalASerReposto.getReceita();
				quantidade_de_produtos= listaDeProdutosFaltantes.get(ide_prod);
				
				for(int ide_receita : receita.keySet()) {		
					
					if(listaDeMateriaPrimaFaltanteTotal.containsKey(ide_receita)) {
						
						valor_residual = listaDeMateriaPrimaFaltanteTotal.get(ide_receita);
						valor_de_reposição = quantidade_de_produtos * receita.get(ide_receita);
						valor_final = valor_residual + valor_de_reposição;
						listaDeMateriaPrimaFaltanteTotal.put(ide_receita, valor_final);
					}
					
					else {					
						valor_final = quantidade_de_produtos * receita.get(ide_receita);
						listaDeMateriaPrimaFaltanteTotal.put(ide_receita, valor_final + materiaPrimaService.procuraPeloId(ide_receita).getQntMinima());
					}
					
				}
			}
			
			for (int id_materia : listaDeMateriaPrimaFaltanteTotal.keySet()) {
				diferença_de_estoque = estoqueService.verificaDisponibilidadeMateriaPrima(id_materia, listaDeMateriaPrimaFaltanteTotal.get(id_materia));
				if(diferença_de_estoque < 0) {
					listaDeMateriaPrimaFaltante.put(id_materia, -1 * diferença_de_estoque);
				}
			}
		}
		
		
		return listaDeMateriaPrimaFaltante;
	}

}
