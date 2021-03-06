package DATA;

import java.util.ArrayList;

import ENTITY.Cliente;

public class ClienteDAO implements IClienteDAO {
	protected ArrayList<Cliente> clientes;
	protected int idSerial;

	// Construtores
	public ClienteDAO() {
		this.clientes = new ArrayList<Cliente>();
		this.idSerial = 1;
	}
	
	public ClienteDAO(ArrayList<Cliente> clientes) {
		super();
		this.clientes = clientes;
	}

	@Override
	public int inserir(Cliente cliente) {
		cliente.setId(this.pegaEIncremanetaId());
		this.clientes.add(cliente);
		return cliente.getId();
	}

	@Override
	public int remover(int id) {
		Cliente aux = new Cliente();
		for (Cliente c: this.clientes) {
			if(c.getId() == id) {
				aux = c;
				break;
			}
		}
		this.clientes.remove(aux);
		return aux.getId();
	}

	@Override
	public int alterar(int id, Cliente cliente) {
		for (Cliente c : this.clientes) {
			if (c.getId() == id) {
				c.setId(id);
				c.setNome(cliente.getNome());
				c.setCpf(cliente.getCpf());
				c.setEndereço(cliente.getEndereço());
				c.setTelefone(cliente.getTelefone());
				break;
			}
		}
		return id;
	}

	@Override
	public Cliente procuraPeloId(int id) {
		for (Cliente c : this.clientes) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Cliente> procuraTodos(){
		return this.clientes;
	}
	
	@Override
	public int pegaEIncremanetaId() {
		// Função com o objetivo de usar as IDs de maneira sequencial e sem repetição
		int idAtual = this.idSerial;
		this.idSerial += 1;
		return idAtual;
	}
}
