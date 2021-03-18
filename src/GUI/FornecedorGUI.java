package GUI;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

import BUSINESS.IFornecedorService;
import BUSINESS.FornecedorService;

public class FornecedorGUI {
	IFornecedorService fornecedorService;
	
	public FornecedorGUI () {
		this.fornecedorService = FornecedorService.getInstance();
	}
	
	public static void init(int a) {
		new FornecedorGUI();
		telaInicial(1);
	}
	
	public static void telaCadastrar (int a) {
		
	}
	
	public static void telaAlterar (int a) {
		
	}
	
	public static void telaConsultar (int a) {
		
	}
	
	public static void telaRemover (int id) {
		
	}
	
	
	
	public static void telaInicial(int a) {
		HashMap<Integer, String> funcoes = new HashMap<Integer, String>();
		HashMap<Integer, Consumer<Integer>> funcoesPtr = new HashMap<Integer, Consumer<Integer>>();

		int opt = -1;

		funcoes.put(0, "Voltar");
		funcoes.put(1, "Cadastrar Fornecedor");
		funcoes.put(2, "Alterar Fornecedor");
		funcoes.put(3, "Consultar Fornecedor");
		funcoes.put(4, "Remover Fornecedor");

		funcoesPtr.put(0, MateriaPrimaGUI::sair);
		funcoesPtr.put(1, MateriaPrimaGUI::telaCadastrar);
		funcoesPtr.put(2, MateriaPrimaGUI::telaAlterar);
		funcoesPtr.put(3, MateriaPrimaGUI::telaConsultar);
		funcoesPtr.put(4, MateriaPrimaGUI::telaRemover);

		while (opt != 0) {
			System.out.println("===== Menu Fornecedor =====");
			System.out.println("\nOperações disponíveis:\n");
			for (int i : funcoes.keySet()) {
				System.out.printf("[%d] %s \n", i, funcoes.get(i));
			}
			try {
				Scanner input = new Scanner(System.in);
				System.out.print("Digite: ");
				opt = Integer.parseInt(input.nextLine());
				funcoesPtr.get(opt).accept(1);
			} catch (Exception e) {
				System.out.println("Digite um valor válido. Erro: " + e.getMessage());
				opt = -1;
			}
			System.out.println("");
		}
	}

}
