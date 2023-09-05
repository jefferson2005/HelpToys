package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import model.DAO;
import utils.Validador;
import java.awt.Font;

public class Clientes extends JDialog {
	private JTextField txtNome;
	private JTextField txtFone;
	private JTextField txtEmail;
	private JTextField txtCpf;
	private JTextField txtID;

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private JButton btnExcluir;
	private JButton btnEditar;
	private JButton btnAdicionar;
	private JTextField txtCep;
	private JTextField txtEndereco;
	private JTextField txtNumero;
	private JTextField txtComplemento;
	private JTextField txtBairro;
	private JTextField txtCidade;
	private JComboBox cboUf;
	private JScrollPane scrollPaneClientes;
	private JList listClientes;
	private JLabel lblNewLabel_3;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Clientes dialog = new Clientes();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Clientes() {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 14));
		getContentPane().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				scrollPaneClientes.setVisible(false);
			}
		});
		getContentPane().setForeground(new Color(255, 255, 255));
		setIconImage(Toolkit.getDefaultToolkit().getImage(Clientes.class.getResource("/img/Cadastro Clientes.png")));
		setTitle("Cadastro Clientes");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(null);

		scrollPaneClientes = new JScrollPane();
		scrollPaneClientes.setVisible(false);
		scrollPaneClientes.setBounds(101, 173, 187, 42);
		getContentPane().add(scrollPaneClientes);

		listClientes = new JList();
		listClientes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				buscarClientesLista();
			}
		});
		scrollPaneClientes.setViewportView(listClientes);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(Clientes.class.getResource("/img/urso medico.png")));
		lblNewLabel_1.setBounds(7, 2, 64, 64);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNome.setBounds(37, 151, 46, 14);
		getContentPane().add(lblNome);

		JLabel lblFone = new JLabel("Telefone:");
		lblFone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblFone.setBounds(37, 226, 57, 14);
		getContentPane().add(lblFone);

		JLabel lblemail = new JLabel("E-mail:");
		lblemail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblemail.setBounds(37, 268, 46, 14);
		getContentPane().add(lblemail);

		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCpf.setBounds(37, 187, 46, 14);
		getContentPane().add(lblCpf);

		txtNome = new JTextField();
		txtNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNome.setDocument(new Validador(8));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				listarClientes();
			}
		});
		txtNome.setBounds(101, 148, 187, 26);
		getContentPane().add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		txtFone = new JTextField();
		txtFone.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtFone.setBounds(101, 223, 189, 26);
		getContentPane().add(txtFone);
		txtFone.setColumns(10);
		txtFone.setDocument(new Validador(15));

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEmail.setBounds(101, 265, 189, 26);
		getContentPane().add(txtEmail);
		txtEmail.setColumns(10);
		txtEmail.setDocument(new Validador(30));

		txtCpf = new JTextField();
		txtCpf.setBounds(101, 186, 151, 20);
		getContentPane().add(txtCpf);
		txtCpf.setColumns(10);
		txtCpf.setDocument(new Validador(11));

		JButton btnLimpar = new JButton("");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limparCampos();
			}
		});
		btnLimpar.setToolTipText("Limpar");
		btnLimpar.setIcon(new ImageIcon(Clientes.class.getResource("/img/clear icon.png")));
		btnLimpar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLimpar.setBounds(580, 502, 48, 48);
		getContentPane().add(btnLimpar);

		JLabel lblID = new JLabel("ID:");
		lblID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblID.setBounds(37, 116, 46, 14);
		getContentPane().add(lblID);

		txtID = new JTextField();
		txtID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtID.setEditable(false);
		txtID.setBounds(101, 111, 46, 26);
		getContentPane().add(txtID);
		txtID.setColumns(10);

		btnEditar = new JButton("");
		btnEditar.setEnabled(false);
		btnEditar.setToolTipText("Editar Cliente");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editarContato();
			}
		});
		btnEditar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnEditar.setIcon(new ImageIcon(Clientes.class.getResource("/img/Editor.png")));
		btnEditar.setBounds(459, 502, 48, 48);
		getContentPane().add(btnEditar);

		btnAdicionar = new JButton("");
		btnAdicionar.setToolTipText("Adicionar Cliente");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnAdicionar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAdicionar.setIcon(new ImageIcon(Clientes.class.getResource("/img/ADD.png")));
		btnAdicionar.setBounds(177, 502, 48, 48);
		getContentPane().add(btnAdicionar);

		btnExcluir = new JButton("");
		btnExcluir.setEnabled(false);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				excluirClientes();
			}
		});
		btnExcluir.setToolTipText("Excluir Cliente");
		btnExcluir.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExcluir.setIcon(new ImageIcon(Clientes.class.getResource("/img/Excluir Contato.png")));
		btnExcluir.setBounds(308, 502, 48, 48);
		getContentPane().add(btnExcluir);

		JLabel lblCep = new JLabel("CEP:");
		lblCep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCep.setBounds(427, 74, 46, 14);
		getContentPane().add(lblCep);

		JLabel lblEndereco = new JLabel("Endereço:");
		lblEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblEndereco.setBounds(427, 123, 74, 14);
		getContentPane().add(lblEndereco);

		JLabel lblNumero = new JLabel("Nº:");
		lblNumero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNumero.setBounds(600, 123, 46, 14);
		getContentPane().add(lblNumero);

		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblComplemento.setBounds(427, 184, 104, 14);
		getContentPane().add(lblComplemento);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBairro.setBounds(600, 181, 102, 14);
		getContentPane().add(lblBairro);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCidade.setBounds(427, 248, 46, 14);
		getContentPane().add(lblCidade);

		JLabel lblUF = new JLabel("UF:");
		lblUF.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUF.setBounds(604, 248, 46, 14);
		getContentPane().add(lblUF);

		txtCep = new JTextField();
		txtCep.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCep.setBounds(427, 92, 74, 26);
		getContentPane().add(txtCep);
		txtCep.setColumns(10);

		txtEndereco = new JTextField();
		txtEndereco.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtEndereco.setBounds(427, 143, 148, 26);
		getContentPane().add(txtEndereco);
		txtEndereco.setColumns(10);

		txtNumero = new JTextField();
		txtNumero.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNumero.setBounds(600, 142, 74, 26);
		getContentPane().add(txtNumero);
		txtNumero.setColumns(10);
		txtNumero.setDocument(new Validador(3));

		txtComplemento = new JTextField();
		txtComplemento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtComplemento.setBounds(424, 206, 151, 26);
		getContentPane().add(txtComplemento);
		txtComplemento.setColumns(10);
		txtComplemento.setDocument(new Validador(20));

		txtBairro = new JTextField();
		txtBairro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtBairro.setBounds(600, 206, 102, 26);
		getContentPane().add(txtBairro);
		txtBairro.setColumns(10);

		txtCidade = new JTextField();
		txtCidade.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtCidade.setBounds(427, 265, 151, 26);
		getContentPane().add(txtCidade);
		txtCidade.setColumns(10);

		JButton btnBuscarCep = new JButton("");
		btnBuscarCep.setDefaultCapable(false);
		btnBuscarCep.setBorderPainted(false);
		btnBuscarCep.setContentAreaFilled(false);
		btnBuscarCep.setIcon(new ImageIcon(Clientes.class.getResource("/img/Buscar.png")));
		btnBuscarCep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buscarCep();
			}
		});
		btnBuscarCep.setBounds(524, 64, 48, 48);
		getContentPane().add(btnBuscarCep);

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setOpaque(true);
		lblNewLabel_2.setBackground(new Color(255, 255, 255));
		lblNewLabel_2.setBounds(0, 0, 385, 488);
		getContentPane().add(lblNewLabel_2);

		cboUf = new JComboBox();
		cboUf.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cboUf.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		cboUf.setBounds(604, 264, 70, 26);
		getContentPane().add(cboUf);
		getRootPane().setDefaultButton(btnBuscarCep);

		lblNewLabel_3 = new JLabel("\r\n");
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBackground(new Color(255, 128, 255));
		lblNewLabel_3.setBounds(-3, 488, 784, 75);
		getContentPane().add(lblNewLabel_3);

	}

	private void limparCampos() {
		txtID.setText(null);
		txtNome.setText(null);
		txtFone.setText(null);
		txtEmail.setText(null);
		txtCpf.setText(null);
		txtCep.setText(null);
		txtEndereco.setText(null);
		txtBairro.setText(null);
		txtCidade.setText(null);
		txtComplemento.setText(null);
		txtNumero.setText(null);
		btnAdicionar.setEnabled(true);
		btnEditar.setEnabled(false);
		btnExcluir.setEnabled(false);
		cboUf.setSelectedItem("");

	}

	private void editarContato() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o nome");
			txtNome.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o fone do contato");
			txtFone.requestFocus();
		} else if (txtCpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digita o CPF");
			txtCpf.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o seu CEP");
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Digite o seu Número");
		} else {

			String upadate = "update clientes set nome=?, fone=?, email=?, cpf=?, cep=?, endereco=?, numero=?, complemento=?, bairro=?, cidade=?, uf=? where idcli=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(upadate);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtCpf.getText());
				pst.setString(5, txtCep.getText());
				pst.setString(6, txtEndereco.getText());
				pst.setString(7, txtNumero.getText());
				pst.setString(8, txtComplemento.getText());
				pst.setString(9, txtBairro.getText());
				pst.setString(10, txtCidade.getText());
				pst.setString(11, cboUf.getSelectedItem().toString());
				pst.setString(12, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do Cliente editados com sucesso");

				limparCampos();

				con.close();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Usuário não adicionado.\nEste Telefone/cpf já está sendo utilizado.");
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void adicionar() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o nome do Cliente");
			txtNome.requestFocus();
		} else if (txtCpf.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Cpf do Cliente");
			txtCpf.requestFocus();
		} else if (txtFone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Telefone do Cliente");
			txtFone.requestFocus();
		} else if (txtCep.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o CEP do Cliente");
		} else if (txtEndereco.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Endereço do Cliente");
		} else if (txtNumero.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Número do Cliente");
		} else if (txtBairro.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o Nùmero do Cliente");
		} else if (txtCidade.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preenca a Cidade do Cliente");
		} else {

			String create = "insert into clientes (nome,fone,email,cpf,cep,endereco,numero,complemento,bairro,cidade,uf) value (?, ?, ?, ?, ? ,? ,? , ?, ?, ?, ?)";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtFone.getText());
				pst.setString(3, txtEmail.getText());
				pst.setString(4, txtCpf.getText());
				pst.setString(5, txtCep.getText());
				pst.setString(6, txtEndereco.getText());
				pst.setString(7, txtNumero.getText());
				pst.setString(8, txtComplemento.getText());
				pst.setString(9, txtBairro.getText());
				pst.setString(10, txtCidade.getText());
				pst.setString(11, cboUf.getSelectedItem().toString());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Cliente adicionado");
				limparCampos();

			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null, "Usuário não adicionado.\nEste CPF já está sendo utilizado.");
				txtCpf.setText(null);
				txtCpf.requestFocus();
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void excluirClientes() {

		if (txtNome.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "SEM IDENTIFICAÇÃO -> Preencha Nome do Cliente ");
		}

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste Cliente ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {

			String delete = "delete from clientes where idcli=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(delete);

				pst.setString(1, txtID.getText());

				pst.executeUpdate();
				limparCampos();

				JOptionPane.showMessageDialog(null, " Cliente excluido");

				con.close();
			} catch (java.sql.SQLIntegrityConstraintViolationException e1) {
				JOptionPane.showMessageDialog(null,
						"Cliente não excluido. \nEste cliente ainda tem um serviço pendente");
			} catch (Exception e2) {
				System.out.println(e2);
			}
		}
	}

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = txtCep.getText();
		try {
			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();
			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {
				Element element = it.next();
				if (element.getQualifiedName().equals("cidade")) {
					txtCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("bairro")) {
					txtBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					cboUf.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					txtEndereco.setText(element.getText());
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						System.out.println("OK");
					} else {
						JOptionPane.showMessageDialog(null, "CEP não encontrado");
					}
				}
			}
			txtEndereco.setText(tipoLogradouro + " " + logradouro);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void listarClientes() {

		DefaultListModel<String> modelo = new DefaultListModel<>();

		listClientes.setModel(modelo);

		String readLista = "select* from clientes where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {

			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			while (rs.next()) {

				scrollPaneClientes.setVisible(true);

				modelo.addElement(rs.getString(2));

				if (txtNome.getText().isEmpty()) {
					scrollPaneClientes.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarClientesLista() {

		int linha = listClientes.getSelectedIndex();
		if (linha >= 0) {

			String readListaUsuario = "select * from clientes where nome like '" + txtNome.getText() + "%'"
					+ "order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {

					scrollPaneClientes.setVisible(false);

					txtID.setText(rs.getString(1));
					txtNome.setText(rs.getString(2));
					txtFone.setText(rs.getString(3));
					txtEmail.setText(rs.getString(4));
					txtCpf.setText(rs.getString(5));
					txtCep.setText(rs.getString(6));
					txtEndereco.setText(rs.getString(7));
					txtNumero.setText(rs.getString(8));
					txtComplemento.setText(rs.getString(9));
					txtBairro.setText(rs.getString(10));
					txtCidade.setText(rs.getString(11));
					cboUf.setSelectedItem(rs.getString(12));

					btnAdicionar.setEnabled(false);
					btnEditar.setEnabled(true);
					btnExcluir.setEnabled(true);

				}
			} catch (Exception e) {
				System.out.println(e);
			}
		} else {

			scrollPaneClientes.setVisible(false);
		}
	}
}
