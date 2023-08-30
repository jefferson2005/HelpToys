package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import model.DAO;

public class Login extends JFrame {

	// objetos JDBC

	DAO dao = new DAO();
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	// objeto tela pricipal
	Principal principal = new Principal();

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtLogins;
	private JPasswordField txtSenha;
	private JLabel lblStatus;
	private JLabel lblNewLabel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				status();
			}
		});
		setTitle("InfoX - Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 201);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblStatus = new JLabel("");
		lblStatus.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
			}

			public void ancestorMoved(AncestorEvent event) {
			}

			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		lblStatus.setIcon(new ImageIcon(Login.class.getResource("/img/9069340_database_fail_icon.png")));
		lblStatus.setBounds(376, 114, 48, 48);
		contentPane.add(lblStatus);

		JLabel lblLogins = new JLabel("Login:");
		lblLogins.setBounds(27, 22, 46, 14);
		contentPane.add(lblLogins);

		txtLogins = new JTextField();
		txtLogins.setBounds(110, 19, 223, 20);
		contentPane.add(txtLogins);
		txtLogins.setColumns(10);

		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setBounds(27, 62, 46, 14);
		contentPane.add(lblSenha);

		JButton btnAcessar = new JButton("");
		btnAcessar.setBorderPainted(false);
		btnAcessar.setContentAreaFilled(false);
		btnAcessar.setToolTipText("Acessar");
		btnAcessar.setIcon(new ImageIcon(Login.class.getResource("/img/9071220_enter_key_icon.png")));
		btnAcessar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				logar();
			}
		});
		btnAcessar.setBounds(376, 22, 48, 48);
		contentPane.add(btnAcessar);
		txtSenha = new JPasswordField();
		txtSenha.setBounds(110, 59, 223, 20);
		contentPane.add(txtSenha);

		getRootPane().setDefaultButton(btnAcessar);
		
		lblNewLabel = new JLabel("\r\n");
		lblNewLabel.setOpaque(true);
		lblNewLabel.setBackground(new Color(255, 128, 255));
		lblNewLabel.setBounds(0, 111, 434, 51);
		contentPane.add(lblNewLabel);

	}// Fim do construtor

	/**
	 * Método para autenticar um usuário
	 */

	private void logar() {

		// Criar uma variável para capturar a senha
		String capturaSenha = new String(txtSenha.getPassword());

		// validação

		if (txtLogins.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o login");
			txtLogins.requestFocus();
		} else if (capturaSenha.length() == 0) {
			JOptionPane.showMessageDialog(null, "Preencha a senha");
			txtSenha.requestFocus();
		} else {

			// Lógica pricipal
			String read = "select * from usuarios where login=? and senha=md5(?)";
			try {
				con = dao.conectar();
				pst = con.prepareStatement(read);
				pst.setString(1, txtLogins.getText());
				pst.setString(2, capturaSenha);
				rs = pst.executeQuery();
				if (rs.next()) {
					// capturar o perfil do usuario
					// System.out.println(rs.getString(5));//apoio a lógica
					// Tratamento do perfil do usuário
					String perfil = rs.getString(5);
					if (perfil.equals("admin")) {
						// logar -> acessar a tela principal
						principal.setVisible(true);
						// setar a tabela da tela principal com o nome do usuario
						principal.lblUsuario.setText(rs.getString(2));
						// habilitar os botões
						principal.btnRelatorio.setEnabled(true);
						principal.bntUsuarios.setEnabled(true);
						// mudar a cor do rodapé
						principal.lblRodape.setBackground(Color.MAGENTA);
						// fechar a tela de login(está tela)
						this.dispose();
					} else {// perfil for diferente de admin
							// logar -> acessar a tela principal
						principal.setVisible(true);
						// setar a label da tela principal com o nome do usuario
						principal.lblUsuario.setText(rs.getString(2));
						// fechar a tela de login(estela tela)
						this.dispose();
					}
				} else {
					JOptionPane.showMessageDialog(null, "Usuário e/ou senha invalido(s)");
				}
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void status() {
		try {
			// abrir a conexão
			con = dao.conectar();
			if (con == null) {
				// System.out.println("Erro de conexão");
				lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/9069340_database_fail_icon.png")));
			} else {
				// System.out.println("Banco conectado");
				lblStatus.setIcon(new ImageIcon(Usuarios.class.getResource("/img/9069499_database_success_icon.png")));
			}
			// NUNCA esquecer de fechar a conexão
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}// Fim do método status()
}// Fim do código