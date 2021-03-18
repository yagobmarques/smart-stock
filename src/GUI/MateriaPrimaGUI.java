package GUI;


import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

import BUSINESS.IMateriaPrimaService;
import BUSINESS.MateriaPrimaService;
import ENTITY.MateriaPrima;
import UTIL.Colors;

public class MateriaPrimaGUI {
	IMateriaPrimaService materiaPrimaService;

	public  MateriaPrimaGUI() {
		// TODO Auto-generated constructor stub
		this.materiaPrimaService = MateriaPrimaService.getInstance();
	}
	
	
	
	public static void telaCadastrar(int a) {
		IMateriaPrimaService materiaPrimaService = MateriaPrimaService.getInstance();
		
		
		Scanner input = new Scanner(System.in);
		String nome = " "; 
		String tipo = " ";
		Boolean perecivel = false;
		String unMedida = " ";
		Float quantidade = (float)0.0;
		
		while(nome != "SAIR") {
			try {
				System.out.println("===== Cadastrar Matéria-Prima =====");
				System.out.println("\nDigite os seguintes parâmetros:");
				System.out.println("[Str ] [Str ] [Boolean  ] [Str              ] [Float     ]");
				System.out.println("[Nome] [Tipo] [Perecível] [Unidade De Medida] [Quantidade]\n");
				System.out.println("Se quiser volta, basta digitar 'sair' ");
				nome = input.next();
				if(!nome.toUpperCase().equals("SAIR")) {
					tipo = input.next();
					perecivel = Boolean.parseBoolean(input.next());
					unMedida = input.next();
					quantidade = Float.parseFloat(input.next());
					materiaPrimaService.inserir(new MateriaPrima(nome, tipo, perecivel, unMedida, quantidade));
				}
				else {
					nome = "SAIR";
					System.out.println("Saindo da tela de cadastro");
				}
				
			} catch (Exception e){
				System.out.println("Digite valores válidos");
				nome = "SAIR";
			}
			System.out.println("");
		}
	
	}
	public static void telaAlterar(int a) {
		
	}
	public static void telaConsultar(int a) {
		
	}
	public static void telaRemover(int a) {
		
	}
	public static void sair(int a) {
		System.out.println("Saindo do Menu Matéria-Prima");
	}
	
	public static void telaInicial(int a) {
		HashMap<Integer, String> funcoes= new HashMap<Integer, String>();
		HashMap<Integer, Consumer<Integer>> funcoesPtr= new HashMap<Integer, Consumer<Integer>>();
			
		Scanner input = new Scanner(System.in);
		int opt = -1;
		
		funcoes.put(0, "voltar");
		funcoes.put(1, "Cadastrar Matéria-Prima");
		funcoes.put(2, "Alterar Matéria-Prima");
		funcoes.put(3, "Consultar Matéria-Prima");
		funcoes.put(4, "Remover Matéria-Prima");
		
		funcoesPtr.put(0, MateriaPrimaGUI::sair);
		funcoesPtr.put(1, MateriaPrimaGUI::telaCadastrar);
		funcoesPtr.put(2, MateriaPrimaGUI::telaAlterar);
		funcoesPtr.put(3, MateriaPrimaGUI::telaConsultar);
		funcoesPtr.put(4, MateriaPrimaGUI::telaRemover);
		
		
		while(opt != 0) {
			System.out.println("===== Menu Matéria-Prima =====");
			System.out.println("\nOperações disponíveis:\n");
			for(int i : funcoes.keySet()) {
				System.out.printf("[%d] %s \n", i, funcoes.get(i));
			}
			try {
				System.out.print("Digite: ");
				opt = Integer.parseInt(input.nextLine());
				funcoesPtr.get(opt).accept(1);
			} catch (Exception e){
				System.out.println("Digite um valor válido");
				opt = -1;
			}
			System.out.println("");
		}
	}

}
