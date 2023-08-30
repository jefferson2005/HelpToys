package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JDateChooser;

import model.DAO;
import utils.Validador;

public class Produtos extends JDialog {
	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	// instanciar objeto para o fluxo de bytes
	private FileInputStream fis;

	// variavel global para armazenar o tamanho da imagem(bytes)
	private int tamanho;

	private final JPanel contentPanel = new JPanel();
	private JTextField txtCodigo;
	private JTextField txtProduto;
	private JTextField txtEstoque;
	private JTextField txtEstoqueMin;
	private JTextField txtCusto;
	private JTextField txtLocal;
	private JTextField txtFornecedor;
	private JList listFornecedor;
	private JScrollPane scrollPaneFornecedor;
	private JTextField txtIDFornecedor;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JButton btnLimpar;
	private JButton btnExcluir;
	private JLabel lblFoto;
	private JButton btnCarregar;
	private JTextField txtBarcode;
	private JTextArea txtDescricao;
	private JTextField txtFabricante;
	private JTextField txtLote;
	private JLabel lblNewLabel_6;
	private JTextField txtLucro;
	private JDateChooser dateEntrada;
	private JDateChooser dateValidade;
	private JLabel lblPorcentagem;
	private JButton btnPesquisar;
	private JScrollPane scrollPaneProdutos;
	private JPanel listFornecedores;
	private JList listProdutos;
	private JComboBox cboUN;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Produtos dialog = new Produtos();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Produtos() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Produtos.class.getResource("/img/Produtos.png")));
		setTitle("Produtos");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				scrollPaneProdutos.setVisible(false);

			}
		});
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		scrollPaneProdutos = new JScrollPane();
		scrollPaneProdutos.setVisible(false);
		scrollPaneProdutos.setBounds(12, 46, 299, 23);
		contentPanel.add(scrollPaneProdutos);
		
		listProdutos = new JList();
		listProdutos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarProdutoLista();
			}
		});
		scrollPaneProdutos.setViewportView(listProdutos);

		listFornecedores = new JPanel();
		listFornecedores.setBorder(new TitledBorder(null, "Fornecedor", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		listFornecedores.setBounds(509, 102, 233, 65);
		contentPanel.add(listFornecedores);
		listFornecedores.setLayout(null);

		txtFornecedor = new JTextField();
		txtFornecedor.setDocument(new Validador(50));
		txtFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarFornecedor();
			}
		});
		txtFornecedor.setBounds(129, 19, 94, 17);
		listFornecedores.add(txtFornecedor);
		txtFornecedor.setColumns(10);

		JLabel lblFornecedor = new JLabel("Nome Fornecedor:");
		lblFornecedor.setBounds(10, 21, 114, 14);
		listFornecedores.add(lblFornecedor);

		JLabel lblIdFor = new JLabel("ID:");
		lblIdFor.setBounds(10, 40, 46, 14);
		listFornecedores.add(lblIdFor);

		txtIDFornecedor = new JTextField();
		txtIDFornecedor.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtIDFornecedor.setEditable(false);
		txtIDFornecedor.setBounds(33, 38, 46, 20);
		listFornecedores.add(txtIDFornecedor);
		txtIDFornecedor.setColumns(10);

		scrollPaneFornecedor = new JScrollPane();
		scrollPaneFornecedor.setBounds(129, 36, 94, 26);
		listFornecedores.add(scrollPaneFornecedor);

		listFornecedor = new JList();
		scrollPaneFornecedor.setViewportView(listFornecedor);
		listFornecedor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarFornecedor();
			}
		});
		scrollPaneFornecedor.setVisible(false);
		{
			JLabel lblCodigo = new JLabel("Código:");
			lblCodigo.setBounds(513, 11, 46, 14);
			contentPanel.add(lblCodigo);
		}
		{
			JLabel lblProduto = new JLabel("Produto:");
			lblProduto.setBounds(14, 15, 59, 14);
			contentPanel.add(lblProduto);
		}
		{
			JLabel lblBarcode = new JLabel("Barcode:");
			lblBarcode.setBounds(574, 56, 107, 14);
			contentPanel.add(lblBarcode);
		}
		{
			JLabel lblDescricao = new JLabel("Descrição:");
			lblDescricao.setBounds(12, 358, 222, 14);
			contentPanel.add(lblDescricao);
		}
		{
			JLabel lblEstoque = new JLabel("Estoque:");
			lblEstoque.setBounds(313, 115, 86, 14);
			contentPanel.add(lblEstoque);
		}
		{
			JLabel lblEstoqueMin = new JLabel("Estoque Min:");
			lblEstoqueMin.setBounds(190, 115, 86, 14);
			contentPanel.add(lblEstoqueMin);
		}
		{
			JLabel lblCusto = new JLabel("Custo:");
			lblCusto.setBounds(153, 286, 46, 14);
			contentPanel.add(lblCusto);
		}
		{
			JLabel lblUnidade = new JLabel("Unidade Medida:");
			lblUnidade.setBounds(313, 286, 107, 14);
			contentPanel.add(lblUnidade);
		}
		{
			JLabel lblLocal = new JLabel("Local:");
			lblLocal.setBounds(343, 195, 86, 14);
			contentPanel.add(lblLocal);
		}
		{
			txtCodigo = new JTextField();
			txtCodigo.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					String caracteres = "0123456789.";

					if (!caracteres.contains(e.getKeyChar() + "")) {

						e.consume();
					}
				}
			});
			txtCodigo.setBounds(513, 24, 86, 20);
			contentPanel.add(txtCodigo);
			txtCodigo.setColumns(10);

		}
		{
			txtProduto = new JTextField();
			txtProduto.setDocument(new Validador(50));
			txtProduto.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					listarProdutos();
				}
			});
			txtProduto.setBounds(12, 28, 299, 20);
			contentPanel.add(txtProduto);
			txtProduto.setColumns(10);
			txtProduto.setDocument(new Validador(50));
		}
		{
			txtEstoque = new JTextField();
			txtEstoque.setDocument(new Validador(7));
			txtEstoque.setDocument(new Validador(5));
			txtEstoque.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					String caracteres = "0123456789.";

					if (!caracteres.contains(e.getKeyChar() + "")) {

						e.consume();
					}
				}
			});
			txtEstoque.setColumns(10);
			txtEstoque.setBounds(312, 128, 86, 20);
			contentPanel.add(txtEstoque);
		}
		{
			txtEstoqueMin = new JTextField();
			txtEstoqueMin.setDocument(new Validador(7));
			txtEstoqueMin.setColumns(10);
			txtEstoqueMin.setBounds(188, 128, 84, 20);
			contentPanel.add(txtEstoqueMin);
		}
		{
			txtCusto = new JTextField();
			txtCusto.setDocument(new Validador(7));
			txtCusto.addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					String caracteres = "0123456789.";

					if (!caracteres.contains(e.getKeyChar() + "")) {

						e.consume();
					}
				}
			});
			txtCusto.setColumns(10);
			txtCusto.setBounds(153, 301, 86, 20);
			contentPanel.add(txtCusto);
		}
		{
			txtLocal = new JTextField();
			txtLocal.setColumns(10);
			txtLocal.setBounds(343, 210, 107, 20);
			contentPanel.add(txtLocal);
			txtLocal.setDocument(new Validador(20));
		}
		{
			btnExcluir = new JButton("");
			btnExcluir.setIcon(new ImageIcon(Produtos.class.getResource("/img/Apagar.png")));
			btnExcluir.setSelectedIcon(new ImageIcon(Produtos.class.getResource("/img/ADD.png")));
			btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnExcluir.setEnabled(false);
			btnExcluir.setToolTipText("Excluir");
			btnExcluir.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					excluirProduto();
				}
			});
			btnExcluir.setBounds(271, 502, 48, 48);
			contentPanel.add(btnExcluir);
		}
		{
			btnLimpar = new JButton("");
			btnLimpar.setIcon(new ImageIcon(Produtos.class.getResource("/img/clear icon.png")));
			btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnLimpar.setToolTipText("Limpar");
			btnLimpar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					limparCampos();
				}
			});
			btnLimpar.setBounds(655, 502, 48, 48);
			contentPanel.add(btnLimpar);
		}
		{
			btnAdicionar = new JButton("");
			btnAdicionar.setIcon(new ImageIcon(Produtos.class.getResource("/img/ADD.png")));
			btnAdicionar.setSelectedIcon(new ImageIcon(Produtos.class.getResource("/img/User.png")));
			btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnAdicionar.setToolTipText("Adicionar");
			btnAdicionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					adicionar();
				}
			});
			btnAdicionar.setBounds(79, 502, 48, 48);
			contentPanel.add(btnAdicionar);
		}
		{
			btnEditar = new JButton("");
			btnEditar.setIcon(new ImageIcon(Produtos.class.getResource("/img/Editor.png")));
			btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btnEditar.setEnabled(false);
			btnEditar.setToolTipText("Editar");
			btnEditar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					editarProduto();
				}
			});
			btnEditar.setBounds(458, 502, 48, 48);
			contentPanel.add(btnEditar);
		}

		lblFoto = new JLabel("");
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/Camera.png")));
		lblFoto.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		lblFoto.setForeground(SystemColor.textHighlight);
		lblFoto.setBounds(501, 178, 256, 256);
		contentPanel.add(lblFoto);

		btnCarregar = new JButton("Carregar Foto");
		btnCarregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				carregarFoto();
			}
		});
		btnCarregar.setBounds(572, 445, 132, 23);
		contentPanel.add(btnCarregar);

		txtBarcode = new JTextField();
		txtBarcode.setDocument(new Validador(13));
		txtBarcode.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					buscarBarCode();
				}
			}
		});
		txtBarcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 buscarBarCode();
			}
		});
		txtBarcode.setBounds(572, 71, 142, 20);
		contentPanel.add(txtBarcode);
		txtBarcode.setColumns(10);
		{
			txtDescricao = new JTextArea();
			txtDescricao.setBorder(UIManager.getBorder("FileChooser.listViewBorder"));
			txtDescricao.setBackground(new Color(255, 255, 255));
			txtDescricao.setBounds(12, 373, 426, 95);
			contentPanel.add(txtDescricao);
		}

		JLabel lblNewLabel_1 = new JLabel("Entrada:");
		lblNewLabel_1.setBounds(12, 197, 46, 14);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Validade:");
		lblNewLabel_2.setBounds(173, 195, 46, 14);
		contentPanel.add(lblNewLabel_2);

		dateEntrada = new JDateChooser();
		dateEntrada.setBounds(12, 210, 142, 20);
		contentPanel.add(dateEntrada);

		dateValidade = new JDateChooser();
		dateValidade.setBounds(173, 210, 132, 20);
		contentPanel.add(dateValidade);

		JLabel lblNewLabel_3 = new JLabel("Fabricante:");
		lblNewLabel_3.setBounds(12, 115, 68, 14);
		contentPanel.add(lblNewLabel_3);

		txtFabricante = new JTextField();
		txtFabricante.setDocument(new Validador(50));
		txtFabricante.setBounds(12, 128, 142, 20);
		contentPanel.add(txtFabricante);
		txtFabricante.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Lote:");
		lblNewLabel_4.setBounds(345, 36, 46, 14);
		contentPanel.add(lblNewLabel_4);

		txtLote = new JTextField();
		txtLote.setBounds(343, 49, 86, 20);
		contentPanel.add(txtLote);
		txtLote.setColumns(10);

		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setIcon(new ImageIcon(Produtos.class.getResource("/img/barcode.png")));
		lblNewLabel_5.setBounds(511, 55, 48, 46);
		contentPanel.add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("Lucro:");
		lblNewLabel_6.setBounds(12, 283, 46, 14);
		contentPanel.add(lblNewLabel_6);

		txtLucro = new JTextField();
		txtLucro.setDocument(new Validador(10));
		txtLucro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String caracteres = "0123456789.";

				if (!caracteres.contains(e.getKeyChar() + "")) {

					e.consume();
				}
			}
		});
		txtLucro.setBounds(12, 296, 61, 20);
		contentPanel.add(txtLucro);
		txtLucro.setColumns(10);

		lblPorcentagem = new JLabel("%");
		lblPorcentagem.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblPorcentagem.setBounds(82, 298, 21, 17);
		contentPanel.add(lblPorcentagem);

		btnPesquisar = new JButton("Buscar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarProdutos(); 
			}
		});
		btnPesquisar.setBounds(615, 23, 89, 23);
		contentPanel.add(btnPesquisar);
		
		cboUN = new JComboBox();
		cboUN.setModel(new DefaultComboBoxModel(new String[] {" ", "UN", "CX ", "PC ", "KG", "M"}));
		cboUN.setBounds(313, 304, 67, 30);
		contentPanel.add(cboUN);
		
		lblNewLabel = new JLabel("\r\n");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(255, 128, 255));
		lblNewLabel.setBounds(1, 485, 784, 75);
		contentPanel.add(lblNewLabel);


		
	}// final construtor

	private void listarFornecedor() {
		// System.out.println("Teste");
		// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// obejto irá temporariamente armazenar os dados
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o model (vetor na lista)
		listFornecedor.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select* from fornecedor where razao like '" + txtFornecedor.getText() + "%'"
				+ "order by razao ";
		try {
			// abri conexão
			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			// uso do while para trazer os usuários enquanto exisitr
			while (rs.next()) {
				// mostrar a lista
				scrollPaneFornecedor.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelo.addElement(rs.getString(2));
				// esconder a lista se nenhuma letra for digitada
				if (txtFornecedor.getText().isEmpty()) {
					scrollPaneFornecedor.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarFornecedor() {
		// System.out.println("teste");
		// variável que captura o indice da linha da lista
		int linha = listFornecedor.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução sql)
			// limit (0,1) -> seleciona o indice 0 e 1 usuário da lista
			String readListafornecedor = "select * from fornecedor where razao like '" + txtFornecedor.getText()
					+ "%'" + "order by razao";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListafornecedor);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPaneFornecedor.setVisible(false);
					// setar campos
					txtIDFornecedor.setText(rs.getString(1));
					txtFornecedor.setText(rs.getString(2));

				}

			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
			scrollPaneFornecedor.setVisible(false);
		}
	}

	private void limparCampos() {
		txtProduto.setText(null);
		txtDescricao.setText(null);
		txtIDFornecedor.setText(null);
		txtCodigo.setText(null);
		txtLote.setText(null);
		txtLocal.setText(null);
		txtCusto.setText(null);
		txtEstoqueMin.setText(null);
		cboUN.setSelectedItem(null);
		txtEstoque.setText(null);
		txtFornecedor.setText(null);
		dateEntrada.setDate(null);
		dateValidade.setDate(null);
		lblFoto.setIcon(new ImageIcon(Produtos.class.getResource("/img/Camera.png")));
		cboUN.setSelectedItem("");
		txtBarcode.setText(null);
		btnAdicionar.setEnabled(true);
		btnExcluir.setEnabled(false);
		btnEditar.setEnabled(false);
		txtFabricante.setText(null);
		txtLucro.setText(null);
	}

	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatóriios
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o valor do produto");
			txtProduto.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha a descrição do produto");
			txtDescricao.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque atual");
			txtEstoque.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o estoque minimo atual");
			txtEstoqueMin.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do produto");
			txtCusto.requestFocus();
		} else if (txtFornecedor.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
			txtFornecedor.requestFocus();
		} else if (dateValidade.getDate()== null) {
			JOptionPane.showMessageDialog(null, "Preencha o fornecedor do produto");
		} else {

			// lógica pricipal
			// CRUD Creat
			String create = "insert into produtos (barcode,produto,lote,descricao,foto,fabricante,dataval,estoque,estoquemin,unidade,localarm,custo,lucro,idfor) value ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";
			// tratamento com exceções
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a execução da query(instrução sql - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1, txtBarcode.getText());
				pst.setString(2, txtProduto.getText());
				pst.setString(3, txtLote.getText());
				pst.setString(4, txtDescricao.getText());
				pst.setBlob(5,fis, tamanho);
				pst.setString(6, txtFabricante.getText());
				SimpleDateFormat formatador = new SimpleDateFormat("yyyyMMdd");
				String dataFormatada = formatador.format(dateValidade.getDate());
				pst.setString(7, dataFormatada);
				pst.setString(8, txtEstoque.getText());
				pst.setString(9, txtEstoqueMin.getText());
				pst.setString(10, cboUN.getSelectedItem().toString());
				pst.setString(12, txtCusto.getText());
				pst.setString(11, txtLocal.getText());
				pst.setString(13, txtLucro.getText());
				pst.setString(14, txtIDFornecedor.getText());

				// executar a query(instruição sql (CRUD - Creat))
				pst.executeUpdate();
				// Confirmar
				JOptionPane.showMessageDialog(null, "Produto adicionado");
				limparCampos();
				// fechar a conexão
				con.close();
			}  catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste Código de Barras já está sendo utilizado.");
				txtBarcode.setText(null);
				txtBarcode.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void excluirProduto() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confima captura a opção escolhida

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste fornecedor ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			// CRUD - Delete
			String delete = "delete from produtos where produto=?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				// substituir a ? pelo id do contato
				pst.setString(1, txtProduto.getText());
				// executar a query
				pst.executeUpdate();
				// limpar Campos
				limparCampos();
				// exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, " Produto excluido");
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarProduto() {
		// System.out.println("teste do Método");

		// Validação dos campos obrigátorios
		if (txtProduto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Brinquedo do Serviço");
			txtProduto.requestFocus();
		} else if (txtLote.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o Lote de Serviço");
			txtLote.requestFocus();
		} else if (txtCusto.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a custo de Serviço");
			txtCusto.requestFocus();
		} else if (txtFabricante.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o fabricante ");
			txtFabricante.requestFocus();
		} else if (txtDescricao.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o descrição do Serviço");
			txtDescricao.requestFocus();
		} else if (txtEstoque.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o estoque do Serviço");
			txtEstoque.requestFocus();
		} else if (txtLocal.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o local do Serviço");
			txtLocal.requestFocus();
		} else if (txtEstoqueMin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o estoque minimo do Serviço");
			txtEstoqueMin.requestFocus();
		} else if (txtLucro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o lucro do Serviço");
			txtLucro.requestFocus();
		} else {

			// Lógica principal
			// CRUD - Update
			String update = "update produtos set produto=?,lote=?, descricao=?, fabricante=?, estoque=?, estoquemin=?, unidade=?, custo=?, lucro=?, localarm=? where codigo=?";
			// tratamentos de exceçoes
			try {
				// como a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtProduto.getText());
				pst.setString(2, txtLote.getText());
				pst.setString(3, txtDescricao.getText());
				pst.setString(4, txtFabricante.getText());
				pst.setString(5, txtEstoque.getText());
				pst.setString(6, txtEstoqueMin.getText());
				pst.setString(7, cboUN.getSelectedItem().toString());
				pst.setString(8, txtCusto.getText());
				pst.setString(9, txtLucro.getText());
				pst.setString(10, txtLocal.getText());
				pst.setString(11, txtCodigo.getText());

				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do Produto editado com sucesso");
				// limpar campos
				limparCampos();
				// fechar conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void carregarFoto() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Selecionar Arquivo");
		jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de Imagens(*.PNG, *JPG, *JPEG)", "png", "jpg", "jpeg"));
		int resultado = jfc.showOpenDialog(this);
		if (resultado == JFileChooser.APPROVE_OPTION) {
			try {
				fis = new FileInputStream(jfc.getSelectedFile());
				tamanho = (int) jfc.getSelectedFile().length();
				Image foto = ImageIO.read(jfc.getSelectedFile()).getScaledInstance(lblFoto.getWidth(),
						lblFoto.getHeight(), Image.SCALE_SMOOTH);
				lblFoto.setIcon(new ImageIcon(foto));
				lblFoto.updateUI();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	
	private void buscarBarCode() {
		// Criar uma variável com a query (instrução do banco)
		String readBarCode = "select * from produtos where barcode = ?";
		// tratamento de exceções
		try {
			// abrir a conexão
			con = dao.conectar();
			// preparar a execução da query (instrução sql - CRUD Read)
			// O parâmetro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(readBarCode);
			// Substituir a ?(Parâmetro) pelo número da OS
			pst.setString(1, txtBarcode.getText());
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else para verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtCodigo.setText(rs.getString(1));
				txtBarcode.setText(rs.getString(2));
				txtProduto.setText(rs.getString(3));
				txtLote.setText(rs.getString(4));
				txtDescricao.setText(rs.getNString(5));
				txtFabricante.setText(rs.getString(7));
				// Setar a data no JCalendar
				// Passo 1: Receber a data do mysql
				String setarDataEnt = rs.getString(8);
				// Passo 2: Formatar a data para o JCalendar
				Date dataEntrada = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataEnt);
				// Passo 3: Exibir o resultado no JCalendar
				dateEntrada.setDate(dataEntrada);
				String setarDataVali = rs.getString(9);
				Date dataValidade = new SimpleDateFormat("yyyy-mm-dd").parse(setarDataVali);
				dateValidade.setDate(dataValidade);
				txtCusto.setText(rs.getString(14));
				txtLucro.setText(rs.getString(15));
				txtEstoque.setText(rs.getString(10));
				txtEstoqueMin.setText(rs.getString(11));
				cboUN.setSelectedItem(rs.getString(12));
				txtLocal.setText(rs.getString(13));
				txtIDFornecedor.setText(rs.getString(16));
				Blob blob = (Blob) rs.getBlob(6);
				byte[] img = blob.getBytes(1, (int) blob.length());
				BufferedImage imagem = null;
				try {
					imagem = ImageIO.read(new ByteArrayInputStream(img));
				} catch (Exception e) {
					System.out.println(e);
				}
				ImageIcon icone = new ImageIcon(imagem);
				Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(),
						Image.SCALE_SMOOTH));
				lblFoto.setIcon(foto);
				// Validação (liberação dos botões)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnAdicionar.setEnabled(false);

			} else {
				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "Barcode do produto não encontrado");
				// Validação (liberação do botão adicionar)
				btnAdicionar.setEnabled(true);
				btnExcluir.setEnabled(false);
				btnEditar.setEnabled(false);
			}
			// fechar conexão (IMPORTANTE)
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// FIM DO METODO BUSCAR
	
	private void buscarProdutos() {

			// Query (instrução sql)
			// limit (0,1) -> seleciona o indice 0 e 1 usuário da lista
			String readCodigo = "select * from produtos inner join fornecedor on produtos.idfor = fornecedor.idfornecedores where codigo=?";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readCodigo);
				pst.setString(1, txtCodigo.getText());
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					// setar campos
					txtCodigo.setText(rs.getString(1));
					txtBarcode.setText(rs.getString(2));
					txtProduto.setText(rs.getString(3));
					txtLote.setText(rs.getString(4));
					txtDescricao.setText(rs.getString(5));
					Blob blob = (Blob) rs.getBlob(6);
					txtFabricante.setText(rs.getString(7));
					dateEntrada.setDate(rs.getDate(8));
					dateValidade.setDate(rs.getDate(9));
					txtEstoque.setText(rs.getString(10));
					txtEstoqueMin.setText(rs.getString(11));
					cboUN.setSelectedItem(rs.getString(12));
					txtLocal.setText(rs.getString(13));
					txtCusto.setText(rs.getString(14));
					txtLucro.setText(rs.getString(15));
					txtIDFornecedor.setText(rs.getString(16));

					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
					btnAdicionar.setEnabled(false);

					byte[] img = blob.getBytes(1, (int) blob.length());
					BufferedImage imagem = null;

					try {
						imagem = ImageIO.read(new ByteArrayInputStream(img));
					} catch (Exception e) {
						System.out.println(e);
					}
					ImageIcon icone = new ImageIcon(imagem);
					Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
							lblFoto.getHeight(), Image.SCALE_SMOOTH));
					lblFoto.setIcon(foto);
				} else {
					// se não existir um contato no banco
					JOptionPane.showMessageDialog(null, "Produto inexistente");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		
	}
		
		private void listarProdutos() {
			// System.out.println("Teste");
			// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
			// obejto irá temporariamente armazenar os dados
			DefaultListModel<String> modelo = new DefaultListModel<>();
			// setar o model (vetor na lista)
			listProdutos.setModel(modelo);
			// Query (instrução sql)
			String readLista = "select* from produtos where produto like '" + txtProduto.getText() + "%'" + "order by produto";
			try {
				// abri conexão
				con = dao.conectar();

				pst = con.prepareStatement(readLista);

				rs = pst.executeQuery();

				// uso do while para trazer os usuários enquanto exisitr
				while (rs.next()) {
					// mostrar a lista
					scrollPaneProdutos.setVisible(true);
					// adicionar os usuarios no vetor -> lista
					modelo.addElement(rs.getString(3));
					// esconder a lista se nenhuma letra for digitada
					if (txtProduto.getText().isEmpty()) {
						scrollPaneProdutos.setVisible(false);
					}
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		/**
		 * Método usado para buscar usuário pela lista
		 */
		private void buscarProdutoLista() {
			// System.out.println("teste");
			// variável que captura o indice da linha da lista
			int linha = listProdutos.getSelectedIndex();
			if (linha >= 0) {
				// Query (instrução sql)
				// limit (0,1) -> seleciona o indice 0 e 1 usuário da lista
				String readListaUsuario = "select * from produtos where produto like '" + txtProduto.getText() + "%'"
						+ "order by produto";
				try {
					con = dao.conectar();
					pst = con.prepareStatement(readListaUsuario);
					rs = pst.executeQuery();
					if (rs.next()) {
						// esconder a lista
						scrollPaneProdutos.setVisible(false);
						// setar campos
						txtCodigo.setText(rs.getString(1));
						txtBarcode.setText(rs.getString(2));
						txtProduto.setText(rs.getString(3));
						txtLote.setText(rs.getString(4));
						txtDescricao.setText(rs.getString(5));
						Blob blob = (Blob) rs.getBlob(6);
						txtFabricante.setText(rs.getString(7));
						dateEntrada.setDate(rs.getDate(8));
						dateValidade.setDate(rs.getDate(9));
						txtEstoque.setText(rs.getString(10));
						txtEstoqueMin.setText(rs.getString(11));
						cboUN.setSelectedItem(rs.getString(12));
						txtLocal.setText(rs.getString(13));
						txtCusto.setText(rs.getString(14));
						txtLucro.setText(rs.getString(15));
						txtIDFornecedor.setText(rs.getString(16));
						
						btnAdicionar.setEnabled(false);
						btnEditar.setEnabled(true);
						btnExcluir.setEnabled(true);
						
						byte[] img = blob.getBytes(1, (int) blob.length());
						BufferedImage imagem = null;

						try {
							imagem = ImageIO.read(new ByteArrayInputStream(img));
						} catch (Exception e) {
							System.out.println(e);
						}
						ImageIcon icone = new ImageIcon(imagem);
						Icon foto = new ImageIcon(icone.getImage().getScaledInstance(lblFoto.getWidth(),
								lblFoto.getHeight(), Image.SCALE_SMOOTH));
						lblFoto.setIcon(foto);
					}
					
				

				} catch (Exception e) {
					System.out.println(e);
				}
			} else {
				// se não existir no banco um usuário da lista
				scrollPaneProdutos.setVisible(false);
			
			}
		}
	}

