import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

// A3 - Projeto 1: Ponto de Venda (PDV)
public class PDV {

    // ---------- CLASSES (só guardam dados) ----------
    static class Produto {
        int codigo;
        String nome;
        double valor;
        int estoque;
    }

    static class Pessoa { // serve para Cliente e para Vendedor
        int codigo;
        String nome;
    }

    static class Item { // um produto dentro de uma venda
        Produto produto;
        int quantidade;
    }

    static class Venda {
        int numero;
        String data; // formato aaaa-mm-dd (ex: 2026-11-16)
        Pessoa cliente;
        Pessoa vendedor;
        ArrayList<Item> itens = new ArrayList<>();
    }

    // ---------- DADOS DO SISTEMA ----------
    static final double COMISSAO = 0.05; // 5% por venda
    static final double IMPOSTO = 0.25;  // 25% sobre as vendas

    static ArrayList<Produto> produtos = new ArrayList<>();
    static ArrayList<Pessoa> clientes = new ArrayList<>();
    static ArrayList<Pessoa> vendedores = new ArrayList<>();
    static ArrayList<Venda> vendas = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // ---------- LEITURA DE DADOS ----------
    static String lerTexto(String msg) {
        System.out.print(msg);
        return sc.nextLine();
    }

    static int lerInt(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Digite um numero inteiro valido.");
            }
        }
    }

    static double lerDouble(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Double.parseDouble(sc.nextLine().replace(",", "."));
            } catch (Exception e) {
                System.out.println("Digite um valor valido (ex: 10.50).");
            }
        }
    }

    // ---------- BUSCAS ----------
    static Produto buscarProduto(int codigo) {
        for (Produto p : produtos) {
            if (p.codigo == codigo) return p;
        }
        return null; // nao achou
    }

    static Pessoa buscarPessoa(ArrayList<Pessoa> lista, int codigo) {
        for (Pessoa p : lista) {
            if (p.codigo == codigo) return p;
        }
        return null;
    }

    // ---------- CADASTROS ----------
    static void criarProduto(int codigo, String nome, double valor, int estoque) {
        Produto p = new Produto();
        p.codigo = codigo;
        p.nome = nome;
        p.valor = valor;
        p.estoque = estoque;
        produtos.add(p);
    }

    static void criarPessoa(ArrayList<Pessoa> lista, int codigo, String nome) {
        Pessoa p = new Pessoa();
        p.codigo = codigo;
        p.nome = nome;
        lista.add(p);
    }

    static void cadastrarProduto() {
        int codigo = lerInt("Codigo: ");
        if (buscarProduto(codigo) != null) {
            System.out.println("Codigo ja existe!");
            return;
        }
        String nome = lerTexto("Nome: ");
        double valor = lerDouble("Valor: ");
        int estoque = lerInt("Estoque: ");
        criarProduto(codigo, nome, valor, estoque);
        System.out.println("Produto cadastrado!");
    }

    static void cadastrarPessoa(ArrayList<Pessoa> lista) {
        int codigo = lerInt("Codigo: ");
        if (buscarPessoa(lista, codigo) != null) {
            System.out.println("Codigo ja existe!");
            return;
        }
        String nome = lerTexto("Nome: ");
        criarPessoa(lista, codigo, nome);
        System.out.println("Cadastrado com sucesso!");
    }

    static void listarProdutos() {
        System.out.println("--- PRODUTOS ---");
        for (Produto p : produtos) {
            System.out.printf("%d - %s | R$ %.2f | estoque: %d%n", p.codigo, p.nome, p.valor, p.estoque);
        }
    }

    static void listarPessoas(ArrayList<Pessoa> lista) {
        for (Pessoa p : lista) {
            System.out.println(p.codigo + " - " + p.nome);
        }
    }

    static void menuCadastros() {
        int op;
        do {
            System.out.println("\n=== CADASTROS ===");
            System.out.println("1 - Cadastrar produto");
            System.out.println("2 - Cadastrar cliente");
            System.out.println("3 - Cadastrar vendedor");
            System.out.println("4 - Listar produtos");
            System.out.println("5 - Listar clientes");
            System.out.println("6 - Listar vendedores");
            System.out.println("0 - Voltar");
            op = lerInt("Opcao: ");
            switch (op) {
                case 1: cadastrarProduto(); break;
                case 2: cadastrarPessoa(clientes); break;
                case 3: cadastrarPessoa(vendedores); break;
                case 4: listarProdutos(); break;
                case 5: listarPessoas(clientes); break;
                case 6: listarPessoas(vendedores); break;
            }
        } while (op != 0);
    }

    // ---------- VENDA ----------
    static double totalVenda(Venda v) {
        double total = 0;
        for (Item i : v.itens) {
            total += i.produto.valor * i.quantidade;
        }
        return total;
    }

    // quanto desse produto ja esta no carrinho da venda
    static int quantidadeNoCarrinho(Venda v, Produto p) {
        for (Item i : v.itens) {
            if (i.produto == p) return i.quantidade;
        }
        return 0;
    }

    static void novaVenda() {
        if (produtos.size() == 0 || clientes.size() == 0 || vendedores.size() == 0) {
            System.out.println("Cadastre produto, cliente e vendedor primeiro!");
            return;
        }

        Venda v = new Venda();
        v.numero = vendas.size() + 1;
        v.data = LocalDate.now().toString();

        // 1) cliente e vendedor sao obrigatorios
        listarPessoas(clientes);
        v.cliente = buscarPessoa(clientes, lerInt("Codigo do cliente: "));
        if (v.cliente == null) {
            System.out.println("Cliente nao encontrado. Venda cancelada.");
            return;
        }
        listarPessoas(vendedores);
        v.vendedor = buscarPessoa(vendedores, lerInt("Codigo do vendedor: "));
        if (v.vendedor == null) {
            System.out.println("Vendedor nao encontrado. Venda cancelada.");
            return;
        }

        // 2) adiciona quantos produtos quiser (0 finaliza)
        int codigo;
        do {
            listarProdutos();
            codigo = lerInt("Codigo do produto (0 para finalizar): ");
            if (codigo == 0) break;

            Produto p = buscarProduto(codigo);
            if (p == null) {
                System.out.println("Produto nao encontrado!");
                continue;
            }
            int qtd = lerInt("Quantidade: ");
            int jaNoCarrinho = quantidadeNoCarrinho(v, p);

            if (qtd <= 0) {
                System.out.println("Quantidade invalida!");
            } else if (qtd + jaNoCarrinho > p.estoque) {
                System.out.println("Estoque insuficiente! Disponivel: " + (p.estoque - jaNoCarrinho));
            } else if (jaNoCarrinho > 0) {
                for (Item i : v.itens) {
                    if (i.produto == p) i.quantidade += qtd;
                }
                System.out.println("Quantidade atualizada no carrinho.");
            } else {
                Item item = new Item();
                item.produto = p;
                item.quantidade = qtd;
                v.itens.add(item);
                System.out.println("Produto adicionado!");
            }
        } while (true);

        if (v.itens.size() == 0) {
            System.out.println("Carrinho vazio. Venda cancelada.");
            return;
        }

        // 3) finaliza: atualiza o estoque automaticamente
        for (Item i : v.itens) {
            i.produto.estoque -= i.quantidade;
        }
        vendas.add(v);
        emitirRecibo(v);
    }

    static void emitirRecibo(Venda v) {
        System.out.println("\n========== RECIBO ==========");
        System.out.println("Venda: " + v.numero);
        System.out.println("Data: " + v.data);
        System.out.println("Cliente: " + v.cliente.nome);
        System.out.println("Vendedor: " + v.vendedor.nome);
        System.out.println("----------------------------");
        for (Item i : v.itens) {
            double subtotal = i.produto.valor * i.quantidade;
            System.out.printf("%s | %d x R$ %.2f = R$ %.2f%n", i.produto.nome, i.quantidade, i.produto.valor, subtotal);
        }
        System.out.println("----------------------------");
        System.out.printf("TOTAL: R$ %.2f%n", totalVenda(v));
        System.out.println("============================");
    }

    // ---------- RELATORIOS ----------
    // a data esta no formato aaaa-mm-dd, entao compareTo funciona para comparar datas
    static boolean dentroDoPeriodo(Venda v, String inicio, String fim) {
        return v.data.compareTo(inicio) >= 0 && v.data.compareTo(fim) <= 0;
    }

    static void relatorio(String inicio, String fim) {
        double totalGeral = 0;
        int quantidadeVendas = 0;

        for (Venda v : vendas) {
            if (dentroDoPeriodo(v, inicio, fim)) {
                quantidadeVendas++;
                totalGeral += totalVenda(v);
            }
        }
        if (quantidadeVendas == 0) {
            System.out.println("Nenhuma venda no periodo.");
            return;
        }

        System.out.println("\n===== RELATORIO " + inicio + " a " + fim + " =====");
        System.out.println("Vendas realizadas: " + quantidadeVendas);

        System.out.println("\n-- Produtos vendidos --");
        for (Produto p : produtos) {
            int qtd = 0;
            for (Venda v : vendas) {
                if (dentroDoPeriodo(v, inicio, fim)) {
                    for (Item i : v.itens) {
                        if (i.produto == p) qtd += i.quantidade;
                    }
                }
            }
            if (qtd > 0) System.out.println(p.nome + ": " + qtd + " un.");
        }

        System.out.println("\n-- Por vendedor --");
        for (Pessoa vend : vendedores) {
            double total = 0;
            for (Venda v : vendas) {
                if (dentroDoPeriodo(v, inicio, fim) && v.vendedor == vend) {
                    total += totalVenda(v);
                }
            }
            if (total > 0) {
                System.out.printf("%s | vendas: R$ %.2f | imposto: R$ %.2f | comissao: R$ %.2f%n",
                        vend.nome, total, total * IMPOSTO, total * COMISSAO);
            }
        }

        System.out.println("\n-- Totais --");
        System.out.printf("Total de vendas: R$ %.2f%n", totalGeral);
        System.out.printf("Imposto (25%%): R$ %.2f%n", totalGeral * IMPOSTO);
        System.out.printf("Comissao (5%%): R$ %.2f%n", totalGeral * COMISSAO);
    }

    // ---------- MAIN ----------
    public static void main(String[] args) {
        // dados de exemplo para facilitar a demonstracao
        criarProduto(1, "Lapis", 5.00, 30);
        criarProduto(2, "Bolo", 30.90, 80);
        criarProduto(3, "Cafe", 16.00, 30);
        criarPessoa(clientes, 1, "Joao");
        criarPessoa(clientes, 2, "Gustavo");
        criarPessoa(vendedores, 1, "Guilherme");
        criarPessoa(vendedores, 2, "Maximus");

        int op;
        do {
            System.out.println("\n###### PDV ######");
            System.out.println("1 - Cadastros");
            System.out.println("2 - Nova venda");
            System.out.println("3 - Relatorio do dia");
            System.out.println("4 - Relatorio por periodo");
            System.out.println("0 - Sair");
            op = lerInt("Opcao: ");
            switch (op) {
                case 1: menuCadastros(); break;
                case 2: novaVenda(); break;
                case 3:
                    String hoje = LocalDate.now().toString();
                    relatorio(hoje, hoje);
                    break;
                case 4:
                    String ini = lerTexto("Data inicial (aaaa-mm-dd): ");
                    String fim = lerTexto("Data final (aaaa-mm-dd): ");
                    relatorio(ini, fim);
                    break;
            }
        } while (op != 0);
        System.out.println("Encerrando...");
    }
}