package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.DAO;
import utils.Validador;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.border.BevelBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.border.MatteBorder;
import java.awt.Font;

public class Usuarios extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
	// Instanciar objetos JDBC
	DAO dao = new DAO();
	private JButton btnAdicionar;
	private JButton btnEditar;
	private JButton btnExcluir;
	private JButton btnPesquisar;
	private JList listUsers;
	private final JScrollPane scrollPaneUsers = new JScrollPane();
	private JComboBox cboPerfil;
	private JCheckBox chckSenha;
	private JPasswordField txtSenha;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			Usuarios dialog = new Usuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/users.png")));
		setTitle("Usuarios");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// clicar no painel JDialog
				scrollPaneUsers.setVisible(false);
			}
		});
		contentPanel.setForeground(new Color(255, 255, 255));
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setBounds(216, 238, 214, 62);
		contentPanel.add(scrollPaneUsers);
		
				listUsers = new JList();
				scrollPaneUsers.setColumnHeaderView(listUsers);
				listUsers.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						buscarUsuarioLista();
					}
				});

		JLabel lblNewLabel = new JLabel("ID:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(162, 138, 46, 14);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(160, 217, 46, 14);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(161, 176, 46, 23);
		contentPanel.add(lblNewLabel_2);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(161, 258, 46, 14);
		contentPanel.add(lblSenha);

		txtID = new JTextField();
		txtID.setSelectionColor(new Color(0, 0, 0));
		txtID.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtID.setCaretColor(new Color(0, 0, 0));
		txtID.setSelectedTextColor(new Color(0, 0, 0));
		txtID.setForeground(new Color(0, 0, 0));
		txtID.setEditable(false);
		txtID.setBounds(218, 135, 86, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		txtNome = new JTextField();
		txtNome.setForeground(new Color(0, 0, 0));
		txtNome.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				// pressionar uma tecla
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// soltar uma tecla
				listarUsuarios();
			}
		});
		txtNome.setBounds(216, 214, 214, 26);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		txtLogin = new JTextField();
		txtLogin.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtLogin.setCaretColor(new Color(0, 0, 0));
		txtLogin.setForeground(new Color(0, 0, 0));
		txtLogin.setBounds(217, 173, 214, 26);
		contentPanel.add(txtLogin);
		txtLogin.setColumns(10);
		// uso do validador para limitar o número de caracteres
		txtLogin.setDocument(new Validador(15));

		btnPesquisar = new JButton("");
		btnPesquisar.setContentAreaFilled(false);
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscar();
			}
		});
		btnPesquisar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnPesquisar.setBorder(null);
		btnPesquisar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/pesquisar.png")));
		btnPesquisar.setToolTipText("Pesquisar");
		btnPesquisar.setBounds(454, 153, 48, 48);
		contentPanel.add(btnPesquisar);

		JButton btnLimpar = new JButton("");
		btnLimpar.setBorderPainted(false);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparcampos();
			}
		});
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBorder(null);
		btnLimpar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/clear icon.png")));
		btnLimpar.setToolTipText("Apagar");
		btnLimpar.setBounds(199, 502, 48, 48);
		contentPanel.add(btnLimpar);

		getRootPane().setDefaultButton(btnPesquisar);

		btnAdicionar = new JButton("");
		btnAdicionar.setEnabled(false);
		btnAdicionar.setBorderPainted(false);
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setBorder(null);
		btnAdicionar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/ADD.png")));
		btnAdicionar.setBounds(377, 495, 55, 55);
		contentPanel.add(btnAdicionar);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.setBorderPainted(false);
		btnEditar.setBorder(null);
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// evento que vai verificar se o checkbox foi selecionado
				if (chckSenha.isSelected()) {
					editarUsuario();

				} else {
					editarUsuarioExcetosenha();

				}
			}
		});
		btnEditar.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Editor.png")));
		btnEditar.setToolTipText("Editar");
		btnEditar.setBounds(461, 495, 55, 55);
		contentPanel.add(btnEditar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.setBorder(null);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirUsuarios();
			}
		});
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setBorderPainted(false);
		btnExcluir.setIcon(new ImageIcon(Usuarios.class.getResource("/img/Excluir Contato.png")));
		btnExcluir.setToolTipText("Excluir");
		btnExcluir.setBounds(282, 495, 55, 55);
		contentPanel.add(btnExcluir);

		JLabel lblNewLabel_3 = new JLabel("\r\n");
		lblNewLabel_3.setBackground(new Color(255, 128, 192));
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBounds(0, 486, 784, 75);
		contentPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Perfil:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(416, 307, 46, 14);
		contentPanel.add(lblNewLabel_4);

		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "user" }));
		cboPerfil.setBounds(461, 300, 70, 29);
		contentPanel.add(cboPerfil);

		chckSenha = new JCheckBox("Alterar Senha");
		chckSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chckSenha.isSelected()) {
					txtSenha.setText(null);
					txtSenha.requestFocus();
					txtSenha.setBackground(Color.yellow);
				} else {
					txtSenha.setBackground(Color.white);

				}
				
			}
		});
		chckSenha.setBounds(150, 306, 118, 23);
		contentPanel.add(chckSenha);
				
				txtSenha = new JPasswordField();
				txtSenha.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
				txtSenha.setBounds(217, 258, 214, 26);
				contentPanel.add(txtSenha);
	}

	private void buscar() {
		// Dica - testar o evento preimeiro
		// System.out.println("teste do botão buscar");
		// Criar ua variável com a query (instruções do banco)
		String read = "select * from usuarios where login = ?";
		// Tratamento de exceções
		try {
			// Abrir a conexão
			con = dao.conectar();

			// Preparar a exucução da query(instrução sql - CRUD Read)
			// O paraêmtro 1 substitui a ? pelo conteúdo da caixa de texto
			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			// executar a query e buscar o resultado
			rs = pst.executeQuery();
			// uso da estrutura if else parar verificar se existe o contato
			// rs.next() -> se existir um contato no banco
			if (rs.next()) {
				txtID.setText(rs.getString(1)); // 1 campo da tabela
				txtNome.setText(rs.getString(2)); // 2campo (nome)
				txtLogin.setText(rs.getString(3));// 3 campo (Login)
				txtSenha.setText(rs.getString(4)); // 4 campo (Senha)
				cboPerfil.setSelectedItem(rs.getString(5));
				// validação (liberação dos botões alterar e excluir)
				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnPesquisar.setEnabled(false);

			} else {

				// se não existir um contato no banco
				JOptionPane.showMessageDialog(null, "Usuarío Inexistente");
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
			}

		} catch (Exception e) {
			System.out.print(e);
		}
	}

	/**
	 * Limpar campos
	 */
	private void limparcampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtSenha.setText(null);
		txtLogin.setText(null);
		btnAdicionar.setEnabled(false);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		btnPesquisar.setEnabled(true);
		scrollPaneUsers.setVisible(false);
		cboPerfil.setSelectedItem("");
		txtSenha.setBackground(Color.white);
		chckSenha.setSelected(false);
	}// fim do método limpar campos()

	/**
	 * Método pra adicionar um novo contato
	 */
	private void adicionar() {
		// System.out.println("teste");
		// Validação de campos obrigatóriios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Usuário");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Senha do Usuário");
			txtSenha.requestFocus();
		} else if (cboPerfil.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(null, "Preencha o Perfil do Usuário");
		} else {

			// lógica pricipal
			// CRUD Creat
			String create = "insert into usuarios (nome,login,senha,perfil) values (?,?,md5(?),?)";
			// tratamento com exceções
			try {
				// abrir conexão
				con = dao.conectar();
				// preparar a execução da query(instrução sql - CRUD Create)
				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				// executar a query(instruição sql (CRUD - Creat))
				pst.executeUpdate();
				// Confirmar
				JOptionPane.showMessageDialog(null, "Usuário adicionado");
				// limpar campos
				limparcampos();
				// fechar a conexão
			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void editarUsuario() {
		// System.out.println("teste do Método");

		// Validação dos campos obrigátorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Senha do Usuário");
			txtSenha.requestFocus();
		} else {

			// Lógica principal
			// CRUD - Update
			String update = "update usuarios set nome=?, login=?, senha=md5(?), perfil=? where id=?";
			// tratamentos de exceçoes
			try {

				// como a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do Usuário editados com sucesso");
				// limpar campos
				limparcampos();
				txtSenha.setBackground(Color.white);
				chckSenha.setSelected(false);
				// fechar conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarUsuarioExcetosenha() {
		// System.out.println("teste do Método");

		// Validação dos campos obrigátorios
		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtLogin.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o login do Usuário");
			txtLogin.requestFocus();
		} else if (txtSenha.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite a Senha do Usuário");
			txtSenha.requestFocus();
		} else {

			// Lógica principal
			// CRUD - Update
			String update = "update usuarios set nome=?, login=?, perfil = ? where id=?";
			// tratamentos de exceçoes
			try {

				// como a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// confirmar para o usuário
				JOptionPane.showMessageDialog(null, "Dados do Usuário editados com sucesso");
				// limpar campos
				limparcampos();
				// fechar conexão
				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	// Método usado para excluir um contato

	private void excluirUsuarios() {
		// System.out.println("Teste do botão excluir");
		// validação de exclusão - a variável confima captura a opção escolhida

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuarios ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {
			// CRUD - Delete
			String delete = "delete from usuarios where id=?";
			// tratamento de exceções
			try {
				// abrir a conexão
				con = dao.conectar();
				// preparar a query (instrução sql)
				pst = con.prepareStatement(delete);
				// substituir a ? pelo id do contato
				pst.setString(1, txtID.getText());
				// executar a query
				pst.executeUpdate();
				// limpar Campos
				limparcampos();
				// exibir uma mensagem ao usuário
				JOptionPane.showMessageDialog(null, " usuario excluido");
				// fechar a conexão
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarUsuarios() {
		// System.out.println("Teste");
		// a linha abaixo cria um objeto usando como referência um vetor dinâmico, este
		// obejto irá temporariamente armazenar os dados
		DefaultListModel<String> modelo = new DefaultListModel<>();
		// setar o model (vetor na lista)
		listUsers.setModel(modelo);
		// Query (instrução sql)
		String readLista = "select* from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {
			// abri conexão
			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			// uso do while para trazer os usuários enquanto exisitr
			while (rs.next()) {
				// mostrar a lista
				scrollPaneUsers.setVisible(true);
				// adicionar os usuarios no vetor -> lista
				modelo.addElement(rs.getString(2));
				// esconder a lista se nenhuma letra for digitada
				if (txtNome.getText().isEmpty()) {
					scrollPaneUsers.setVisible(false);
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
	private void buscarUsuarioLista() {
		// System.out.println("teste");
		// variável que captura o indice da linha da lista
		int linha = listUsers.getSelectedIndex();
		if (linha >= 0) {
			// Query (instrução sql)
			// limit (0,1) -> seleciona o indice 0 e 1 usuário da lista
			String readListaUsuario = "select * from usuarios where nome like '" + txtNome.getText() + "%'"
					+ "order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {
					// esconder a lista
					scrollPaneUsers.setVisible(false);
					// setar campos
					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtLogin.setText(rs.getString(3));
					txtSenha.setText(rs.getString(4));
					cboPerfil.setSelectedItem(rs.getString(5));
					
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);
				}

			} catch (Exception e) {
				System.out.println(e);
			}
		} else {
			// se não existir no banco um usuário da lista
			scrollPaneUsers.setVisible(false);
		
		}
	}
}