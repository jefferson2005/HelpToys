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

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtID;
	private JTextField txtNome;
	private JTextField txtLogin;
	private PreparedStatement pst;
	private ResultSet rs;
	private Connection con;
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
	private JLabel lblNewLabel_5;
	private JLabel lblNewLabel_6;

	public static void main(String[] args) {
		try {
			Usuarios dialog = new Usuarios();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuarios() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Usuarios.class.getResource("/img/users.png")));
		setTitle("Usuarios");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				scrollPaneUsers.setVisible(false);
			}
		});
		contentPanel.setForeground(new Color(255, 255, 255));
		contentPanel.setBorder(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		scrollPaneUsers.setVisible(false);
		scrollPaneUsers.setBounds(257, 236, 214, 62);
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
		lblNewLabel.setBounds(203, 138, 46, 14);
		contentPanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(201, 217, 46, 14);
		contentPanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Login:");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(202, 176, 46, 23);
		contentPanel.add(lblNewLabel_2);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSenha.setBounds(202, 258, 46, 14);
		contentPanel.add(lblSenha);

		txtID = new JTextField();
		txtID.setSelectionColor(new Color(0, 0, 0));
		txtID.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtID.setCaretColor(new Color(0, 0, 0));
		txtID.setSelectedTextColor(new Color(0, 0, 0));
		txtID.setForeground(new Color(0, 0, 0));
		txtID.setEditable(false);
		txtID.setBounds(259, 135, 86, 20);
		contentPanel.add(txtID);
		txtID.setColumns(10);

		txtNome = new JTextField();
		txtNome.setForeground(new Color(0, 0, 0));
		txtNome.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {

			}

			@Override
			public void keyReleased(KeyEvent e) {

				listarUsuarios();
			}
		});
		txtNome.setBounds(257, 214, 214, 26);
		contentPanel.add(txtNome);
		txtNome.setColumns(10);
		txtNome.setDocument(new Validador(50));

		txtLogin = new JTextField();
		txtLogin.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtLogin.setCaretColor(new Color(0, 0, 0));
		txtLogin.setForeground(new Color(0, 0, 0));
		txtLogin.setBounds(258, 173, 214, 26);
		contentPanel.add(txtLogin);
		txtLogin.setColumns(10);

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
		btnPesquisar.setBounds(495, 153, 48, 48);
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
		lblNewLabel_3.setBackground(new Color(255, 128, 255));
		lblNewLabel_3.setOpaque(true);
		lblNewLabel_3.setBounds(0, 486, 784, 75);
		contentPanel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Perfil:");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_4.setBounds(457, 307, 46, 14);
		contentPanel.add(lblNewLabel_4);

		cboPerfil = new JComboBox();
		cboPerfil.setModel(new DefaultComboBoxModel(new String[] { "", "admin", "user" }));
		cboPerfil.setBounds(502, 300, 70, 29);
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
		chckSenha.setBounds(191, 306, 118, 23);
		contentPanel.add(chckSenha);

		txtSenha = new JPasswordField();
		txtSenha.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		txtSenha.setBounds(258, 258, 214, 26);
		contentPanel.add(txtSenha);

		lblNewLabel_5 = new JLabel("\r\n");
		lblNewLabel_5.setOpaque(true);
		lblNewLabel_5.setBackground(new Color(255, 128, 255));
		lblNewLabel_5.setBounds(0, 0, 100, 490);
		contentPanel.add(lblNewLabel_5);

		lblNewLabel_6 = new JLabel("\r\n");
		lblNewLabel_6.setOpaque(true);
		lblNewLabel_6.setBackground(new Color(255, 128, 255));
		lblNewLabel_6.setBounds(684, 0, 100, 490);
		contentPanel.add(lblNewLabel_6);
	}

	private void buscar() {

		String read = "select * from usuarios where login = ?";

		try {

			con = dao.conectar();

			pst = con.prepareStatement(read);
			pst.setString(1, txtLogin.getText());
			rs = pst.executeQuery();

			if (rs.next()) {
				txtID.setText(rs.getString(1));
				txtNome.setText(rs.getString(2));
				txtLogin.setText(rs.getString(3));
				txtSenha.setText(rs.getString(4));
				cboPerfil.setSelectedItem(rs.getString(5));

				btnEditar.setEnabled(true);
				btnExcluir.setEnabled(true);
				btnPesquisar.setEnabled(false);

			} else {

				JOptionPane.showMessageDialog(null, "Usuarío Inexistente");
				btnAdicionar.setEnabled(true);
				btnPesquisar.setEnabled(false);
			}

		} catch (Exception e) {
			System.out.print(e);
		}
	}

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
	}

	private void adicionar() {

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

			String create = "insert into usuarios (nome,login,senha,perfil) values (?,?,md5(?),?)";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(create);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Usuário adicionado");
				limparcampos();

			} catch (Exception e) {
				System.out.println(e);
			}
		}

	}

	private void editarUsuario() {

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

			String update = "update usuarios set nome=?, login=?, senha=md5(?), perfil=? where id=?";

			try {

				con = dao.conectar();
				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, txtSenha.getText());
				pst.setString(4, cboPerfil.getSelectedItem().toString());
				pst.setString(5, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do Usuário editados com sucesso");

				limparcampos();
				txtSenha.setBackground(Color.white);
				chckSenha.setSelected(false);

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void editarUsuarioExcetosenha() {

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

			String update = "update usuarios set nome=?, login=?, perfil = ? where id=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(update);
				pst.setString(1, txtNome.getText());
				pst.setString(2, txtLogin.getText());
				pst.setString(3, cboPerfil.getSelectedItem().toString());
				pst.setString(4, txtID.getText());

				pst.executeUpdate();

				JOptionPane.showMessageDialog(null, "Dados do Usuário editados com sucesso");

				limparcampos();

				con.close();

			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void excluirUsuarios() {

		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste usuarios ?", "Atenção !",
				JOptionPane.YES_NO_OPTION);
		if (confirma == JOptionPane.YES_NO_OPTION) {

			String delete = "delete from usuarios where id=?";

			try {

				con = dao.conectar();

				pst = con.prepareStatement(delete);

				pst.setString(1, txtID.getText());

				pst.executeUpdate();

				limparcampos();

				JOptionPane.showMessageDialog(null, " usuario excluido");

				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void listarUsuarios() {

		DefaultListModel<String> modelo = new DefaultListModel<>();

		listUsers.setModel(modelo);

		String readLista = "select* from usuarios where nome like '" + txtNome.getText() + "%'" + "order by nome";
		try {

			con = dao.conectar();

			pst = con.prepareStatement(readLista);

			rs = pst.executeQuery();

			while (rs.next()) {

				scrollPaneUsers.setVisible(true);

				modelo.addElement(rs.getString(2));

				if (txtNome.getText().isEmpty()) {
					scrollPaneUsers.setVisible(false);
				}
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void buscarUsuarioLista() {

		int linha = listUsers.getSelectedIndex();
		if (linha >= 0) {

			String readListaUsuario = "select * from usuarios where nome like '" + txtNome.getText() + "%'"
					+ "order by nome";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(readListaUsuario);
				rs = pst.executeQuery();
				if (rs.next()) {

					scrollPaneUsers.setVisible(false);

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

			scrollPaneUsers.setVisible(false);

		}
	}
}